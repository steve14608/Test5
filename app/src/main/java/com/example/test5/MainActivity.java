package com.example.test5;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.test5.AnnoyedFragment.FragmentSchedule;
import com.example.test5.AnnoyedFragment.FragmentView;
import com.example.test5.components.MainScheduleView;
import com.example.test5.components.NavigationFragment;
import com.example.test5.subActivity.SubDecorationActivity;
import com.example.test5.adapter.CustomFragmentAdapter;
import com.example.test5.adapter.CustomViewPagerAdapter;
import com.example.test5.adapter.ListViewAdapter;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private RadioGroup btnGroup;//导航栏的按钮组
    private ViewPager viewpager;//主界面
//    private ListViewAdapter listViewAdapter;//
//    private CustomViewPagerAdapter customViewPagerAdapter;
    private CustomFragmentAdapter customFragmentAdapter;
//    private ListView listView;
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
        test();



        //先是获取基础控件
        btnGroup = (RadioGroup) findViewById(R.id.main_navigation_btnGroup);
        viewpager = (ViewPager) findViewById(R.id.main_viewPager);
        //fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
//        customViewPagerAdapter = new CustomViewPagerAdapter();
        customFragmentAdapter = new CustomFragmentAdapter(getSupportFragmentManager(),dbOpenHelper.getWritableDatabase());
        //listViewAdapter = new ListViewAdapter();








        //然后对控件进行初始化
//        customViewPagerAdapter.addView(getLayoutInflater().inflate(R.layout.main_schedule,null,false));
//        customViewPagerAdapter.addView(getLayoutInflater().inflate(R.layout.main_view,null,false));
//        customViewPagerAdapter.addView(getLayoutInflater().inflate(R.layout.main_concentrate,null,false));
//        customViewPagerAdapter.addView(getLayoutInflater().inflate(R.layout.main_user,null,false));




        customFragmentAdapter = new CustomFragmentAdapter(getSupportFragmentManager(),dbOpenHelper.getWritableDatabase());
        customFragmentAdapter.addFragment(R.layout.main_schedule,new FragmentSchedule(dbOpenHelper.getWritableDatabase()));
        customFragmentAdapter.addFragment(R.layout.main_view,new FragmentView(dbOpenHelper.getWritableDatabase()));
        //customFragmentAdapter.addFragment(R.layout.main_concentrate);
        //customFragmentAdapter.addFragment(R.layout.main_user);

//        customFragmentAdapter.addFragment(R.layout.main_schedule,new Fragment(){
//            @Nullable
//            @Override
//            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//                View view = inflater.inflate(R.layout.main_schedule,container,false);
//                //initialize the view
//                return view;
//            }
//        });
//        customFragmentAdapter.addFragment(R.layout.main_schedule,new NavigationFragment(dbOpenHelper.getWritableDatabase(),R.layout.main_schedule){
//            MainScheduleView[] li = new MainScheduleView[4];
//            @SuppressLint("Range")
//            @Override
//            public View initViewFromDatabase(View v) {
//                Cursor cursor = database.query("scheduleList",new String[]{"finished","type"},null,null,null,null,null);
//                li[0] = (MainScheduleView) v.findViewById(R.id.schedule_view_e_n);
//                li[1] = (MainScheduleView) v.findViewById(R.id.schedule_view_e_no_n);
//                li[2] = (MainScheduleView) v.findViewById(R.id.schedule_view_no_e_n);
//                li[3] = (MainScheduleView) v.findViewById(R.id.schedule_view_no_e_no_n);
//                int[][] da ={{0,0},{0,0},{0,0},{0,0}};
//                while(cursor.moveToNext()){
//                    try{
//                        int z = cursor.getInt(cursor.getColumnIndex("type"));
//                        da[z][0]++;
//                        //li[z].addTask();
//                        if(cursor.getInt(cursor.getColumnIndex("finished"))==1) da[z][1]++;
//
//                    }catch(Exception e){
//                        Toast.makeText(getContext(),"ERROR,cursor.getColumnIndex(type or finished) return -1",Toast.LENGTH_SHORT).show();
//                    }
//                }
//                for(int i=0;i<4;i++){
//                    li[i].setTotal_task_num(da[i][0]);
//                    li[i].setFinished_task_num(da[i][1]);
//                }
//                cursor.close();
//                return v;
//            }
//
//            @Override
//            public View addListener(View v) {
//                return super.addListener(v);
//            }
//        });







        RadioButton z = (RadioButton) btnGroup.getChildAt(0);
        z.setChecked(true);
        viewpager.setAdapter(customFragmentAdapter);
        btnGroup.setOnCheckedChangeListener(this);
        viewpager.addOnPageChangeListener(this);
        viewpager.setCurrentItem(0);

//        listView = customViewPagerAdapter.getView(1).findViewById(R.id.main_view_list);
//        listViewAdapter = new ListViewAdapter(customViewPagerAdapter.getView(1).getContext(),null);
//        listView.setAdapter(listViewAdapter);



    }

    private void test(){
        //listViewAdapter.add(new ListViewData(R.color.sakura,true,"a","a"));
//        //startActivity(new Intent(MainActivity.this, SubDecorationActivity.class));
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("finished",1);
//        contentValues.put("type",0);
//        contentValues.put("title","test");
//        contentValues.put("subTitle","test1");
//        dbOpenHelper.getWritableDatabase().insert("scheduleList",null,contentValues);
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