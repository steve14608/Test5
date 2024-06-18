package com.example.test5.subActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.test5.DBOpenHelper;
import com.example.test5.MainActivity;
import com.example.test5.R;
import com.example.test5.adapter.CustomViewPagerAdapter;
import com.example.test5.components.CommonNavigationBar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class SubStatisticActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private CommonNavigationBar commonNavigationBar;
    private ViewPager viewPager;
    private final ArrayList<String> list = new ArrayList<>();
    private CustomViewPagerAdapter customViewPagerAdapter;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_statictics);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        init();
    }
    @SuppressLint("Range")
    protected void init(){
        viewPager = findViewById(R.id.viewPager);
        commonNavigationBar = findViewById(R.id.navigationBar);

        commonNavigationBar.setViewPager(viewPager);
        commonNavigationBar.setMcontext(this);

        commonNavigationBar.addChild("累计");
        commonNavigationBar.addChild("七天");
        commonNavigationBar.addChild("今日");


        commonNavigationBar.setSelected(0);
        //commonNavigationBar.setOnCheckedChangeListener(this);


        customViewPagerAdapter = new CustomViewPagerAdapter();
        viewPager.setAdapter(customViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        commonNavigationBar.setViewPager(viewPager);
//
        LayoutInflater inflater = LayoutInflater.from(this);
        customViewPagerAdapter.addView(inflater.inflate(R.layout.component_statistics1,null));
        customViewPagerAdapter.addView(inflater.inflate(R.layout.component_statistics2,null));
        customViewPagerAdapter.addView(inflater.inflate(R.layout.component_statistics3,null));
        //customViewPagerAdapter.addView();
        //initFromDatabase();


        Cursor cursor = MainActivity.getSql().query("statistics",new String[]{"data"},"userId=?",new String[]{String.valueOf(MainActivity.userId)},null,null,null,null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("data")));
        }
        cursor.close();

        initSpan();

    }
    private void initSpan(){
        Spannable spannedString;
        TextView textView;
        View pageView;


        pageView = customViewPagerAdapter.getView(0);

        textView = pageView.findViewById(R.id.textView);
        spannedString = new SpannableString(textView.getText());
        spannedString.setSpan(new ForegroundColorSpan(Color.parseColor("#FDD835")),0,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannedString);

        textView = pageView.findViewById(R.id.textView1);
        spannedString = new SpannableString(list.get(13));
        spannedString.setSpan(new RelativeSizeSpan(1.75f),0,spannedString.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(spannedString);

        textView = pageView.findViewById(R.id.textView3);
        spannedString = new SpannableString(">累计专注"+Integer.parseInt(list.get(3))/60+"h");
        spannedString.setSpan(new UnderlineSpan(),5,5+String.valueOf(Integer.parseInt(list.get(3))/60).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannedString);

        textView = pageView.findViewById(R.id.textView4);
        spannedString = new SpannableString(">专注"+list.get(2)+"次");
        spannedString.setSpan(new UnderlineSpan(),3,3+String.valueOf(list.get(2)).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannedString);

        textView = pageView.findViewById(R.id.textView5);
        spannedString = new SpannableString(">完成计划"+list.get(1)+"条");
        spannedString.setSpan(new UnderlineSpan(),5,5+String.valueOf(list.get(1)).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannedString);


        pageView = customViewPagerAdapter.getView(1);

        textView = pageView.findViewById(R.id.textView);
        spannedString = new SpannableString(textView.getText());
        spannedString.setSpan(new ForegroundColorSpan(Color.parseColor("#43A047")),0,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannedString);

        textView = pageView.findViewById(R.id.textView1);
        //spannedString = new SpannableString(resolve(list.get(7)));
        spannedString = new SpannableString(String.valueOf(resolve(list.get(7))+Integer.parseInt(list.get(4))));
        spannedString.setSpan(new RelativeSizeSpan(1.75f),0,spannedString.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(spannedString);

        String z = String.valueOf(resolve(list.get(9))+Integer.parseInt(list.get(6)));
        textView = pageView.findViewById(R.id.textView4);
        spannedString = new SpannableString(">累计专注"+Integer.parseInt(z)/60+"h");
        spannedString.setSpan(new UnderlineSpan(),5,5+String.valueOf(Integer.parseInt(z)/60).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannedString);

        z = String.valueOf(resolve(list.get(8))+Integer.parseInt(list.get(5)));
        textView = pageView.findViewById(R.id.textView5);
        spannedString = new SpannableString(">专注"+z+"次");
        spannedString.setSpan(new UnderlineSpan(),3,3+z.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannedString);



        pageView = customViewPagerAdapter.getView(2);

        textView = pageView.findViewById(R.id.textView);
        spannedString = new SpannableString(textView.getText());
        spannedString.setSpan(new ForegroundColorSpan(Color.parseColor("#1E88E5")),0,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannedString);

        textView = pageView.findViewById(R.id.textView1);
        spannedString = new SpannableString(list.get(4));
        spannedString.setSpan(new RelativeSizeSpan(1.75f),0,spannedString.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(spannedString);

        textView = pageView.findViewById(R.id.textView4);
        spannedString = new SpannableString(">累计专注"+list.get(6)+"分钟");
        spannedString.setSpan(new UnderlineSpan(),5,5+String.valueOf(list.get(2)).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannedString);

        textView = pageView.findViewById(R.id.textView5);
        spannedString = new SpannableString(">专注"+list.get(5)+"次");
        spannedString.setSpan(new UnderlineSpan(),3,3+String.valueOf(list.get(3)).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannedString);

    }
    private int resolve(String v){
        int an=0;
        long a = Long.parseLong(v);
        while(a>0){
            an+= (int) (a%1023);//a|1023代替
            a=(a>>>10);
        }
        return an;
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
    private void share(){
//        View view = viewPager.getChildAt(viewPager.getCurrentItem());
        View view = new View(this);
//        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        String path = Environment.getExternalStorageDirectory().toString() + "/" + "统计数据.png";
        FileOutputStream out;
        try {
            out = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        }catch (Exception ignored){}
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
        shareIntent.setType("image/png");
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
        }
}
