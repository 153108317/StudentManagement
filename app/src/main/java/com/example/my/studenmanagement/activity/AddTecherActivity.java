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
 * 添加老师信息的界面,修改老师信息的界面
 *
 */
public class AddTecherActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_techer_info_layout);

        name = (EditText) findViewById(R.id.add_student_layout_name);
        password = (EditText) findViewById(R.id.add_student_layout_password);
        number = (EditText) findViewById(R.id.add_student_layout_id);
        dbHelper = DatabaseHelper.getInstance(this);

        oldData = getIntent();
        if (oldData.getStringExtra("haveData").equals("true")) {
            initInfo();//恢复旧数据
        }


        sure = (Button) findViewById(R.id.add_student_layout_sure);
        //将数据插入数据库
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sex不能为空否则程序崩溃，因为在StudentAdapter中有一个ImageView要设置图片
                //我这里要求id,name,sex都不能为空
                String name_ = name.getText().toString();
                String password_ = password.getText().toString();
                String number_=number.getText().toString();

                if (!TextUtils.isEmpty(name_) && !TextUtils.isEmpty(number_) && !TextUtils.isEmpty(password_)) {

                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.beginTransaction();//开启事务
                        db.execSQL("delete from techer where number=?", new String[]{oldID});//删除旧数据

                        //判断学号是否重复
                        Cursor cursor = db.rawQuery("select * from techer where number=?", new String[]{number_});
                        if (cursor.moveToNext()) {
                            Toast.makeText(AddTecherActivity.this, "已有教师使用该编号,请重新输入", Toast.LENGTH_SHORT).show();
                        } else {
                            db.execSQL("insert into techer(name,password,number) values(?,?,?)", new String[]{name_, password_,number_ });
                            db.setTransactionSuccessful();//事务执行成功
                            db.endTransaction();//结束事务
                            Intent intent = new Intent(AddTecherActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }



                } else {
                    Toast.makeText(AddTecherActivity.this, "姓名，密码均不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //恢复旧数据
    private void initInfo() {
        String oldName = oldData.getStringExtra("name");
        name.setText(oldName);
//        String oldSex = oldData.getStringExtra("sex");
//        sex.setText(oldSex);
        String oldId =oldData.getStringExtra("number");
        oldID = oldId;
//        id.setText(oldId);
        String oldNumber = oldData.getStringExtra("number");
        number.setText(oldNumber);
        String oldPassword = oldData.getStringExtra("password");
        password.setText(oldPassword);
//        int classScore = oldData.getIntExtra("classScore", 0);
//        math.setText(String.valueOf(classScore));
//        int workScore = oldData.getIntExtra("workScore", 0);
//        chinese.setText(String.valueOf(workScore));
//        int dayScore = oldData.getIntExtra("dayScore", 0);
//        english.setText(String.valueOf(dayScore));
    }


}
