package com.example.test5.dataset;

public class AttendanceTaskListViewData {
    public String title;
    public int progress;
    public AttendanceTaskListViewData(String b,int d){
        title=b;
        progress=d;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }


    public long getProgress() {
        return progress;
    }

}
