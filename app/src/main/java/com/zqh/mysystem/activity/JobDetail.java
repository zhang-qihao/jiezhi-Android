package com.zqh.mysystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zqh.mysystem.R;
import com.zqh.mysystem.bean.job_infos;
import com.zqh.mysystem.utils.HttpUtil;
import com.zqh.mysystem.utils.JsonParseUtil;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class JobDetail extends AppCompatActivity {

    ArrayList<job_infos> jobs = new ArrayList<>();

    TextView job_name, salary, location, job_require;
    String jid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        init();
        httpRequest(jid);
    }

    private void init() {
        job_name = findViewById(R.id.tv_detail_job_name);
        salary = findViewById(R.id.tv_detail_salary);
        location = findViewById(R.id.tv_detail_location);
        job_require = findViewById(R.id.tv_detail_require);

        Intent intent = getIntent();
        jid = intent.getStringExtra("jid");
    }

    @SuppressLint("SetTextI18n")
    private void onSetText() {
        job_name.setText(jobs.get(0).getTitle());
        salary.setText(jobs.get(0).getSalary());
        location.setText(jobs.get(0).getAddress());
        job_require.setText(jobs.get(0).getExperience() +
                "·" + jobs.get(0).getEducation() + "·" + jobs.get(0).getNature());
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description /getJob HTTP 请求
     * @date 2022/5/31
     */
    private void httpRequest(String query) {
        Log.i("JobDetail", "jid: " + jid);
        String url = "http://139.196.72.52:8080/getJobDetail/?jid=" + jid;
        Log.i("JobDetail", "Try url: " + url);
        HttpUtil.sendRequestWithOkhttp(url , new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("SearchResult", "HTTP 请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("SearchResult", "HTTP 请求成功");
                jobs = JsonParseUtil.parseJsonWithJsonObject(response);
                Log.i("SearchResult", "display: " + jobs.size());
                runOnUiThread(new Runnable() { // 子线程的回调函数中调用 runOnUiThread 函数回到主线程更新UI
                    @Override
                    public void run() {
                        onSetText();
                    }
                });
            }
        });
    }
}