package com.example.my.studenmanagement.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 单例模式
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;
    public static final String CREATE_ADMIN = "create table admin(id integer primary key autoincrement, " +
            "name text," +
            "password text)";//创建管理员表
    public static final String CREATE_TECHER = "create table techer(id integer  ," +
            " name text," +
            " number integer primary key," +
            "password text)";//创建教师表

    public static final String CREATE_STUDENT = "create table student(id text primary key," +
            "name text," +
            "password text," +
            "sex text,number text," +
            "head integer," +
            "dutyCount integer," +//
            "unDutyCount integer," +
            "classScore integer," +
            "workScore integer," +
            "dayScore integer)";//创建学生表


    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ADMIN);
        db.execSQL(CREATE_TECHER);
        db.execSQL(CREATE_STUDENT);
        db.execSQL("alter table student add  column ranking integer");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(oldVersion==1){
                db.execSQL("alter table student add  column ranking integer");
            }
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context, "StudentManagement.db", null, 2);
        }
        return instance;

    }


}
