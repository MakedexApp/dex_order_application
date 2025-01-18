package com.example.dexorderapplication.Domain;

import java.io.Serializable;

public class ItemDomain implements Serializable {

    private String title;
    private String pic;

    private Integer fee;
    private int numberInCart;


    public ItemDomain(String title, int numberInCart) {
        this.title = title;
        this.numberInCart = numberInCart;
    }



    public ItemDomain(String title, String pic, Integer fee) {
        this.title = title;
        this.pic = pic;
        this.fee = fee;
    }

    public ItemDomain(String title, String pic, Integer fee, int numberInCart) {
        this.title = title;
        this.pic = pic;
        this.fee = fee;
        this.numberInCart = numberInCart;
    }

    public ItemDomain() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
