package com.example.chatengine.Models;

public class AllUserModel {

    public String email, username, uid, userphoto;

    public AllUserModel() {
    }

    public AllUserModel(String email, String username, String uid, String userphoto) {
        this.email = email;
        this.username = username;
        this.uid = uid;
        this.userphoto= userphoto;
    }

    public AllUserModel(String email, String username, String uid) {
        this.email = email;
        this.username = username;
        this.uid = uid;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserphoto() {
        return userphoto;
    }

    public void setUserphoto(String userphoto) {
        this.userphoto = userphoto;
    }


}
