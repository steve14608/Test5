package com.example.test5.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private Context mcontext;
    private GridView gridView;
    public GridViewAdapter(Context m, GridView z){
        list = new ArrayList<String>();
        mcontext=m;
        gridView=z;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView!=null)return convertView;
        TextView textView = new TextView(mcontext);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setTextSize(14.0f);
        return textView;
    }
    public void addItem(String a){
        list.add(a);
        notifyDataSetChanged();
    }
    public void addItems(ArrayList<String> list){
        this.list.addAll(list);
    }

    public void testAdd(){//此函数是在测试阶段尝试添加一些组件到gridview，观察并进行调整的函数

    }
}
