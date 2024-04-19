package com.example.test5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test5.R;

import com.example.test5.dataset.GridViewDecorationData;

import java.util.LinkedList;
import java.util.ListIterator;

public class GridViewDecorationAdapter extends BaseAdapter implements View.OnClickListener {
    private LinkedList<GridViewDecorationData> list;
    private int currentDecorated=0;
    private Context mcontext;
    public GridViewDecorationAdapter(Context a){
        super();
        list = new LinkedList<GridViewDecorationData>();
        mcontext = a;
    }
    public GridViewDecorationAdapter(LinkedList<GridViewDecorationData> e,Context a){
        list = e;
        mcontext = a;
    }
    public void add(GridViewDecorationData a){
        list.add(a);
        //Toast.makeText(mcontext,"ad",Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }
    public void add(LinkedList<GridViewDecorationData> li){
        list.addAll(li);
        notifyDataSetChanged();
    }
    public void changeDecoratedStatus(int position,boolean a){
        list.get(position).setDecorated(a);
    }
    public void changeDecorated(int position){
        if(currentDecorated==position)return;
        list.get(currentDecorated).setDecorated(false);
        list.get(position).setDecorated(true);
        currentDecorated=position;
        notifyDataSetChanged();
    }
    public void changeGrid(LinkedList<GridViewDecorationData> li,int decoratedIndex){
        list=li;
        currentDecorated=decoratedIndex;
        list.get(currentDecorated).setDecorated(true);
        notifyDataSetChanged();
    }
    public int getViewPoisition(String title){
        int num=0;
        ListIterator<GridViewDecorationData> iterator = list.listIterator();
        while(iterator.hasNext()){
            if(iterator.next().getTitle().compareTo(title)==0)return num;
            num++;
        }
        return num;
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
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ATag aTag = null;
        if(convertView==null){
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.component_decoration_grid_view,parent,false);
            aTag = new ATag();
            aTag.a=(ImageView)convertView.findViewById(R.id.imageView);
            aTag.b=(TextView)convertView.findViewById(R.id.textView1);
            aTag.c=(TextView)convertView.findViewById(R.id.textView2);
            convertView.setTag(aTag);

            convertView.setOnClickListener(this);

        }
        else aTag=(ATag) convertView.getTag();
        aTag.a.setBackgroundResource(list.get(position).getIconResourceId());
        aTag.b.setText(list.get(position).getTitle());
        aTag.c.setText(list.get(position).isDecorated()?"已装扮" : "");



        return convertView;
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(mcontext,v.toString(),Toast.LENGTH_SHORT).show();
        ATag aTag = (ATag) v.getTag();
        changeDecorated(getViewPoisition((String) aTag.b.getText()));

    }

    private static class ATag{
        ImageView a;
        TextView b;
        TextView c;
    }
}
