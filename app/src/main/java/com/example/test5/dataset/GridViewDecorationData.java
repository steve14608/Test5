package com.example.test5.dataset;

public class GridViewDecorationData {
    private int icon;
    private String title;
    private boolean isDecorated;
    public GridViewDecorationData(int iconResourceId,String title,boolean isDecorated){
        icon=iconResourceId;
        this.title=title;
        this.isDecorated=isDecorated;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setDecorated(boolean isDecorated){
        this.isDecorated=isDecorated;
    }
    public int getIconResourceId(){
        return icon;
    }
    public String getTitle(){
        return title;
    }
    public boolean isDecorated(){
        return isDecorated;
    }
}
