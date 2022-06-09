package com.zqh.mysystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zqh.mysystem.R;

public class CompanyHome extends AppCompatActivity {

    Button btn_publish_job, btn_check_post;
    String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);
        GetIntent();
        init();
    }

    private void init() {
        btn_publish_job = findViewById(R.id.btn_publish_job);
        btn_check_post = findViewById(R.id.btn_check_post);

        btn_publish_job.setOnClickListener(new myClick());
        btn_check_post.setOnClickListener(new myClick());
    }

    private void GetIntent() {
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
    }

    class myClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("jid", account);
            if (view.getId() == R.id.btn_publish_job)
                intent.setClass(CompanyHome.this, PublishJob.class);
            else
                intent.setClass(CompanyHome.this, CheckPost.class);
            startActivity(intent);
        }
    }
}