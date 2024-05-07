package com.example.test5.components;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.test5.R;

public class DecorationGridView extends GridView {
    public DecorationGridView(Context context) {
        super(context);
        setId(R.id.gridView);
        setPadding(10,0,10,0);
        setNumColumns(2);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //setAdapter();
//        setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
    }
}
