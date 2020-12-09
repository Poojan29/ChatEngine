package com.example.chatengine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button login;
    FirebaseAuth Auth;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPwd);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.lnkRegister);
        Auth = FirebaseAuth.getInstance();

        if (Auth.getCurrentUser() != null){
            Toast.makeText(this, "Already Logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), AllUsers.class));
            finish();
        }else{

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtxt = email.getText().toString();
                String Password = password.getText().toString();

                if (emailtxt.isEmpty()){
                    Toast.makeText(Login.this, "Please enter email...", Toast.LENGTH_SHORT).show();
                }else if (Password.isEmpty()){
                    Toast.makeText(Login.this, "Please enter password...", Toast.LENGTH_SHORT).show();
                }else{
                    Auth.signInWithEmailAndPassword(emailtxt, Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Login.this, "Login Successful..", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), AllUsers.class));
                                        finish();
                                    }else{
                                        Toast.makeText(Login.this, "Error...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }
        });

    }
}