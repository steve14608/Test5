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
import com.example.test5.manager.AppearanceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class TopBackBar extends LinearLayout {
    public TopBackBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.component_top_back,this);
        findViewById(R.id.top_back_btn).setOnClickListener(v -> ((Activity)getContext()).finish());
        TypedArray z = context.obtainStyledAttributes(attrs,R.styleable.component_top_back_bar);
        ((TextView)findViewById(R.id.textViewTitle)).setText(z.getString(R.styleable.component_top_back_bar_topTitle));
        z.recycle();
        updateColor();
    }
    public void updateColor(){
        //setBackgroundColor(ContextCompat.getColor(getContext(),AppearanceManager.color));
        setBackgroundColor(AppearanceManager.color);
        //getRootView().setBackgroundColor(AppearanceManager.color);
    }

}
