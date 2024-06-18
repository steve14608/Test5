package com.example.test5.AnnoyedFragment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.test5.MainActivity;
import com.example.test5.R;
import com.example.test5.adapter.TaskListViewAdapter;
import com.example.test5.components.NavigationFragment;
import com.example.test5.dataset.TaskListViewData;
import com.example.test5.manager.AppearanceManager;

import java.util.ArrayList;

public class FragmentView extends NavigationFragment implements AdapterView.OnItemSelectedListener {
    TaskListViewAdapter listViewAdapter;
    ArrayList<TaskListViewData> list;
    private static boolean ifChecked;
    public FragmentView(ViewPager viewPager){
        super( R.layout.main_view,viewPager);
        list = new ArrayList<>();
    }
    public void refresh(){
        View v = getView();
        if(v!=null){
            ((ListView)v.findViewById(R.id.main_view_list)).setAdapter(listViewAdapter);
        }
    }

    @SuppressLint("Range")
    @Override
    public View initViewFromDatabase(View v) {
        Cursor cursor = MainActivity.getSql().query("scheduleList",new String[]{"scheduleIndex","finished","type","title","subTitle"},"userId=?",new String[]{String.valueOf(MainActivity.userId)},null,null,null);
        while(cursor.moveToNext()){
            TaskListViewData z = new TaskListViewData();
            z.index= cursor.getInt(cursor.getColumnIndex("scheduleIndex"));
            z.isDone=cursor.getInt(cursor.getColumnIndex("finished"))==1;
            //z.background
            z.type = cursor.getInt(cursor.getColumnIndex("type"));
            z.setSubTitle(cursor.getString(cursor.getColumnIndex("subTitle")));
            z.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            list.add(z);
        }
        cursor.close();
        ListView listView = v.findViewById(R.id.main_view_list);
        listViewAdapter= new TaskListViewAdapter(v.getContext(),list,this);
        listView.setAdapter(listViewAdapter);
        return super.initViewFromDatabase(v);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_view,container,false);
        v = initViewFromDatabase(v);
        v = addListener(v);
        TextView textView = v.findViewById(R.id.checkBox);
        textView.setOnClickListener(v1 -> {
            ifChecked=!ifChecked;
            listViewAdapter.reverseList();
            textView.setBackgroundColor(ifChecked ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));

        });
        Spinner spinner = v.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        v.setBackground(AppearanceManager.wallpaper[1]);
        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        listViewAdapter.newList(list);//需要判断是否有必要调用这函数，以避免不必要的开销
        ifChecked=false;
    }


    public void sort(int element, int priority){
        interface Compare{
            boolean cmp(TaskListViewData a, TaskListViewData b);
        }
        Compare cmp;
        switch (element){
            case 0:
                cmp = (a, b) -> a.index>b.index;
                break;
            case 1:
                cmp = (a, b) -> {
                    if (a.type==priority){
                        return false;
                    }
                    if (b.type==priority){
                        return true;
                    }
                    return a.type>b.type;
                };
                break;
            case 2:
                cmp = (a, b) -> {
                    boolean sign;
                    sign= priority == 1;
                    if (a.isDone==sign){
                        return false;
                    }
                    return b.isDone == sign;
                };
                break;
            default:{
                System.out.print("Error,unexpected sort op");
                return;
            }

        }

        ArrayList<TaskListViewData> list1 = new ArrayList<>(list);
        for (int i=0;i<list1.size();i++){
            for (int j=1;j<list1.size()-i;j++){
                if (cmp.cmp(list1.get(j-1),list1.get(j))){

                    TaskListViewData t=list1.get(j);
                    list1.set(j,list1.get(j-1));
                    list1.set(j-1,t);
                }
            }
        }
        listViewAdapter.newList(list1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            case 0:
                sort(1,0);
                break;
            case 2:
                sort(0,0);
                break;
            case 1:
                sort(2,1);
                break;
            default:
                throw new IllegalArgumentException("Unexpected input");
        }
    }
    public void notifyChangeFinished(int index,boolean b){
        ContentValues contentValues = new ContentValues();
        contentValues.put("finished",b ? 1:0);
        MainActivity.getSql().update("scheduleList",contentValues,"scheduleIndex=? and userId=?",new String[]{String.valueOf(index),String.valueOf(MainActivity.userId)});
        fuck(b);


        ((MainActivity)getActivity()).getFragmentSchedule().databaseRefresh();
        FragmentConcentrate.handler.sendEmptyMessage(0x1211);
    }
    public void notifyDeleteItem(int index){
        MainActivity.getSql().delete("scheduleList","scheduleIndex=? and userId=?",new String[]{String.valueOf(index),String.valueOf(MainActivity.userId)});



        ((MainActivity)getActivity()).getFragmentSchedule().databaseRefresh();
    }
    @SuppressLint("Range")
    private void fuck(boolean a){
        Cursor cursor = MainActivity.getSql().query("statistics",new String[]{"data"},"userId=? and dataIndex=?",new String[]{String.valueOf(MainActivity.userId),"5"},null,null,null);
        int z=0;
        while (cursor.moveToNext()){
            z = Integer.parseInt(cursor.getString(cursor.getColumnIndex("data")));
        }
        ContentValues co = new ContentValues();
        if(a){
            co.put("data",String.valueOf(z+1));
            MainActivity.getSql().update("statistics",co,"userId=? and dataIndex=?",new String[]{String.valueOf(MainActivity.userId),"5"});
        }
        else{
            co.put("data",String.valueOf(z-1));
            MainActivity.getSql().update("statistics",co,"userId=? and dataIndex=?",new String[]{String.valueOf(MainActivity.userId),"5"});
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void deleteItem(int position){
        listViewAdapter.remove(position);
    }
}
