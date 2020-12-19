package com.example.chatengine.Models;

public class FriendlyMessage {

    private String text;
    private String Sender;
    private String photoUrl;
    private String uid;
    private String date;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public FriendlyMessage(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public FriendlyMessage() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FriendlyMessage(String text, String sender, String photoUrl, String uid, String date) {
        this.text = text;
        Sender = sender;
        this.photoUrl = photoUrl;
        this.uid = uid;
        this.date = date;
    }
}
