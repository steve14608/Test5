//import android.content.Context;
//import android.content.SharedPreferences;
//
//public class Learn_SQL {
//    DBOpenHelper dbsqLiteOpenHelper = new DBOpenHelper(MainActivity.this,"users.db",null,1);
//    final SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
//
//    Button button1 = findViewById(R.id.button2); //add
//        button1.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            //创建存放数据的ContentValues对象
//            ContentValues values = new ContentValues();
//            values.put("username","test");
//            values.put("password","123456");
//            values.put("age",21);
//            //数据库执行插入命令
//            db.insert("user", null, values);
//        }
//    });
//    Button button2 = findViewById(R.id.button3); //删
//        button2.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            db.delete("user", "username=?", new String[]{"test"});
//        }
//    });
//    Button button3= findViewById(R.id.button4); //改
//        button3.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            ContentValues values2 = new ContentValues();
//            values2.put("username", "admin123");
//            db.update("user", values2, "username=?", new String[]{"admin"});
//        }
//    });
//    Button button4 = findViewById(R.id.button5); //查
//        button4.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            //创建游标对象
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
//        }
//    });
//        public void save(){
//            SharedPreferences sp  = context.getShaedPreferences("mysp", Context.PRINT_SERVICE);
//            Editor editor = sp.edit();
//            editor.putString("ad",asd);
//            editor.apply();
//        }
//        public void read(){
//            SharedPreferences sp  = context.getShaedPreferences("mysp", Context.PRINT_SERVICE);
//            sp.getString();
//        }
//public void cal(View source)
//        {
//        // 创建消息
//        Message msg = new Message();
//        msg.what = 0x123;
//        Bundle bundle = new Bundle();
//        bundle.putInt(UPPER_NUM ,
//        Integer.parseInt(etNum.getText().toString()));
//        msg.setData(bundle);
//        // 向新线程中的Handler发送消息
//        calThread.mHandler.sendMessage(msg);
//        }
//}
//public class CalPrime extends Activity
//{
//    static final String UPPER_NUM = "upper";
//    EditText etNum;
//    CalThread calThread;
//    // 定义一个线程类
//    class CalThread extends Thread
//    {
//        public Handler mHandler;
//
//        public void run()
//        {
//            Looper.prepare();
//            mHandler = new Handler()
//            {
//                // 定义处理消息的方法
//                @Override
//                public void handleMessage(Message msg)
//                {
//                    if(msg.what == 0x123)
//                    {
//                        int upper = msg.getData().getInt(UPPER_NUM);
//                        List<Integer> nums = new ArrayList<Integer>();
//                        // 计算从2开始、到upper的所有质数
//                        outer:
//                        for (int i = 2 ; i <= upper ; i++)
//                        {
//                            // 用i处于从2开始、到i的平方根的所有数
//                            for (int j = 2 ; j <= Math.sqrt(i) ; j++)
//                            {
//                                // 如果可以整除，表明这个数不是质数
//                                if(i != 2 && i % j == 0)
//                                {
//                                    continue outer;
//                                }
//                            }
//                            nums.add(i);
//                        }
//                        // 使用Toast显示统计出来的所有质数
//                        Toast.makeText(CalPrime.this , nums.toString()
//                                , Toast.LENGTH_LONG).show();
//                    }
//                }
//            };
//            Looper.loop();
//        }
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        etNum = (EditText)findViewById(R.id.etNum);
//        calThread = new CalThread();
//        // 启动新线程
//        calThread.start();
//    }
//    // 为按钮的点击事件提供事件处理函数
//    public void cal(View source)
//    {
//        // 创建消息
//        Message msg = new Message();
//        msg.what = 0x123;
//        Bundle bundle = new Bundle();
//        bundle.putInt(UPPER_NUM ,
//                Integer.parseInt(etNum.getText().toString()));
//        msg.setData(bundle);
//        // 向新线程中的Handler发送消息
//        calThread.mHandler.sendMessage(msg);
//    }
//}
//getActivity.runOnUiThread(new Runnable() {
//public void run() {
//        ...
//        }
//        });
//SpannableStringBuilder ssb =new SpannableStringBuilder(content);
//        ssb.setSpan(newUnderlineSpan(), start, start + sub.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        tv.setText(ssb);


