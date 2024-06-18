package com.example.test5.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class CustomViewPagerAdapter extends PagerAdapter {
    private ArrayList<View> list ;
    public CustomViewPagerAdapter(){
        super();
        list = new ArrayList<>();
    }
    public void addView(View v){
        list.add(v);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
        notifyDataSetChanged();
    }
    public View getView(int position){
        return list.get(position);
    }
}
