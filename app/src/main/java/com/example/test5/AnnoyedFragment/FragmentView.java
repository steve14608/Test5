package com.example.test5.AnnoyedFragment;

import android.annotation.SuppressLint;
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

import com.example.test5.R;
import com.example.test5.adapter.CustomFragmentAdapter;
import com.example.test5.adapter.ListViewAdapter;
import com.example.test5.components.NavigationFragment;
import com.example.test5.dataset.ListViewData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class FragmentView extends NavigationFragment implements View.OnClickListener , AdapterView.OnItemSelectedListener {
    ListViewAdapter listViewAdapter;
    ArrayList<ListViewData> list;
    private static boolean ifChecked;
    public FragmentView(SQLiteDatabase li, ViewPager viewPager){
        super(li, R.layout.main_view,viewPager);
        list = new ArrayList<ListViewData>();
    }

    @SuppressLint("Range")
    @Override
    public View initViewFromDatabase(View v) {
        Cursor cursor = database.query("scheduleList",new String[]{"scheduleIndex","finished","type","title","subTitle"},null,null,null,null,null);
        while(cursor.moveToNext()){
            ListViewData z = new ListViewData();
            z.index= cursor.getInt(cursor.getColumnIndex("scheduleIndex"));
            z.isDone=cursor.getInt(cursor.getColumnIndex("finished"))==1;
            //z.background
            z.setSubTitle(cursor.getString(cursor.getColumnIndex("subTitle")));
            z.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            list.add(z);
        }
        cursor.close();
        ListView listView = (ListView) v.findViewById(R.id.main_view_list);
        listViewAdapter= new ListViewAdapter(v.getContext(),list);
        listView.setAdapter(listViewAdapter);
        return super.initViewFromDatabase(v);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_view,container,false);
        v = initViewFromDatabase(v);
        v = addListener(v);

        TextView textView = (TextView) v.findViewById(R.id.checkBox);
        textView.setOnClickListener(FragmentView.this);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

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
            boolean cmp(ListViewData a,ListViewData b);
        }
        Compare cmp;
        switch (element){
            case 0:
                cmp = new Compare() {
                    @Override
                    public boolean cmp(ListViewData a,ListViewData b) {
                        return a.index>b.index;
                    }
                };
                break;
            case 1:
                cmp = new Compare() {
                    @Override
                    public boolean cmp(ListViewData a,ListViewData b) {
                        if (a.type==priority){
                            return false;
                        }
                        if (b.type==priority){
                            return true;
                        }
                        return a.type>b.type;
                    }
                };
                break;
            case 2:
                cmp = new Compare() {
                    @Override
                    public boolean cmp(ListViewData a,ListViewData b) {
                        boolean sign;
                        if (priority==1){
                            sign=true;
                        }
                        else{
                            sign=false;
                        }
                        if (a.isDone==sign){
                            return false;
                        }
                        if (b.isDone==sign){
                            return true;
                        }
                        return false;
                    }
                };
                break;
            default:{
                System.out.print("Error,unexpected sort op");
                return;
            }

        }

        ArrayList<ListViewData> list1 = new ArrayList<ListViewData>();
        Collections.copy(list1,list);
        for (int i=0;i<list1.size();i++){
            for (int j=1;j<list1.size()-i;j++){
                if (cmp.cmp(list1.get(j-1),list1.get(j))){
                    ListViewData t=list1.get(j);
                    list1.set(j,list1.get(j-1));
                    list1.set(j-1,t);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.checkBox){
            ifChecked=!ifChecked;
            reverse();
        }
    }
    private void reverse(){
        listViewAdapter.reverseList();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
