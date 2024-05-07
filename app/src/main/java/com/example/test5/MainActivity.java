package com.example.test5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.test5.AnnoyedFragment.FragmentConcentrate;
import com.example.test5.AnnoyedFragment.FragmentSchedule;
import com.example.test5.AnnoyedFragment.FragmentView;
import com.example.test5.components.MainScheduleView;
import com.example.test5.components.NavigationFragment;
import com.example.test5.components.TopBackBar;
import com.example.test5.subActivity.SubCreateTargetActivity;
import com.example.test5.subActivity.SubDecorationActivity;
import com.example.test5.adapter.CustomFragmentAdapter;
import com.example.test5.adapter.CustomViewPagerAdapter;
import com.example.test5.adapter.ListViewAdapter;

import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private RadioGroup btnGroup;//导航栏的按钮组
    public static int[] bottomBtnPic = new int[4];
    private ViewPager viewpager;//主界面

    private CustomFragmentAdapter customFragmentAdapter;

    private DBOpenHelper dbOpenHelper = new DBOpenHelper(MainActivity.this,"my.db",null,1);
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
        //test();



        //先是获取基础控件
        btnGroup = (RadioGroup) findViewById(R.id.main_navigation_btnGroup);
        viewpager = (ViewPager) findViewById(R.id.main_viewPager);

        customFragmentAdapter = new CustomFragmentAdapter(getSupportFragmentManager(),dbOpenHelper.getWritableDatabase());

        customFragmentAdapter = new CustomFragmentAdapter(getSupportFragmentManager(),dbOpenHelper.getWritableDatabase());
        customFragmentAdapter.addFragment(R.layout.main_schedule,new FragmentSchedule(dbOpenHelper.getWritableDatabase(),viewpager));
        customFragmentAdapter.addFragment(R.layout.main_view,new FragmentView(dbOpenHelper.getWritableDatabase(),viewpager));
        customFragmentAdapter.addFragment(R.layout.main_concentrate,new FragmentConcentrate(R.layout.main_concentrate,dbOpenHelper));


        RadioButton z = (RadioButton) btnGroup.getChildAt(0);
        z.setChecked(true);
        viewpager.setAdapter(customFragmentAdapter);
        btnGroup.setOnCheckedChangeListener(this);
        viewpager.addOnPageChangeListener(this);
        viewpager.setCurrentItem(0);


        //对底部导航栏的四个按钮的背景图片进行配置
//        ((Button)findViewById(R.id.main_navigation_btn0))


        //test();
    }

    private void test(){
        //Toast.makeText(getBaseContext(),R.layout.activity_main+"",Toast.LENGTH_SHORT).show();
        //getLayoutInflater().inflate(a,viewpager).getTag();
        //listViewAdapter.add(new ListViewData(R.color.sakura,true,"a","a"));
        startActivity(new Intent(MainActivity.this, SubDecorationActivity.class));
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("finished",1);
//        contentValues.put("type",0);
//        contentValues.put("title","test");
//        contentValues.put("subTitle","test1");
//        dbOpenHelper.getWritableDatabase().insert("scheduleList",null,contentValues);
        //dbOpenHelper.getWritableDatabase().execSQL("DROP TABLE wallpaper");
        //dbOpenHelper.getWritableDatabase().execSQL("CREATE TABLE wallpaper(wallpaperIndex INTEGER PRIMARY KEY AUTOINCREMENT,coverResourceId INTEGER,name TEXT,resourceId INTEGER)");
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
            ((RadioButton)btnGroup.getChildAt(viewpager.getCurrentItem())).setChecked(true);
//            if(z<2)((RadioButton)btnGroup.getChildAt(viewpager.getCurrentItem())).setChecked(true);
//            else ((RadioButton)btnGroup.getChildAt(viewpager.getCurrentItem()+1)).setChecked(true);

        }

    }
    public static int getColorByType(int type){
        return switch (type) {
            case 0 -> R.color.e_n;
            case 1 -> R.color.e_no_n;
            case 2 -> R.color.no_e_n;
            default -> R.color.no_e_no_n;
        };
    }
    public static Activity getActivity(View view){
        if (null != view) {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity) context;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        return null;
    }
    static class AppearanceManager{
        private SQLiteDatabase db;
        //处理范围：四个annoyedFragment,topBack的backgroundColor,navigationBar的四个图标及navigation的背景,component_create_alert的color，ProfilePic，
        public void init(SQLiteDatabase db){
            this.db = db;
        }
        public void updateAll(){

        }
        public void updateWallpaper(){

        }
        public void updateBottom(){
            //底部四个按钮不能仅仅只是四张图片，应设置clicked和normal状态，故须写对应的四个xml
//            Cursor cursor = db.query();
//            while(cursor.moveToNext()){
//                MainActivity.bottomBtnPic[0]=cursor.getInt(cursor.getColumnIndex());
//                MainActivity.bottomBtnPic[1]=cursor.getInt(cursor.getColumnIndex());
//                MainActivity.bottomBtnPic[2]=cursor.getInt(cursor.getColumnIndex());
//                MainActivity.bottomBtnPic[3]=cursor.getInt(cursor.getColumnIndex());
//            }
//            cursor.close();
        }
        public void updateProfilePic(){
//            Cursor cursor = db.query();//直接查profilePic,theme的更改同时同步到其他三个表
//            int z;
//            while(cursor.moveToNext()){
//                z = cursor.getInt(cursor.getColumnIndex());
//            }
//            cursor.close();
//            SubCreateTargetActivity.profilePic=z;
        }
        public void updateTopBackGround(){

//            Cursor cursor = db.query();
//            int z;
//            while(cursor.moveToNext()){
//                z = cursor.getInt(cursor.getColumnIndex("topBackColor"));
//            }
//            cursor.close();
//            TopBackBar.backgroundColor=z;
        }
    }
}