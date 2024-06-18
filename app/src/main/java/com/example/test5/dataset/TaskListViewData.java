package com.example.test5.dataset;

public class TaskListViewData {
    public int index;
    public int type;
    public boolean isDone;
    public String title;
    public String subTitle;
    public TaskListViewData(){}
    public TaskListViewData(int a, boolean b, String c, String d){
        type=a;
        isDone=b;
        title =c;
        subTitle=d;
    }
    public void setType(int a){
        type = a;
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
    public int getType(){
        return type;
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
