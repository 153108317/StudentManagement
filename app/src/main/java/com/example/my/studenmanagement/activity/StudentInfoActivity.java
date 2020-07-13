package com.example.my.studenmanagement.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.my.studenmanagement.R;
import com.example.my.studenmanagement.tools.Student;
import com.example.my.studenmanagement.tools.StudentAdapter;
import com.example.my.studenmanagement.tools.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示学生信息的activity
 *
 */
public class StudentInfoActivity extends Activity {
    private List<Student> studentList = new ArrayList<Student>();
    private DatabaseHelper dbHelper;
    private ListView listView;
    private StudentAdapter adapter;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.studentinfo_activity_layout);
        dbHelper = DatabaseHelper.getInstance(this);
        initStudent();//从数据库中检索所有学生信息
        adapter = new StudentAdapter(StudentInfoActivity.this, R.layout.student_item, studentList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        type=getIntent().getIntExtra("type",1);


        //listView点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Student student = studentList.get(position);//捕获学生实例
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentInfoActivity.this);
                LayoutInflater factory = LayoutInflater.from(StudentInfoActivity.this);
                final View textEntryView = factory.inflate(R.layout.stundent_info_layout, null);//加载AlertDialog自定义布局
                builder.setView(textEntryView);
                builder.setTitle("请选择相关操作");

                Button selectInfo = (Button) textEntryView.findViewById(R.id.student_info_select);//查看学生详细信息按钮
                selectInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //再次弹出一个alertDialog，用于显示详细学生信息
                        AlertDialog.Builder select_builder = new AlertDialog.Builder(StudentInfoActivity.this);
                        select_builder.setTitle("学生详细信息");
                        StringBuilder sb = new StringBuilder();
                        sb.append("姓名：" + student.getName() + "\n");
                        sb.append("学号：" + student.getId() + "\n");
                        sb.append("手机号：" + student.getNumber() + "\n");
                        int math = student.getClassScore();//数学成绩
                        sb.append("课堂成绩：" + math + "\n");
                        int chinese = student.getWorkScore();
                        sb.append("作业成绩：" + chinese + "\n");
                        int english = student.getDayScore();
                        sb.append("平时成绩：" + english + "\n");
//                        int sum = math + chinese + english;//总成绩
//                        sb.append("总评成绩：" + sum/3 + "\n");
//                        sb.append("排名："+student.getOrder()+"\n");
                        select_builder.setMessage(sb.toString());
                        select_builder.create().show();

                    }
                });


                //删除学生信息
                Button delete_info = (Button) textEntryView.findViewById(R.id.student_info_delete);
                if(type==2){
                    delete_info.setVisibility(View.GONE);
                }
                delete_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder delete_builder = new AlertDialog.Builder(StudentInfoActivity.this);
                        delete_builder.setTitle("警告！！！！");
                        delete_builder.setMessage("您将删除该学生信息，此操作不可逆，请谨慎操作！");

                        delete_builder.setNegativeButton("取消", null);
                        delete_builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.execSQL("delete from student where id=?", new String[]{student.getId()});
                                studentList.remove(position);//移除
                                adapter.notifyDataSetChanged();//刷新列表

                            }
                        });
                        delete_builder.create().show();

                    }
                });

                //修改学生信息,通过intent传递旧学生信息
                Button update_info = (Button) textEntryView.findViewById(R.id.student_info_update);
                update_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到添加学生信息的界面,通过intent传递数据
                        Intent intent = new Intent(StudentInfoActivity.this, AddStudentInfoActivity.class);
                        intent.putExtra("haveData", "true");
                        intent.putExtra("name", student.getName());
                        intent.putExtra("sex", student.getSex());
                        intent.putExtra("id", student.getId());
                        intent.putExtra("number", student.getNumber());
                        intent.putExtra("password", student.getPassword());
                        intent.putExtra("classScore", student.getClassScore());
                        intent.putExtra("workScore", student.getWorkScore());
                        intent.putExtra("dayScore", student.getDayScore());
                        intent.putExtra("type",type);
                        startActivity(intent);
                    }
                });

                builder.create().show();
            }
        });

    }

    //初始化学生信息
    private void initStudent() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from student order by id", null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            int classScore = cursor.getInt(cursor.getColumnIndex("classScore"));
            int workScore = cursor.getInt(cursor.getColumnIndex("workScore"));
            int dayScore = cursor.getInt(cursor.getColumnIndex("dayScore"));
            int order=cursor.getInt(cursor.getColumnIndex("ranking"));
            studentList.add(new Student(name, sex, id, password, number, classScore, workScore, dayScore,order,0,0,0));
        }
        cursor.close();


    }


}
