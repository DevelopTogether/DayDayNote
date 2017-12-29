package com.daydaynote.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daydaynote.Adapter.ConsumeTypeAdapter;
import com.daydaynote.Adapter.GridSpacingItemDecoration;
import com.daydaynote.R;
import com.daydaynote.entity.ConsumeBean;
import com.daydaynote.entity.NoteConsumeMsg;
import com.daydaynote.uitls.KeyboardUtil;
import com.daydaynote.uitls.PublicUtil;
import com.daydaynote.uitls.greendao.DaoUtils;

import java.util.Calendar;
import java.util.List;

import static com.daydaynote.uitls.PublicUtil.getCurrentTime;

/**
 * 添加消费记录的类
 */
public class AddBillActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mTypeMarkerIv;
    /**
     * 一般
     */
    private TextView mTypeNameTv;
    /**
     * 100
     */
    private EditText mMoneyEt;
    private RecyclerView mConsumeTypesRv;
    private RelativeLayout mTypeInfoRl;
    private ConsumeTypeAdapter adapter;
    private KeyboardUtil keyboardUtil;
    /**
     * 一般
     */
    private TextView mNoteDayTv;
    private ImageView mNoteContentTv;
    private String currentDay;
    private String selectedDay;
    private DaoUtils greenDaoUtil;
    private NoteConsumeMsg noteConsumeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bills);
        greenDaoUtil = new DaoUtils(this);
        initNoteConsumeMsg();

        initView();
        initNoteDateDefault();
        initDataForRecyclerView();
        PublicUtil.setStatusBarBg(AddBillActivity.this, getResources().getColor(R.color.yiban));

       List<NoteConsumeMsg>  arrays = greenDaoUtil.listAll();
        int size = arrays.size();

    }

    /**
     * 初始化NoteConsumeMsg实体类
     */
    private void initNoteConsumeMsg() {
        noteConsumeMsg = new NoteConsumeMsg();
        noteConsumeMsg.setType("一般");//默认
        noteConsumeMsg.setTypeIcon(R.mipmap.type_yiban);
        noteConsumeMsg.setConsumeSum("0元");
        noteConsumeMsg.setConsumeDate("");
        noteConsumeMsg.setNoteInfo("");
        noteConsumeMsg.setNotePicPah("");
        noteConsumeMsg.setNoteTime("");
    }

    /**
     * 初始化Recyclerview的数据
     */
    private void initDataForRecyclerView() {

        ConsumeTypeAdapter adapter = new ConsumeTypeAdapter();
        adapter.setConsumeTypeClickedCallBack(new ConsumeTypeAdapter.ConsumeTypeAdapterInterface() {
            @Override
            public void consumeTypeClicked(ConsumeBean bean) {
                mTypeInfoRl.setBackgroundResource(bean.getType_bg());
                mTypeMarkerIv.setBackgroundResource(bean.getMarker());
                mTypeNameTv.setText(bean.getType_name());
                mMoneyEt.setText("¥0.00");
                noteConsumeMsg.setTypeIcon(bean.getMarker());
                noteConsumeMsg.setConsumeSum("0");
                noteConsumeMsg.setType(bean.getType_name());
                PublicUtil.setStatusBarBg(AddBillActivity.this, getResources().getColor(bean.getType_bg()));

            }
        });
        mConsumeTypesRv.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(this, 5);
        mConsumeTypesRv.setLayoutManager(manager);
        int spanCount = 5;
        int spacing = 50;
        boolean includeEdge = false;
        mConsumeTypesRv.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));


    }

    private void initView() {
        mMoneyEt = (EditText) findViewById(R.id.money_et);
        keyboardUtil = new KeyboardUtil(AddBillActivity.this, AddBillActivity.this, mMoneyEt);
        mMoneyEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int numberType = mMoneyEt.getInputType();
                mMoneyEt.setInputType(InputType.TYPE_NULL);
                keyboardUtil.showKeyboard();
                mMoneyEt.setInputType(numberType);
                return true;
            }
        });
        //键盘确定按钮的点击事件
        keyboardUtil.setOnEnterListener(new KeyboardUtil.EnterListener() {
            @Override
            public void enter() {
                String consumeSum = mMoneyEt.getText().toString().trim();
                if ("¥0.00".equals(consumeSum)) {
                    Toast.makeText(getApplicationContext(), "请填写消费金额", Toast.LENGTH_LONG).show();
                    return;
                }
                noteConsumeMsg.setConsumeSum(consumeSum);
                noteConsumeMsg.setConsumeDate(PublicUtil.noteDate);
                noteConsumeMsg.setNoteTime(getCurrentTime());
               if (greenDaoUtil.insertEntity(noteConsumeMsg)) {
                   initNoteConsumeMsg();
                   Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();
               }
            }

            //键盘清楚按钮的点击事件
            @Override
            public void clear() {
                mMoneyEt.setText("¥0.00");
            }
        });
        mTypeInfoRl = (RelativeLayout) findViewById(R.id.type_info_rl);
        mTypeMarkerIv = (ImageView) findViewById(R.id.type_marker_iv);
        mTypeNameTv = (TextView) findViewById(R.id.type_name_tv);

        mConsumeTypesRv = (RecyclerView) findViewById(R.id.consume_types_rv);
        mNoteDayTv = (TextView) findViewById(R.id.note_day_tv);
        mNoteContentTv = (ImageView) findViewById(R.id.note_content_tv);
        mNoteContentTv.setOnClickListener(this);
        mNoteDayTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_content_tv://备注点击事件
                String date = mNoteDayTv.getText().toString().trim();
                if (date.equals("今天")) {
                    PublicUtil.noteDate = currentDay;
                } else {
                    PublicUtil.noteDate = selectedDay;
                }
                startActivityForResult(new Intent(AddBillActivity.this, NoteInfoActivity.class), 0);
                break;
            case R.id.note_day_tv://备注日期点击事件
                showPopwindow();
                break;
        }
    }

    private void showPopwindow() {
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(this, R.layout.calendar_view, null);
        CalendarView calendarView = (CalendarView) popView.findViewById(R.id.calendar_cv);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        final PopupWindow popWindow = new PopupWindow(popView, width, height);
        popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                initNoteDate(year, month + 1, dayOfMonth);
                popWindow.dismiss();
            }
        });
    }

    /**
     * 初始化记账日期
     *
     * @param year
     * @param month
     * @param dayOfMonth
     */
    private void initNoteDate(int year, int month, int dayOfMonth) {
        String date = year + "年" + "\n" + month + "月" + dayOfMonth + "日";
        selectedDay = year + "年" + month + "月" + dayOfMonth + "日";
        if (PublicUtil.compareTimeReturnInteger(selectedDay, currentDay) == 2) {//选择日期大于当前时间，提示所选时间超出范围
            Toast.makeText(getApplicationContext(), "所选时间超出范围", Toast.LENGTH_SHORT).show();
            return;
        } else if (PublicUtil.compareTimeReturnInteger(selectedDay, currentDay) == 1) {//当天日期
            date = year + "年" + "\n" + "今天";
        }
        int start = date.indexOf("\n");
        int end = date.length();
        SpannableString textSpan = new SpannableString(date);
        textSpan.setSpan(new AbsoluteSizeSpan(30), 0, start, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(38), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mNoteDayTv.setText(textSpan);
    }

    /**
     * 初始化默认记账日期
     */
    private void initNoteDateDefault() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        currentDay = year + "年" + month + "月" + day + "日";
        initNoteDate(year, month, day);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == PublicUtil.NOTEDTOCOMMIT) {
            if (data != null) {
                String notecontent = data.getStringExtra("noteContent");
                String picPath = data.getStringExtra("picPath");
                noteConsumeMsg.setNoteInfo(notecontent);
                noteConsumeMsg.setNotePicPah(picPath);
            }
        }
    }
}
