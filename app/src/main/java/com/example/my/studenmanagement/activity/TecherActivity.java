package com.example.my.studenmanagement.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.my.studenmanagement.R;

/**
 * 老师的管理界面
 *
 */
public class TecherActivity extends Activity {

    private Button select;//查询学生信息按钮
    private Button add;//添加学生信息按钮
    private Button order;//查看总成绩排名按钮
    private TextView forceOffline;//强制下线

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.admin_layout);
        findViewById(R.id.admin_techer_select).setVisibility(View.GONE);
        findViewById(R.id.admin_techer_add).setVisibility(View.GONE);
        select = (Button) findViewById(R.id.admin_activity_select);
        add = (Button) findViewById(R.id.admin_activity_add);
        order = (Button) findViewById(R.id.admin_activity_order);
        forceOffline = (TextView) findViewById(R.id.admin_activity_forceOffline);
        add.setVisibility(View.GONE);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TecherActivity.this, StudentInfoActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TecherActivity.this, AddStudentInfoActivity.class);
                intent.putExtra("haveData","false");
                startActivity(intent);
            }
        });


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TecherActivity.this, StudentTotalScore.class);
                startActivity(intent);
            }
        });


        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.he.example.OfflineBradcast");
                sendBroadcast(intent);
            }
        });


    }
}
