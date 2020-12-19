package com.example.chatengine.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatengine.R;

public class About extends AppCompatActivity {

    TextView aboutme, aboutapp;
    ImageView git, linkedin, instagram;

    private String meDescription = "I'm Poojan Prajapati. I'm a CE student. If you wanted any kind of help in android then you can contact me through the link which are given below. I am happy to help you. My all projects are open source. you can contact me or download any of my open source project source code from below mentioned github.";
    private String me = "This app created using Firebase database as a backend. Here you can chat through Groupchat or Personalchat to your friends.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        git = findViewById(R.id.github);
        linkedin = findViewById(R.id.linkedin);
        instagram = findViewById(R.id.instagram);

        aboutme = findViewById(R.id.aboutme);
        aboutapp = findViewById(R.id.aboutapp);
        aboutme.setText(meDescription);
        aboutapp.setText(me);

        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://github.com/Poojan29";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}