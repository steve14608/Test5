package com.example.test5.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.test5.R;


public class MainScheduleView extends RelativeLayout {
    private int total_task_num;
    private int finished_task_num;
    private String title;

    public MainScheduleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.component_schedule_view,this);
        initattr(context,attrs);
    }
    protected void initattr(Context context,AttributeSet attrs) {
        TypedArray z = context.obtainStyledAttributes(attrs,R.styleable.schedule_view);
        total_task_num=0;
        finished_task_num=0;
        title=z.getString(R.styleable.schedule_view_title);
        ((TextView)findViewById(R.id.schedule_view_title)).setText(title);
        z.recycle();
        updateProgress();
    }
    protected void updateComponent(){
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.schedule_view_progressBar);
        progressBar.setProgress(finished_task_num);
        progressBar.setMax(total_task_num);
        TextView textView = (TextView)findViewById(R.id.schedule_view_progress);
        textView.setText(String.format(getResources().getString(R.string.progress_format),finished_task_num,total_task_num));
    }

    public String getTitle(){
        return title;
    }
    public int getFinished_task_num(){
        return finished_task_num;
    }
    public int getTotal_task_num(){
        return total_task_num;
    }
    public void setTotal_task_num(int num){
        total_task_num=num;
        updateProgress();
    }
    public void setFinished_task_num(int num){
        finished_task_num=num;
        updateProgress();
    }
    public void completeTask(){
        completeTask(1);
    }
    public void completeTask(int num){
        finished_task_num+=num;
        if(finished_task_num>total_task_num){
            System.out.println("Error,finished task num i bigger than total task num");
            System.exit(-1);
        }
        updateProgress();
    }
    public void addTask(){
        addTask(1);
    }
    public void addTask(int num){
        total_task_num+=num;
        updateProgress();
    }




    public void updateProgress(){
        TextView zz = (TextView) findViewById(R.id.schedule_view_progress);
        zz.setText(String.format(getResources().getString(R.string.component_view_format),finished_task_num,total_task_num));
        ProgressBar zzz = (ProgressBar)findViewById(R.id.schedule_view_progressBar);
        zzz.setMax(total_task_num);
        zzz.setProgress(finished_task_num);

    }

}
