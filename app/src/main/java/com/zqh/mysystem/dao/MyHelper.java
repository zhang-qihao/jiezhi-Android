package com.zqh.mysystem.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.dao
 * @className: MyHelper
 * @author: Zhangqihao
 * @description: 自定义 MyHelper 类继承 SQLiteOpenHelper
 * @date: 2022/5/21
 */
public class MyHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "demo.db";

    public MyHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public MyHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE applicant(id integer primary key autoincrement, account char(20), password char(20), pid char(20))");
        sqLiteDatabase.execSQL("CREATE TABLE hr(id integer primary key autoincrement, account char(20), password char(20), cid char(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}