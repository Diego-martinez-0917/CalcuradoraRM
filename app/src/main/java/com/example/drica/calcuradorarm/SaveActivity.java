package com.example.drica.calcuradorarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SaveActivity extends AppCompatActivity {


    private int peso;
    private int rep;
    private int est;
    private int RM;
    private TextView txvpeso;
    private TextView txvrep;
    private TextView txvest;
    private TextView txvrm;
    private Button btnsave;
    private EditText edtejer;
    private EditText edtcomen;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txvpeso =findViewById(R.id.TextviewRowpeso);
        txvrep =findViewById(R.id.TextviewRowrep);
        txvest =findViewById(R.id.TextviewRowMetodo);
        txvrm =findViewById(R.id.TextviewRowRM);
        edtejer =findViewById(R.id.edtejercicio);
        edtcomen =findViewById(R.id.edtcomentarios);
        btnsave =findViewById(R.id.butttonsave);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnsave =findViewById(R.id.butttonsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()){
                guardardatos(optenertoken());//sube datos afirebase
                    Intent intent = new Intent(SaveActivity.this,Main_Activity.class);
                    startActivity(intent);
                }
            }
        });


    }


    private String optenertoken(){
            String token="";
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                Log.e("mitag","user ok");
                token = firebaseUser.getUid();//obtiene el token
                Log.e("mitag","token = "+token);
            }
            return token;
    }

    private void guardardatos(String tokenuser) {
        databaseReference= databaseReference.child(tokenuser).child("dataRM").push();

        databaseReference.child("peso").setValue(peso);
        databaseReference.child("repe").setValue(rep);
        databaseReference.child("RM").setValue(RM);
        databaseReference.child("metodo").setValue(est);
        databaseReference.child("nombreejer").setValue(edtejer.getText().toString());
        databaseReference.child("comentario").setValue(edtcomen.getText().toString());
    }

    private boolean validar() {
        boolean complete=true;
        edtejer.setError(null);
        edtcomen.setError(null);

        // Store values at the time of the login attempt.
        String eje = edtejer.getText().toString();
        String comen = edtcomen.getText().toString();
        if (TextUtils.isEmpty(eje)){
            edtejer.setError(getString(R.string.error_field_required));
            complete = false;
        }
        if (TextUtils.isEmpty(comen)){
            edtcomen.setError(getString(R.string.error_field_required));
            complete = false;
        }
        return complete;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle !=null){
                peso= bundle.getInt("peso");
                rep = bundle.getInt("rep");
                est = bundle.getInt("est");
                RM = bundle.getInt("rm");
            llenartabla();

        }
    }

    private void llenartabla() {
        txvpeso.setText(String.valueOf(peso));
        txvrep.setText(String.valueOf(rep));
        txvrm.setText(String.valueOf(RM));
        if (est==1){
            txvest.setText("Progresivo");
        }else if (est==2){
            txvest.setText("Piramidal");
        }else if (est==3){
            txvest.setText("trunco");
        }
    }
}
