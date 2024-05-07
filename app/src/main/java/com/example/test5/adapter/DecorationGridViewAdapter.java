package com.example.test5.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test5.R;

import com.example.test5.dataset.DecorationGridViewData;

import java.util.LinkedList;
import java.util.ListIterator;

public class DecorationGridViewAdapter extends BaseAdapter implements View.OnClickListener {
    protected LinkedList<DecorationGridViewData> list;
    private int currentDecorated=0;
    private Context mcontext;
    public DecorationGridViewAdapter(Context a, SQLiteDatabase db){
        super();
        list = new LinkedList<DecorationGridViewData>();
        mcontext = a;
        init(db);
    }
    protected void init(SQLiteDatabase db){

    }
    public void changeDecoratedStatus(int position,boolean a){
        list.get(position).isDecorated=a;
    }
    public void changeDecorated(int position){
        if(currentDecorated==position)return;
        list.get(currentDecorated).isDecorated=false;
        list.get(position).isDecorated=true;
        currentDecorated=position;
        notifyDataSetChanged();
    }
    public int getViewPoisition(String title){
        int num=0;
        ListIterator<DecorationGridViewData> iterator = list.listIterator();
        while(iterator.hasNext()){
            if(iterator.next().title.compareTo(title)==0)return num;
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
        aTag.a.setBackgroundResource(list.get(position).icon);
        aTag.b.setText(list.get(position).title);
        aTag.c.setText(list.get(position).isDecorated?"已装扮" : "");



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
