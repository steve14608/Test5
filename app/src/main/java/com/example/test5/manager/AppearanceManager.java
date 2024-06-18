package com.example.test5.manager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.test5.DBOpenHelper;
import com.example.test5.MainActivity;
import com.example.test5.R;
import com.example.test5.dataset.ResourcesData;

import java.util.ArrayList;

public class AppearanceManager{
    public static int color=0;
    public static Drawable profilePic;
    public static Drawable[] bottom = new Drawable[4];
    public static Drawable[] wallpaper = new Drawable[4];

    //处理范围：四个annoyedFragment,topBack的backgroundColor,navigationBar的四个图标及navigation的背景,component_create_alert的color，ProfilePic，
    @SuppressLint("Range")
    public static void init(DBOpenHelper dbOpenHelper){
        Cursor cursor = dbOpenHelper.getReadableDatabase().query("decoration",new String[]{"themeIndex","wallpaperIndex","bottomIndex","profilePicIndex"},"userId=?",new String[]{String.valueOf(MainActivity.userId)},null,null,null);
        while (cursor.moveToNext()){
            updateProfilePic(cursor.getInt(cursor.getColumnIndex("profilePicIndex")));
            updateWallpaper(cursor.getInt(cursor.getColumnIndex("wallpaperIndex")));
            updateBottom(cursor.getInt(cursor.getColumnIndex("bottomIndex")));
            updateTheme(cursor.getInt(cursor.getColumnIndex("themeIndex")));
        }


//        //头图
//        updateProfilePic(sp.getInt("profilePicIndex",0));
//
//        //主题颜色
//        updateTheme(sp.getInt("themeIndex",0));
//
//        //背景
//        updateBottom(sp.getInt("bottomIndex",0));
//
//        //wallpaper = sp.getInt("wallpaperId",R.drawable.)
//        updateWallpaper(sp.getInt("wallpaperIndex",0));
    }
    private static Object getResources(String type,int index){
        if(type.startsWith("c")){
            return Color.parseColor((String)( ((Object[]) (MainActivity.customAssetsManager.getColorList()[index].data)) [0] ) );
        }
        else if(type.startsWith("p")){
            return new BitmapDrawable((Bitmap)( ((Object[]) (MainActivity.customAssetsManager.getProfilePicList()[index].data)) [0] ) );
        }
        else if(type.startsWith("b")){
            ArrayList<Drawable> list = new ArrayList<>();
            for( Object i : (Object[])(MainActivity.customAssetsManager.getBottomList()[index].data)){
                list.add(new BitmapDrawable((Bitmap)i));
            }
            return list.toArray(new Drawable[0]);
        }
        ArrayList<Drawable> list = new ArrayList<>();
        for( Object i : (Object[])(MainActivity.customAssetsManager.getWallpaperList()[index].data)){
            list.add(new BitmapDrawable((Bitmap)i));
        }
        return list.toArray(new Drawable[0]);
    }

    public void updateAll(){

    }
    public static void updateWallpaper(int index){
        wallpaper = (Drawable[])getResources("wallpaper",index);
    }
    public static void updateBottom(int index){
        bottom = (Drawable[]) getResources("bottom",index);
    }
    public static void updateProfilePic(int index){
        profilePic = (Drawable) getResources("profilePic",index);
    }
    public static void updateTheme(int index){
        color = (int)getResources("color",index);
    }
}
