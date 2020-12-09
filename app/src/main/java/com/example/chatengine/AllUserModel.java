package com.example.chatengine;

public class AllUserModel {

    public String username, uid;

    public AllUserModel() {
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

    public AllUserModel(String username, String uid) {
        this.username = username;
        this.uid = uid;
    }
}
