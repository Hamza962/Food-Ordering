package com.example.khana_online.fyp;

public class Cart_class {
    private String dishname, chefid, deliverytype, address, Total_Price, customerid, quantity, phonenumber, customername, orderid,time;
    public String status;
    public Cart_class(String dishname, String chefid, String deliverytype, String address, String Total_Price, String customerid, String phonenumber, String quantity, String customername, String orderid,String time) {
        this.dishname = dishname;
        this.chefid = chefid;
        this.deliverytype = deliverytype;
        this.address = address;
        this.Total_Price = Total_Price;
        this.customerid = customerid;
        this.phonenumber = phonenumber;
        this.quantity = quantity;
        this.customername = customername;
        this.orderid = orderid;
        this.time = time;
    }
public  Cart_class(){


}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Cart_class(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public String getChefid() {
        return chefid;
    }

    public void setChefid(String chefid) {
        this.chefid = chefid;
    }

    public String getDeliverytype() {
        return deliverytype;
    }

    public void setDeliverytype(String deliverytype) {
        this.deliverytype = deliverytype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal_Price() {
        return Total_Price;
    }

    public void setTotal_Price(String total_Price) {
        Total_Price = total_Price;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }




}