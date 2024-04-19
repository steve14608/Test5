package com.example.test5.subActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test5.R;
import com.example.test5.components.CommonNavigationBar;

public class SubStatisticsActivity extends AppCompatActivity {
    private CommonNavigationBar commonNavigationBar;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_statictics);
        init();
    }
    private void init(){
        commonNavigationBar = (CommonNavigationBar) findViewById(R.id.navigationBar);


        commonNavigationBar.addChild("概述");
        commonNavigationBar.addChild("计划");
        commonNavigationBar.addChild("专注");

        commonNavigationBar.setSelected(0);
    }
}
