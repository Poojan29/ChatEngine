package com.example.chatengine.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chatengine.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.example.chatengine.Adapters.AllUserAdapter;
import com.example.chatengine.Models.AllUserModel;

public class AllUsers extends AppCompatActivity {

    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<AllUserModel> allUserModelArrayList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uid;
    private AllUserAdapter allUserAdapter;
    private FirebaseAuth firebaseAuth;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        uid = FirebaseAuth.getInstance().getUid();

        recyclerView = findViewById(R.id.alluser_recyclerview);
        floatingActionButton = findViewById(R.id.floatingButton);
        progressBar = findViewById(R.id.alluserprogressBar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");

        allUserModelArrayList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                allUserModelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String email = dataSnapshot.child("username").getValue(String.class);
                    String name = dataSnapshot.child("username").getValue(String.class);
                    String uid = dataSnapshot.child("uid").getValue(String.class);
                    if (dataSnapshot.child("photourl").exists()){
                        String photourl = dataSnapshot.child("photourl").getValue(String.class);

                        AllUserModel allUserModel = new AllUserModel(email, name, uid, photourl);
                        allUserModelArrayList.add(allUserModel);
                    }else{
                        AllUserModel allUserModel = new AllUserModel(email, name , uid);
                        allUserModelArrayList.add(allUserModel);
                    }

                }
                allUserAdapter = new AllUserAdapter(AllUsers.this, allUserModelArrayList);
                recyclerView.setAdapter(allUserAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), Profile.class));
//                Toast.makeText(this, "Coming Soon..", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.sign_out_menu:
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                return true;

            case R.id.about:
                startActivity(new Intent(getApplicationContext(), About.class));
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}