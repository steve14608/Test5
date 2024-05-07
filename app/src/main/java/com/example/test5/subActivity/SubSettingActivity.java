package com.example.test5.subActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test5.MainActivity;
import com.example.test5.R;

public class SubSettingActivity extends AppCompatActivity {
    private Button btnBack;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_setting);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubSettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        sp = getSharedPreferences("Preference", Context.MODE_PRIVATE);


    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong("defaultDuration",18000000);//默认专注时长为18000毫秒
        /*
        还有其他三个



        */
        edit.apply();
    }
}