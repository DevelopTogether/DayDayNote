package com.daydaynote.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daydaynote.R;

public class Xiangqing extends AppCompatActivity {

    private ImageView mXiangqingIamge;
    private Toolbar mXiangqingToolbar;
    private CollapsingToolbarLayout mCollapsing;
    private AppBarLayout mAppbar;
    private TextView mXiangqingText;
    private CoordinatorLayout mActivityXiangqing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangqing);
        initView();

    }

    private void initView() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int imageid = intent.getIntExtra("image", 0);
        mXiangqingIamge = (ImageView) findViewById(R.id.xiangqing_iamge);
        mXiangqingToolbar = (Toolbar) findViewById(R.id.xiangqing_toolbar);
        mCollapsing = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        mAppbar = (AppBarLayout) findViewById(R.id.appbar);
        mXiangqingText = (TextView) findViewById(R.id.xiangqing_text);
        mActivityXiangqing = (CoordinatorLayout) findViewById(R.id.activity_xiangqing);
        setSupportActionBar(mXiangqingToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //设置返回上一层的返回按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mCollapsing.setTitle("MaterialD");
        Glide.with(this).load(imageid).into(mXiangqingIamge);
        String content_text = gener(name);
        mXiangqingText.setText(content_text);


        //给actionButton设置监听事件
        mAppbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"评论",3000).setAction("评论", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Xiangqing.this, "评论被点击了", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

    }

    private String gener(String a) {
        StringBuilder text_contentJ = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            text_contentJ.append(a);
        }
        return text_contentJ.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://??????androird
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
