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
        db.execSQL("CREATE TABLE scheduleList(scheduleIndex INTEGER PRIMARY KEY AUTOINCREMENT,finished INTEGER,type INTEGER,title TEXT,subTitle TEXT,time INTEGER,userId INTEGER)");
        db.execSQL("CREATE TABLE decoration(userId INTEGER,themeIndex INTEGER,wallpaperIndex INTEGER,bottomIndex INTEGER,profilePicIndex INTEGER)");
//        //主题，coverResourceId为主题的封面图片资源int值，name为主题名字,wallpaperIndex为在该主题的壁纸在表wallpaper中的序号，bottomIconIndex类似
//        db.execSQL("CREATE TABLE theme(themeIndex INTEGER PRIMARY KEY AUTOINCREMENT,coverResourceId INTEGER,name TEXT,color INTEGER)");
//        //coverResourceId为显示在那个选择封面、主题、头像等界面的封面图，可以理解为简单的预览图，真正的图在ResourceId
//        db.execSQL("CREATE TABLE wallpaper(wallpaperIndex INTEGER PRIMARY KEY AUTOINCREMENT,coverResourceId INTEGER,name TEXT,resourceId INTEGER)");
//        //
//        db.execSQL("CREATE TABLE bottomIcon(iconIndex INTEGER PRIMARY KEY AUTOINCREMENT,coverResourceId INTEGER,name TEXT,RIdOne INTEGER,RIdTwo INTEGER,RIdThr INTEGER,RIdFor INTEGER)");
//        db.execSQL("CREATE TABLE profilePic(picIndex INTEGER PRIMARY KEY AUTOINCREMENT,coverResourceId INTEGER,name TEXT,resourceId INTEGER)");
//        //看不懂概念图，有待讨论
//        //db.execSQL("CREATE TABLE statistics");


        //此为推荐任务的数据集
        //需要对每个dataIndex所代表的值进行解释
        db.execSQL("CREATE TABLE statistics(dataIndex INTEGER,data Text,userId INTEGER)");
        //注意，数据库里面的序号是从1开始的
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



        //重置
//        List下标(database从1开始)
//        0:最后一次登录时间戳
//        1:累计完成计划数（实际上不把今日的算进去，以避免反复编辑，显示数据的时候把这与今
//        日的加上去，下同
//        2：累计专注次数
//        3：累计专注时间（分钟）
//        4：今日完成计划数
//        5：今日专注次数
//        6：今日专注时间
//        7：7天的累计完成计划次数，计算方法为：新的一天，原来的数据右移10位，再加上昨天
//        的数据*2^50,即昨天的数据long左移50位加上原来的数据右移10位，加法可以用异	或代替。当然，也不包括今天的数据（不会有傻子一天专注1024次吧？），其实6天
//        8：7天的累计专注次数，计算方法同上
//        9：7天的累计专注时长分钟（一天专注17将近18个小时你也不需要这b软件了）
//        10：累计连续签到天数
//        11：上一次签到的时间戳
//        12:上一次做计划的时间戳
//        13:累计做计划的天数
//砍掉任务完成的奖励领取，就变为，如果完成了就显示已完成，否则显示进行中，当然，砍掉那个按钮


        db.execSQL("CREATE TABLE user(account TEXT,password TEXT,userId INTEGER PRIMARY KEY AUTOINCREMENT)");



        //initDecoration(db);
        //initStatistics(db);




    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
