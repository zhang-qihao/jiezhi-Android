package com.zqh.mysystem.utils;

import android.util.Log;

import com.zqh.mysystem.bean.job_infos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import okhttp3.Response;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.utils
 * @className: JsonParseUtil
 * @author: Zhangqihao
 * @description: JSON 格式数据解析
 * @date: 2022/5/31
 */
public class JsonParseUtil {

    /**
     * @param response: response 响应
     * @return ArrayList<job_infos> 返回 job_infos 类型数组
     * @author Zhangqihao
     * @description 将 JSON 格式数据解析为 job_infos 类型数组
     * @date 2022/5/31
     */
    public static ArrayList<job_infos> parseJsonWithJsonObject(Response response) throws IOException {

        ArrayList<job_infos> arr = new ArrayList<>();
        job_infos job_info;

        assert response.body() != null;
        String responseData = response.body().string(); // 从响应中获取数据

        try{
            // 解析请求中的 json 数据
            JSONObject jsonData = new JSONObject(responseData);
            // 获取 'Jobs' 对应的 value 值，转为 JSONArray 格式
            JSONArray jobs = jsonData.getJSONArray("Jobs");
            // 获取 'Total' 对于的 value 值
            int total = jsonData.getInt("Total");

            int[] visited = new int[total]; // 定义数组，存放已访问的索引
            Arrays.fill(visited, -1); // 初始化数组
            int count = 0; // 计数，控制返回数组大小

            while(count < total) { // 取数据一半用于显示
                int i = new Random().nextInt(total); // 随机获取索引，[0,total)
                boolean flag = false;
                for (int k : visited) // 遍历 visited 数组
                    if (i == k) { // 如果索引存在于 visited 数组中
                        flag = true;
                        break;
                    }
                if (flag)
                    continue;
                // 获取数据
                String jid = jobs.getJSONObject(i).getString("jid");
                String title = jobs.getJSONObject(i).getString("title");
                String shortName = jobs.getJSONObject(i).getString("shortname");
                String salary = jobs.getJSONObject(i).getString("salary");
                String education = jobs.getJSONObject(i).getString("education");
                String experience = jobs.getJSONObject(i).getString("experience");
                String address = jobs.getJSONObject(i).getString("address");
                String industry = jobs.getJSONObject(i).getString("industry");
                String scale = jobs.getJSONObject(i).getString("scale");
                String companyType = jobs.getJSONObject(i).getString("companyType");
                String nature = jobs.getJSONObject(i).getString("nature");
                // 实例化 job_info 对象
                job_info = new job_infos(jid, title, shortName, salary, education, experience,
                        address, industry, scale, companyType, nature);
                arr.add(job_info); // 添加元素

                visited[count] = i; // 索引被访问
                count++; // 计数+1
            }

        } catch (JSONException e) { // JSON 数据解析异常
            Log.e("HomePage", "PARSE JSON ERROR");
            e.printStackTrace();
        }

        return arr;
    }
}
