package com.example.my.studenmanagement.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my.studenmanagement.R;
import com.example.my.studenmanagement.tools.DatabaseHelper;

/**
 * 添加学生信息的界面,修改学生信息的界面
 */
public class AddStudentInfoActivity extends Activity {

    private EditText name;
    private EditText sex;
    private EditText id;
    private EditText number;
    private EditText password;
    private EditText math;
    private EditText chinese;
    private EditText english;

    private String oldID;//用于防治修改信息时将ID也修改了，而原始的有该ID的学生信息还保存在数据库中


    private Button sure;//确定按钮
    private DatabaseHelper dbHelper;
    Intent oldData;
    private int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_student_info_layout);

        name = (EditText) findViewById(R.id.add_student_layout_name);
        sex = (EditText) findViewById(R.id.add_student_layout_sex);
        id = (EditText) findViewById(R.id.add_student_layout_id);
        number = (EditText) findViewById(R.id.add_student_layout_number);
        password = (EditText) findViewById(R.id.add_student_layout_password);
        math = (EditText) findViewById(R.id.add_student_layout_math);
        chinese = (EditText) findViewById(R.id.add_student_layout_chinese);
        english = (EditText) findViewById(R.id.add_student_layout_english);

        dbHelper = DatabaseHelper.getInstance(this);

        oldData = getIntent();
        if (oldData.getStringExtra("haveData").equals("true")) {
            type = getIntent().getIntExtra("type", 1);
            if (type == 2) {
                id.setEnabled(false);
                name.setEnabled(false);
                sex.setEnabled(false);
                password.setEnabled(false);
                number.setEnabled(false);
                password.setVisibility(View.GONE);
                findViewById(R.id.password).setVisibility(View.GONE);
            }
            initInfo();//恢复旧数据
        }


        sure = (Button) findViewById(R.id.add_student_layout_sure);
        //将数据插入数据库
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sex不能为空否则程序崩溃，因为在StudentAdapter中有一个ImageView要设置图片
                //我这里要求id,name,sex都不能为空
                String id_ = id.getText().toString();
                String name_ = name.getText().toString();
                String sex_ = sex.getText().toString();
                String password_ = password.getText().toString();
                String number_ = number.getText().toString();
                String classScore = math.getText().toString();
                String workScore = chinese.getText().toString();
                String dayScore = english.getText().toString();

                if (!TextUtils.isEmpty(id_) && !TextUtils.isEmpty(name_) && !TextUtils.isEmpty(sex_)) {

                    if (sex_.matches("[女|男]")) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.beginTransaction();//开启事务
                        db.execSQL("delete from student where id=?", new String[]{oldID});//删除旧数据

                        //判断学号是否重复
                        Cursor cursor = db.rawQuery("select * from student where id=?", new String[]{id_});
                        if (cursor.moveToNext()) {
                            Toast.makeText(AddStudentInfoActivity.this, "已有学生使用该学号,请重新输入", Toast.LENGTH_SHORT).show();
                        } else {
                            db.execSQL("insert into student(id,name,sex,password,number,classScore,workScore,dayScore) values(?,?,?,?,?,?,?,?)", new String[]{id_, name_, sex_, password_, number_, classScore, workScore, dayScore,});
                            db.setTransactionSuccessful();//事务执行成功
                            db.endTransaction();//结束事务
                            Intent intent = null;
                            if (type == 2) {
                                Toast.makeText(AddStudentInfoActivity.this, "成绩已修改", Toast.LENGTH_SHORT).show();
                                intent = new Intent(AddStudentInfoActivity.this, TecherActivity.class);
                            } else {
                                intent = new Intent(AddStudentInfoActivity.this, AdminActivity.class);
                            }
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(AddStudentInfoActivity.this, "请输入正确的性别信息", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(AddStudentInfoActivity.this, "姓名，学号，性别均不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //恢复旧数据
    private void initInfo() {
        String oldName = oldData.getStringExtra("name");
        name.setText(oldName);
        String oldSex = oldData.getStringExtra("sex");
        sex.setText(oldSex);
        String oldId = oldData.getStringExtra("id");
        oldID = oldId;
        id.setText(oldId);
        String oldNumber = oldData.getStringExtra("number");
        number.setText(oldNumber);
        String oldPassword = oldData.getStringExtra("password");
        password.setText(oldPassword);
        int classScore = oldData.getIntExtra("classScore", 0);
        math.setText(String.valueOf(classScore));
        int workScore = oldData.getIntExtra("workScore", 0);
        chinese.setText(String.valueOf(workScore));
        int dayScore = oldData.getIntExtra("dayScore", 0);
        english.setText(String.valueOf(dayScore));
    }


}
