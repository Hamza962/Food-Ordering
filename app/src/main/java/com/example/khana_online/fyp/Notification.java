package com.example.khana_online.fyp;

public class Notification {
    private String sender , deviceid, type,reciver;


    public Notification() {
    }

    public Notification(String sender, String deviceid, String type, String reciver) {
        this.sender = sender;
        this.deviceid = deviceid;
        this.type = type;
        this.reciver = reciver;
    }

    public Notification(String sender) {
        this.sender = sender;
    }

    public Notification(String sender, String deviceid, String type) {
        this.sender = sender;
        this.deviceid = deviceid;
        this.type = type;
    }

    public Notification(String deviceid, String type) {
        this.deviceid = deviceid;
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }
}
