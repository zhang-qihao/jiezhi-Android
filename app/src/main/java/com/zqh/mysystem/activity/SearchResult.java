package com.zqh.mysystem.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zqh.mysystem.R;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.activity
 * @className: SearchResult
 * @author: Zhangqihao
 * @description: 搜索结果页面
 * @date: 2022/5/29
 */
public class SearchResult extends AppCompatActivity {

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        init();
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description 控件初始化
     * @date 2022/5/29
     */
    private void init() {
        rv = findViewById(R.id.job_search);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new myAdapter());
    }


    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: viewHolder
     * @author: Zhangqihao
     * @description: 自定义 viewHolder
     * @date: 2022/5/29
     */
    class viewHolder extends RecyclerView.ViewHolder {
        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    /**
     * @projectName: MySystem
     * @package: com.zqh.mysystem.activity
     * @className: SearchResult
     * @author: Zhangqihao
     * @description: 自定义 adapter
     * @date: 2022/5/29
     */
    class myAdapter extends RecyclerView.Adapter<viewHolder> {
        public myAdapter() {
            Log.i("SearchResult", "total result: " + getItemCount());
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new viewHolder(LayoutInflater.from(SearchResult.this).inflate(R.layout.job_preview_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }
}