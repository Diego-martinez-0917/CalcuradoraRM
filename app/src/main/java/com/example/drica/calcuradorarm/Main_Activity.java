package com.example.drica.calcuradorarm;

import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drica.calcuradorarm.Model.DataRM;
import com.example.drica.calcuradorarm.Model.DataUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private EditText text_peso;
    private EditText text_repeti;
    private int peso;
    private int rep;


    private Button btnpiramidal;
    private Button btnprogresivo;
    private Button btnptrunco;
    private TextView txthadername;
    private TextView txthadermail;
    DataUser dataUser;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        text_peso=findViewById(R.id.txt_peso);
        text_repeti = findViewById(R.id.edt_repeticion);


        btnprogresivo =findViewById(R.id.btprogre);
        btnprogresivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    peso = Integer.parseInt(String.valueOf(text_peso.getText()));
                    rep = Integer.parseInt(String.valueOf(text_repeti.getText()));
                    enviardatos(1);
                }
            }
        });




        btnpiramidal = findViewById(R.id.btpirami);
        btnpiramidal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    peso = Integer.parseInt(String.valueOf(text_peso.getText()));
                    rep = Integer.parseInt(String.valueOf(text_repeti.getText()));
                    enviardatos(2);
                }

            }
        });

        btnptrunco =findViewById(R.id.bttrunco);
        btnptrunco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    peso = Integer.parseInt(String.valueOf(text_peso.getText()));
                    rep = Integer.parseInt(String.valueOf(text_repeti.getText()));
                    enviardatos(3);
                }
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }




    private void enviardatos(int est) {


        Log.e("mitag",peso+" "+rep);
        Intent intent = new Intent(Main_Activity.this,graficosActivity.class);
        intent.putExtra("peso",peso);
        intent.putExtra("rep",rep);
        intent.putExtra("est",est);
        startActivity(intent);




    }

    private boolean validar() {
        boolean complete=true;
        text_peso.setError(null);
        text_repeti.setError(null);

        // Store values at the time of the login attempt.
        String peso = text_peso.getText().toString();
        String repe = text_repeti.getText().toString();
        if (TextUtils.isEmpty(peso)){
            text_peso.setError(getString(R.string.error_field_required));
            complete = false;
        }
        if (TextUtils.isEmpty(repe)){
            text_repeti.setError(getString(R.string.error_field_required));
            complete = false;
        }
        return complete;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_data) {
            Intent intent = new Intent(Main_Activity.this,UpdateActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_record) {
            Intent intent = new Intent(Main_Activity.this,HistorialActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_singOut) {
            FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            Intent intent = new Intent(Main_Activity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
