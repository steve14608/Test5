package com.example.test5;

import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.test5.tools.MainFragmentAdapter;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private RadioGroup btnGroup;
    private ViewPager viewpager;
    private MainFragmentAdapter fragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initialize();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

    }

    private void initialize(){
        //先是获取控件
        btnGroup = (RadioGroup) findViewById(R.id.main_navigation_btnGroup);
        viewpager = (ViewPager) findViewById(R.id.main_viewPager);
        fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());

        //然后对控件进行初始化
        RadioButton z = (RadioButton) btnGroup.getChildAt(0);
        z.setChecked(true);
        viewpager.setAdapter(fragmentAdapter);
        btnGroup.setOnCheckedChangeListener(this);
        viewpager.addOnPageChangeListener(this);
        viewpager.setCurrentItem(0);


    }









    //下面的是接口重写的方法
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId==R.id.main_navigation_btn0){
            viewpager.setCurrentItem(0);
        }
        else if(checkedId==R.id.main_navigation_btn1){
            viewpager.setCurrentItem(1);
        }
        else if(checkedId==R.id.main_navigation_btn2){
            viewpager.setCurrentItem(2);
        }
        else viewpager.setCurrentItem(3);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(state==2){
            int z = viewpager.getCurrentItem();
            if(z<2)((RadioButton)btnGroup.getChildAt(viewpager.getCurrentItem())).setChecked(true);
            else ((RadioButton)btnGroup.getChildAt(viewpager.getCurrentItem()+1)).setChecked(true);

        }
    }
}