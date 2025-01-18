package com.example.dexorderapplication;

public class Cart {
    private String title;
    private int count;

    public Cart(String title, int count) {
        this.title = title;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getcount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
