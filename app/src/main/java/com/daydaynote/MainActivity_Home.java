package com.daydaynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daydaynote.activity.AddBillActivity;
import com.daydaynote.fragments.BlankFragment_tab;
import com.daydaynote.fragments.BlankFragment_tab2;
import com.daydaynote.fragments.BlankFragment_tab3;
import com.daydaynote.fragments.BlankFragment_tab4;
import com.daydaynote.uitls.PublicUtil;


public class MainActivity_Home extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mLinear, mLinear2, mLinear3, mLinear4;
    private ImageView mImg, mImg2, mImg3, mImg4, mImg5;
    private TextView mTlt, mTlt2, mTlt3, mTlt4, mTlt_txt;
    private FragmentManager fragmentManager;
    private BlankFragment_tab blankFragment_tab = new BlankFragment_tab();
    private BlankFragment_tab2 blankFragment_tab2 = new BlankFragment_tab2();
    private BlankFragment_tab3 blankFragment_tab3 = new BlankFragment_tab3();
    private BlankFragment_tab4 blankFragment_tab4 = new BlankFragment_tab4();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            blankFragment_tab = (BlankFragment_tab) fragmentManager.getFragment(savedInstanceState,"blankFragment_tab");
            blankFragment_tab2 = (BlankFragment_tab2) fragmentManager.getFragment(savedInstanceState,"blankFragment_tab2");
            blankFragment_tab3 = (BlankFragment_tab3) fragmentManager.getFragment(savedInstanceState,"blankFragment_tab3");
            blankFragment_tab4 = (BlankFragment_tab4) fragmentManager.getFragment(savedInstanceState,"blankFragment_tab4");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__home);
        PublicUtil.setStatusBarBg(MainActivity_Home.this, getResources().getColor(R.color.colorPrimary));
        initView();
        initFragmentSelected(0);
    }

    private void initView() {
        mTlt_txt = (TextView) findViewById(R.id.include_title);
        mLinear = (LinearLayout) findViewById(R.id.line1);
        mLinear2 = (LinearLayout) findViewById(R.id.line2);
        mLinear3 = (LinearLayout) findViewById(R.id.line3);
        mLinear4 = (LinearLayout) findViewById(R.id.line4);
        mImg = (ImageView) findViewById(R.id.img1);
        mImg2 = (ImageView) findViewById(R.id.img2);
        mImg3 = (ImageView) findViewById(R.id.img3);
        mImg4 = (ImageView) findViewById(R.id.img4);
        mImg5 = (ImageView) findViewById(R.id.img5);
        mTlt = (TextView) findViewById(R.id.tlt1);
        mTlt2 = (TextView) findViewById(R.id.tlt2);
        mTlt3 = (TextView) findViewById(R.id.tlt3);
        mTlt4 = (TextView) findViewById(R.id.tlt4);
        mLinear.setOnClickListener(this);
        mLinear2.setOnClickListener(this);
        mLinear3.setOnClickListener(this);
        mLinear4.setOnClickListener(this);
        mImg5.setOnClickListener(this);
        setBottombar(0);

    }

    /**
     * 初始化fragment
     *
     * @param i
     */
    private void initFragmentSelected(int i) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hindFragments(fragmentTransaction);
        switch (i) {
            case 0:
                if (!blankFragment_tab.isAdded()) {
                    fragmentTransaction.add(R.id.content_group_zhong, blankFragment_tab, "blankFragment_tab");
                }
                    fragmentTransaction.show(blankFragment_tab);


                break;
            case 1:
                if (!blankFragment_tab2.isAdded()) {
                    fragmentTransaction.add(R.id.content_group_zhong, blankFragment_tab2, "blankFragment_tab2");
                }
                fragmentTransaction.show(blankFragment_tab2);
                break;
            case 2:
                if (!blankFragment_tab3.isAdded()) {
                    fragmentTransaction.add(R.id.content_group_zhong, blankFragment_tab3, "blankFragment_tab3");
                }
                fragmentTransaction.show(blankFragment_tab3);
                break;
            case 3:
                if (!blankFragment_tab4.isAdded()) {
                    fragmentTransaction.add(R.id.content_group_zhong, blankFragment_tab4, "blankFragment_tab4");
                }
                fragmentTransaction.show(blankFragment_tab4);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 隐藏fragment
     *
     * @param fragmentTransaction
     */
    private void hindFragments(FragmentTransaction fragmentTransaction) {
        if (blankFragment_tab != null) {
            fragmentTransaction.hide(blankFragment_tab);
        }
        if (blankFragment_tab2 != null) {
            fragmentTransaction.hide(blankFragment_tab2);
        }
        if (blankFragment_tab3 != null) {
            fragmentTransaction.hide(blankFragment_tab3);
        }
        if (blankFragment_tab4 != null) {
            fragmentTransaction.hide(blankFragment_tab4);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line1:
                setBottombar(0);
                initFragmentSelected(0);
                break;
            case R.id.line2:
                setBottombar(1);
                initFragmentSelected(1);
                break;
            case R.id.line3:
                setBottombar(2);
                initFragmentSelected(2);
                break;
            case R.id.line4:
                setBottombar(3);
                initFragmentSelected(3);
                break;
            case R.id.img5:
                Intent intent = new Intent(MainActivity_Home.this, AddBillActivity.class);
                startActivity(intent);
                break;
        }


    }




    public void setBottombar(int num) {
        mImg.setImageResource(R.mipmap.my_normal);
        mImg2.setImageResource(R.mipmap.my_normal);
        mImg3.setImageResource(R.mipmap.my_normal);
        mImg4.setImageResource(R.mipmap.my_normal);
        mTlt.setTextColor(getResources().getColor(R.color.text_bg));
        mTlt2.setTextColor(getResources().getColor(R.color.text_bg));
        mTlt3.setTextColor(getResources().getColor(R.color.text_bg));
        mTlt4.setTextColor(getResources().getColor(R.color.text_bg));
        switch (num) {
            case 0:
                mTlt_txt.setText("账单");
                mImg.setImageResource(R.mipmap.message_select);
                mTlt.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 1:
                mTlt_txt.setText("统计");
                mImg2.setImageResource(R.mipmap.message_select);
                mTlt2.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                mTlt_txt.setText("其他");
                mImg3.setImageResource(R.mipmap.message_select);
                mTlt3.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                mTlt_txt.setText("我的");
                mImg4.setImageResource(R.mipmap.message_select);
                mTlt4.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
        }

    }
}
