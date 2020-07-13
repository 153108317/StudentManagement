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
 * 老师登录界面
 *
 */
public class TecherLoginActivity extends Activity {
    private EditText name;
    private EditText password;
    private Button login;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.techer_login_layout);

        name = (EditText) findViewById(R.id.student_login_activity_name_input);
        password = (EditText) findViewById(R.id.student_login_activity_password_input);
        login = (Button) findViewById(R.id.student_login_activity_login);

        dbHelper = DatabaseHelper.getInstance(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String studentId = name.getText().toString();
                String studentPassword = password.getText().toString();
                if (!TextUtils.isEmpty(studentId) && !TextUtils.isEmpty(studentPassword)) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor cursor = db.rawQuery("select password from techer where name=?", new String[]{studentId});
                    if (cursor.moveToNext()) {
                        String password = cursor.getString(cursor.getColumnIndex("password"));
                        if (password.equals(studentPassword)) {
                            //启动教师登录后的界面并将教师的账户（id）传过去
                            Intent intent = new Intent(TecherLoginActivity.this, TecherActivity.class);
                            intent.putExtra("id", name.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(TecherLoginActivity.this, "密码错误请重新输入", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(TecherLoginActivity.this, "该教师未注册，请联系管理员", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(TecherLoginActivity.this, "帐户，密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
