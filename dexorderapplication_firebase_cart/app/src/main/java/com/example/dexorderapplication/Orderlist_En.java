package com.example.dexorderapplication;

public class Orderlist_En {
    public String title;

    public String getTitle() {
        if(title == "파워에이드"){
            title = "pwa";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
