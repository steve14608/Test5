package com.example.test5.adapter;




import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.core.content.ContextCompat;

import com.example.test5.MainActivity;
import com.example.test5.R;
import com.example.test5.dataset.AttendanceTaskListViewData;

import java.util.ArrayList;
import java.util.LinkedList;

public class AttendanceTaskListViewAdapter extends BaseAdapter {
    private ArrayList<AttendanceTaskListViewData> list;
    private Context mContext;


    public AttendanceTaskListViewAdapter(ArrayList<AttendanceTaskListViewData> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
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
    private static class AdapterTag{
        TextView title;
        TextView state;
        ProgressBar progressBar;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterTag aTag = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.component_task_list2,parent,false);
            aTag = new AdapterTag();
            aTag.state = (TextView) convertView.findViewById(R.id.task_list2_state);
            aTag.title = (TextView) convertView.findViewById(R.id.task_list2_title);
            aTag.progressBar = (ProgressBar) convertView.findViewById(R.id.task_list2_progressbar);
            convertView.setTag(aTag);
        }else{
            aTag = (AdapterTag) convertView.getTag();
        }
        aTag.title.setText(list.get(position).getTitle());
        aTag.progressBar.setMax(100);

        //aTag.state.setText((list.get(position).getState()==1)?"已完成":"进行中");
        aTag.state.setText(list.get(position).progress==100?"已完成":"进行中");
        aTag.state.setBackgroundColor(list.get(position).progress==100? ContextCompat.getColor(mContext,R.color.shadow_gray):ContextCompat.getColor(mContext,R.color.white));

        upDateTaskListViewProgressBar(aTag.progressBar,(int)list.get(position).progress);
        return convertView;
    }

    private void upDateTaskListViewProgressBar(ProgressBar progressBar,int num){
        progressBar.setProgress(num);
    }
}
