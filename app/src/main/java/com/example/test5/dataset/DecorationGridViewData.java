package com.example.test5.dataset;

import java.util.LinkedList;

public class DecorationGridViewData {
    public int icon;
    public String title;
    public boolean isDecorated;
    public LinkedList<Integer> resourceList;
    public int type;

    public DecorationGridViewData(int iconResourceId, String title, boolean isDecorated){
        icon=iconResourceId;
        this.title=title;
        this.isDecorated=isDecorated;
        resourceList = new LinkedList<Integer>();
    }
    public void addResource(int a){
        resourceList.add(a);
    }

}
