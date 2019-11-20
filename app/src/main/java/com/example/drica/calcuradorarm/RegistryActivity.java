package com.example.drica.calcuradorarm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistryActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText lastName;
    private EditText size;
    private EditText age;
    private EditText email;
    private EditText passwort;
    private Button visible_pass;
    private Button bt_registry;
    private String sName;
    private String slastName;
    private int sage ;
    private int ssize ;
    private String sEmail;
    private String sPasswort;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        nameEditText = findViewById(R.id.name);
        lastName = findViewById(R.id.lastname);
        size = findViewById(R.id.size);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email_registry);
        passwort = findViewById(R.id.password_registry);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        visible_pass = findViewById(R.id.visiblepass);

        visible_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoButton();
            }
        });

        bt_registry = findViewById(R.id.button_registry);
        bt_registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCuenta();
            }
        });
    }



    public boolean validarFrom() {

        sName = nameEditText.getText().toString();
        slastName = lastName.getText().toString();
        sage = Integer.parseInt(age.getText().toString());
        ssize = Integer.parseInt(size.getText().toString());
        sEmail = email.getText().toString();
        sPasswort = passwort.getText().toString();

        boolean complete = true;

        if (sName.equals("")) {
            nameEditText.setError(getString(R.string.error_field_required));
            complete = false;
        }
        if (slastName.equals("")) {
            lastName.setError(getString(R.string.error_field_required));
            complete = false;
        }
        if (age.equals("") ) {
            lastName.setError(getString(R.string.error_field_required));
            complete = false;
        }
        if (size.equals("")) {
            lastName.setError(getString(R.string.error_field_required));
            complete = false;
        }
        if (sEmail.equals("")) {
            email.setError(getString(R.string.error_field_required));
            complete = false;
        }
        if (sPasswort.equals("")) {
            passwort.setError(getString(R.string.error_field_required));
            complete = false;
        }
        return complete;
    }

    private void crearCuenta() {
        if (validarFrom()) {
            firebaseAuth.createUserWithEmailAndPassword(sEmail, sPasswort).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        if (firebaseAuth!=null){
                            Log.e("mitag","auth ok");
                            firebaseUser = firebaseAuth.getCurrentUser();

                            if (firebaseUser != null) {
                                Log.e("mitag","user ok");
                                token = firebaseUser.getUid();//obtiene el token
                                Log.e("mitag","token = "+token);
                                updatos(token);
                            }
                        }

                        else {Log.e("mitag","no tengo token");}
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegistryActivity.this, "no se fue posible crear la cuenta.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



    private void updatos(String tokenuser) {


        Log.e("mytagdiego", "token = " + tokenuser);

            databaseReference.child(tokenuser).child("datauser").child("nombre").setValue(sName);
            databaseReference.child(tokenuser).child("datauser").child("apellido").setValue(slastName);
            databaseReference.child(tokenuser).child("datauser").child("edad").setValue(sage);
            databaseReference.child(tokenuser).child("datauser").child("talla").setValue(ssize);


            Toast.makeText(RegistryActivity.this, "Cuenta Creada Exitosamente.",
                    Toast.LENGTH_SHORT).show();
        firebaseAuth.signOut();

        }

    public void estadoButton() {
        if (passwort.getInputType() == 129) {
            passwort.setInputType(145);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                visible_pass.setBackgroundDrawable(ContextCompat.getDrawable(RegistryActivity.this, R.drawable.ic_visibility_off_black_24dp));
            } else {
                visible_pass.setBackground(ContextCompat.getDrawable(RegistryActivity.this, R.drawable.ic_visibility_off_black_24dp));
            }

        } else if (passwort.getInputType() == 145) {
            passwort.setInputType(129);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                visible_pass.setBackgroundDrawable(ContextCompat.getDrawable(RegistryActivity.this, R.drawable.ic_visibility_black_24dp));
            } else {
                visible_pass.setBackground(ContextCompat.getDrawable(RegistryActivity.this, R.drawable.ic_visibility_black_24dp));
            }
        }
    }
}