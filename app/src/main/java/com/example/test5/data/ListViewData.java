package com.example.test5.data;

import android.graphics.Color;
import android.widget.ListView;

import com.example.test5.R;

public class ListViewData {
    public int background;
    public boolean isDone;
    public String title;
    public String subTitle;
    public ListViewData(){}
    public ListViewData(int a,boolean b,String c,String d){
        background=a;
        isDone=b;
        title =c;
        subTitle=d;
    }
    public void setColor(int a){
        background = a;
    }
    public void setIsDone(boolean a){
        isDone=a;
    }
    public void setTitle(String a){
        title = a;
    }
    public void setSubTitle(String a){
        subTitle = a;
    }
    public int getColorId(){
        return background;
    }
    public boolean getIsDone(){
        return isDone;
    }
    public String getTitle(){
        return title;
    }
    public String getSubTitle(){
        return subTitle;
    }


}
