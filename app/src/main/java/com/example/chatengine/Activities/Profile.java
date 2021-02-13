package com.example.chatengine.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatengine.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    TextInputEditText textInputEditTextname, textInputEditTextemail;
    CircleImageView circleImageView;
    MaterialButton editbutton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String uid, name, email, photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textInputEditTextname = findViewById(R.id.Name);
        textInputEditTextemail = findViewById(R.id.Email);
        circleImageView = findViewById(R.id.userprofile);
//        materialButton = findViewById(R.id.update);
        editbutton = findViewById(R.id.editbtn);

        textInputEditTextname.setFocusable(false);
        textInputEditTextemail.setFocusable(false);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("username").getValue(String.class);
                email = snapshot.child("email").getValue(String.class);

                Log.d("QWER", name);

                if (snapshot.child("photourl").exists()){
                    photo = snapshot.child("photourl").getValue(String.class);

                    Picasso.get().load(photo).fit().centerCrop().into(circleImageView);

                }else {
                    Picasso.get().load(R.drawable.night).fit().centerCrop().into(circleImageView);
                }

                textInputEditTextname.setText(name);
                textInputEditTextemail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();

            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Profile.this);
        LayoutInflater inflater =getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog , null);
        alert.setView(dialog);
        alert.setTitle(name);
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();// show the dialog
        alertDialog.setCancelable(false);// does not close dialog when outside of dialog clicked(touch)

        final TextInputEditText username = dialog.findViewById(R.id.usertxt);
        final TextInputEditText emaildata = dialog.findViewById(R.id.emailtxt);
        MaterialButton update = dialog.findViewById(R.id.updated);
        MaterialButton cancel = dialog.findViewById(R.id.cancel);

        username.setText(name);
        emaildata.setText(email);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();// dismiss dialog
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.equals(username.getText().toString()) && !email.equals(emaildata.getText().toString())){
                    Toast.makeText(Profile.this, "Email changed.", Toast.LENGTH_SHORT).show();
                    databaseReference.child("email").setValue(emaildata.getText().toString());
                }else if (!name.equals(username.getText().toString()) && email.equals(emaildata.getText().toString())){
                    Toast.makeText(Profile.this, "Username changed.", Toast.LENGTH_SHORT).show();
                    databaseReference.child("username").setValue(username.getText().toString());
                }else if (!name.equals(username.getText().toString()) && !email.equals(emaildata.getText().toString())){
                    Toast.makeText(Profile.this, "Data changed.", Toast.LENGTH_SHORT).show();
                    databaseReference.child("email").setValue(emaildata.getText().toString());
                    databaseReference.child("username").setValue(username.getText().toString());
                }else {
                    Toast.makeText(Profile.this, "Nothing Changed.", Toast.LENGTH_SHORT).show();
                }

//                Toast.makeText(Profile.this, "Data Updated.", Toast.LENGTH_SHORT).show();

                alertDialog.cancel();
            }
        });
    }
}