package com.zqh.mysystem.utils;

import com.zqh.mysystem.bean.job_infos;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.utils
 * @className: HttpUtil
 * @author: Zhangqihao
 * @description: OKHttp 请求
 * @date: 2022/5/31
 */
public class HttpUtil {
    /**
     * @param address: url 地址
     * @param callback: 回调方法
     * @return void
     * @author Zhangqihao
     * @description 根据 url 获取 get 请求
     * @date 2022/5/31
     */
    public static void sendRequestWithOkhttp(String address,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * @param address: url 地址
     * @param json: 传输数据
     * @param callback: 回调方法
     * @return void
     * @author Zhangqihao
     * @description post 请求传递 json 格式数据
     * @date 2022/6/11
     */
    public static void postJobWithOKHttp(String address, JSONObject json, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(json));
        Request request = new Request.Builder()
                .url(address)
                .addHeader("key", "value")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
