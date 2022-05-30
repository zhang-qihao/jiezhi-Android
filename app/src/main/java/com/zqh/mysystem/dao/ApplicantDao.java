package com.zqh.mysystem.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.zqh.mysystem.bean.Applicant;
import com.zqh.mysystem.bean.User;

import java.util.ArrayList;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.dao
 * @interfaceName: ApplicantDao
 * @author: Zhangqihao
 * @description: 求职者数据库增删改查接口
 * @date: 2022/5/21
 */
public interface ApplicantDao {
    // 修改
    public void Update(ContentValues contentValues);
    // 增加
    public boolean Insert(User user);
    // 删除
    public void Delete(User user);
    // 查找
    public ArrayList<Applicant> Select(); // 无参数查找全部
    public Applicant Select(User user); // 根据 account 参数查找
}
