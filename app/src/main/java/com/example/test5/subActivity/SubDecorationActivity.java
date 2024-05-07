package com.example.test5.subActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.test5.DBOpenHelper;
import com.example.test5.R;
import com.example.test5.adapter.CustomViewPagerAdapter;
import com.example.test5.adapter.DecorationGridViewAdapter;
import com.example.test5.components.CommonNavigationBar;
import com.example.test5.components.DecorationGridView;
import com.example.test5.dataset.DecorationGridViewData;

public class SubDecorationActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,ViewPager.OnPageChangeListener{
    private CommonNavigationBar commonNavigationBar;
    private DBOpenHelper dbOpenHelper;
    //private DBOpenHelper dbOpenHelper = new DBOpenHelper(MainActivity.this,"my.db",null,1);
    //private GridView gridView;
    //private GridViewDecorationAdapter gridViewDecorationAdapter;
    private ViewPager viewPager;
    private CustomViewPagerAdapter customViewPagerAdapter;
    private SharedPreferences sp;
    private int[] selected = new int[3];
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_decoration);
        init();
    }
    private void init(){
        dbOpenHelper = new DBOpenHelper(this,"my.db",null,1);
        sp = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        commonNavigationBar = (CommonNavigationBar) findViewById(R.id.navigationBar);
//        gridView = (GridView) findViewById(R.id.gridView);
//        gridViewDecorationAdapter = new GridViewDecorationAdapter(getBaseContext());
//        gridView.setAdapter(gridViewDecorationAdapter);

        commonNavigationBar.setMcontext(this);

        commonNavigationBar.addChild("主题");
        commonNavigationBar.addChild("壁纸");
        commonNavigationBar.addChild("底部");
        commonNavigationBar.addChild("头像");

        commonNavigationBar.setSelected(0);

        customViewPagerAdapter = new CustomViewPagerAdapter();
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(customViewPagerAdapter);

        //GridView z = new GridView(getBaseContext());
        //
        //
        //
        //
        //
        //
        //
        //
        DecorationGridView gridView = new DecorationGridView(this);
        gridView.setAdapter(new DecorationGridViewAdapter(this,dbOpenHelper.getWritableDatabase()){
            @SuppressLint("Range")
            @Override
            protected void init(SQLiteDatabase db) {
                //从数据库读取基本，从偏好文件读取选择了哪一个
                Cursor cursor = db.query("theme", new String[]{"coverResourceId","name","wallpaperIndex","bottomIconIndex"}, null, null, null, null, null);
                while(cursor.moveToNext()){
                    DecorationGridViewData z = new DecorationGridViewData(cursor.getInt(cursor.getColumnIndex("coverResourceId")),cursor.getString(cursor.getColumnIndex("name")),false);
                    z.type=0;
                    z.addResource(cursor.getInt(cursor.getColumnIndex("wallpaperIndex")));
                    z.addResource(cursor.getInt(cursor.getColumnIndex("bottomIconIndex")));
                    list.add(z);
                }
                cursor.close();
                int z = sp.getInt("themeIndex",0);
                selected[0]=z;
                try{list.get(z).isDecorated=true;}catch(Exception ignored){}

            }
        });
        customViewPagerAdapter.addView(gridView);



        gridView = new DecorationGridView(this);
        gridView.setAdapter(new DecorationGridViewAdapter(this,dbOpenHelper.getWritableDatabase()){
            @SuppressLint("Range")
            @Override
            protected void init(SQLiteDatabase db) {
                Cursor cursor = db.query("wallpaper", new String[]{"coverResourceId","name","resourceId",}, null, null, null, null, null);
                while(cursor.moveToNext()){
                    DecorationGridViewData z = new DecorationGridViewData(cursor.getInt(cursor.getColumnIndex("coverResourceId")),cursor.getString(cursor.getColumnIndex("name")),false);
                    z.type=1;
                    z.addResource(cursor.getInt(cursor.getColumnIndex("resourceId")));
                    list.add(z);
                }
                cursor.close();
                int z = sp.getInt("wallpaperIndex",0);
                selected[1]=z;
                try{list.get(z).isDecorated=true;}catch(Exception ignored){}
            }
        });
        customViewPagerAdapter.addView(gridView);

        gridView = new DecorationGridView(this);
        gridView.setAdapter(new DecorationGridViewAdapter(this,dbOpenHelper.getWritableDatabase()){
            @SuppressLint("Range")
            @Override
            protected void init(SQLiteDatabase db) {
                Cursor cursor = db.query("bottomIcon", new String[]{"coverResourceId","name","RIdOne","RIdTwo","RIdThr","RIdFor"}, null, null, null, null, null);
                while(cursor.moveToNext()){
                    DecorationGridViewData z = new DecorationGridViewData(cursor.getInt(cursor.getColumnIndex("coverResourceId")),cursor.getString(cursor.getColumnIndex("name")),false);
                    z.type=2;
                    z.addResource(cursor.getInt(cursor.getColumnIndex("RIdOne")));
                    z.addResource(cursor.getInt(cursor.getColumnIndex("RIdTwo")));
                    z.addResource(cursor.getInt(cursor.getColumnIndex("RIdThr")));
                    z.addResource(cursor.getInt(cursor.getColumnIndex("RIdFor")));
                    list.add(z);
                }
                cursor.close();
                int z = sp.getInt("bottomIndex",0);
                selected[2]=z;
                try{list.get(z).isDecorated=true;}catch(Exception ignored){}
            }
        });
        customViewPagerAdapter.addView(gridView);

        gridView = new DecorationGridView(this);
        gridView.setAdapter(new DecorationGridViewAdapter(this,dbOpenHelper.getWritableDatabase()){
            @SuppressLint("Range")
            @Override
            protected void init(SQLiteDatabase db) {
                Cursor cursor = db.query("profilePic", new String[]{"coverResourceId","name","resourceId",}, null, null, null, null, null);
                while(cursor.moveToNext()){
                    DecorationGridViewData z = new DecorationGridViewData(cursor.getInt(cursor.getColumnIndex("coverResourceId")),cursor.getString(cursor.getColumnIndex("name")),false);
                    z.type=3;
                    z.addResource(cursor.getInt(cursor.getColumnIndex("resourceId")));
                    list.add(z);
                }
                cursor.close();
                int z = sp.getInt("ProfilePicIndex",0);
                selected[1]=z;
                try{list.get(z).isDecorated=true;}catch(Exception ignored){}
            }
        });
        customViewPagerAdapter.addView(gridView);
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //

        test();
    }
    public void test(){
//        GridViewDecorationData z = new GridViewDecorationData(R.color.black,"test",true);
//        gridViewDecorationAdapter.add(z);
//        z = new GridViewDecorationData(R.color.no_e_n,"test2",false);
//        gridViewDecorationAdapter.add(z);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId==R.id.main_navigation_btn0){
            viewPager.setCurrentItem(0,true);
        }
        else if(checkedId==R.id.main_navigation_btn1){
            viewPager.setCurrentItem(1,true);
        }
        else if(checkedId==R.id.main_navigation_btn2){
            viewPager.setCurrentItem(2,true);
        }
        else{
            viewPager.setCurrentItem(3,true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("themeIndex",selected[0]);
        editor.putInt("wallpaperIndex",selected[1]);
        editor.putInt("bottomIndex",selected[2]);
        editor.putInt("profilePic",selected[3]);
        editor.apply();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
