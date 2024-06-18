
package com.example.test5.subActivity;

import static java.lang.Math.abs;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test5.DBOpenHelper;
import com.example.test5.MainActivity;
import com.example.test5.R;
import com.example.test5.adapter.AttendanceTaskListViewAdapter;
import com.example.test5.dataset.AttendanceTaskListViewData;

import java.util.ArrayList;

public class SubAttendance extends AppCompatActivity {

    private Button btnChecking;


    private volatile String[] list = new String[14];
    /*
    0:最后一次登录时间戳
    1:累计完成计划数（实际上不把今日的算进去，以避免反复编辑，显示数据的时候把这与今日的加上去，下同
    2：累计专注次数
    3：累计专注时间（分钟）
    4：今日完成计划数           1
    5：今日专注次数            4
    6：今日专注时间            30
    7：7天的累计完成计划次数，计算方法为：新的一天，原来的数据右移10位，再加上昨天的数据*2^50,即昨天的数据long左移50位加上原来的数据右移10位，加法可以用异	或代替。当然，也不包括今天的数据（不会有傻子一天专注1024次吧？），其实6天
    8：7天的累计专注次数，计算方法同上
    9：7天的累计专注时长分钟（一天专注17将近18个小时你也不需要这b软件了）
    10：累计连续签到天数
    11：上一次签到的时间戳
    //砍掉任务完成的奖励领取，就变为，如果完成了就显示已完成，否则显示进行中，当然，砍掉那个按钮
     */
    ArrayList<AttendanceTaskListViewData> taskList = new ArrayList<>();
    //这里应该还需要创建一个存任务名称数据的表，表名就叫task?
    //如果有这样一个表，那这个表就可以存任务名称、任务进度、任务状态等
    //任务状态的话，我后续都是这么设定的，-1为未完成且未领取奖励，0为已完成但未领取奖励，1为完成且已领取奖励
    //进度的话，值域就从0到100吧,但其实这个进度太抽象了点吧，具体的任务怎么来描述它的进度呢，除非都是有数值的才好表示吧？
    private long timeMills;
    //private final long dayMillSeconds = 86400000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_attendance);
        btnChecking =findViewById(R.id.checkInButton);
        initData();
        init();
        btnChecking.setOnClickListener(v -> {
            upDateContinuousCheckInDate();
            TextView t = findViewById(R.id.checkInCountTextView);
            t.setText(list[10]);
        });
    }


    private void upDateContinuousCheckInDate(){
        btnChecking.setEnabled(false);
        btnChecking.setBackgroundColor(MainActivity.getColorByType(R.color.task_grad));
//        list.set(11,timeMills);
//        list.set(10,list.get(10)+1);
        list[11]=String.valueOf(System.currentTimeMillis());
        list[10] = String.valueOf(Integer.parseInt(list[10])+1);
    }
    //初始化签到数据
    private void initCheckInData(){
//        timeMills = Long.valueOf(System.currentTimeMillis());
//        if (!list.isEmpty()&&(list.get(11)/dayMillSeconds==timeMills/dayMillSeconds)){
//            btnChecking.setEnabled(false);
//        }
//        else{
//            btnChecking.setEnabled(true);
//        }
        btnChecking.setEnabled(MainActivity.getDayBetween(Long.parseLong(list[11]), System.currentTimeMillis()) != 0);
        TextView textView = findViewById(R.id.checkInCountTextView);
        textView.setText(String.valueOf(Integer.parseInt(list[10])));
    }
    //初始化任务数据
    private void initTask(){
        ListView listView = findViewById(R.id.taskRecyclerView);
        ListAdapter attendanceTaskListViewAdapter = new AttendanceTaskListViewAdapter(taskList,this);
        listView.setAdapter(attendanceTaskListViewAdapter);
    }
    //导入数据库数据
    @SuppressLint("Range")
    private void initData(){
        //test();
        Cursor cursor = MainActivity.getSql().query("statistics",new String[]{"data"},"userId=?",new String[]{String.valueOf(MainActivity.userId)},null,null,null,null);
        //listID = cursor.getString(cursor.getColumnIndexOrThrow("ID"));
        int pointer=0;
        while(cursor.moveToNext()){
            //list.add(Long.valueOf(cursor.getString(cursor.getColumnIndex("data"))));
            list[pointer++]=cursor.getString(cursor.getColumnIndex("data"));
        }
        System.out.println(pointer);
        cursor.close();
        //此处的list是把所有的statistics加载了进来
        AttendanceTaskListViewData t = new AttendanceTaskListViewData("完成1个计划",Integer.parseInt(list[4])>0?100:0);
        taskList.add(t);
        //t = new AttendanceTaskListViewData((list.get(5)>=4)?1:0,"今日专注4次","已经专注"+list.get(5)+"次",(list.get(5)>=4)?100:(list.get(5)*100/4));
        int z = Integer.parseInt(list[5]);
        t = new AttendanceTaskListViewData("专注4次",z>=4?100:z*25);
        
        taskList.add(t);
        //t= new AttendanceTaskListViewData((list.get(6)>=30)?1:0,"今日专注30分钟","已经专注"+list.get(6)+"分钟",(list.get(6)>=30)?100:list.get(6)*100/30);
        z = Integer.parseInt(list[6]);
        t = new AttendanceTaskListViewData("专注30分钟",z>=30?100:(int)(z/30.0f*100));
        taskList.add(t);
    }
    //初始化
    private void init(){

        //初始化连续签到天数
        initCheckInData();
        //初始化任务完成情况
        initTask();
    }
//    void test(){
//        for (int i=0;i<=11;i++){
//            list.add((long)0);
//        }
////        list.set(11,System.currentTimeMillis());
//        list.set(11,(long)114514);
//        list.set(4,(long)1);
//        list.set(5,(long)3);
//        list.set(6,(long)15);
//        list.set(10,(long)2);
//    }

    //更新数据

    @Override
    protected void onStop() {
        ContentValues contentValues = new ContentValues();
        //更新list
        for(int i=0;i<14;i++){
            contentValues.put("data",list[i]);
            MainActivity.getSql().update("statistics",contentValues,"userId = ?and dataIndex=?",new String[]{String.valueOf(MainActivity.userId),String.valueOf(i+1)});
        }
        super.onStop();
    }

}
