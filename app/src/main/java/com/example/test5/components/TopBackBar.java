package com.example.test5.components;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test5.MainActivity;
import com.example.test5.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TopBackBar extends LinearLayout {
    public static int backgroundColor;
    public TopBackBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.component_top_back,this);
        findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = MainActivity.getActivity(getRootView());
                act.finish();
            }
        });
        TypedArray z = context.obtainStyledAttributes(attrs,R.styleable.component_top_back_bar);
        ((TextView)findViewById(R.id.textViewTitle)).setText(z.getString(R.styleable.component_top_back_bar_topTitle));
        z.recycle();

    }

}
