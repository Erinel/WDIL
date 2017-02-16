package agalvezmarco.wdil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        verificarLogin();

    }

    @Override
    public void onStop() {
        guardarDatosUsuario();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        guardarDatosUsuario();
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout: {
                guardarDatosUsuario();
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
                return true;
            }
            case R.id.action_exit: {
                guardarDatosUsuario();
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void guardarDatosUsuario() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").child(uid).child("series").setValue(Usuario.getUsuario().getSeries());
            mDatabase.child("users").child(uid).child("mangas").setValue(Usuario.getUsuario().getMangas());
            mDatabase.child("users").child(uid).child("libros").setValue(Usuario.getUsuario().getLibros());

        }
    }

    private void verificarLogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else cargarDatosUsuario();
    }

    public void cargarDatosUsuario() {
        dialog = ProgressDialog.show(MainActivity.this, "",
                "Loading. Please wait...", true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("Usuario", user.getEmail());
        if (user != null) {
            String uid = user.getUid();
            mDatabase.child("users").child(uid).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Usuario.getUsuario().setSeries(cargarSeries(dataSnapshot));
                            Usuario.getUsuario().setMangas(cargarMangas(dataSnapshot));
                            Usuario.getUsuario().setLibros(cargarLibros(dataSnapshot));
                            dialog.dismiss();

                            cargarInterfaz();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

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
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);


        viewPager.setAdapter(new AdaptadorPager(getSupportFragmentManager()));

        viewPager.setPageTransformer(true, new DepthPageTransformer());
        tabLayout = (TabLayout) findViewById(R.id.tabsBar);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

    }
}
