package com.example.test5.tools;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.test5.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CustomViewPagerAdapter extends PagerAdapter {
    private ArrayList<View> list ;
    public CustomViewPagerAdapter(){
        super();
        list = new ArrayList<View>();
    }
    public CustomViewPagerAdapter(ArrayList<View> l){
        super();
        list = l;
    }
    public void addView(View v){
        list.add(v);
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

    }
    public View getView(int position){
        return list.get(position);
    }
}
