package com.example.test5.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private ArrayList<String> textTable;
    private Context mcontext;
    private GridView gridView;
    public GridViewAdapter(Context m, GridView z){
        textTable = new ArrayList<String>();
        mcontext=m;
        gridView=z;

    }
    @Override
    public int getCount() {
        return textTable.size();
    }

    @Override
    public Object getItem(int position) {
        return textTable.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterTag atag = new AdapterTag();
        if(convertView==null){
            convertView = gridView;
            atag.textView=new TextView(gridView.getContext());
            convertView.setTag(atag);
        }
        else atag = (AdapterTag) convertView.getTag();
        atag.textView.setText(textTable.get(position));
        return convertView;
    }
    public void addItem(String a){
        textTable.add(a);
        notifyDataSetChanged();
    }

    public void testAdd(){//此函数是在测试阶段尝试添加一些组件到gridview，观察并进行调整的函数

    }
    public static class AdapterTag{
        TextView textView;
    }
}
