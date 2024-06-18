package com.example.test5.dataset;

import android.graphics.drawable.Drawable;

import java.util.LinkedList;

public class DecorationGridViewData {
    public Drawable cover;
    public String title;
    public boolean isDecorated;

    public DecorationGridViewData(Drawable iconResourceId, String title, boolean isDecorated){
        cover=iconResourceId;
        this.title=title;
        this.isDecorated=isDecorated;
    }

}
