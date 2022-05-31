package com.zqh.mysystem.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

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
}
