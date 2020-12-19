package com.example.chatengine.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatengine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button login;
    FirebaseAuth Auth;
    TextView register;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPwd);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.lnkRegister);
        progressBar = findViewById(R.id.progressBar);
        Auth = FirebaseAuth.getInstance();

        if (Auth.getCurrentUser() != null){
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Already Logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), AllUsers.class));
            progressBar.setVisibility(View.GONE);
            finish();
        }else{

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String emailtxt = email.getText().toString();
                String Password = password.getText().toString();

                if (emailtxt.isEmpty()){
                    Toast.makeText(Login.this, "Please enter email...", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }else if (Password.isEmpty()){
                    Toast.makeText(Login.this, "Please enter password...", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }else{
                    Auth.signInWithEmailAndPassword(emailtxt, Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Login.this, "Login Successful..", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), AllUsers.class));
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                    }else{
                                        String exception = task.getException().toString();
                                        Toast.makeText(Login.this, exception, Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
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
            }
        });

    }
}