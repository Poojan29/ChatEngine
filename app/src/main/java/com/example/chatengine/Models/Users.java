package com.example.chatengine.Models;

public class Users {

    public String username, email,uid, photourl;

    public Users() {
    }

    public Users(String username, String email, String uid, String photourl) {
        this.username = username;
        this.email = email;
        this.uid = uid;
        this.photourl = photourl;
    }
    public Users(String username, String email, String uid) {
        this.username = username;
        this.email = email;
        this.uid = uid;

    }

}
