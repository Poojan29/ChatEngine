package com.example.chatengine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText email,password,username;
    TextView logintxt;
    Button registor;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPwd);
        username = findViewById(R.id.txtName);
        registor = findViewById(R.id.btnRegistor);
        logintxt = findViewById(R.id.lnkLogin);

        username.setError("Username will be visible to other contacts");

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        registor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailname = email.getText().toString();
                final String user = username.getText().toString();
                String pass = password.getText().toString();
                //String uid = databaseReference

                if (emailname.isEmpty()){
                    Toast.makeText(Register.this, "Please enter your E-mail.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (user.isEmpty()){
                    Toast.makeText(Register.this, "Please enter your Fullname.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.isEmpty()){
                    Toast.makeText(Register.this, "Please enter your Password.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mAuth.createUserWithEmailAndPassword(emailname, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


                            Users users = new Users(user, emailname, uid);
                            databaseReference
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Register.this, "Successful Register...", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), AllUsers.class));
                                    finish();
                                }
                            });

                            }else{
                                Toast.makeText(Register.this, "Error..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


    }
}