package com.example.chatengine;

public class PersonModel {

    private String msg;
    private String name;
    private String photoUrl;
    private String To;

    public PersonModel() {
    }

    public PersonModel(String msg, String name, String photoUrl, String to) {
        this.msg = msg;
        this.name = name;
        this.photoUrl = photoUrl;
        To = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }


}
