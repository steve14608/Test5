package com.example.test5.adapter;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class CustomFragmentAdapter extends FragmentPagerAdapter {
    ArrayList<Pair<Integer,Fragment>> list;
    private SQLiteDatabase database;
    public CustomFragmentAdapter(@NonNull FragmentManager fm, SQLiteDatabase da) {
        super(fm);
        list = new ArrayList<Pair<Integer,Fragment>>();
        database = da;
    }
    public void addFragment(int resourceId){
        Fragment z = new Fragment(){
            @Nullable
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                return inflater.inflate(resourceId,container,false);
            }
        };
        list.add(new Pair<>(resourceId,z));
    }
    public void addFragment(int resourceId,Fragment z){
        list.add(new Pair<>(resourceId,z));
    }

    @NonNull


    @Override
    public Fragment getItem(int position) {
        return list.get(position).second;
    }
    public Fragment getItemByResourceId(int resourceId){
        for(int i=0;i<list.size();i++){
            if(list.get(i).first==resourceId)return list.get(i).second;
        }
        return null;
    }


    @Override
    public int getCount() {
        return list.size();
    }

}
