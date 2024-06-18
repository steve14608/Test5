package com.example.test5.AnnoyedFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test5.DBOpenHelper;
import com.example.test5.MainActivity;
import com.example.test5.R;
import com.example.test5.components.NavigationFragment;
import com.example.test5.manager.AppearanceManager;
import com.example.test5.subActivity.SubSettingActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;

public class FragmentConcentrate extends NavigationFragment {
    static final UpdateHandler handler = new UpdateHandler();
    private static final TimerThread thread = new TimerThread();

    public static boolean ifTimerRunning=true;
    private static int[] statistics = null;
    private static boolean[] ifChanged = new boolean[30];
    public TextView tvClock;
    private AlertDialog alert;
    private String[] itemList;
    public TextView state;
    private AlertDialog.Builder builder;
    private TextView startBtn;
    private SharedPreferences sp;
    public FragmentConcentrate(int resourceId,SharedPreferences sp){
        super(resourceId);
        this.sp = sp;
        handler.updateFragment(this);

        //init the statistics list
        if(statistics==null){
            statistics = new int[30];
            int pointer=0;

            Cursor cursor = MainActivity.getSql().query("statistics",new String[]{"data"},"userId=?",new String[]{String.valueOf(MainActivity.userId)},null,null,null);
            int columnIndex = cursor.getColumnIndex("data");
            while(cursor.moveToNext()){
                statistics[pointer++]=cursor.getInt(columnIndex);
            }
        }

    }

    private void getEventListFromPreference(){
        LinkedList<BaseEvent> eventList = new LinkedList<>();
        int counts=sp.getInt("concentratedTimes",2);
        int t1 = sp.getInt("defaultDuration",60);
        int t2 = sp.getInt("breakTime",10);
        for(int i=0;i<counts-1;i++){
            eventList.add(new BaseEvent((long) t1 * 60 * 1000, false));
            eventList.add(new BaseEvent((long) t2 * 60 * 1000, true));
        }
        eventList.add(new BaseEvent((long)t1*60*1000,false));
        handler.updateEventList(eventList);
    }

    private void test(){
        LinkedList<BaseEvent> list = new LinkedList<>();
        list.add(new BaseEvent(3000, false));
        list.add(new BaseEvent(5000, true));
        list.add(new BaseEvent(3000, false));
        list.add(new BaseEvent(5000, true));
        handler.updateEventList(list);
    }
    public static void updateStatisticsList(int index,int value){
        FragmentConcentrate.statistics[index]+=value;
        FragmentConcentrate.ifChanged[index]=true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_concentrate,container,false);
        setAlert();

        v.findViewById(R.id.btnSelectPlan).setOnClickListener(v13 -> alert.show());


        ImageButton settingBtn = v.findViewById(R.id.setting);
        startBtn = v.findViewById(R.id.btnStart);
        startBtn.setText(!thread.getTimerState() ? "开始专注": "重置");
        settingBtn.setOnClickListener(v12 -> startActivityForResult(new Intent(getActivity(), SubSettingActivity.class),0x11011));

        tvClock = v.findViewById(R.id.tvClock);
        state = v.findViewById(R.id.currentState);

        startBtn.setOnClickListener(v1 -> {
            if(!thread.getTimerState()){
                thread.startTimer();
            }
            else {
                thread.resetTimer();
            }
        });
        v.setBackground(AppearanceManager.wallpaper[0]);

        //test();
        if(handler.ifSetEventList())getEventListFromPreference();
        return v;
    }
    public void setAlert(){
        builder = new AlertDialog.Builder(getContext());
        itemList = getTask();
        alert = builder.setTitle("选择一个计划来专注").setItems(itemList, (dialog, which) -> {
            Message msg = new Message();
            msg.obj = itemList[which];
            msg.what=0x1121;
            handler.sendMessage(msg);
        }).create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0x11011&&resultCode==1){
            getEventListFromPreference();
        }
    }

    public void refresh(){
        View rootView = getView();
        if(rootView!=null)rootView.setBackground(AppearanceManager.wallpaper[0]);
    }


    @SuppressLint("Range")
    private String[] getTask(){
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = MainActivity.getSql().query("scheduleList",new String[]{"finished","title"},"finished=? and userId=?",new String[]{"0",String.valueOf(MainActivity.userId)},null,null,null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("title")));
        }
        cursor.close();
        String[] re = new String[list.size()];
        list.toArray(re);
        return re;
    }
    private void saveStatistics(){
        for(int i=0;i<14;i++){
            if(ifChanged[i]){
                ContentValues co = new ContentValues();
                co.put("data",String.valueOf(FragmentConcentrate.statistics[i]));
                MainActivity.getSql().update("statistics",co,"dataIndex=? and userId=?",new String[]{String.valueOf(i+1),String.valueOf(MainActivity.userId)});
                ifChanged[i]=false;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveStatistics();
    }

    //handler线程
    static class UpdateHandler extends Handler{
        WeakReference<FragmentConcentrate> frag;
        private LinkedList<BaseEvent> eventList;
        String text="从左上角选择一项计划";
        private volatile BaseEvent curEve;
        private LinkedList<BaseEvent> eventListCopy;
        public void updateFragment(FragmentConcentrate f){
            frag = new WeakReference<>(f);
        }
        public void updateEventList(LinkedList<BaseEvent> list){
            //eventList = new LinkedList<>(list);
            eventList = new LinkedList<>(list);
            eventListCopy = new LinkedList<>(list);
            thread.setTimeTotal(eventList.get(0).time);
        }
        public boolean ifSetEventList(){
            return null==eventListCopy||eventListCopy.isEmpty();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            FragmentConcentrate z = frag.get();
            switch (msg.what){
                //重置计时器（手动）
                case 0x1110:{
                    if(z!=null){
                        z.startBtn.setText("开始专注");
                        z.state.setText(text);
                        eventList = new LinkedList<>(eventListCopy);
                        //System.out.println("d");
                    }
                }
                //计时器运行中
                case 0x1000:{
                    long time = (long)msg.obj;
                    if(z!=null){
                        int minutes = (int)(time/1000)/60;
                        int seconds = (int)(time/1000)%60;
                        String timeLeft = String.format("%02d:%02d", minutes, seconds);
                        z.tvClock.setText(timeLeft);
                    }
                }break;
                //startTimer()
                case 0x1100:{
                    if(z!=null) z.startBtn.setText("重置");
                    curEve = eventList.pop();
                }break;
                //消息队列的处理，当一个倒计时完成后发送该消息，先判断消息队列是否为空，否则转入重置操作,一次分段完成
                case 0x1111:{
                    if(!curEve.ifRest){
                        //更新本日专注时长、专注次数  1，2
                        FragmentConcentrate.updateStatisticsList(1,(int)(curEve.time/60/1000));
                        FragmentConcentrate.updateStatisticsList(2,1);
                    }

                    if(eventList==null||eventList.isEmpty()){
                        eventList = new LinkedList<>(eventListCopy);
                        thread.setTimeTotal(eventList.get(0).time);

                        return;
                    }
                    curEve = eventList.pop();

                    if(z!=null){
                        int minutes = (int)(curEve.time/1000)/60;
                        int seconds = (int)(curEve.time/1000)%60;
                        String timeLeft = String.format("%02d:%02d", minutes, seconds);
                        z.tvClock.setText(timeLeft);
//                        System.out.println(z.tvClock.getText());
                        z.state.setText(curEve.ifRest? "休息":text);
                    }
                    new Thread(() -> {
                        try{
                            Thread.sleep(1000);
                        }catch (Exception ignored){}
                        thread.reStartTimer(curEve.time);
                    }).start();
                }break;
                //设置专注计划
                case 0x1121:{

                    text = (String)msg.obj;
                    if(curEve!=null&&!curEve.ifRest) z.state.setText(text);
                }break;
                case 0x1211:{
                    z.setAlert();
                }break;
                default:{
                    throw new IllegalArgumentException("Unknown message"+msg.what);
                }
            }

        }
    }


    //计时器线程
    static class TimerThread extends Thread{
        final Object lock = new Object();
        final Object lock2 = new Object();
        private volatile static boolean isRunning = false;
        private  long timeLeft=0;
        private  long timeTotal=0;//专注的时长
        public TimerThread(){
            start();
        }
        //当专注时长改变的时候，无论休息时长是否变化都调用这个
        public void setTimeTotal(long time){
            synchronized (lock){
                isRunning=false;
                timeTotal=time;
            }
            resetTimer();
        }
        //开始一次完整的历程
        public void startTimer(){
            synchronized (lock){
                if(isRunning)return;
                timeLeft=timeTotal;
                isRunning=true;
                handler.sendEmptyMessage(0x1100);
            }
            synchronized (lock2){
                lock2.notify();
            }

        }
        //重置历程为未开始状态
        public void resetTimer(){
            synchronized (lock){
                //if(!isRunning)return;
                timeLeft=timeTotal;
                isRunning=false;
                Message msg = new Message();
                msg.what = 0x1110;
                msg.obj=timeTotal;
                handler.sendMessage(msg);
            }
        }
        //历程的一个分段的开始,即专注或者休息段的倒计时
        public void reStartTimer(long time){
            timeTotal = time;
            synchronized (lock){
                timeLeft = time;
            }
            synchronized (lock2){
                lock2.notify();
            }
        }
        public boolean getTimerState(){
            boolean z;
            synchronized (lock){
                z = isRunning;
            }

            return z;
        }

        @Override
        public void run() {
            while(FragmentConcentrate.ifTimerRunning){
                synchronized (lock2){
                    while(timeLeft>0&&isRunning){
                        timeLeft-=1000;
                        Message message = new Message();
                        message.obj=timeLeft;
                        message.what=0x1000;
                        handler.sendMessage(message);
                        try{
                            Thread.sleep(1000);
                        }catch(Exception e){e.printStackTrace();}

                    }
                    if(isRunning)handler.sendEmptyMessage(0x1111);
                    //isRunning=false;
                    //resetTimer();
                    try{
                        lock2.wait();
                    }catch(Exception e){e.printStackTrace();}
                }
                //resetTimer();
                //lock.notifyAll();
            }
        }
    }
     static class BaseEvent{
        public long time;
        public boolean ifRest;
        public BaseEvent(long time,boolean ifRest){
            this.time=time;
            this.ifRest=ifRest;
        }
    }
}
