package com.zqh.mysystem.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zqh.mysystem.bean.Applicant;
import com.zqh.mysystem.bean.HR;
import com.zqh.mysystem.bean.User;

import java.util.ArrayList;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.dao
 * @className: HRImpl
 * @author: Zhangqihao
 * @description: HR 数据库增删改查实现
 * @date: 2022/5/21
 */
public class HRImpl implements HRDao{
    private SQLiteDatabase writableDatabase;
    private static final String TABLE_NAME = "hr";
    private static HRImpl hrImpl;

    // 带 myHelper 参数构造方法
    public HRImpl(MyHelper myHelper) {
        writableDatabase = myHelper.getWritableDatabase();
    }

    public static HRImpl getInstance(MyHelper myHelper) {
        if (hrImpl == null)
            hrImpl = new HRImpl(myHelper);
        return hrImpl;
    }

    // 修改
    public void Update(ContentValues contentValues) {
        writableDatabase.update(TABLE_NAME, contentValues, "account=?", new String[]{contentValues.get("account").toString()});
    }

    // 增加
    public boolean Insert(User user) {
        // user 对象转换成 contentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put("account", user.getAccount());
        contentValues.put("password", user.getPassword());
        if (Select(user) != null) // 数据库中存在此账号
            return false;
        writableDatabase.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    // 删除
    public void Delete(User user) {
        writableDatabase.delete(TABLE_NAME, "account=?", new String[]{user.getAccount()});
    }

    // 查找
    @SuppressLint("Range")
    public ArrayList<HR> Select() { // 无参数查找全部
        ArrayList<HR> hrs = new ArrayList<>();
        Cursor cursor = writableDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext())
            hrs.add(new HR(cursor.getString(cursor.getColumnIndex("account")), cursor.getString(cursor.getColumnIndex("password"))));
        return hrs; // 查询所有行转为数组返回
    }
    @SuppressLint("Range")
    public HR Select(User user) { // 根据 account 参数查找
        HR hr = null;
        Cursor cursor = writableDatabase.query(TABLE_NAME, null, "account=?", new String[]{user.getAccount()}, null, null, null);
        while (cursor.moveToNext())
            hr = new HR(cursor.getString(cursor.getColumnIndex("account")), cursor.getString(cursor.getColumnIndex("password")));
        return hr; // 数据库中有该行就返回该行数据对应的对象，反之不存在则返回 nul
    }
}
