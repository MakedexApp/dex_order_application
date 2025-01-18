package com.example.dexorderapplication.Domain;

import java.io.Serializable;

public class BeverageDomain {
    private String title;
    private String pic;
    private String fee;

    public BeverageDomain(String title, String pic, String fee) {
        this.title = title;
        this.pic = pic;
        this.fee = fee;
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

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
