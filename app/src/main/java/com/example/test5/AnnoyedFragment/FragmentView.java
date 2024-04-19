package com.example.test5.AnnoyedFragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test5.R;
import com.example.test5.adapter.ListViewAdapter;
import com.example.test5.components.NavigationFragment;
import com.example.test5.dataset.ListViewData;

import java.util.LinkedList;

public class FragmentView extends NavigationFragment {
    ListViewAdapter listViewAdapter;
    LinkedList<ListViewData> list;
    public FragmentView(SQLiteDatabase li){
        super(li, R.layout.main_view);
        list = new LinkedList<ListViewData>();
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
        return v;
    }
}
