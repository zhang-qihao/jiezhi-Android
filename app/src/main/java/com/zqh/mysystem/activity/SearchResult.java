package com.zqh.mysystem.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.activity
 * @className: SearchResult
 * @author: Zhangqihao
 * @description: 搜索结果页面
 * @date: 2022/5/29
 */
public class SearchResult extends AppCompatActivity {

    ArrayList<job_infos> jobs = new ArrayList<>();
    String query;

    RecyclerView rv;
    ImageView back_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        init();
        getIntentData();
        httpRequest(query);
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description 控件初始化
     * @date 2022/5/29
     */
    private void init() {
        rv = findViewById(R.id.job_search);
        back_home = findViewById(R.id.back_home);

        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SearchResult.this, HomePage.class);
                startActivity(intent);
            }
        });
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description 获取 intent 携带的数据
     * @date 2022/5/31
     */
    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null)
            query = intent.getStringExtra("query");
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description /getJob HTTP 请求
     * @date 2022/5/31
     */
    void httpRequest(String query) {
        String url = "http://139.196.72.52:8080/v1/queryJob?query=" + query;
        Log.i("SearchResult", "Try url: " + url);
        HttpUtil.sendRequestWithOkhttp(url , new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("SearchResult", "HTTP 请求失败");
                Log.e("SearchResult", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("SearchResult", "HTTP 请求成功");
                jobs = JsonParseUtil.parseJobObject(response);
                Log.i("SearchResult", "display: " + jobs.size());
                runOnUiThread(new Runnable() { // 子线程的回调函数中调用 runOnUiThread 函数回到主线程更新UI
                    @Override
                    public void run() {
                        onSetAdapter(); // 设置 adapter
                    }
                });
            }
        });
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description recyclerView 设置 adapter
     * @date 2022/5/31
     */
    void onSetAdapter() {
        // 绑定 adapter
        rv.setLayoutManager(new LinearLayoutManager(SearchResult.this));
        rv.setAdapter(new myAdapter());
    }

    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: myAdapter
     * @author: Zhangqihao
     * @description: 自定义 viewHolder
     * @date: 2022/5/28
     */
    class viewHolder extends RecyclerView.ViewHolder {

        TextView jid, job_name, job_salary, job_require_position, job_require_education,
                job_require_experience, job_company, job_industry, company_type, company_scale, job_address;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            jid = itemView.findViewById(R.id.tv_jid);
            job_name = itemView.findViewById(R.id.job_name);
            job_salary = itemView.findViewById(R.id.job_salary);
            job_require_position = itemView.findViewById(R.id.job_require_position);
            job_require_education = itemView.findViewById(R.id.job_require_education);
            job_require_experience = itemView.findViewById(R.id.job_require_experience);
            job_company = itemView.findViewById(R.id.job_company);
            job_industry = itemView.findViewById(R.id.job_industry);
            company_type = itemView.findViewById(R.id.company_type);
            company_scale = itemView.findViewById(R.id.company_scale);
            job_address = itemView.findViewById(R.id.job_address);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("jid", jid.getText().toString());
                    intent.setClass(SearchResult.this, JobDetail.class);
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: myAdapter
     * @author: Zhangqihao
     * @description: 自定义 adapter
     * @date: 2022/5/31
     */
    class myAdapter extends RecyclerView.Adapter<viewHolder> {
        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new viewHolder(LayoutInflater.from(SearchResult.this).inflate(R.layout.job_preview_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            holder.jid.setText(jobs.get(position).getJid());
            holder.job_name.setText(jobs.get(position).getTitle());
            holder.job_salary.setText(jobs.get(position).getSalary());
            holder.job_require_education.setText(jobs.get(position).getEducation());
            holder.job_require_experience.setText(jobs.get(position).getExperience());
            holder.job_require_experience.setText(jobs.get(position).getExperience());
            holder.job_company.setText(jobs.get(position).getShortName());
            holder.job_industry.setText(jobs.get(position).getIndustry());
            holder.company_type.setText(jobs.get(position).getCompanyType());
            holder.company_scale.setText(jobs.get(position).getScale());
            holder.job_address.setText(jobs.get(position).getAddress());
            holder.job_require_position.setText(jobs.get(position).getNature());
        }

        @Override
        public int getItemCount() {
            return jobs.size();
        }
    }
}