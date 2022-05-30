package com.zqh.mysystem.utils;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

// TODO: Android 通过 HTTP 访问服务器数据库 【https://blog.csdn.net/weixin_43518544/article/details/107965571】

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.utils
 * @className: DBUtil
 * @author: Zhangqihao
 * @description: 数据库工具类：连接数据库用、获取数据库数据用
 * @date: 2022/5/29
 */
public class DBUtil {
    private static final String DRIVER = "com.mysql.jdbc.Driver"; // MySql驱动

    private static final String USER = "root"; // 用户名

    private static final String PASSWORD = "zqh20020111"; // 密码

    private static final String TABLE_NAME = ""; // 数据库表名

    private static Connection getConn(String dbName){

        Connection connection = null;
        try{
            Class.forName(DRIVER); // 动态加载类
            String ip = "192.168.3.61"; // 写成本机地址，不能写成localhost，同时手机和电脑连接的网络必须是同一个

            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + dbName, USER, PASSWORD);

        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }

    public static HashMap<String, Object> getInfoByName(String name){

        HashMap<String, Object> map = new HashMap<>();
        // 根据数据库名称，建立连接
        Connection connection = getConn("map_designer_test_db");

        try {
            // mysql简单的查询语句。这里是根据MD_CHARGER表的NAME字段来查询某条记录
            String sql = "select * from MD_CHARGER where NAME = ?";
            if (connection != null){ // connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null){
                    // 设置上面的sql语句中的？的值为name
                    ps.setString(1, name);
                    // 执行sql查询语句并返回结果集
                    ResultSet rs = ps.executeQuery();
                    if (rs != null){
                        int count = rs.getMetaData().getColumnCount();
                        Log.e("DBUtils","列总数：" + count);
                        while (rs.next()){
                            // 注意：下标是从1开始的
                            for (int i = 1;i <= count;i++){
                                String field = rs.getMetaData().getColumnName(i);
                                map.put(field, rs.getString(field));
                            }
                        }
                        connection.close();
                        ps.close();
                        return map;
                    }else {
                        return null;
                    }
                }else {
                    return null;
                }
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils","异常：" + e.getMessage());
            return null;
        }

    }
}
