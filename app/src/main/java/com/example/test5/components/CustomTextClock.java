package com.example.test5.components;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextClock;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.test5.R;

import java.util.Calendar;
import java.util.Date;

public class CustomTextClock extends TextClock {
    private static boolean isDay = true;
    public CustomTextClock(Context context) {
        super(context);
        setBackground(ContextCompat.getDrawable(getContext(),R.drawable.day));
    }
    public CustomTextClock(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        setBackground(ContextCompat.getDrawable(getContext(),R.drawable.day));
    }
    @Override
    public void refreshTime() {
        super.refreshTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        boolean z = 6<=h&&h<18;
        if(isDay!=z){
            isDay=z;
            setBackground(isDay ? ContextCompat.getDrawable(getContext(), R.drawable.day):ContextCompat.getDrawable(getContext(),R.drawable.night));
        }
    }

}
