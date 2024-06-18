package com.example.test5.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.test5.AnnoyedFragment.FragmentView;
import com.example.test5.MainActivity;
import com.example.test5.R;
import com.example.test5.dataset.TaskListViewData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class TaskListViewAdapter extends BaseAdapter {
    private final Context mcontext;

    private ArrayList<TaskListViewData> list;

    FragmentView fragmentView;


    public TaskListViewAdapter(Context a, ArrayList<TaskListViewData> b, FragmentView c){
        mcontext = a;
        list = b;
        if(b==null) list = new ArrayList<>();
        fragmentView = c;
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
        //atag.checkBox.setText(list.get(position).isDone?"O":"");
        atag.checkBox.setBackgroundColor(list.get(position).isDone ? mcontext.getResources().getColor(R.color.shadow_green) : mcontext.getResources().getColor(R.color.white));

        atag.title.setText(list.get(position).title);
        atag.subTitle.setText(list.get(position).subTitle);
        //convertView.setBackgroundColor(list.get(position).background);
        convertView.setBackgroundColor(ContextCompat.getColor(mcontext,MainActivity.getColorByType((list.get(position).type))));


        atag.checkBox.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
            String z1="完成";
            if(list.get(position).isDone){
                z1 = "取消";
            }
            builder.setTitle("提示").setMessage("确认是否"+z1).setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).setPositiveButton("确认", (dialog, which) -> changeItemIsDone(position)).show();
        });
        convertView.setId(position);
        ((Activity)mcontext).registerForContextMenu(convertView);

        return convertView;
    }

    public void add(TaskListViewData a){
        list.add(a);
        notifyDataSetChanged();
    }
    public void add(TaskListViewData a, int position){
        list.add(position,a);
        notifyDataSetChanged();
    }
    public void add(LinkedList<TaskListViewData> a){
        list.addAll(a);
        notifyDataSetChanged();
    }
    public void remove(TaskListViewData a){
        list.remove(a);
        notifyDataSetChanged();
    }
    public void remove(int position){
        fragmentView.notifyDeleteItem(list.get(position).index);
        list.remove(position);
        notifyDataSetChanged();
    }
    public void remove(LinkedList<TaskListViewData> a){
        list.removeAll(a);
        notifyDataSetChanged();
    }
    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }
    public void newList(TaskListViewData a){
        list.clear();
        list.add(a);
        notifyDataSetChanged();
    }
    public void newList(ArrayList<TaskListViewData> a){
        list.clear();
        list.addAll(a);
        notifyDataSetChanged();
    }
    public void replaceItem(TaskListViewData a, int b){
        list.remove(b);
        list.add(b,a);
        notifyDataSetChanged();
    }
    public void changeItemIsDone(int position,boolean b){
        list.get(position).setIsDone(b);
        fragmentView.notifyChangeFinished(list.get(position).index,b);
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("finished",b ? 1:0);
//        database.update("scheduleList",contentValues,"scheduleIndex=?",new String[]{String.valueOf(list.get(position).index)});
        notifyDataSetChanged();
    }
    public void changeItemIsDone(int position){
        changeItemIsDone(position,!list.get(position).getIsDone());
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
