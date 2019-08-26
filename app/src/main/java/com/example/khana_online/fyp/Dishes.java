package com.example.khana_online.fyp;

public class Dishes {
    private String name, category, quantity, chefid, imageUrl, price, time, chefname, id;

    public Dishes(String name, String category, String quantity, String chefid, String imageUrl, String price, String time, String chefname, String id) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.chefid = chefid;
        this.imageUrl = imageUrl;
        this.price = price;
        this.time = time;
        this.chefname = chefname;
        this.id = id;
    }


    public Dishes() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getChefid() {
        return chefid;
    }

    public void setChefid(String chefid) {
        this.chefid = chefid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChefname() {
        return chefname;
    }

    public void setChefname(String chefname) {
        this.chefname = chefname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}