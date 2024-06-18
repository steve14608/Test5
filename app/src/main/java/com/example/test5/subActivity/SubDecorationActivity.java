package com.example.test5.subActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.test5.DBOpenHelper;
import com.example.test5.MainActivity;
import com.example.test5.R;
import com.example.test5.adapter.CustomViewPagerAdapter;
import com.example.test5.adapter.DecorationGridViewAdapter;
import com.example.test5.components.CommonNavigationBar;
import com.example.test5.components.DecorationGridView;
import com.example.test5.components.TopBackBar;
import com.example.test5.dataset.DecorationGridViewData;
import com.example.test5.dataset.ResourcesData;

public class SubDecorationActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,ViewPager.OnPageChangeListener{
    private CommonNavigationBar commonNavigationBar;
    //private DBOpenHelper dbOpenHelper = new DBOpenHelper(MainActivity.this,"my.db",null,1);
    //private GridView gridView;
    //private GridViewDecorationAdapter gridViewDecorationAdapter;
    private ViewPager viewPager;
    private CustomViewPagerAdapter customViewPagerAdapter;
    public static final int[] selected = new int[4];
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_decoration);
        init();
    }
    @SuppressLint("Range")
    private void init(){
//        sp = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        commonNavigationBar = findViewById(R.id.navigationBar);
        customViewPagerAdapter = new CustomViewPagerAdapter();
        viewPager = findViewById(R.id.viewPager);

        commonNavigationBar.setViewPager(viewPager);




//        gridView = (GridView) findViewById(R.id.gridView);
//        gridViewDecorationAdapter = new GridViewDecorationAdapter(getBaseContext());
//        gridView.setAdapter(gridViewDecorationAdapter);

        commonNavigationBar.setMcontext(this);

        commonNavigationBar.addChild("主题");
        commonNavigationBar.addChild("壁纸");
        commonNavigationBar.addChild("底部");
        commonNavigationBar.addChild("头像");

        commonNavigationBar.setSelected(0);



        viewPager.setAdapter(customViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);


        Cursor cursor = MainActivity.getSql().query("decoration",new String[]{"themeIndex","wallpaperIndex","bottomIndex","profilePicIndex"},"userId=?",new String[]{String.valueOf(MainActivity.userId)},null,null,null);
        while (cursor.moveToNext()){
            selected[0]=cursor.getInt(cursor.getColumnIndex("themeIndex"));
            selected[1]=cursor.getInt(cursor.getColumnIndex("wallpaperIndex"));
            selected[2]=cursor.getInt(cursor.getColumnIndex("bottomIndex"));
            selected[3]=cursor.getInt(cursor.getColumnIndex("profilePicIndex"));
        }







        DecorationGridView gridView = new DecorationGridView(this);
        gridView.setAdapter(new DecorationGridViewAdapter(this,this,0){
            @SuppressLint("Range")
            @Override
            protected void init() {
//                //从数据库读取基本，从偏好文件读取选择了哪一个

                ResourcesData[] da = MainActivity.customAssetsManager.getColorList();
                for (ResourcesData data : da) {
                    DecorationGridViewData z = new DecorationGridViewData(new ColorDrawable(Color.parseColor((String)( ((Object[]) (data.data)) [0] ) )), data.name, false);
                    list.add(z);
                }
//                int z = sp.getInt("themeIndex",0);
//                SubDecorationActivity.selected[0]=z;
                currentDecorated = selected[0];
                try{list.get(currentDecorated).isDecorated=true;}catch(Exception ignored){}
            }
        });
        gridView.setSelection(selected[0]);
        customViewPagerAdapter.addView(gridView);



        gridView = new DecorationGridView(this);

        gridView.setAdapter(new DecorationGridViewAdapter(this,this,1){
            @SuppressLint("Range")
            @Override
            protected void init() {
                ResourcesData[] da = MainActivity.customAssetsManager.getWallpaperList();
                for( ResourcesData data : da){
                    Drawable drawable = new BitmapDrawable((Bitmap)( ((Object[]) (data.data)) [0] ) );
                    DecorationGridViewData z = new DecorationGridViewData(drawable,data.name,false);
                    list.add(z);
                }
//                int z = sp.getInt("wallpaperIndex",0);
//                SubDecorationActivity.selected[1]=z;
                currentDecorated = selected[1];
                try{list.get(currentDecorated).isDecorated=true;}catch(Exception ignored){}
            }
        });
        gridView.setSelection(selected[1]);
        customViewPagerAdapter.addView(gridView);

        gridView = new DecorationGridView(this);
        gridView.setAdapter(new DecorationGridViewAdapter(this,this,2){
            @SuppressLint("Range")
            @Override
            protected void init() {
                ResourcesData[] da = MainActivity.customAssetsManager.getBottomList();
                for( ResourcesData data : da){
                    Drawable drawable = new BitmapDrawable((Bitmap)( ((Object[]) (data.data)) [0] ) );
                    DecorationGridViewData z = new DecorationGridViewData(drawable,data.name,false);
                    list.add(z);
                }
//                int z = sp.getInt("bottomIndex",0);
//                SubDecorationActivity.selected[2]=z;
                currentDecorated = selected[2];
                try{list.get(currentDecorated).isDecorated=true;}catch(Exception ignored){}
            }
        });

        gridView.setSelection(selected[2]);
        customViewPagerAdapter.addView(gridView);

        gridView = new DecorationGridView(this);
        gridView.setAdapter(new DecorationGridViewAdapter(this,this,3){
            @SuppressLint("Range")
            @Override
            protected void init() {
                ResourcesData[] da = MainActivity.customAssetsManager.getProfilePicList();
                for( ResourcesData data : da){
                    Drawable drawable = new BitmapDrawable((Bitmap)( ((Object[]) (data.data)) [0] ) );
                    DecorationGridViewData z = new DecorationGridViewData(drawable,data.name,false);
                    list.add(z);
                }
//                int z = sp.getInt("profilePicIndex",0);
//                SubDecorationActivity.selected[3]=z;
                currentDecorated = selected[3];

                try{list.get(currentDecorated).isDecorated=true;}catch(Exception ignored){}
            }
        });
        gridView.setSelection(selected[3]);
        customViewPagerAdapter.addView(gridView);

        //test();
    }
    public void notifyColorChange(){
        ((TopBackBar)findViewById(R.id.top_back)).updateColor();

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
    protected void onPause() {
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putInt("themeIndex",SubDecorationActivity.selected[0]);
//        editor.putInt("wallpaperIndex",SubDecorationActivity.selected[1]);
//        editor.putInt("bottomIndex",SubDecorationActivity.selected[2]);
//        editor.putInt("profilePicIndex",SubDecorationActivity.selected[3]);
//        editor.apply();
        ContentValues contentValues = new ContentValues();
        contentValues.put("themeIndex",selected[0]);
        contentValues.put("wallpaperIndex",selected[1]);
        contentValues.put("bottomIndex",selected[2]);
        contentValues.put("profilePicIndex",selected[3]);
        MainActivity.getSql().update("decoration",contentValues,"userId=?",new String[]{String.valueOf(MainActivity.userId)});
        super.onPause();
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
