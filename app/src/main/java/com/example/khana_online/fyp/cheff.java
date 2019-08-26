package com.example.khana_online.fyp;

public class cheff {
    private String name;
    private String email;
    private String phno;
    private String address;
    private  String devicetoken;
    private String id;
    private String spec;

    public cheff()
    {

    }

    public cheff(String name, String email, String phno, String address, String devicetoken,String id,String spec) {
        this.name = name;
        this.email = email;
        this.phno = phno;
        this.address = address;
        this.devicetoken = devicetoken;
        this.id = id;
        this.spec = spec;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getName() {
        return name;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getAddress() {
        return address;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
