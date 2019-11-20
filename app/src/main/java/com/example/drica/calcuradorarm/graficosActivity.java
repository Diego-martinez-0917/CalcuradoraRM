package com.example.drica.calcuradorarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class graficosActivity extends AppCompatActivity {
    private int peso;
    private int rep;
    private int RM;
    private TextView txvpeso;
    private TextView txvrep;
    private TextView txvrm;
    private int est;
    private LinearLayout linearLayoutpiramidal;
    private LinearLayout linearLayoutprogresivo;
    private LinearLayout linearLayouttrunco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txvpeso = findViewById(R.id.TextviewRowpeso);
        txvrep = findViewById(R.id.Textviewrep);
        txvrm  = findViewById(R.id.TextviewRowRM);
        linearLayoutpiramidal = findViewById(R.id.lnpiramidal);
        linearLayoutprogresivo = findViewById(R.id.prograsivo);
        linearLayouttrunco =findViewById(R.id.trunco);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(graficosActivity.this,MainTable.class);
                intent.putExtra("peso",peso);
                intent.putExtra("rep",rep);
                intent.putExtra("est",est);
                intent.putExtra("rm",RM);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recibirdatos();
        mostrargrafi();
        Scribirdatos();
    }

    private void Scribirdatos() {
        RM = (int) (peso/(1.0278-(0.0278*rep)));//calculo de rm segun Brzycki
        txvrm.setText(String.valueOf(RM));
        txvrep.setText(String.valueOf(rep));
        txvpeso.setText(String.valueOf(peso));
    }

    private void mostrargrafi() {
        if (est == 1){
            linearLayoutprogresivo.setVisibility(View.VISIBLE);
        }
        else if (est == 2){
            linearLayoutpiramidal.setVisibility(View.VISIBLE);
        }else if (est ==3){
            linearLayouttrunco.setVisibility(View.VISIBLE);
        }
    }

    private void recibirdatos() {
        Bundle bundle = this.getIntent().getExtras();
        if (bundle!=null) {
            peso = bundle.getInt("peso");
            rep = bundle.getInt("rep");
            est = bundle.getInt("est");
        }
    }
}

