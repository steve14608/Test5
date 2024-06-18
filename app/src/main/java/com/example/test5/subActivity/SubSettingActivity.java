package com.example.test5.subActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test5.AnnoyedFragment.FragmentConcentrate;
import com.example.test5.MainActivity;
import com.example.test5.R;
import com.example.test5.manager.AppearanceManager;

import java.util.ArrayList;

public class SubSettingActivity extends AppCompatActivity {
    SharedPreferences sp;
    private Context mContext;

    //0为默认专注时长
    //1为默认休息时长
    //2为连续专注次数
    private ArrayList<Integer> dataList = new ArrayList<>();        //当前值
//    private ArrayList<Integer> originalDataList = new ArrayList<>();//默认值
    private static final int[] originalDataList ={60,10,2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(0);
        setContentView(R.layout.sub_setting);
        setResult(0);
        init();
        setOnClickListener();
    }

    private void init() {
        mContext = this;
        sp = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        dataList.add(sp.getInt("defaultDuration", 60));
        dataList.add(sp.getInt("breakTime", 10));
        dataList.add(sp.getInt("concentratedTimes", 2));
        ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0).setBackground(AppearanceManager.wallpaper[0]);
    }

    private void savePreference() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("defaultDuration", dataList.get(0));
        editor.putInt("breakTime", dataList.get(1));
        editor.putInt("concentratedTimes", dataList.get(2));
        editor.apply();
        setResult(1);
    }

    public void showAlertDialog(Context context, String title, int dataType, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.component_number_picker);
        AlertDialog alert = builder.create();
        alert.setTitle(title);


        alert.show();
        TextView textView = alert.findViewById(R.id.dialog_title);
        textView.setText(message);
        NumberPicker numberPicker = alert.findViewById(R.id.dialog_number_picker);
        switch (dataType) {
            case 2:
                numberPicker.setMinValue(1);
                numberPicker.setValue(2);
                numberPicker.setMaxValue(10);
                break;
            default:
                numberPicker.setMinValue(5);
                numberPicker.setValue(5);

                numberPicker.setMaxValue(180);
                break;
        }
        numberPicker.setWrapSelectorWheel(false);

        alert.findViewById(R.id.dialog_cancel).setOnClickListener(v -> alert.dismiss());
        alert.findViewById(R.id.dialog_reset).setOnClickListener(v -> {
            dataList.set(dataType, originalDataList[dataType]);
            savePreference();
            alert.dismiss();
        });
        alert.findViewById(R.id.dialog_ok).setOnClickListener(v -> {
            dataList.set(dataType, numberPicker.getValue());
            savePreference();

            alert.dismiss();
        });
    }

    private void setOnClickListener() {
        findViewById(R.id.defaultDuration).setOnClickListener(v -> showAlertDialog(mContext, "修改默认专注时长", 0, "请输入修改的时长(单位:分钟)"));
        findViewById(R.id.breakTime).setOnClickListener(v -> showAlertDialog(mContext, "修改默认休息时长", 1, "请输入修改的时长(单位:分钟)"));
        findViewById(R.id.concentratedTimes).setOnClickListener(v -> showAlertDialog(mContext, "修改连续专注次数", 2, "请输入修改的次数(单位:次)"));

    }

    //默认专注时长defaultDuration
    //默认休息时长breakTime
    //连续专注次数concentratedTimes
}