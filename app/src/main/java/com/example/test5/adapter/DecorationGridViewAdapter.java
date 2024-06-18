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
import com.example.test5.manager.AppearanceManager;
import com.example.test5.subActivity.SubDecorationActivity;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.ListIterator;

public class DecorationGridViewAdapter extends BaseAdapter implements View.OnClickListener {
    protected LinkedList<DecorationGridViewData> list;
    private int type;
    WeakReference<SubDecorationActivity> act;
    protected int currentDecorated=0;
    private Context mcontext;
    public DecorationGridViewAdapter(Context a,SubDecorationActivity ac,int type){
        super();
        list = new LinkedList<>();
        mcontext = a;
        act = new WeakReference<>(ac);
        this.type=type;
        init();
    }
    protected void init(){

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
        notifyAppearanceChange(position,type);
    }
    private void notifyAppearanceChange(int index,int type){
        //System.out.println(index+" "+type);
        SubDecorationActivity.selected[type]=index;
        switch(type){
            case 0->{
                AppearanceManager.updateTheme(index);
                act.get().notifyColorChange();
            }
            case 1->{
                AppearanceManager.updateWallpaper(index);
            }
            case 2->{
                AppearanceManager.updateBottom(index);
            }
            case 3->{
                AppearanceManager.updateProfilePic(index);
            }
        }
    }
    public int getViewPoisition(String title){
        int num=0;
        for (DecorationGridViewData decorationGridViewData : list) {
            if (decorationGridViewData.title.compareTo(title) == 0) return num;
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
            aTag.a= convertView.findViewById(R.id.imageView);
            aTag.b= convertView.findViewById(R.id.textView1);
            aTag.c= convertView.findViewById(R.id.textView2);
            convertView.setTag(aTag);

            convertView.setOnClickListener(this);

        }
        else aTag=(ATag) convertView.getTag();
        aTag.a.setBackground(list.get(position).cover);
        aTag.b.setText(list.get(position).title);
        aTag.c.setText(list.get(position).isDecorated?"已装扮" : "");



        return convertView;
    }

    @Override
    public void onClick(View v) {
        ATag aTag = (ATag) v.getTag();
        changeDecorated(getViewPoisition((String) aTag.b.getText()));

    }

    private static class ATag{
        ImageView a;
        TextView b;
        TextView c;
    }
}
