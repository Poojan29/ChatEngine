package com.example.chatengine.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatengine.Models.Users;
import com.example.chatengine.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {

    TextInputEditText email,password,username;
    TextView logintxt;
    MaterialButton registor;
    CircleImageView circleImageView;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String uid;
    Uri selectedImageUri, downloadUri;
    Task<Uri> downloadUrl;

    private static final int USER_PHOTO_PICKER =  1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.RtxtEmail);
        password = findViewById(R.id.RtxtPwd);
        username = findViewById(R.id.RtxtName);

        registor = findViewById(R.id.btnRegistor);
        logintxt = findViewById(R.id.lnkLogin);
        progressBar = findViewById(R.id.progressBar);
        circleImageView = findViewById(R.id.userProfilePic);

//        username.setError("Username will be visible to other contacts");

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("chat_photos");

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPhoto();
            }
        });

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        registor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registor.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                final String emailname = email.getText().toString();
                final String user = username.getText().toString();
                String pass = password.getText().toString();
                //String uid = databaseReference

                if(emailname.isEmpty()){
                    Toast.makeText(Register.this, "Please enter your E-mail.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
                else if (user.isEmpty()){
                    Toast.makeText(Register.this, "Please enter your Fullname.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
                else if (pass.isEmpty()){
                    Toast.makeText(Register.this, "Please enter your Password.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }else{

                    mAuth.createUserWithEmailAndPassword(emailname, pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                Users users;
                                uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                if (selectedImageUri == null){
                                    users = new Users(user, emailname, uid);
                                }else{
                                    users = new Users(user, emailname, uid, downloadUri.toString());
                                }

                                databaseReference
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Register.this, "Successful Register...", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), AllUsers.class));
                                    progressBar.setVisibility(View.GONE);
                                    finish();
                                }
                            });

                            }else{
                                String exe = task.getException().toString();
                                Toast.makeText(Register.this, exe, Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                registor.setEnabled(true);
                            }
                        }
                    });
                }

            }
        });


    }



    private void SelectPhoto() {
        //for pick an image from local storage.
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent, USER_PHOTO_PICKER);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == USER_PHOTO_PICKER && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            selectedImageUri = data.getData();
            Picasso.get().load(selectedImageUri).into(circleImageView);
            uploadPhoto();
        }
    }
    private void uploadPhoto() {

        final StorageReference photoRef = storageReference.child(String.valueOf(System.currentTimeMillis()));

        photoRef.putFile(selectedImageUri)
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return photoRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();
                } else {
                    Toast.makeText(Register.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}