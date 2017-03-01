package agalvezmarco.wdil;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText email;
    private EditText pass;
    private Button login;
    private Button signIn;
    private String emailS;
    private String nickS;
    private String passS;
    private boolean puedoGuardar = true;
    private static final String TAG = "firebase";
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        signIn = (Button) findViewById(R.id.signUp);

        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setTypeface(Typeface.createFromAsset(getAssets(), "Courgette-Regular.ttf"));

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    cargarDatosUsuario();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String password = pass.getText().toString();
                if (mail.isEmpty() || password.isEmpty()) {
                    return;
                }
                signIn(mail, password);

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearDialogoNuevoUsuario().show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    public void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Ha fallado la autenticacion.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = user.getUid();
                            mDatabase.child("users").child(uid).setValue(user);
                            mDatabase.child("users").child(uid).child("nick").setValue(nickS);

                        }

                        // ...
                    }
                });
    }

    public void signIn(String mail, String password) {

        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(getApplicationContext(), "Ha fallado la autenticacion.",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                        }
                    }

                });
    }

    private AlertDialog crearDialogoNuevoUsuario() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.dialognuevousuario, null);

        final EditText nick = (EditText) v.findViewById(R.id.nick);
        final EditText email = (EditText) v.findViewById(R.id.emailNuevo);
        final EditText contraseña = (EditText) v.findViewById(R.id.nuevaContraseña);
        builder.setView(v);

        builder.setPositiveButton("Crear!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (!(nick.getText() == null) && !(nick.getText().toString().equals("")))
                    nickS = (nick.getText().toString());

                else {
                    Toast.makeText(getApplicationContext(), "No has introducido nick, saliendo.", Toast.LENGTH_LONG).show();
                    puedoGuardar = false;
                    dialog.dismiss();
                }

                if (!(email.getText() == null) && !(email.getText().toString().equals("")))
                    emailS = email.getText().toString();

                else {
                    Toast.makeText(getApplicationContext(), "No has introducido email, saliendo.", Toast.LENGTH_LONG).show();
                    puedoGuardar = false;
                    dialog.dismiss();
                }

                if (!(contraseña.getText() == null) && (!contraseña.getText().toString().equals("")))
                    passS = contraseña.getText().toString();

                else {
                    Toast.makeText(getApplicationContext(), "No has introducido contraseña, saliendo.", Toast.LENGTH_LONG).show();
                    puedoGuardar = false;
                    dialog.dismiss();
                }

                if (puedoGuardar) {
                    createAccount(emailS, passS);
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancelar.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    public void cargarDatosUsuario() {

        mostrarDialog();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("Usuario", user.getEmail());
        if (user != null)
        {
            String uid = user.getUid();
            mDatabase.child("users").child(uid).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Usuario.getUsuario().setSeries(cargarSeries(dataSnapshot));
                            Usuario.getUsuario().setMangas(cargarMangas(dataSnapshot));
                            Usuario.getUsuario().setLibros(cargarLibros(dataSnapshot));
                            dismissDialog();

                            cargarInterfaz();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "getUser:onCancelled", databaseError.toException());

                        }

                    });

        }

    }

    private ArrayList<Serie> cargarSeries(DataSnapshot ds) {
        ArrayList<Serie> series = new ArrayList<>();

        for (DataSnapshot d : ds.child("series").getChildren()) {
            Serie aux;
            aux = d.getValue(Serie.class);
            series.add(aux);
        }
        return series;
    }

    private ArrayList<Manga> cargarMangas(DataSnapshot ds) {
        ArrayList<Manga> ret = new ArrayList<>();
        for (DataSnapshot d : ds.child("mangas").getChildren()) {
            Manga aux;
            aux = d.getValue(Manga.class);
            ret.add(aux);
        }
        return ret;
    }

    private ArrayList<Libro> cargarLibros(DataSnapshot ds) {
        ArrayList<Libro> ret = new ArrayList<>();
        for (DataSnapshot d : ds.child("libros").getChildren()) {
            Libro aux;
            aux = d.getValue(Libro.class);
            ret.add(aux);
        }
        return ret;
    }

    private void cargarInterfaz() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void mostrarDialog() {
        if(!this.isFinishing())
        progress = ProgressDialog.show(LoginActivity.this, "",
                "Loading. Please wait...", true);
    }

    private void dismissDialog() {
        progress.dismiss();
    }

}