package com.daydaynote.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.daydaynote.R;

import java.util.ArrayList;
import java.util.List;

import com.daydaynote.Adapter.Find_tab_Adapter;
import com.daydaynote.fragments.BlankFragment;
import com.daydaynote.fragments.BlankFragment2;
import com.daydaynote.fragments.BlankFragment3;

public class MainActivity_test extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mTablayoutTest;
    private ViewPager mViewpagerTest;
    private FragmentPagerAdapter fAdapter;                               //定义adapter

    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表

    private BlankFragment hotRecommendFragment;              //热门推荐fragment
    private BlankFragment2 hotCollectionFragment;            //热门收藏fragment
    private BlankFragment3 hotMonthFragment;                      //本月热榜fragment
    private Toolbar mToolbalTest;
    private CollapsingToolbarLayout mCospingTets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        initView();
    }

    private void initView() {
        mTablayoutTest = (TabLayout) findViewById(R.id.tablayout_test);
        mTablayoutTest.setOnClickListener(this);
        mViewpagerTest = (ViewPager) findViewById(R.id.viewpagerTest);
        mViewpagerTest.setOnClickListener(this);
        //初始化各fragment
        hotRecommendFragment = new BlankFragment();
        hotCollectionFragment = new BlankFragment2();
        hotMonthFragment = new BlankFragment3();
        list_fragment = new ArrayList<>();
        list_fragment.add(hotRecommendFragment);
        list_fragment.add(hotCollectionFragment);
        list_fragment.add(hotMonthFragment); //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("热门推荐");
        list_title.add("热门收藏");
        list_title.add("本月热榜"); //设置TabLayout的模式
        mTablayoutTest.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        mTablayoutTest.addTab(mTablayoutTest.newTab().setText(list_title.get(0)));
        mTablayoutTest.addTab(mTablayoutTest.newTab().setText(list_title.get(1)));
        mTablayoutTest.addTab(mTablayoutTest.newTab().setText(list_title.get(2)));
        fAdapter = new Find_tab_Adapter(MainActivity_test.this.getSupportFragmentManager(), list_fragment, list_title);

        //viewpager加载adapter
        mViewpagerTest.setAdapter(fAdapter);
        mTablayoutTest.setupWithViewPager(mViewpagerTest);

        //设置返回按钮
        mToolbalTest = (Toolbar) findViewById(R.id.toolbal_test);
        mToolbalTest.setTitle("1");
        mToolbalTest.setSubtitle("2");
        setSupportActionBar(mToolbalTest);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        mCospingTets = (CollapsingToolbarLayout) findViewById(R.id.cosping_tets);
        mCospingTets.setOnClickListener(this);
        mCospingTets.setCollapsedTitleGravity(Gravity.CENTER_VERTICAL);
        mCospingTets.setTitle("这是一个标题");//设置可以滑动的标题，折叠标题
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tablayout_test:
                break;
            case R.id.viewpagerTest:
                break;
            case R.id.cosping_tets:

                break;
        }
    }

    //设置toolbar的相关监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
