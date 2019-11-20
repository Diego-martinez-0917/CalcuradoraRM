package com.example.drica.calcuradorarm;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainTable extends AppCompatActivity {
    private Button calcular;
    private EditText text_peso;
    private EditText text_repeticiones;
    private TableLayout tableLayout;
    private TextView row1;
    private TextView row2;
    private TextView row3;
    private TextView row4;
    private TextView row5;
    private TextView row6;
    private TextView row7;
    private TextView row8;
    private TextView rm;
    private int peso;
    private int rep;
    private int est;
    private int RM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        text_peso=findViewById(R.id.txt_peso);
        text_repeticiones = findViewById(R.id.edt_repeticion);
        row1 = findViewById(R.id.TextviewRow1);
        row2 = findViewById(R.id.TextviewRow2);
        row3 = findViewById(R.id.TextviewRow3);
        row4 = findViewById(R.id.TextviewRow4);
        row5 = findViewById(R.id.TextviewRow5);
        row6 = findViewById(R.id.TextviewRow6);
        row7 = findViewById(R.id.TextviewRow7);
        row8 = findViewById(R.id.TextviewRow8);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle!=null){
            peso= bundle.getInt("peso");
            rep = bundle.getInt("rep");
            est = bundle.getInt("est");
            RM = bundle.getInt("rm");
            gen_tabla();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTable.this,SaveActivity.class);
                intent.putExtra("peso",peso);
                intent.putExtra("rep",rep);
                intent.putExtra("est",est);
                intent.putExtra("rm",RM);
                startActivity(intent);
            }
        });




        /*calcular=findViewById(R.id.cal);
        calcular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                peso = Integer.parseInt(String.valueOf(text_peso.getText()));
                rep = Integer.parseInt(String.valueOf(text_repeticiones.getText()));
                RM = (int) (peso/(1.0278-(0.0278*rep)));
                rm.setVisibility(View.VISIBLE);
                rm.setText(String.valueOf("RM segun Brzycki = "+RM));
                gen_tabla();



            }

        });*/
    }
    public void gen_tabla(){
        row1.setText(String.valueOf(Math.round(RM*0.80)));
        row2.setText(String.valueOf(Math.round(RM)));
        row3.setText(String.valueOf(Math.round(RM*0.70)));
        row4.setText(String.valueOf(Math.round((RM*0.95))));
        row5.setText(String.valueOf(Math.round((RM*0.60))));
        row6.setText(String.valueOf(Math.round((RM*0.80))));
        row7.setText(String.valueOf(Math.round((RM*0.40))));
        row8.setText(String.valueOf(Math.round((RM*0.60))));
    }
}
