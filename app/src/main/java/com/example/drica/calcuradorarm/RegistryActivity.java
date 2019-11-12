package com.example.drica.calcuradorarm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegistryActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText lastName;
    private EditText email;
    private EditText passwort;
    private Button visible_pass;
    private Button bt_registry;
    private String sName;
    private String slastName;
    private String sEmail;
    private String sPasswort;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        nameEditText = findViewById(R.id.name);
        lastName = findViewById(R.id.lastname);
        email = findViewById(R.id.email_registry);
        passwort = findViewById(R.id.password_registry);
        firebaseAuth = FirebaseAuth.getInstance();

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
        sEmail = email.getText().toString();
        sPasswort = passwort.getText().toString();

        boolean complete = true;

        if (sName.equals("")) {
            nameEditText.setError(getString(R.string.error_field_required));
            complete = true;
        }
        if (slastName.equals("")) {
            lastName.setError(getString(R.string.error_field_required));
            complete = true;
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
        if (!validarFrom()) {
            return;
        } else {
            firebaseAuth.createUserWithEmailAndPassword(sEmail, sPasswort).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        firebaseUser = firebaseAuth.getCurrentUser();
                        addname();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegistryActivity.this, "Creacion de cuenta fallida.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void addname() {
        String nombre = sName + " " + slastName;
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nombre)
                .build();
        firebaseUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    firebaseAuth.signOut();
                    Toast.makeText(RegistryActivity.this, "Cuenta Creada Exitosamente.",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistryActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    // If sign in fails, display a message to the user.
                    Toast.makeText(RegistryActivity.this, "Asignacion de datos fallida.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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