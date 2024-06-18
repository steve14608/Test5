package com.example.test5.subActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test5.DBOpenHelper;
import com.example.test5.MainActivity;
import com.example.test5.R;
import com.example.test5.components.CircleImageView;
import com.example.test5.manager.AppearanceManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SubCreateTargetActivity extends AppCompatActivity{
    private String[] list=new String[14];
    public static int alertLayoutResourceId = R.layout.component_create_target_alert;
    private AlertDialog alert;
    private AlertDialog.Builder builder;



    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setResult(0);
        setContentView(R.layout.sub_creat_target);


        ((CircleImageView)findViewById(R.id.profilePic)).setImageDrawable(AppearanceManager.profilePic);

        findViewById(R.id.btn0).setOnClickListener(v -> {
            builder = new AlertDialog.Builder(SubCreateTargetActivity.this);
            Map<String,String> map = new HashMap<>();
            map.put("title",null);
            map.put("subTitle",null);
            alert = builder.setView(initAlertView(map)).show();
        });
        ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0).setBackground(AppearanceManager.wallpaper[0]);

        Cursor cursor = MainActivity.getSql().query("statistics",new String[]{"data"},"userId=?",new String[]{String.valueOf(MainActivity.userId)},null,null,null);
        int pointer=0;
        while (cursor.moveToNext()){
            list[pointer++]=cursor.getString(cursor.getColumnIndex("data"));
        }
    }
    private View initAlertView(Map<String,String> da){
        View convertView = LayoutInflater.from(this).inflate(SubCreateTargetActivity.alertLayoutResourceId,null,false);
        EditText title = (EditText)convertView.findViewById(R.id.editTextText);
        title.setText(da.get("title"));
        EditText subTitle = (EditText)convertView.findViewById(R.id.editTextText3);
        subTitle.setText(da.get("subTitle"));
        Spinner spinner = (Spinner)convertView.findViewById(R.id.spinner2);
        //确认按钮，检查完整性后添加到数据库
        convertView.findViewById(R.id.button5).setOnClickListener(v -> {
            //检查合法
            if(title.getText().toString().isEmpty()||subTitle.getText().toString().isEmpty()){
                Toast.makeText(getBaseContext(),"检查是否填写完整数据",Toast.LENGTH_SHORT).show();
            }
            else{
                ContentValues values = new ContentValues();
                values.put("finished",0);
                values.put("type",spinner.getSelectedItemPosition());
                values.put("title",title.getText().toString());
                values.put("subTitle",subTitle.getText().toString());
                values.put("userId", MainActivity.userId);
                values.put("time",System.currentTimeMillis());
                //数据库执行插入命令
                MainActivity.getSql().insert("scheduleList", null, values);
                Toast.makeText(SubCreateTargetActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                updateSta();
                alert.dismiss();
            }
        });
        convertView.findViewById(R.id.button).setOnClickListener(v -> alert.dismiss());
        return convertView;
    }
    public void updateSta(){

        long time1 = Long.parseLong(list[12]);
        long time2 = System.currentTimeMillis();
        System.out.println(MainActivity.getDayBetween(time1,time2));
        if(time2==0||MainActivity.getDayBetween(time1,time2)>0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("data",String.valueOf(Integer.parseInt(list[13])+1));
            MainActivity.getSql().update("statistics",contentValues,"userId=? and dataIndex=?",new String[]{String.valueOf(MainActivity.userId),"14"});
        }
        ContentValues values = new ContentValues();
        values.put("data",String.valueOf(System.currentTimeMillis()));
        MainActivity.getSql().update("statistics",values,"userId=? and dataIndex=?",new String[]{String.valueOf(MainActivity.userId),"13"});

    }
}
