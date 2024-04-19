package com.example.test5.subActivity;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test5.R;
import com.example.test5.components.CommonNavigationBar;
import com.example.test5.dataset.GridViewDecorationData;
import com.example.test5.adapter.GridViewDecorationAdapter;

public class SubDecorationActivity extends AppCompatActivity {
    private CommonNavigationBar commonNavigationBar;
    private GridView gridView;
    private GridViewDecorationAdapter gridViewDecorationAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_decoration);
        init();
    }
    private void init(){
        commonNavigationBar = (CommonNavigationBar) findViewById(R.id.navigationBar);
        gridView = (GridView) findViewById(R.id.gridView);
        gridViewDecorationAdapter = new GridViewDecorationAdapter(getBaseContext());
        gridView.setAdapter(gridViewDecorationAdapter);

        commonNavigationBar.setMcontext(getBaseContext());

        commonNavigationBar.addChild("主题");
        commonNavigationBar.addChild("壁纸");
        commonNavigationBar.addChild("底部");
        commonNavigationBar.addChild("头像");

        commonNavigationBar.setSelected(0);

        test();
    }
    public void test(){
        GridViewDecorationData z = new GridViewDecorationData(R.color.black,"test",true);
        gridViewDecorationAdapter.add(z);
        z = new GridViewDecorationData(R.color.no_e_n,"test2",false);
        gridViewDecorationAdapter.add(z);
    }
}
