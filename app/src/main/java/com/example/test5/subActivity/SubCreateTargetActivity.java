package com.example.test5.subActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
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
import com.example.test5.R;
import com.example.test5.components.CircleImageView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SubCreateTargetActivity extends AppCompatActivity{
    public static int profilePic;
    private DBOpenHelper dbOpenHelper;


//    private View alertView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_creat_target);

        dbOpenHelper = new DBOpenHelper(this,"my.db",null,1);
//        CircleImageView cir =
        ((CircleImageView) findViewById(R.id.profilePic)).setImageResource(SubCreateTargetActivity.profilePic);


    }
}
