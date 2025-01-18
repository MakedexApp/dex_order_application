package com.example.dexorderapplication.Interface;

import android.os.Handler;

public class Kor_Eng {
    public Object title;

    public Object getTitle() {
        if (title == "파워에이드") {
            title = "pwa";
        }
        return title;
    }
    public void setTitle(Object title) {
        this.title = title;
    }
}
