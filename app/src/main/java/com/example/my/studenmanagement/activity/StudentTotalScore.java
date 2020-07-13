package com.example.my.studenmanagement.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.example.my.studenmanagement.R;
import com.example.my.studenmanagement.tools.Student;
import com.example.my.studenmanagement.tools.StudentScoreAdapter;
import com.example.my.studenmanagement.tools.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示学生总成绩排名的activity
 *
 */
public class StudentTotalScore extends Activity {
    private ListView total_score;
    private List<Student> list = new ArrayList<Student>();
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.student_toal_score_layout);
        dbHelper = DatabaseHelper.getInstance(this);
        initInfo();//初始化数据
        StudentScoreAdapter adapter = new StudentScoreAdapter(StudentTotalScore.this, R.layout.student_score_item, list);
        total_score = (ListView) findViewById(R.id.total_list_view);
        total_score.setAdapter(adapter);

    }


    //初始化数据
    private void initInfo() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from student order by classScore+workScore+dayScore desc", null);
        int i = 0;
        while (cursor.moveToNext()) {
            i++;
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            int classScore = cursor.getInt(cursor.getColumnIndex("classScore"));
            int workScore = cursor.getInt(cursor.getColumnIndex("workScore"));
            int dayScore = cursor.getInt(cursor.getColumnIndex("dayScore"));
            db.execSQL("update  student set ranking=? where id=?",new String[]{String.valueOf(i),id});//将排名插入数据库
            list.add(new Student(name, sex, id, password, number,  classScore, workScore, dayScore,i,0,0,0));
        }
        cursor.close();

    }


}
