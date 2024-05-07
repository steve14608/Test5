package com.example.test5.subActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test5.DBOpenHelper;
import com.example.test5.R;
import com.example.test5.adapter.GridViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubTargetGridActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DBOpenHelper dbOpenHelper;
    public static int alertLayoutResourceId = R.layout.component_create_target_alert;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private GridViewAdapter gridViewAdapter;
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_target_grid);

        dbOpenHelper = new DBOpenHelper(this,"my.db",null,1);


        gridView = (GridView) findViewById(R.id.gridView);
        gridViewAdapter = new GridViewAdapter(this,gridView);
        gridViewAdapter.addItems(getDataFromDb());
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemSelectedListener(this);

        findViewById(R.id.btn0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getBaseContext());
                Map<String,String> map = new HashMap<>();
                map.put("title",null);
                map.put("subTitle",null);
                alert = builder.setView(initAlertView(map)).show();
                alert.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
            }
        });
    }
    @SuppressLint("Range")
    private ArrayList<String> getDataFromDb(){
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = dbOpenHelper.getWritableDatabase().query("sampleTask",new String[]{"name"},null,null,null,null,null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("name")));
        }
        cursor.close();
        return list;
    }
    private View initAlertView(Map<String,String> da){
        View convertView = getLayoutInflater().inflate(SubTargetGridActivity.alertLayoutResourceId,null,false);
        EditText title = (EditText)convertView.findViewById(R.id.editTextText);
        title.setText(da.get("title"));
        EditText subTitle = (EditText)convertView.findViewById(R.id.editTextText3);
        subTitle.setText(da.get("subTitle"));
        Spinner spinner = (Spinner)convertView.findViewById(R.id.spinner2);
        convertView.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {//确认按钮，检查完整性后添加到数据库
            @Override
            public void onClick(View v) {
                //检查合法
                if(title.getText()==null){
                    Toast.makeText(getBaseContext(),"检查是否填写完整数据",Toast.LENGTH_SHORT).show();
                }
                else{
                    ContentValues values = new ContentValues();
                    values.put("finished",0);
                    values.put("type",spinner.getSelectedItemPosition());
                    values.put("title",title.getText().toString());
                    values.put("subTitle",subTitle.getText().toString());
                    //数据库执行插入命令
                    dbOpenHelper.getWritableDatabase().insert("scheduleList", null, values);
                    alert.dismiss();
                }
            }
        });
        return convertView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        builder = new AlertDialog.Builder(getBaseContext());
        Map<String,String> map = new HashMap<>();
        map.put("title",((TextView)view).getText().toString());
        map.put("subTitle",null);
        alert = builder.setView(initAlertView(map)).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
