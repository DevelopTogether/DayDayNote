package com.daydaynote.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daydaynote.Adapter.BillConditionMonthAdapter;
import com.daydaynote.Adapter.BillConditionYearAdapter;
import com.daydaynote.R;
import com.daydaynote.entity.MonthBean;
import com.daydaynote.entity.YearBean;
import com.daydaynote.other.MonthDatesClickedCallBack;
import com.daydaynote.other.PagingScrollHelper;
import com.daydaynote.uitls.PublicUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.daydaynote.uitls.PublicUtil.compareTime;
import static com.daydaynote.uitls.PublicUtil.monthHashmap;
import static com.daydaynote.uitls.PublicUtil.monthPageId;
import static com.daydaynote.uitls.PublicUtil.stringGetWeek;


public class BlankFragment_tab extends Fragment implements View.OnClickListener {
    private String TAG = "888888";
    private LinearLayout linearLayout;
    private View view;

    //图片轮播
    List<Integer> mImage_list = new ArrayList<>();
    List<String> mString_list = new ArrayList<>();
    /**
     * 2017年6月
     */
    private TextView mBillConditionTitleTv;
    private ImageView mBillConditionTitleIv;
    private LinearLayout mBillConditionLl;
    /**
     * 月账单
     */
    private TextView mMonthBillTv;
    /**
     * 年账单
     */
    private TextView mYearBillTv;
    /**
     * 自定义
     */
    private TextView mCustomBillTv;
    private LinearLayout mConditionTitleLl;
    private RecyclerView mConditionContentRv;
    private List<MonthBean> monthBeans = new ArrayList<>();
    private List<YearBean> yearBeans = new ArrayList<>();
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
    private RelativeLayout mCondition_contentRL;
    /**
     * 年月日
     */
    private TextView mStartTimeTv;
    private RelativeLayout mStartTimeRl;
    /**
     * 年月日
     */
    private TextView mEndTimeTv;
    private RelativeLayout mEndTimeRl;
    /**
     * 确    定
     */
    private TextView mConfirmCustomTimeTv;
    private LinearLayout mCustomContentLl;
    private ImageView mMonthArrowLeftIv;
    private ImageView mMonthArrowRightIv;
    private RelativeLayout mConditionContentRl;
    private DatePicker mStartDp;
    private DatePicker mEndDp;
    private int yearStart;
    private int monthStart;
    private int dayStart;
    private int year_end;
    private int month_end;
    private int day_end;
    private String date_start_str;
    private String date_end_str;

    private HashMap<Integer, String> yearHashmap = new HashMap<>();
    private Activity context;

    boolean isToShowSelecter = true;
    private RecyclerView mBillContentRv;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    /**
     * 初始化日期
     */
    private void initCalender() {
        Calendar calendar_end = Calendar.getInstance();
        Date date_end = calendar_end.getTime();
        SimpleDateFormat form = new SimpleDateFormat("yyyy年MM月dd日");
        String ymd = form.format(date_end);
        date_end_str = ymd;
        String week = stringGetWeek(ymd);
        mEndTimeTv.setText(ymd + " " + week);
        year_end = calendar_end.get(Calendar.YEAR);
        month_end = calendar_end.get(Calendar.MONTH);
        day_end = calendar_end.get(Calendar.DAY_OF_MONTH);

        Calendar calendar_start = Calendar.getInstance();
        calendar_start.add(Calendar.MONTH, -1);
        Date date_start = calendar_start.getTime();
        String ymd_start = form.format(date_start);
        date_start_str = ymd_start;
        String week_start = stringGetWeek(ymd_start);
        mStartTimeTv.setText(ymd_start + " " + week_start);
        yearStart = calendar_start.get(Calendar.YEAR);
        monthStart = calendar_start.get(Calendar.MONTH);
        dayStart = calendar_start.get(Calendar.DAY_OF_MONTH);
        mBillConditionTitleTv.setText(date_start_str + "-" + date_end_str);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank_fragment_tab, container, false);
        initDataForMonthAdapter();
        initDataForYearAdapter();
        initView(view);
        selectMonthAdapter();
        return view;
    }

    private void showWhatYouWantShow(View view_hide, View view_show) {
        view_hide.setVisibility(View.GONE);
        view_show.setVisibility(View.VISIBLE);
    }

    private void initDataForYearAdapter() {
        yearBeans.clear();
        yearHashmap.clear();
        yearBeans.add(setDataToYearBean(2007));
        yearHashmap.put(0, "2007年-2016年");
        yearBeans.add(setDataToYearBean(2017));
        yearHashmap.put(1, "2017年-2026年");
        yearBeans.add(setDataToYearBean(2027));
        yearHashmap.put(2, "2027年-2036年");
        yearBeans.add(setDataToYearBean(2037));
        yearHashmap.put(3, "2037年-2046年");

    }

    private YearBean setDataToYearBean(int topYear) {
        YearBean bean = new YearBean();
        bean.setYear1(String.valueOf(topYear));
        bean.setYear2(String.valueOf(topYear + 1));
        bean.setYear3(String.valueOf(topYear + 2));
        bean.setYear4(String.valueOf(topYear + 3));
        bean.setYear5(String.valueOf(topYear + 4));
        bean.setYear6(String.valueOf(topYear + 5));
        bean.setYear7(String.valueOf(topYear + 6));
        bean.setYear8(String.valueOf(topYear + 7));
        bean.setYear9(String.valueOf(topYear + 8));
        bean.setYear10(String.valueOf(topYear + 9));
        return bean;
    }

    private void selectMonthAdapter() {
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mConditionContentRv.setLayoutManager(manager);
        BillConditionMonthAdapter adapter = new BillConditionMonthAdapter(monthBeans);
        adapter.setMonthClick(new MonthDatesClickedCallBack() {
            @Override
            public void monthClicked(String str) {
                Toast.makeText(context, str + "被点击了", Toast.LENGTH_SHORT).show();
            }
        });
        mConditionContentRv.setAdapter(adapter);
        mConditionContentRv.scrollToPosition(monthPageId);
        scrollHelper.setUpRecycleView(mConditionContentRv);


    }


    /**
     * 加载数据
     */
    private void initDataForMonthAdapter() {
        monthBeans.clear();
        monthHashmap.clear();
        int y = 2007;
        for (int i = 0; i < 40; i++) {
            monthHashmap.put(i, y);
            y++;
            MonthBean bean = new MonthBean();
            bean.setJanuary("1" + "-" + i);
            bean.setFebruary("2");
            bean.setMarch("3");
            bean.setApril("4");
            bean.setMay("5");
            bean.setJune("6");
            bean.setJuly("7");
            bean.setAugust("8");
            bean.setSeptember("9");
            bean.setOctober("10");
            bean.setNovember("11");
            bean.setDecember("12");
            monthBeans.add(bean);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bill_condition_title_tv://账单条件标题


                if (isToShowSelecter) {
                    isToShowSelecter = false;
                    mConditionTitleLl.setVisibility(View.VISIBLE);
                    mConditionContentRl.setVisibility(View.VISIBLE);
                } else {
                    isToShowSelecter = true;
                    mConditionTitleLl.setVisibility(View.GONE);
                    mConditionContentRl.setVisibility(View.GONE);
                }

                break;
            case R.id.month_bill_tv:
                setSearchConditionViewsStatus(0);
                showWhatYouWantShow(mCustomContentLl, mCondition_contentRL);
                selectMonthAdapter();
                break;
            case R.id.year_bill_tv://年账单点击事件
                setSearchConditionViewsStatus(1);
                showWhatYouWantShow(mCustomContentLl, mCondition_contentRL);
                changeYearBillAdapter();
                break;
            case R.id.custom_bill_tv://自定义账单点击事件
                setSearchConditionViewsStatus(2);
                showWhatYouWantShow(mCondition_contentRL, mCustomContentLl);
                initDatePicker();
                break;
            case R.id.condition_title_ll:
                break;
            case R.id.start_time_rl:
                changeDatePickerStatus(mStartDp, mEndDp);
                break;
            case R.id.end_time_rl:
                changeDatePickerStatus(mEndDp, mStartDp);
                break;
            case R.id.confirm_custom_time_tv:
                break;
            case R.id.arrow_left_iv:
//                if (PublicUtil.yearBillSelected) {//年账单状态
//
//                } else {//月账单状态
//
//                }
                if (PublicUtil.scrolled) {
                    PublicUtil.scrolled = false;

                } else {
                    monthPageId--;
                }

                if (monthPageId < 0) {
                    monthPageId = 0;
                }
                mConditionContentRv.scrollToPosition(monthPageId);
                break;
            case R.id.arrow_right_iv:
                if (PublicUtil.scrolled) {
                    PublicUtil.scrolled = false;

                } else {
                    monthPageId++;
                }

                if (monthPageId > monthHashmap.size() - 1) {
                    monthPageId = monthHashmap.size() - 1;
                }
                mConditionContentRv.scrollToPosition(monthPageId);

                break;
            case R.id.custom_content_ll:
                break;
            case R.id.condition_content_rl:
                break;
        }
    }

    /**
     * 改变两个DatePicker的状态
     *
     * @param displayDP
     * @param unDisplayDp
     */
    private void changeDatePickerStatus(DatePicker displayDP, DatePicker unDisplayDp) {
        displayDP.setVisibility(View.VISIBLE);
        unDisplayDp.setVisibility(View.GONE);
    }

    private void changeYearBillAdapter() {
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mConditionContentRv.setLayoutManager(manager);
        mConditionContentRv.setAdapter(new BillConditionYearAdapter(yearBeans, mBillConditionTitleTv));
        scrollHelper.setUpRecycleView(mConditionContentRv);
    }

    /**
     * 设置条件选择框中控件的状态
     *
     * @param i
     */
    private void setSearchConditionViewsStatus(int i) {
        mMonthBillTv.setBackgroundResource(R.drawable.moth_content_white_shape);
        mMonthBillTv.setTextColor(getResources().getColor(R.color.black));
        mYearBillTv.setBackgroundResource(R.drawable.year_content_white_shape);
        mYearBillTv.setTextColor(getResources().getColor(R.color.black));
        mCustomBillTv.setBackgroundResource(R.drawable.custom_content_white_shape);
        mCustomBillTv.setTextColor(getResources().getColor(R.color.black));
        switch (i) {
            case 0:
                PublicUtil.yearBillSelected = false;
                mMonthBillTv.setBackgroundResource(R.drawable.moth_content_shape);
                mMonthBillTv.setTextColor(getResources().getColor(R.color.white));
                break;
            case 1:
                PublicUtil.yearBillSelected = true;
                mYearBillTv.setBackgroundResource(R.drawable.year_content_shape);
                mYearBillTv.setTextColor(getResources().getColor(R.color.white));
                break;
            case 2:
                PublicUtil.yearBillSelected = false;
                mCustomBillTv.setBackgroundResource(R.drawable.custom_content_shape);
                mCustomBillTv.setTextColor(getResources().getColor(R.color.white));
                break;
            default:
                break;
        }

    }

    private void initView(View view) {
        initBillConditionTitalView(view);
        mBillConditionTitleIv = (ImageView) view.findViewById(R.id.bill_condition_title_iv);
        mBillConditionTitleIv.setOnClickListener(this);
        mBillConditionLl = (LinearLayout) view.findViewById(R.id.bill_condition_ll);
        mBillConditionLl.setOnClickListener(this);
        mMonthBillTv = (TextView) view.findViewById(R.id.month_bill_tv);
        mMonthBillTv.setOnClickListener(this);
        mYearBillTv = (TextView) view.findViewById(R.id.year_bill_tv);
        mCondition_contentRL = (RelativeLayout) view.findViewById(R.id.condition_content_rl);
        mYearBillTv.setOnClickListener(this);
        mCustomBillTv = (TextView) view.findViewById(R.id.custom_bill_tv);
        mCustomBillTv.setOnClickListener(this);
        mConditionTitleLl = (LinearLayout) view.findViewById(R.id.condition_title_ll);
        mConditionTitleLl.setVisibility(View.GONE);
        mConditionTitleLl.setOnClickListener(this);
        mConditionContentRv = (RecyclerView) view.findViewById(R.id.condition_content_rv);
        mStartTimeTv = (TextView) view.findViewById(R.id.start_time_tv);
        mStartTimeRl = (RelativeLayout) view.findViewById(R.id.start_time_rl);
        mStartTimeRl.setOnClickListener(this);
        mEndTimeTv = (TextView) view.findViewById(R.id.end_time_tv);
        mEndTimeRl = (RelativeLayout) view.findViewById(R.id.end_time_rl);
        mEndTimeRl.setOnClickListener(this);
        mConfirmCustomTimeTv = (TextView) view.findViewById(R.id.confirm_custom_time_tv);
        mConfirmCustomTimeTv.setOnClickListener(this);
        mCustomContentLl = (LinearLayout) view.findViewById(R.id.custom_content_ll);
        mMonthArrowLeftIv = (ImageView) view.findViewById(R.id.arrow_left_iv);
        mMonthArrowLeftIv.setOnClickListener(this);
        mMonthArrowRightIv = (ImageView) view.findViewById(R.id.arrow_right_iv);
        mMonthArrowRightIv.setOnClickListener(this);
        mConditionContentRl = (RelativeLayout) view.findViewById(R.id.condition_content_rl);
        mConditionContentRl.setVisibility(View.GONE);
        mStartDp = (DatePicker) view.findViewById(R.id.start_dp);
        mEndDp = (DatePicker) view.findViewById(R.id.end_dp);
        mCustomContentLl.setOnClickListener(this);
        mConditionContentRl.setOnClickListener(this);

        mBillContentRv = (RecyclerView) view.findViewById(R.id.bill_content_rv);
    }

    private void initBillConditionTitalView(View view) {
        Calendar calendar = Calendar.getInstance();
        Date date_end = calendar.getTime();
        SimpleDateFormat form = new SimpleDateFormat("yyyy年MM月");
        String ymd = form.format(date_end);
        mBillConditionTitleTv = (TextView) view.findViewById(R.id.bill_condition_title_tv);
        mBillConditionTitleTv.setOnClickListener(this);
        mBillConditionTitleTv.setText(ymd);
        int year = calendar.get(Calendar.YEAR);
        monthPageId = PublicUtil.getKeyFromHashmap(monthHashmap, year);
    }

    /**
     * 初始化DatePicker
     */
    private void initDatePicker() {
        initCalender();
        mStartDp.init(yearStart, monthStart, dayStart, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String str_start = getString(year, monthOfYear + 1, dayOfMonth);
                mStartTimeTv.setText(str_start);
                date_start_str = new StringBuffer().append(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日").toString();
                mBillConditionTitleTv.setText(date_start_str + "-" + date_end_str);
                initCustomConfirmBtStatus();
            }
        });
        mEndDp.init(year_end, month_end, day_end, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String str_end = getString(year, monthOfYear + 1, dayOfMonth);
                date_end_str = new StringBuffer().append(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日").toString();
                mEndTimeTv.setText(str_end);
                mBillConditionTitleTv.setText(date_start_str + "-" + date_end_str);
                initCustomConfirmBtStatus();
            }
        });
    }

    /**
     * 初始化自定义栏中确定按钮的状态
     */
    private void initCustomConfirmBtStatus() {
        boolean sure = compareTime(date_start_str, date_end_str);
        if (!sure) {
            mConfirmCustomTimeTv.setBackgroundResource(R.drawable.custom_confirm_no_bg);
            mConfirmCustomTimeTv.setClickable(false);
        } else {
            mConfirmCustomTimeTv.setBackgroundResource(R.drawable.custom_confirm_click_bg);
        }
    }


    @NonNull
    private String getString(int year, int month, int day) {
        StringBuffer sb = new StringBuffer();
        if (month < 10) {
            sb.append(year + "年" + "0" + month + "月" + day + "日");
        } else {
            sb.append(year + "年" + month + "月" + day + "日");
        }
        String week = stringGetWeek(sb.toString());
        sb.append(" " + week);
        return sb.toString();
    }
    //设置banner数据
//    private void addData() {
//        mImage_list.add(R.mipmap.ic_launcher);
//        mImage_list.add(R.mipmap.ic_launcher);
//        mImage_list.add(R.mipmap.ic_launcher);
//        mImage_list.add(R.mipmap.ic_launcher);
//        mString_list.add("1");
//        mString_list.add("2");
//        mString_list.add("3");
//        mString_list.add("4");
//        //设置banner样式
//        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
//        //设置图片加载器
//        mBanner.setImageLoader(new Lunbo_Adapter());
//        //设置图片集合
//        mBanner.setImages(mImage_list);
//        //设置banner动画效果
//        mBanner.setBannerAnimation(Transformer.DepthPage);
//        //设置标题集合（当banner样式有显示title时）
//        mBanner.setBannerTitles(mString_list);
//        //设置自动轮播，默认为true
//        mBanner.isAutoPlay(true);
//        //设置轮播时间
//        mBanner.setDelayTime(1500);
//        //设置指示器位置（当banner模式中有指示器时）
//        mBanner.setIndicatorGravity(BannerConfig.CENTER);
//        //banner设置方法全部调用完毕时最后调用
//        mBanner.start();
//        mBanner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//                switch (position) {
//                    case 1:
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        break;
//                    case 4:
//                        break;
//                }
//            }
//        });
//    }


}