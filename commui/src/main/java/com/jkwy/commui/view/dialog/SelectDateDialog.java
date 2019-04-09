package com.jkwy.commui.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;

import com.jkwy.commui.R;
import com.jkwy.commui.util.AbDateUtil;
import com.jkwy.commui.view.PickerView;
import com.jkwy.commui.base.BaseDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 2016/5/17.
 */
public class SelectDateDialog extends BaseDialog {
    private TextView mCancle, mAscertain, mTitle;
    private PickerView mYearPv, mMonthPv, mDayPv;

    private String dateStr;

    private SparseIntArray daysOfMonth; // 保存月份对应的天数

    int yearRangeLow = 20;//时间选择范围下限
    int yearRangeHigh = 1;//时间选择范围上限

    // 已选择的日期
    private String mYear = null;
    private String mMonth = null;
    private String mDay = null;

    // 当前的
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDay;

    private boolean isPast;//只能选择过去的

    public SelectDateDialog(Context context, String dateStr) {
        this(context, dateStr, 1);
    }

    public SelectDateDialog(Context context, String dateStr, int yearRangeHigh) {
        super(context);
        this.dateStr = dateStr;
        this.yearRangeHigh = yearRangeHigh;
        init();
    }

    public void setTitleTx(String titleTx) {
        mTitle.setText(titleTx);
    }

    /**
     * 是否只能选择过去时间
     */
    public SelectDateDialog past(boolean b) {
        isPast = b;
        updateDays();
        return this;
    }

    @Override
    protected void init() {
        super.init();
        mRoot = View.inflate(getContext(), R.layout.view_dia_select_date, null);
        setContentView(mRoot);

        mCancle = (TextView) mRoot.findViewById(R.id.textView1);
        mTitle = (TextView) mRoot.findViewById(R.id.textView2);
        mAscertain = (TextView) mRoot.findViewById(R.id.textView3);
        mCancle.setOnClickListener(this);
        mAscertain.setOnClickListener(this);

        mYearPv = (PickerView) mRoot.findViewById(R.id.pv_year);
        mMonthPv = (PickerView) mRoot.findViewById(R.id.pv_month);
        mDayPv = (PickerView) mRoot.findViewById(R.id.pv_day);


        mYearPv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(int i, Object year) {
                SelectDateDialog.this.mYear = year.toString().replace("年", "");
                updateDays();
            }
        });
        mMonthPv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(int i, Object month) {
                SelectDateDialog.this.mMonth = month.toString().replace("月", "");
                updateDays();
            }
        });
        mDayPv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(int i, Object day) {
                SelectDateDialog.this.mDay = day.toString().replace("日", "");
            }
        });


        daysOfMonth = new SparseIntArray();
        daysOfMonth.put(1, 31);
        daysOfMonth.put(2, 28);
        daysOfMonth.put(3, 31);
        daysOfMonth.put(4, 30);
        daysOfMonth.put(5, 31);
        daysOfMonth.put(6, 30);
        daysOfMonth.put(7, 31);
        daysOfMonth.put(8, 31);
        daysOfMonth.put(9, 30);
        daysOfMonth.put(10, 31);
        daysOfMonth.put(11, 30);
        daysOfMonth.put(12, 31);

        //************************************************************************************************
        // 获取当天日期  默认
        Calendar today = Calendar.getInstance(Locale.CHINA);
        int curYear = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DAY_OF_MONTH);
        mCurrentYear = curYear;
        mCurrentMonth = month;
        mCurrentDay = day;
        //************************************************************************************************
        int year = -1; //default selected year
        if (!TextUtils.isEmpty(dateStr)) {
            dateStr = dateStr.trim();
            String[] tmp = dateStr.split("-");
            if (tmp.length < 4 && tmp.length > 0) {//tmp.length:1--3
                SimpleDateFormat sdf = new SimpleDateFormat(AbDateUtil.dateFormatYMD, Locale.CHINA);
                try {
                    Date date = sdf.parse(dateStr);
                    Calendar cal = Calendar.getInstance(Locale.CHINA);
                    cal.setTime(date);
                    if ((cal.get(Calendar.YEAR) <= curYear && cal.get(Calendar.YEAR) >= curYear - yearRangeLow)//在当前年份倒退yearRangeHigh年之内
                            || (cal.get(Calendar.YEAR) >= curYear && cal.get(Calendar.YEAR) <= curYear + yearRangeHigh)) {//在当前年份前进yearRangeHigh年之内
                        year = cal.get(Calendar.YEAR);
                    }
                    month = cal.get(Calendar.MONTH) + 1;
                    day = cal.get(Calendar.DAY_OF_MONTH);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        //如果带入的年份在当前年份倒退20年之外，展示为现在的年份。
        mYear = String.format("%4d", year == -1 ? curYear : year); //不合法则显示默认当前年份
        mMonth = String.format("%02d", month);
        mDay = String.format("%02d", day);


        List<String> years = new ArrayList<String>();
        for (int i = 0 - yearRangeLow; i <= 0 + yearRangeHigh; ++i) {  //picker view year range, curYear to curYear-20
            years.add(String.format("%4d", curYear + i) + "年");
        }
//        List<String> months = new ArrayList<String>(12);
//        for (int i = 1; i <= 12; ++i) {
//            months.add(String.format("%02d", i)+"月");
//        }
//        List<String> days = new ArrayList<String>(31);
//
        mYearPv.setData(years);
//        mMonthPv.setData(months);
//        mDayPv.setData(days);

        // 设置默认值
        mYearPv.setSelected(mYear + "年");
        updateDays();
//        mMonthPv.setSelected(mMonth+"月");
//        mDayPv.setSelected(mDay+"日");
    }


    protected void updateDays() {
        if (!TextUtils.isEmpty(mYear) && !TextUtils.isEmpty(mMonth)) {
            //月
            int year = Integer.parseInt(mYear);
            List<String> months = new ArrayList<String>(12);
            if (isPast && year == mCurrentYear) {//只能选择过去的月
                for (int i = 1; i <= mCurrentMonth; ++i) {
                    months.add(String.format("%02d", i) + "月");
                }
            } else {
                for (int i = 1; i <= 12; ++i) {
                    months.add(String.format("%02d", i) + "月");
                }
            }
            mMonthPv.setData(months);
            if (months.contains(mMonth + "月")) {
                mMonthPv.setSelected(mMonth + "月");
            } else {
                mMonthPv.setSelected(0);
                mMonth = "01";
            }
            //日
            int month = Integer.parseInt(mMonth);
            int realDays = daysOfMonth.get(month);
            if (2 == month && (year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                ++realDays;
            }
            if (isPast && year == mCurrentYear && month == mCurrentMonth) {
                realDays = Math.min(realDays, mCurrentDay);
            }
            List<String> days = new ArrayList<String>();
            for (int i = 1; i <= realDays; ++i) {
                days.add(String.format("%02d", i) + "日");
            }
            mDayPv.setData(days);
            if (days.contains(mDay + "日")) {
                mDayPv.setSelected(mDay + "日");
            } else {
                mDayPv.setSelected(0);
                mDay = "01";
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.textView3) {
            if (listener != null) {
                v.setTag(mYear + "-" + mMonth + "-" + mDay);
                listener.onClick(v);
            }
        }
        dismiss();
    }


}
