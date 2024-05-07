package com.example.test5.AnnoyedFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test5.DBOpenHelper;
import com.example.test5.R;
import com.example.test5.components.NavigationFragment;
import com.example.test5.subActivity.SubSettingActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

public class FragmentConcentrate extends NavigationFragment {
    MyHandler handler = new MyHandler(this);
    private  Thread thread;

    private static long startTimeInMillis = 1800000; // 30 minutes in milliseconds
    public static long timeLeftInMillis = 1800000;
    public static boolean ifPaused = true;
    public static boolean ifContinue = true;
    public TextView tvClock;
    private AlertDialog alert;
    public static int chosenTaskIndex;
    private AlertDialog.Builder builder;
    private DBOpenHelper dbOpenHelper;
    private Button startBtn;
    public FragmentConcentrate(int resourceId,DBOpenHelper dbOpenHelper){
        super(resourceId);
        this.dbOpenHelper = dbOpenHelper;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_concentrate,container,false);
        builder = new AlertDialog.Builder(getContext());
        alert = builder.setTitle("选择一个计划来专注").setItems(getTask(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FragmentConcentrate.chosenTaskIndex = which;
                handler.sendEmptyMessage(0x112);
            }
        }).create();

        v.findViewById(R.id.btnSelectPlan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });


        Button settingBtn = (Button)v.findViewById(R.id.setting);
        startBtn = (Button)v.findViewById(R.id.btnStart);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SubSettingActivity.class));
            }

        });

        tvClock = (TextView)v.findViewById(R.id.tvClock);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ifPaused){
                    ifContinue=true;
                    thread = new Thread(() -> {
                        FragmentConcentrate.ifPaused=false;
                        while(FragmentConcentrate.timeLeftInMillis>0&&FragmentConcentrate.ifContinue){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ignored) {}
                            if(!FragmentConcentrate.ifContinue)continue;
                            FragmentConcentrate.timeLeftInMillis-=1000;
                            handler.sendEmptyMessage(0x111);
                        }
                    });
                    thread.start();
                    startBtn.setText("重置");
                }
                else {
                    ifContinue=false;
                    FragmentConcentrate.ifPaused = true;
                    timeLeftInMillis = startTimeInMillis;
                    Message msg = new Message();
                    msg.what=0x111;
                    handler.sendMessage(msg);
                    startBtn.setText("开始专注");
                }
            }
        });
        //updateCountDownText();
        handler.sendEmptyMessage(0x111);
        return v;


    }
    @SuppressLint("Range")
    private String[] getTask(){
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = dbOpenHelper.getWritableDatabase().query("scheduleList",new String[]{"finished","title"},"finished=?",new String[]{"false"},null,null,null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("title")));
        }
        cursor.close();
        String[] re = new String[list.size()];
        list.toArray(re);
        return re;
    }
//    private void updateCountDownText() {
//        int minutes = (int) (timeLeftInMillis / 1000) / 60;
//        int seconds = (int) (timeLeftInMillis / 1000) % 60;
//        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
//        tvClock.setText(timeLeftFormatted);
//    }
    static class MyHandler extends Handler{
        WeakReference<FragmentConcentrate> frag;
        MyHandler(FragmentConcentrate f){
            frag = new WeakReference<>(f);
        }
        void updateFragMent(WeakReference<FragmentConcentrate> f){
            frag = f;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==0x111){
                FragmentConcentrate z = frag.get();
                if(z!=null){
                    int minutes = (int) (timeLeftInMillis / 1000) / 60;
                    int seconds = (int) (timeLeftInMillis / 1000) % 60;
                    String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
                    z.tvClock.setText(timeLeftFormatted);
                }
            } else if (msg.what==0x112) {
                System.out.println("UnImpliedMethod");
            }
        }
    }
}
