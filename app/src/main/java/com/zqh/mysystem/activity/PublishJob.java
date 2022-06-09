package com.zqh.mysystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zqh.mysystem.R;
import com.zqh.mysystem.bean.job_infos;
import com.zqh.mysystem.utils.HttpUtil;
import com.zqh.mysystem.utils.JsonParseUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PublishJob extends AppCompatActivity {

    EditText title, salary, address;
    Button publish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_job);
        init();
    }

    private void init() {
        title = findViewById(R.id.publish_title);
        salary = findViewById(R.id.publish_salary);
        address = findViewById(R.id.publish_address);
        publish = findViewById(R.id.btn_publish);

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                JSONObject json = new JSONObject();
                try {
                    json.put("jid", intent.getStringExtra("jid"));
                    json.put("title", title.getText().toString());
                    json.put("salary", salary.getText().toString());
                    json.put("address", salary.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = "http://139.196.72.52:8080/v1/publishJob";
                Log.i("PublishJob", "Try url: " + url);
                HttpUtil.postJobWithOKHttp(url, json, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("PublishJob", "HTTP 请求失败");
                        Log.e("PublishJob", e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("PublishJob", "HTTP 请求成功");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (JsonParseUtil.parseCode(response) == 1000)
                                        Toast.makeText(PublishJob.this, "发布成功", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(PublishJob.this, "发布失败", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}