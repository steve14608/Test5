package com.example.test5.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.test5.R;
import com.example.test5.adapter.CustomFragmentAdapter;


public class NavigationFragment extends Fragment {

    protected int resourceId;
    protected ViewPager viewPager;
    public NavigationFragment( int resourceId,ViewPager viewPager){
        super();
        this.resourceId=resourceId;
        this.viewPager=viewPager;
    }
    public NavigationFragment(int resourceId){
        super();
        this.resourceId=resourceId;
    }
    public View initViewFromDatabase(View v){
        //            Cursor cursor = db.query("user", new String[]{"id","username","age"}, "username=?or age =?", new String[]{"test","20"}, null, null, null);
//            //利用游标遍历所有数据对象
//            while(cursor.moveToNext()){
//                String id = cursor.getString(cursor.getColumnIndex("id"));
//                String username = cursor.getString(cursor.getColumnIndex("username"));
//                int age = cursor.getInt(cursor.getColumnIndex("age"));
//                Log.i("Mainactivity","result: id="  + id +" username: " + username +"  age:" + age);
//            }
//            // 关闭游标，释放资源
//            cursor.close();
        return v;
    }
    public View initViewFromPreference(View v){
        //SharedPreferences sp = v.getContext().getSharedPreferences("preference", Context.MODE_PRIVATE);
        //sp.getString("a");
        return v;
    }
    public void savePreference(){
//        try{
//            SharedPreferences sp = getContext().getSharedPreferences("preference", Context.MODE_PRIVATE);
//            SharedPreferences.Editor edit = sp.edit();
//            //edit.putString()
//            edit.apply();
//        }catch(NullPointerException e){
//            Toast.makeText(getContext(),"ERROR,getView() return a null object",Toast.LENGTH_SHORT).show();
//        }

    }
    public View addListener(View v){
        return v;
    }

}
