package com.zqh.mysystem.utils;

import android.util.Log;

import com.zqh.mysystem.bean.job_infos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Response;

public class JsonParseUtil {

    public static ArrayList<job_infos> parseJsonWithJsonObject(Response response) throws IOException {

        ArrayList<job_infos> arr = new ArrayList<>();
        job_infos job_info;

        assert response.body() != null;
        String responseData = response.body().string();

        try{
            // 解析请求中的 json 数据
            JSONObject jsonData = new JSONObject(responseData);
            // 获取 'Jobs' 对应的 value 值，转为 JSONArray 格式
            JSONArray jobs = jsonData.getJSONArray("Jobs");
            // 获取 'Total' 对于的 value 值
            int total = jsonData.getInt("Total");

            int[] visited = new int[total / 2];
            int count = 0;

            while(count < total / 2) {
                int i = new Random().nextInt(total);
                boolean flag = false;
                for (int k : visited)
                    if (i == k) {
                        flag = true;
                        break;
                    }
                if (flag)
                    continue;
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
                job_info = new job_infos(title, shortName, salary, education, experience,
                        address, industry, scale, companyType, nature);
                arr.add(job_info);

                visited[count] = i;
                count++;
            }

            Log.i("HomePage", "total: " + total);
            Log.i("HomePage", "display: " + total / 2);
        } catch (JSONException e) {
            Log.e("HomePage", "PARSE JSON ERROR");
            e.printStackTrace();
        }

        return arr;
    }
}
