package com.example.test5.AnnoyedFragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test5.R;
import com.example.test5.components.NavigationFragment;
import com.example.test5.manager.AppearanceManager;
import com.example.test5.subActivity.SubAttendance;
import com.example.test5.subActivity.SubDecorationActivity;
import com.example.test5.subActivity.SubStatisticActivity;

public class FragmentUser extends NavigationFragment {
    public FragmentUser(int resourceId){
        super(resourceId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_user,container,false);
        return bindView(view);
    }
    private View bindView(View v){
        ((ImageView)v.findViewById(R.id.imageView1)).setImageDrawable(AppearanceManager.profilePic);
        v.findViewById(R.id.button3).setOnClickListener(v12 -> startActivity(new Intent(getActivity(), SubDecorationActivity.class)));
        v.findViewById(R.id.button4).setOnClickListener(v13 -> startActivity(new Intent(getActivity(), SubStatisticActivity.class)));
        v.findViewById(R.id.checkInButton).setOnClickListener(v14 -> startActivity(new Intent(getActivity(), SubAttendance.class)));
        v.findViewById(R.id.button6).setOnClickListener(v15 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("公告").setMessage("修复了一些BUG，更新了一些特性").show();
        });
        v.setBackground(AppearanceManager.wallpaper[2]);
        return v;
    }
    public void refresh(){
        View v = getView();
        if(null!=v){
            ((ImageView)v.findViewById(R.id.imageView1)).setImageDrawable(AppearanceManager.profilePic);
            v.setBackground(AppearanceManager.wallpaper[2]);
        }
    }
}
