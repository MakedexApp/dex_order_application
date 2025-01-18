package com.example.dexorderapplication;

/**
 * 주문 정보
 */

public class order {
    private String pkc;
    private String swc;
    private String hrb;
    private String place;
    private String order;

    public order(String pkc, String hrb, String swc, String place, String order){
        this.pkc = pkc;
        this.hrb = hrb;
        this.swc = swc;
        this.place = place;
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPlace() {
        return place;
    }

    public order() {}

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPkc() {
        return pkc;
    }

    public void setPkc(String pkc) {
        this.pkc = pkc;
    }

    public String getSwc() {
        return swc;
    }

    public void setSwc(String swc) {
        this.swc = swc;
    }

    public String getHrb() {
        return hrb;
    }

    public void setHrb(String hrb) {
        this.hrb = hrb;
    }
}
