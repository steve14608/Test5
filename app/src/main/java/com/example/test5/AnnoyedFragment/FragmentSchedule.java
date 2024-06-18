package com.example.test5.AnnoyedFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.test5.MainActivity;
import com.example.test5.R;
import com.example.test5.adapter.CustomFragmentAdapter;
import com.example.test5.components.MainScheduleView;
import com.example.test5.components.NavigationFragment;
import com.example.test5.manager.AppearanceManager;
import com.example.test5.subActivity.SubCreateTargetActivity;
import com.example.test5.subActivity.SubSettingActivity;

public class FragmentSchedule extends NavigationFragment {
    MainScheduleView[] li = new MainScheduleView[4];
    public static boolean isDay = MainActivity.getIsDay();

    public FragmentSchedule(ViewPager viewPager) {
        super( R.id.main_schedule,viewPager);
    }

    @SuppressLint("Range")
    @Override
    public View initViewFromDatabase(View v) {

        li[0] = v.findViewById(R.id.schedule_view_e_n);
        li[1] = v.findViewById(R.id.schedule_view_e_no_n);
        li[2] = v.findViewById(R.id.schedule_view_no_e_n);
        li[3] = v.findViewById(R.id.schedule_view_no_e_no_n);
        databaseRefresh();
        return v;
    }
    @SuppressLint("Range")
    public void databaseRefresh(){
        Cursor cursor = MainActivity.getSql().query("scheduleList",new String[]{"finished","type"},"userId=?",new String[]{String.valueOf(MainActivity.userId)},null,null,null);
        int[][] da ={{0,0},{0,0},{0,0},{0,0}};

        while(cursor.moveToNext()){
            try{
                int z = cursor.getInt(cursor.getColumnIndex("type"));
                da[z][0]++;

                if(cursor.getInt(cursor.getColumnIndex("finished"))==1) da[z][1]++;

            }catch(Exception e){
                Toast.makeText(getContext(),"ERROR,cursor.getColumnIndex(type or finished) return -1",Toast.LENGTH_SHORT).show();
            }
        }
        for(int i=0;i<4;i++){
            li[i].setTotal_task_num(da[i][0]);
            li[i].setFinished_task_num(da[i][1]);
        }
        cursor.close();
    }

    @Override
    public View addListener(View v) {
        for(int i=0;i<4;i++){
            int j=i;
            li[i].setOnClickListener(v1 -> {

                ((MainActivity)getActivity()).getFragmentView().sort(1,j);
                viewPager.setCurrentItem(1);
            });
        }
        v.findViewById(R.id.textView2).setOnClickListener(v12 -> {
            ((FragmentView)((CustomFragmentAdapter)viewPager.getAdapter()).getItem(1)).sort(2,0);
            viewPager.setCurrentItem(1);
        });
        v.findViewById(R.id.textView1).setOnClickListener(v13 -> startActivityForResult(new Intent(getActivity(), SubCreateTargetActivity.class),0));
        return v;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_schedule,container,false);
        v = initViewFromDatabase(v);
        v = addListener(v);
        v.setBackground(AppearanceManager.wallpaper[0]);
        v.findViewById(R.id.textView1).setBackgroundColor(Color.argb(0.25f,1.0f,1.0f,1.0f));
        v.findViewById(R.id.textView2).setBackgroundColor(Color.argb(0.25f,1.0f,1.0f,1.0f));
        //v.findViewById(R.id.textClock).setBackground(isDay? ContextCompat.getDrawable(getContext(),R.drawable.day):ContextCompat.getDrawable(getContext(),R.drawable.night));
        return v;
    }
    public void refresh(){
        View v = getView();
        if(v!=null){
            v.setBackground(AppearanceManager.wallpaper[0]);
            v.findViewById(R.id.textView1).setBackgroundColor(Color.argb(0.25f,1.0f,1.0f,1.0f));
            v.findViewById(R.id.textView2).setBackgroundColor(Color.argb(0.25f,1.0f,1.0f,1.0f));
        }
    }
}

