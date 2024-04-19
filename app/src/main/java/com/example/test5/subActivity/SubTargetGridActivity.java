package com.example.test5.subActivity;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test5.R;
import com.example.test5.adapter.GridViewAdapter;

public class SubTargetGridActivity extends AppCompatActivity {
    private GridViewAdapter gridViewAdapter;
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_target_grid);

        gridView = (GridView) findViewById(R.id.sub_target_grid_gridView);
        gridViewAdapter = new GridViewAdapter(this,gridView);
        gridView.setAdapter(gridViewAdapter);

    }
}
