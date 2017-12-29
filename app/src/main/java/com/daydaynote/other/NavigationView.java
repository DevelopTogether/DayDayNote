package com.daydaynote.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daydaynote.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class NavigationView extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;

    //初始化网络监听广播
    private ConnectionChangeReceiver myReceiver;

    //xialaishuaxia
    private SwipeRefreshLayout refreshLayout;


    private FuiltAdapter adapter;
    private List<Fuilt> mList = new ArrayList<>();
    private Fuilt[] fuilt = {new Fuilt("好地方", R.drawable.qaa), new Fuilt("好地方", R.drawable.qaa2), new Fuilt("好方", R.drawable.qaa3), new Fuilt("老虎", R.drawable.qaa4)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);

        registerReceiver();
        //初始化Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobarl);
        setSupportActionBar(toolbar);


        //初始化下拉刷新
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refrech);
        refreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refrech_();
            }
        });


        //初始化滑动菜单
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_navigation_view_b);
        //设置导航按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }

        android.support.design.widget.NavigationView navigationView = (android.support.design.widget.NavigationView) findViewById(R.id.nav_view);


        initFilts();
        //初始化recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recy_view);
        GridLayoutManager manager = new GridLayoutManager(this, 2);//表示列数
        recyclerView.setLayoutManager(manager);
        adapter = new FuiltAdapter(mList);
        recyclerView.setAdapter(adapter);

        //FloatingActionButton悬浮按钮
        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.floting_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在底部弹出来并且会自动取消，带点击事件
                Snackbar.make(v, "data deleted", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(NavigationView.this, "flatinfAction", Toast.LENGTH_SHORT).show();

                    }
                }).show();


            }
        });


        //活动菜单里面的监听事件
        navigationView.setCheckedItem(R.id.nav_call);//这只前选中的
        navigationView.setNavigationItemSelectedListener(new android.support.design.widget.NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_feiends) {
                    drawerLayout.closeDrawers();
                }
                if (item.getItemId() == R.id.nav_lacation) {
                    drawerLayout.closeDrawers();
                }

                return true;
            }
        });
    }

    //添加数据
    private void initFilts() {
        mList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int a = random.nextInt(fuilt.length);
            mList.add(fuilt[a]);
        }
    }

    //下拉刷新执行动作
    private void refrech_() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);//


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFilts();
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);//刷新时间结束  隐藏进度条
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:


                Toast.makeText(this, "seting", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    public class ConnectionChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                Toast.makeText(context, "不可用", Toast.LENGTH_SHORT).show();
                //改变背景或者 处理网络的全局变量
            }else {
                Toast.makeText(context, "网络可用", Toast.LENGTH_SHORT).show();
                //改变背景或者 处理网络的全局变量
            }
        }
    }
    private  void registerReceiver(){
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver=new ConnectionChangeReceiver();
        this.registerReceiver(myReceiver, filter);
    }
    private  void unregisterReceiver(){
        this.unregisterReceiver(myReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }
}
