package com.example.test5.dataset;

public class NoteListViewData {
    public long time;
    public String text;
    public int index;
    public NoteListViewData(long time){
        this.time = time;
    }
    public NoteListViewData(long time,String text,int index){
        this.time=time;
        this.index=index;
        this.text=text;
    }
}
