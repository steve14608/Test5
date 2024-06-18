package com.example.test5.subActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test5.DBOpenHelper;
import com.example.test5.MainActivity;
import com.example.test5.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

public class SubLoginActivity extends AppCompatActivity {
    private DBOpenHelper dbOpenHelper;
    EditText account;
    EditText password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_login);
        init();
        //test();
    }
    @SuppressLint("Range")
    private void init(){
        dbOpenHelper = new DBOpenHelper(this,"my.db",null,1);
        account = (EditText) findViewById(R.id.editTextText2);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        //登录
        findViewById(R.id.button8).setOnClickListener(v -> {
            boolean isfounded = false;
            int userId=1;
            if(isIllegal()){
                Toast.makeText(this,"请填写账号与密码",Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor cursor = dbOpenHelper.getWritableDatabase().query("user",new String[]{"account","password","userId"},"account=?and password=?",new String[]{account.getText().toString(),password.getText().toString()},null,null,null);

            while(cursor.moveToNext()){
                isfounded = true;
                userId=cursor.getInt(cursor.getColumnIndex("userId"));
            }
            cursor.close();
            if(isfounded){
                Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(SubLoginActivity.this, MainActivity.class));
                start(userId);
                finish();
                return;
            }
            Toast.makeText(this,"账号或者密码错误",Toast.LENGTH_SHORT).show();
            //获取account和password，先判断是否留空,从数据库里查，没有弹出toast，有则登录

        });
        //注册
        findViewById(R.id.button9).setOnClickListener(v -> {
            boolean isExisted = false;
            if(isIllegal()){
                Toast.makeText(this,"请填写账号与密码",Toast.LENGTH_SHORT).show();
                return;
            }
            //检测账号是否已经存在
            Cursor cursor = dbOpenHelper.getWritableDatabase().query("user",new String[]{"account"},"account=?",new String[]{account.getText().toString()},null,null,null);
            while(cursor.moveToNext()){
                isExisted = true;
            }
            if(isExisted){
                Toast.makeText(this,"该账号已被人注册啦",Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put("account",account.getText().toString());
            contentValues.put("password",password.getText().toString());
            dbOpenHelper.getWritableDatabase().insert("user",null,contentValues);

            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();

            cursor = dbOpenHelper.getWritableDatabase().query("user",new String[]{"userId"},"account=?",new String[]{account.getText().toString()},null,null,null);

            int userId=0;
            while(cursor.moveToNext()){
                userId = cursor.getInt(cursor.getColumnIndex("userId"));
            }
            initStatistics(userId);
            start(userId);

            finish();
        });
    }
    private void start(int userId){

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }
    private void test(){
        //System.out.println(getResources().getStringArray(R.array.task).length);
//        try{
//            File file = new File("1.txt");
//            if(!file.exists())file.createNewFile();
//            FileWriter fileWriter = new FileWriter(file);
//            fileWriter.write(1);
//            fileWriter.close();
//        }catch (Exception e){e.printStackTrace();}
//        try{
//            for(String z: getAssets().list("color/")){
//                System.out.println(z);
//                InputStream inputStream =  getAssets().open("color/"+z);
//                Reader reader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(reader);
//                System.out.println(bufferedReader.readLine());
//            }
//        }catch(Exception e){e.printStackTrace();}
//        try{
//            JSONArray js = new JSONArray("[1,23]");
//            System.out.println(js.get(0));
//        }catch(Exception e){e.printStackTrace();}
//        ArrayList<String> list = new ArrayList<>();
//        list.add("aa");
//        list.add("b");
//        String[] z = list.toArray(new String[0]);
//        System.out.println(z[0]);
//        class Data{
//            int a;
//            int b;
//            Data(int a,int b){
//                this.a=a;
//                this.b=b;
//            }
//        }
//        ArrayList<Data> list = new ArrayList<>();
//        list.add(new Data(1,2));
//        list.add(new Data(3,4));
//        Data[] da = list.toArray(new Data[0]);
//        System.out.println(da[0].a+" "+da[0].b);
    }
    private boolean isIllegal(){
        return account.getText().toString().isEmpty()||password.getText().toString().isEmpty();
    }
    private void initStatistics(int userId){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("data","0");
        contentValues.put("userId",userId);
        for(int i=0;i<14;i++){
            contentValues.put("dataIndex",i+1);
            db.insert("statistics",null,contentValues);
        }
        contentValues = new ContentValues();
        contentValues.put("themeIndex",0);
        contentValues.put("bottomIndex",0);
        contentValues.put("wallpaperIndex",0);
        contentValues.put("profilePicIndex",0);
        contentValues.put("userId",userId);
        db.insert("decoration",null,contentValues);
    }
}
