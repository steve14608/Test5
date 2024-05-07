package com.example.test5.subActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.test5.DBOpenHelper;
import com.example.test5.R;
import com.example.test5.adapter.CustomViewPagerAdapter;
import com.example.test5.components.CommonNavigationBar;
import com.example.test5.dataset.DecorationGridViewData;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class SubStatisticsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private CommonNavigationBar commonNavigationBar;
    private ViewPager viewPager;
    private DBOpenHelper dbOpenHelper;
    private CustomViewPagerAdapter customViewPagerAdapter;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_statictics);
        init();
    }
    protected void init(){
        dbOpenHelper = new DBOpenHelper(this,"my.db",null,1);
        commonNavigationBar = (CommonNavigationBar) findViewById(R.id.navigationBar);

        commonNavigationBar.addChild("概述");
        commonNavigationBar.addChild("计划");
        commonNavigationBar.addChild("专注");
        commonNavigationBar.setSelected(0);
        commonNavigationBar.setOnCheckedChangeListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        customViewPagerAdapter = new CustomViewPagerAdapter();
        viewPager.setAdapter(customViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        customViewPagerAdapter.addView(inflater.inflate(R.layout.component_statistics1,null));
        customViewPagerAdapter.addView(inflater.inflate(R.layout.component_statistics2,null));
        customViewPagerAdapter.addView(inflater.inflate(R.layout.component_statistics3,null));
        //customViewPagerAdapter.addView();
        initFromDatabase();

    }
    @SuppressLint("Range")
    protected void initFromDatabase(){
        //先获取数据库的接口
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        //list用来保存从数据库中读取的数据。
        //list的结构为：
        //0：累计坚持计划天数
        //1：累计专注分钟
        //2：累计专注次数
        //3：累计完成计划次数
        //4：本周累计坚持计划天数
        //5：本周累计完成计划次数
        //6：本周累计专注分钟时间(分钟)
        //7：本周累计专注次数
        //8：今日完成计划数
        //9：今日计划完成率
        //10-20：今日任务完成情况
        //21：最后一次访问数据库的月数，见下条
        //22：最后一次访问数据库的天数，这两个是用来判断是否要对每日及每周数据进行更新的依据
        ArrayList<Integer> list =new ArrayList<Integer>();
        //开始从数据库中读取数据
        Cursor cursor = db.query("statistics",new String[]{"data"},null,null,null,null,null,null);
        while(cursor.moveToNext()){
            list.add(cursor.getInt(cursor.getColumnIndex("data")));
        }
        cursor.close();

        //根据添加的顺序，getView(0)为最先添加的页面，即component_statistics1
        //根据它的layout，确定要对哪些组件初始化
        ConstraintLayout view = (ConstraintLayout) customViewPagerAdapter.getView(0);
        //查看component_statistics.xml，初始化textView，textView3，textView4，textView5
        //((TextView)view.findViewById(R.id.textView)).setText(String.format("已坚持计划%d天",list.get(0)));
        //剩下的包括其他两个页面补全下。
        //c_s1
        ((TextView)view.findViewById(R.id.textView)).setText(String.format("已坚持计划%d天",list.get(0)));
        ((TextView)view.findViewById(R.id.textView3)).setText(String.format("累计专注%d分钟",list.get(1)));
        ((TextView)view.findViewById(R.id.textView4)).setText(String.format("累计专注次数：%d次",list.get(2)));
        ((TextView)view.findViewById(R.id.textView5)).setText(String.format("累计完成计划：%d次",list.get(3)));
//c_s2
        view = (ConstraintLayout) customViewPagerAdapter.getView(1);
        ((TextView)view.findViewById(R.id.textView)).setText(String.format("本周已坚持计划%d天",list.get(4)));
        ((TextView)view.findViewById(R.id.textView4)).setText(String.format("本周专注时间：%d分钟",list.get(6)));
        ((TextView)view.findViewById(R.id.textView5)).setText(String.format("本周完成计划：%d次",list.get(5)));
//c_s3
        view = (ConstraintLayout) customViewPagerAdapter.getView(2);
        ((TextView)view.findViewById(R.id.textView)).setText(String.format("今日完成计划%d次",list.get(8)));
        ((TextView)view.findViewById(R.id.textView4)).setText(String.format("今日专注时间：%d分钟",list.get(7)));
        ((TextView)view.findViewById(R.id.textView5)).setText(String.format("今日计划完成率：%d%%",list.get(9)));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        viewPager.setCurrentItem(checkedId);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {
        if(state==2){
            commonNavigationBar.setSelected(viewPager.getCurrentItem());
        }
    }
}
