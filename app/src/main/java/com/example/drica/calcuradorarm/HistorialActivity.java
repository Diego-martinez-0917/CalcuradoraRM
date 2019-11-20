package com.example.drica.calcuradorarm;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.drica.calcuradorarm.Model.DataRM;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistorialActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.reycleView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child(cosultarToken()).child("dataRM");


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataRM,ViewHolder>firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<DataRM, ViewHolder>(
                DataRM.class,R.layout.row_recycle,ViewHolder.class,databaseReference
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, DataRM model, int position) {
                int imge=0;
                if (model.getMetodo()==1){
                    imge = R.drawable.progresivo;
                }
                if (model.getMetodo()==2){
                    imge = R.drawable.piramidal;
                }
                if (model.getMetodo()==3){
                    imge = R.drawable.trunco;
                }

                viewHolder.setdetails(getApplicationContext(),model.getNombreejer(),"Peso : "+model.getPeso(),"repeticion : "+model.getRepe(),"RM : "+model.getRM(),model.getComentario(),imge);
                Log.e("mitag","peso"+ model.getPeso() );
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    private String cosultarToken() {
        String token="";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null) {
            token = firebaseUser.getUid();//obtiene el token
        }
        return token;
    }
}
