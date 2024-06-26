package com.example.test5.components;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.test5.R;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.Random;

public class CommonNavigationBar extends RadioGroup implements RadioGroup.OnCheckedChangeListener{
    private float childTextSize;
    private ColorStateList childTextColor;
    private ViewPager viewPager;

    private Context mcontext;
    public CommonNavigationBar(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context,attrs);
    }
    public void setMcontext(Context a){
        mcontext=a;
    }
    private void init(Context m, AttributeSet attrs){
        TypedArray z = m.obtainStyledAttributes(attrs, R.styleable.common_navigation_bar);
        childTextSize = z.getDimension(R.styleable.common_navigation_bar_childTextSize, 10.0F);
        childTextColor = z.getColorStateList(R.styleable.common_navigation_bar_childTextColor);
        z.recycle();
        setOnCheckedChangeListener(this);


    }
    public void setViewPager(ViewPager z){
        viewPager = z;
    }

    public void addChild(String a){
        if(mcontext==null) System.out.println("a");
        RadioButton z = new RadioButton(mcontext);
        z.setText(a);
        z.setId(getChildCount());
        z.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_btn_bg));
        z.setTextColor(childTextColor);
        z.setTextSize(childTextSize);
        RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(0, RadioGroup.LayoutParams.WRAP_CONTENT,1.0f);
        z.setLayoutParams(lp);
        z.setButtonDrawable(null);
        z.setGravity(1);
        addView(z);

    }

    public void setSelected(int poi){
        getRadioChildAt(poi).setChecked(true);
    }
    public RadioButton getRadioChildAt(int poi){
        return (RadioButton) getChildAt(poi);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        viewPager.setCurrentItem(checkedId);
        //Toast.makeText(getContext(),getRadioChildAt(0).getLeft()+"\n"+getRadioChildAt(1).getLeft()+"\n",Toast.LENGTH_SHORT).show();
    }
}
