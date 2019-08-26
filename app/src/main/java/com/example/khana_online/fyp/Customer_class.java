package com.example.khana_online.fyp;

public class Customer_class {
    private String phonenumber;
    private String cust_name;
    private String usern1;
    private String cust_add;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getUsern1() {
        return usern1;
    }

    public void setUsern1(String usern1) {
        this.usern1 = usern1;
    }

    public String getCust_add() {
        return cust_add;
    }

    public void setCust_add(String cust_add) {
        this.cust_add = cust_add;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public Customer_class(String phonenumber, String cust_name, String usern1, String cust_add, String token) {
        this.phonenumber = phonenumber;
        this.cust_name = cust_name;
        this.usern1 = usern1;
        this.cust_add = cust_add;
        this.token = token;
    }
}

