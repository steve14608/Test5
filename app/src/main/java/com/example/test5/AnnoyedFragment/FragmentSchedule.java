package com.example.test5.AnnoyedFragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test5.R;
import com.example.test5.components.MainScheduleView;
import com.example.test5.components.NavigationFragment;

public class FragmentSchedule extends NavigationFragment {
    MainScheduleView[] li = new MainScheduleView[4];

    public FragmentSchedule(SQLiteDatabase database) {
        super(database, R.id.main_schedule);

    }

    @SuppressLint("Range")
    @Override
    public View initViewFromDatabase(View v) {
        Cursor cursor = database.query("scheduleList",new String[]{"finished","type"},null,null,null,null,null);
        li[0] = (MainScheduleView) v.findViewById(R.id.schedule_view_e_n);
        li[1] = (MainScheduleView) v.findViewById(R.id.schedule_view_e_no_n);
        li[2] = (MainScheduleView) v.findViewById(R.id.schedule_view_no_e_n);
        li[3] = (MainScheduleView) v.findViewById(R.id.schedule_view_no_e_no_n);
        int[][] da ={{0,0},{0,0},{0,0},{0,0}};
        //if(cursor==null) return v;
        while(cursor.moveToNext()){
            try{
                int z = cursor.getInt(cursor.getColumnIndex("type"));
                da[z][0]++;
                //li[z].addTask();
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
        return v;
    }

    @Override
    public View addListener(View v) {
        return super.addListener(v);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_schedule,container,false);
        v = initViewFromDatabase(v);
        v = addListener(v);
        return v;
    }
}

