package com.zqh.mysystem.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
 * @className: HomePage
 * @author: Zhangqihao
 * @description: 主页
 * @date: 2022/5/28
 */
public class HomePage extends AppCompatActivity implements View.OnClickListener {

    final String POSITION_FULL = "quanzhi";
    final String POSITION_PART = "shixi";

    ArrayList<job_infos> jobs = new ArrayList<>();

    TextView tv_quanzhi, tv_shixi, line_quanzhi, line_shixi;
    TextView tv_tuijian, tv_golang, tv_android, tv_c;
    EditText et_search;
    RecyclerView rv;
    String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        httpRequest();
        init();
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description 控件初始化
     * @date 2022/5/28
     */
    private void init() {
        tv_quanzhi = findViewById(R.id.tv_quanzhi); // 全职标签
        tv_shixi = findViewById(R.id.tv_shixi); // 实习标签
        line_quanzhi = findViewById(R.id.line_quanzhi); // 全职下划线
        line_shixi = findViewById(R.id.line_shixi); // 实习下划线
        rv = findViewById(R.id.rv_job); // 工作 recyclerview
        et_search = findViewById(R.id.et_search); // 搜索框
        tv_tuijian = findViewById(R.id.tv_tuijian);
        tv_golang = findViewById(R.id.tv_golang);
        tv_android = findViewById(R.id.tv_android);
        tv_c = findViewById(R.id.tv_c);

        // 初始化职业类型——全职
        changePosition(R.id.tv_quanzhi);

        // 绑定监听事件
        // 全职/实习切换监听事件
        tv_quanzhi.setOnClickListener(this);
        tv_shixi.setOnClickListener(this);
        // 工作切换监听事件
        tv_tuijian.setOnClickListener(this);
        tv_golang.setOnClickListener(this);
        tv_android.setOnClickListener(this);
        tv_c.setOnClickListener(this);
        // 搜索框监听事件
        et_search.setOnEditorActionListener(new myOnEditorActionListener());
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description recyclerView 设置 adapter
     * @date 2022/5/31
     */
    void onSetAdapter() {
        // 绑定 adapter
        rv.setLayoutManager(new LinearLayoutManager(HomePage.this));
        rv.setAdapter(new myAdapter());
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description /getJob HTTP 请求
     * @date 2022/5/31
     */
    void httpRequest() {
        String url = "http://139.196.72.52:8080/v1/getJob";
        Log.i("HomePage", "Try url: " + url);
        HttpUtil.sendRequestWithOkhttp(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("HomePage", "HTTP 请求失败");
                Log.e("HomePage", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("HomePage", "HTTP 请求成功");
                jobs = JsonParseUtil.parseJobObject(response);
                Log.i("HomePage", "display: " + jobs.size());
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
     * @param id: 控件 id
     * @return void
     * @author Zhangqihao
     * @description 切换职业类型选项
     * @date 2022/5/28
     */
    void changePosition(int id) {
        if (id == R.id.tv_quanzhi) {
            tv_quanzhi.setTextColor(Color.BLACK);
            tv_shixi.setTextColor(Color.GRAY);
            tv_quanzhi.setTextSize(20);
            tv_shixi.setTextSize(15);
            line_quanzhi.setVisibility(View.VISIBLE);
            line_shixi.setVisibility(View.INVISIBLE);
            position = POSITION_FULL;
            Log.i("HomePage", "Position: full-time job");
        }else {
            tv_quanzhi.setTextColor(Color.GRAY);
            tv_shixi.setTextColor(Color.BLACK);
            tv_quanzhi.setTextSize(15);
            tv_shixi.setTextSize(20);
            line_quanzhi.setVisibility(View.INVISIBLE);
            line_shixi.setVisibility(View.VISIBLE);
            position = POSITION_PART;
            Log.i("HomePage", "Position: part-time job");
        }
    }

    /**
     * @param view: 选定的控件
     * @return void
     * @author Zhangqihao
     * @description 工作选项点击
     * @date 2022/5/29
     */
    @SuppressLint("NonConstantResourceId")
    void changeJob(View view) {
        defaultStyle();
        switch (view.getId()) {
            case R.id.tv_tuijian:
                tv_tuijian.setTextColor(Color.BLACK);
                tv_tuijian.setTextSize(20);
                break;
            case R.id.tv_golang:
                tv_golang.setTextColor(Color.BLACK);
                tv_golang.setTextSize(20);
                break;
            case R.id.tv_android:
                tv_android.setTextColor(Color.BLACK);
                tv_android.setTextSize(20);
                break;
            case R.id.tv_c:
                tv_c.setTextColor(Color.BLACK);
                tv_c.setTextSize(20);
                break;
        }
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description 工作标签默认颜色大小
     * @date 2022/5/29
     */
    void defaultStyle() {
        tv_tuijian.setTextColor(Color.GRAY);
        tv_tuijian.setTextSize(17);
        tv_golang.setTextColor(Color.GRAY);
        tv_golang.setTextSize(17);
        tv_android.setTextColor(Color.GRAY);
        tv_android.setTextSize(17);
        tv_c.setTextColor(Color.GRAY);
        tv_c.setTextSize(17);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_quanzhi:
                changePosition(R.id.tv_quanzhi);
                break;
            case R.id.tv_shixi:
                changePosition(R.id.tv_shixi);
                break;
            case R.id.tv_tuijian:
            case R.id.tv_golang:
            case R.id.tv_android:
            case R.id.tv_c:
                changeJob(view);
        }
    }

    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: myOnEditorActionListener
     * @author: Zhangqihao
     * @description: 自定义 onEditorActionListener 搜索框搜索事件
     * @date: 2022/5/28
     */
    class myOnEditorActionListener implements TextView.OnEditorActionListener {
        String query;
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            query = textView.getText().toString();
            if ((i  == EditorInfo.IME_ACTION_UNSPECIFIED || i == EditorInfo.IME_ACTION_SEARCH) && keyEvent == null) {
                Log.i("HomePage", "[点击搜索] | query: " + query);
                doSearchJob(query);
                return true;
            }
            return false;
        }

        void doSearchJob(String query) {
            Intent intent = new Intent(HomePage.this, SearchResult.class);
            intent.putExtra("query", query);
            startActivity(intent);
        }
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
                    intent.setClass(HomePage.this, JobDetail.class);
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
     * @date: 2022/5/28
     */
    class myAdapter extends RecyclerView.Adapter<viewHolder> {
        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new viewHolder(LayoutInflater.from(HomePage.this).inflate(R.layout.job_preview_item, parent, false));
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