package com.example.chatengine.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.chatengine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.chatengine.Adapters.PersonToPersonAdapter;
import com.example.chatengine.Models.PersonModel;

public class PersonToPersonChat extends AppCompatActivity {

    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<PersonModel> persontopersonarray;
    private PersonToPersonAdapter personToPersonAdapter;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, databaseReference2;
    private EditText mMessageEditText;
    private Button mSendButton;
    private String uid, Sender, Receiver, ptop, finalPtop;

    ArrayList<String> newMsg = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_to_person_chat);

//
//        DateFormat df = new SimpleDateFormat("h:mm a");
//        String date = df.format(Calendar.getInstance().getTime());

        DateFormat dateFormat = new SimpleDateFormat("h:mm a");
        final String time = dateFormat.format(Calendar.getInstance().getTime());

        recyclerView = findViewById(R.id.personchatrecyclerview);
        mMessageEditText = findViewById(R.id.messageEditText);
        mSendButton = findViewById(R.id.sendButton);
        progressBar = findViewById(R.id.progressBar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        persontopersonarray = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        final String opositeuid = bundle.getString("uid", "User's UID");
        final String opositename = bundle.getString("name", "OpositeName");

        setTitle(opositename);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Messages").child("PersonChat");
        databaseReference2 = firebaseDatabase.getReference().child("Users");
        ptop = uid + " - " + opositeuid;


        databaseReference2.child(opositeuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Receiver = snapshot.child("username").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseReference2.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Sender = snapshot.child("username").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child(ptop).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                if (snapshot.exists()) {
                    finalPtop = ptop;
                    getMessage(finalPtop);
                    persontopersonarray.clear();
                } else {
                    ptop = opositeuid + " - " + uid;
                    databaseReference.child(ptop).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                finalPtop = ptop;
                            } else {
                                finalPtop = uid + " - " + opositeuid;
                            }
                            getMessage(finalPtop);
                            persontopersonarray.clear();
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PersonModel personModel = new PersonModel(mMessageEditText.getText().toString(), Sender, null, Receiver, uid, time);
                if (mMessageEditText.getText().length() <= 0) {
                    mMessageEditText.setText("");
                }
                databaseReference.child(ptop).push().setValue(personModel);
                // Clear input box
                mMessageEditText.setText("");
            }
        });
    }

    void getMessage(String finalPtop) {


        databaseReference.child(finalPtop).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    //Log.d("DataSnap", String.valueOf(snapshot));
                    PersonModel personModel = snapshot.getValue(PersonModel.class);
                    persontopersonarray.add(personModel);
                    Log.d("Array", String.valueOf(persontopersonarray));
                    personToPersonAdapter = new PersonToPersonAdapter(PersonToPersonChat.this, persontopersonarray);
                    recyclerView.setAdapter(personToPersonAdapter);
                    progressBar.setVisibility(View.GONE);
                } else {
                    Log.v("Not Existtttttttttt", "NOOOOOOOOOOOOOo");
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("Snapshot", snapshot.toString());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", error.toString());
            }
        });
    }
}

