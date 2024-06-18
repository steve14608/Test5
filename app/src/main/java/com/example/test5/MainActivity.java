package com.example.test5;


import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.test5.AnnoyedFragment.FragmentConcentrate;
import com.example.test5.AnnoyedFragment.FragmentSchedule;
import com.example.test5.AnnoyedFragment.FragmentUser;
import com.example.test5.AnnoyedFragment.FragmentView;
import com.example.test5.manager.AppearanceManager;
import com.example.test5.adapter.CustomFragmentAdapter;
import com.example.test5.manager.CustomAssetsManager;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private RadioGroup btnGroup;//导航栏的按钮组
    public static CustomAssetsManager customAssetsManager = new CustomAssetsManager();
    private ViewPager viewpager;//主界面
    private static boolean isFirstResume = true;
    private int selected=0;
    public static Integer userId = null;
    private static boolean isDay = true;

    private CustomFragmentAdapter customFragmentAdapter;

    private static DBOpenHelper dbOpenHelper = null;//new DBOpenHelper(MainActivity.this,"my.db",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MainActivity.dbOpenHelper==null){
            MainActivity.dbOpenHelper=new DBOpenHelper(MainActivity.this,"my.db",null,1);
        }

        //首次一次性初始化
        if(userId==null){
            firstInit();
        }

        customAssetsManager.init(getAssets());

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        initialize();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        //login
        //startActivityForResult(new Intent(this, SubLoginActivity.class),0);
        //



    }

    private void firstInit(){
        MainActivity.userId = getIntent().getIntExtra("userId",1);

        ContentValues contentValues;
        Cursor cursor;
        String[] list = new String[14];
        int day=0;
        long se=0,sefoo=0;

        //先获取所有的
        cursor = dbOpenHelper.getWritableDatabase().query("statistics",new String[]{"data"},"userId=?",new String[]{String.valueOf(MainActivity.userId)},null,null,null);
        int pointer=0;
        int columnIndex = cursor.getColumnIndex("data");
        while (cursor.moveToNext()){
            list[pointer++]=cursor.getString(columnIndex);
        }

        //登录时间戳
        long time = System.currentTimeMillis();
        contentValues = new ContentValues();
        contentValues.put("data",String.valueOf(time));
        dbOpenHelper.getWritableDatabase().update("statistics",contentValues,"dataIndex=? and userId=?",new String[]{"1",String.valueOf(MainActivity.userId)});

//        LocalDate localDate = LocalDate.ofEpochDay(Long.parseLong(list[0]));
//        LocalDate localDate1 = LocalDate.ofEpochDay(time);
//        LocalDate localDate = Instant.ofEpochMilli(Long.parseLong(list[0])).atZone(ZoneOffset.ofHours(8)).toLocalDate();
//        LocalDate localDate1 = Instant.ofEpochMilli(Long.parseLong(list[0])).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        day = MainActivity.getDayBetween(Long.parseLong(list[0]),time);
        if(day==0)return;

        //对累计时间的
        contentValues = new ContentValues();
        contentValues.put("data",String.valueOf(Integer.parseInt(list[1])+Integer.parseInt(list[4])));
        dbOpenHelper.getWritableDatabase().update("statistics",contentValues,"dataIndex=? and userId=?",new String[]{"2",String.valueOf(MainActivity.userId)});

        contentValues = new ContentValues();
        contentValues.put("data",String.valueOf(Integer.parseInt(list[2])+Integer.parseInt(list[5])));
        dbOpenHelper.getWritableDatabase().update("statistics",contentValues,"dataIndex=? and userId=?",new String[]{"3",String.valueOf(MainActivity.userId)});

        contentValues = new ContentValues();
        contentValues.put("data",String.valueOf(Integer.parseInt(list[3])+Integer.parseInt(list[6])));
        dbOpenHelper.getWritableDatabase().update("statistics",contentValues,"dataIndex=? and userId=?",new String[]{"4",String.valueOf(MainActivity.userId)});


        //对七天时间的
        se = Long.parseLong(list[7]);
        sefoo = Long.parseLong(list[4]);
        if(day<6){
            se=se>>>(10*day);
            sefoo=sefoo<<(10*(6-day));
        }
        else{
            se=0;
        }
        se = se|sefoo;
        contentValues = new ContentValues();
        contentValues.put("data",String.valueOf(se));
        dbOpenHelper.getWritableDatabase().update("statistics",contentValues,"dataIndex=? and userId=?",new String[]{"8",String.valueOf(MainActivity.userId)});



        se = Long.parseLong(list[8]);
        sefoo = Long.parseLong(list[5]);
        if(day<6){
            se=se>>>(10*day);
            sefoo=sefoo<<(10*(6-day));
        }
        else{
            se=0;
        }
        se = se|sefoo;
        contentValues = new ContentValues();
        contentValues.put("data",String.valueOf(se));
        dbOpenHelper.getWritableDatabase().update("statistics",contentValues,"dataIndex=? and userId=?",new String[]{"9",String.valueOf(MainActivity.userId)});


        se = Long.parseLong(list[9]);
        sefoo = Long.parseLong(list[6]);
        if(day<6){
            se=se>>>(10*day);
            sefoo=sefoo<<(10*(6-day));
        }
        else{
            se=0;
        }
        se = se|sefoo;
        contentValues = new ContentValues();
        contentValues.put("data",String.valueOf(se));
        dbOpenHelper.getWritableDatabase().update("statistics",contentValues,"dataIndex=? and userId=?",new String[]{"10",String.valueOf(MainActivity.userId)});



        //重置今日的
        contentValues = new ContentValues();
        contentValues.put("data","0");
        dbOpenHelper.getWritableDatabase().update("statistics",contentValues,"dataIndex=? and userId=?",new String[]{"5",String.valueOf(MainActivity.userId)});
        dbOpenHelper.getWritableDatabase().update("statistics",contentValues,"dataIndex=? and userId=?",new String[]{"6",String.valueOf(MainActivity.userId)});
        dbOpenHelper.getWritableDatabase().update("statistics",contentValues,"dataIndex=? and userId=?",new String[]{"7",String.valueOf(MainActivity.userId)});


        //清除昨天的已完成计划
        dbOpenHelper.getWritableDatabase().delete("scheduleList","finished=? and userId=?",new String[]{"1",String.valueOf(MainActivity.userId)});

    }





    private void initialize(){

        //外观管理器的初始化
        AppearanceManager.init(dbOpenHelper);



        //先是获取基础控件
        btnGroup = findViewById(R.id.main_navigation_btnGroup);
        viewpager = findViewById(R.id.main_viewPager);

        customFragmentAdapter = new CustomFragmentAdapter(getSupportFragmentManager());

        customFragmentAdapter = new CustomFragmentAdapter(getSupportFragmentManager());
        customFragmentAdapter.addFragment(R.layout.main_schedule,new FragmentSchedule(viewpager));
        customFragmentAdapter.addFragment(R.layout.main_view,new FragmentView(viewpager));
        customFragmentAdapter.addFragment(R.layout.main_concentrate,new FragmentConcentrate(R.layout.main_concentrate,getSharedPreferences("Preference",Context.MODE_PRIVATE)));
        customFragmentAdapter.addFragment(R.layout.main_user,new FragmentUser(R.layout.main_user));


        RadioButton z = (RadioButton) btnGroup.getChildAt(0);
        z.setChecked(true);
        viewpager.setAdapter(customFragmentAdapter);
        btnGroup.setOnCheckedChangeListener(this);
        viewpager.addOnPageChangeListener(this);
        viewpager.setCurrentItem(0);




        refreshDecoration();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refreshPage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!MainActivity.isFirstResume){
            refreshPage();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.isFirstResume = false;
    }
    private void refreshPage(){

        getSupportFragmentManager().beginTransaction().detach(customFragmentAdapter.getItem(1)).commit();
        getSupportFragmentManager().beginTransaction().detach(customFragmentAdapter.getItem(0)).commit();
//        getSupportFragmentManager().beginTransaction().detach(customFragmentAdapter.getItem(2)).commit();
//        getSupportFragmentManager().beginTransaction().detach(customFragmentAdapter.getItem(3)).commit();
        getSupportFragmentManager().beginTransaction().attach(customFragmentAdapter.getItem(1)).commit();
        getSupportFragmentManager().beginTransaction().attach(customFragmentAdapter.getItem(0)).commit();
//        getSupportFragmentManager().beginTransaction().attach(customFragmentAdapter.getItem(2)).commit();
//        getSupportFragmentManager().beginTransaction().attach(customFragmentAdapter.getItem(3)).commit();
        ((FragmentConcentrate)customFragmentAdapter.getItem(2)).refresh();
        //((FragmentSchedule)customFragmentAdapter.getItem(0)).refresh();
        //((FragmentView)customFragmentAdapter.getItem(1)).refresh();
        ((FragmentUser)customFragmentAdapter.getItem(3)).refresh();
        refreshDecoration();
    }
    private void refreshDecoration(){
        btnGroup.setBackgroundColor(AppearanceManager.color);
        for(int i=0;i<4;i++){
            Drawable drawable = AppearanceManager.bottom[i];
            drawable.setBounds(0,0,200,200);
            ((RadioButton)(btnGroup.getChildAt(i))).setCompoundDrawables(null,drawable,null,null);

        }
    }



    private void test(){
        //Toast.makeText(getBaseContext(),R.layout.activity_main+"",Toast.LENGTH_SHORT).show();
        //getLayoutInflater().inflate(a,viewpager).getTag();
        //listViewAdapter.add(new ListViewData(R.color.sakura,true,"a","a"));
       // startActivity(new Intent(MainActivity.this, SubStatisticsActivity.class));
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("finished",1);
//        contentValues.put("type",0);
//        contentValues.put("title","test");
//        contentValues.put("subTitle","test1");
//        dbOpenHelper.getWritableDatabase().insert("scheduleList",null,contentValues);
        //dbOpenHelper.getWritableDatabase().execSQL("DROP TABLE wallpaper");
        //dbOpenHelper.getWritableDatabase().execSQL("CREATE TABLE wallpaper(wallpaperIndex INTEGER PRIMARY KEY AUTOINCREMENT,coverResourceId INTEGER,name TEXT,resourceId INTEGER)");
    }
    public FragmentSchedule getFragmentSchedule(){
        return (FragmentSchedule) (customFragmentAdapter.getItem(0));
    }
    public FragmentView getFragmentView(){
        return (FragmentView) (customFragmentAdapter.getItem(1));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alert;
        alert = builder.setPositiveButton("确认",null).setNegativeButton("取消",null).setMessage("是否删除").show();
        alert.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
            ((FragmentView)customFragmentAdapter.getItem(1)).deleteItem(selected);
            alert.dismiss();
        });
        alert.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(v -> alert.dismiss());
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.list_view_contextmenu,menu);
        selected = v.getId();
        super.onCreateContextMenu(menu,v,menuInfo);
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

        }

    }





    public static int getColorByType(int type){
        return switch (type) {
            case 0 -> R.color.shadow_e_n;
            case 1 -> R.color.shadow_e_no_n;
            case 2 -> R.color.shadow_e_no_e_n;
            default -> R.color.shadow_no_e_no_n;
        };
    }
    public static boolean getIsDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int z = calendar.get(Calendar.HOUR_OF_DAY);
        return 6<=z&&18>z;
    }
    public static int getDayBetween(long time1,long time2){
        LocalDate localDate = Instant.ofEpochMilli(time1).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        LocalDate localDate1 = Instant.ofEpochMilli(time2).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        return (int) ChronoUnit.DAYS.between(localDate,localDate1);
    }
    public synchronized static SQLiteDatabase getSql(){
        return MainActivity.dbOpenHelper.getWritableDatabase();
    }


}