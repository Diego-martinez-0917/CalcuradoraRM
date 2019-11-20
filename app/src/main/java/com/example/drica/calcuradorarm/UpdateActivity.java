package com.example.drica.calcuradorarm;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v13.view.DragStartHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drica.calcuradorarm.Model.DataUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText lastName;
    private EditText size;
    private EditText age;
    private Button update;
    private String sName;
    private String slastName;
    private int sage ;
    private int ssize ;

    private DataUser dataUser;

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        nameEditText = findViewById(R.id.name);
        lastName = findViewById(R.id.lastname);
        size = findViewById(R.id.age);
        age = findViewById(R.id.size);

        dataUser = new DataUser();




        databaseReference = FirebaseDatabase.getInstance().getReference();

        update=findViewById(R.id.button_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatefire(cosultarToken());
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();
        updatedata(cosultarToken());
    }
    //consulta el token del usuario devuelve un string
    private String cosultarToken() {
        String token="";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null) {
            token = firebaseUser.getUid();//obtiene el token
        }
        return token;
    }

    //actualiza datos en firebase
    private void updatefire(String token) {
            if (valid()){

                databaseReference.child(token).child("datauser").child("nombre").setValue(sName);
                databaseReference.child(token).child("datauser").child("apellido").setValue(slastName);
                databaseReference.child(token).child("datauser").child("edad").setValue(sage);
                databaseReference.child(token).child("datauser").child("talla").setValue(ssize);

                updatedata(token);


                Toast.makeText(UpdateActivity.this, "Datos Actualizados Exitosamente.",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UpdateActivity.this, Main_Activity.class);
                startActivity(intent);
                finish();
        }
    }

    //sincroniza datauser con firebase
    private void updatedata(String token) {
        Log.e("mitag","token "+token);
       databaseReference.child(token).child("datauser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataUser = dataSnapshot.getValue(DataUser.class);
                Log.e("mitag","nombredata "+dataUser.getNombre());
                escribirdata();
            }



           @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateActivity.this, "existe un error al leer los datos",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    //escribe datos en el activiti
    private void escribirdata() {

        nameEditText.setText(dataUser.getNombre());
        lastName.setText(dataUser.getApellido());
        age.setText(String.valueOf(dataUser.getEdad()));
        size.setText(String.valueOf(dataUser.getTalla()));

    }

    //valida campos
    private boolean valid() {
                sName = nameEditText.getText().toString();
                slastName = lastName.getText().toString();
                sage = Integer.parseInt(age.getText().toString());
                ssize = Integer.parseInt(size.getText().toString());


                boolean complete = true;

                if (sName.equals("")) {
                    nameEditText.setError(getString(R.string.error_field_required));
                    complete = false;
                }
                if (slastName.equals("")) {
                    lastName.setError(getString(R.string.error_field_required));
                    complete = false;
                }
                if (age.equals("")) {
                    lastName.setError(getString(R.string.error_field_required));
                    complete = false;
                }
                if (size.equals("")) {
                    lastName.setError(getString(R.string.error_field_required));
                    complete = false;
                }
                return complete;
            }
}


