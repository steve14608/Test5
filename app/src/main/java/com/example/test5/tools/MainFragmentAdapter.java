package com.example.test5.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.test5.R;


public class MainFragmentAdapter extends FragmentPagerAdapter {
    private final int pageCounts=4;
    MainFragment[] list = new MainFragment[pageCounts];
    public MainFragmentAdapter(@NonNull FragmentManager fm) {

        super(fm);
        list[0] = new MainFragment(R.layout.main_schedule);
        list[1] = new MainFragment(R.layout.main_view);
        list[2] = new MainFragment(R.layout.main_concentrate);
        list[3] = new MainFragment(R.layout.main_user);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list[position];
    }
    @Override
    public int getCount() {
        return pageCounts;
    }
    public static class MainFragment extends Fragment {
        private int layoutResource;
        public MainFragment(int layoutResource){
            this.layoutResource=layoutResource;
        }
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(layoutResource, container, false);
        }
    }
}
