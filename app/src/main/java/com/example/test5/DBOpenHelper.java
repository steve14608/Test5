package com.example.test5;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //每个计划的详细数据，scheduleIndex按添加的时间顺序递增，finished为该计划是否完成，type0-3对应那四个类别，title为计划的标题，subTitle为计划的描述，详情去看component_task_list
        db.execSQL("CREATE TABLE scheduleList(scheduleIndex INTEGER PRIMARY KEY AUTOINCREMENT,finished INTEGER,type INTEGER,title TEXT,subTitle TEXT)");
        //主题，coverResourceId为主题的封面图片资源int值，name为主题名字,wallpaperIndex为在该主题的壁纸在表wallpaper中的序号，bottomIconIndex类似
        db.execSQL("CREATE TABLE theme(themeIndex INTEGER PRIMARY KEY AUTOINCREMENT,coverResourceId INTEGER,name TEXT,wallpaperIndex INTEGER,bottomIconIndex INTEGER)");
        //coverResourceId为显示在那个选择封面、主题、头像等界面的封面图，可以理解为简单的预览图，真正的图在ResourceId
        db.execSQL("CREATE TABLE wallpaper(wallpaperIndex INTEGER PRIMARY KEY AUTOINCREMENT,coverResourceId INTEGER,name TEXT,resourceId INTEGER)");
        //
        db.execSQL("CREATE TABLE bottomIcon(iconIndex INTEGER PRIMARY KEY AUTOINCREMENT,coverResourceId INTEGER,name TEXT,RIdOne INTEGER,RIdTwo INTEGER,RIdThr INTEGER,RIdFor INTEGER)");
        db.execSQL("CREATE TABLE profilePic(picIndex INTEGER PRIMARY KEY AUTOINCREMENT,coverResourceId INTEGER,name TEXT,resourceId INTEGER)");
        //看不懂概念图，有待讨论
        //db.execSQL("CREATE TABLE statistics");

        //以下的为还未添加到数据库里的表

        //此为推荐任务的数据集
        db.execSQL("CREATE TABLE sampleTask(name TEXT)");
        //需要对每个dataIndex所代表的值进行解释
        db.execSQL("CREATE TABLE statistics(dataIndex INTEGER PRIMARY KEY AUTOINCREMENT,data INTEGER)");
        //0：累计坚持计划天数
        //1：累计专注分钟
        //2：累计专注次数
        //3：累计完成计划次数
        //4：本周累计坚持计划天数
        //5：本周累计完成计划次数
        //6：本周累计专注分钟时间(分钟)
        //7：本周累计专注分钟
        //8：今日完成计划数
        //9：今日计划完成率
        //10-20：今日任务完成情况
        //21：最后一次访问数据库的月数，见下条
        //22：最后一次访问数据库的天数，这两个是用来判断是否要对每日及每周数据进行更新的依据


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
