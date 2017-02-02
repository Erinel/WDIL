package agalvezmarco.wdil;

import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);


        viewPager.setAdapter(new AdaptadorPager(getSupportFragmentManager()));

        viewPager.setPageTransformer(true, new DepthPageTransformer());
        tabLayout = (TabLayout) findViewById(R.id.tabsBar);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    private void guardarDatosUsuario() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            String uid = user.getUid();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").child(uid).child("series").setValue(Usuario.getUsuario().getSeries());
            mDatabase.child("users").child(uid).child("mangas").setValue(Usuario.getUsuario().getMangas());
            mDatabase.child("users").child(uid).child("libros").setValue(Usuario.getUsuario().getLibros());

        }
        FirebaseAuth.getInstance().signOut();
    }
}
