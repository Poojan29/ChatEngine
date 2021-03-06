package com.example.chatengine.Models;

public class PersonModel {

    private String msg;
    private String Sender;
    private String photoUrl;
    private String Receiver;
    private String Senderuid;
    private String time;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getSenderuid() {
        return Senderuid;
    }

    public void setSenderuid(String senderuid) {
        Senderuid = senderuid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String date) {
        this.time = date;
    }

    public PersonModel(String msg, String sender, String photoUrl, String receiver, String senderuid, String time) {
        this.msg = msg;
        Sender = sender;
        this.photoUrl = photoUrl;
        Receiver = receiver;
        Senderuid = senderuid;
        this.time = time;
    }

    public PersonModel() {
    }
}
