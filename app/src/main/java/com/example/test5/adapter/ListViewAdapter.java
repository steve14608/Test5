package com.example.test5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.test5.MainActivity;
import com.example.test5.R;
import com.example.test5.dataset.ListViewData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class ListViewAdapter extends BaseAdapter {
    private Context mcontext;
    private ArrayList<ListViewData> list;
    public ListViewAdapter(Context a,ArrayList<ListViewData> b){
        mcontext = a;
        list = b;
        if(b==null) list = new ArrayList<ListViewData>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterTag atag = null;
        if(convertView==null){
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.component_task_list,parent,false);
            atag = new AdapterTag();
            atag.checkBox=(TextView) convertView.findViewById(R.id.task_list_checkbox);
            atag.title=(TextView) convertView.findViewById(R.id.task_list_title);
            atag.subTitle=(TextView) convertView.findViewById(R.id.task_list_subtitle);
            convertView.setTag(atag);
        }
        else atag = (AdapterTag) convertView.getTag();
        atag.checkBox.setText(list.get(position).isDone?"O":"");
        atag.title.setText(list.get(position).title);
        atag.subTitle.setText(list.get(position).subTitle);
        //convertView.setBackgroundColor(list.get(position).background);
        convertView.setBackgroundColor(MainActivity.getColorByType((list.get(position).type)));
        return convertView;
    }

    public void add(ListViewData a){
        list.add(a);
        notifyDataSetChanged();
    }
    public void add(ListViewData a,int position){
        list.add(position,a);
        notifyDataSetChanged();
    }
    public void add(LinkedList<ListViewData> a){
        list.addAll(a);
        notifyDataSetChanged();
    }
    public void remove(ListViewData a){
        list.remove(a);
        notifyDataSetChanged();
    }
    public void remove(int position){
        list.remove(position);
        notifyDataSetChanged();
    }
    public void remove(LinkedList<ListViewData> a){
        list.removeAll(a);
        notifyDataSetChanged();
    }
    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }
    public void newList(ListViewData a){
        list.clear();
        list.add(a);
        notifyDataSetChanged();
    }
    public void newList(ArrayList<ListViewData> a){
        list.clear();
        list.addAll(a);
        notifyDataSetChanged();
    }
    public void replaceItem(ListViewData a,int b){
        list.remove(b);
        list.add(b,a);
        notifyDataSetChanged();
    }
    public void changeItemIsDone(int position,boolean b){
        list.get(position).setIsDone(b);
        notifyDataSetChanged();
    }
    public void changeItemTitle(int position,String b){
        list.get(position).setTitle(b);
        notifyDataSetChanged();
    }
    public void changeItemSubTitle(int position,String b){
        list.get(position).setSubTitle(b);
        notifyDataSetChanged();
    }
    public void changeItemColor(int position,int b){
        list.get(position).setType(b);
        notifyDataSetChanged();
    }
    public void reverseList(){
        Collections.reverse(list);
        notifyDataSetChanged();
    }

    private static class AdapterTag{
        TextView checkBox;
        TextView title;
        TextView subTitle;
    }
}
