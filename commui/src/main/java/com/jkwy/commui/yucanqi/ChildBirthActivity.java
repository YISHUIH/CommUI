package com.jkwy.commui.yucanqi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jkwy.commui.R;
import com.jkwy.commui.base.BasicActivity;
import com.jkwy.commui.yimiao.util.AbDateUtil;
import com.jkwy.commui.view.dialog.SelectDateDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tangzejin on 2016/9/26.
 * 功能说明：预产期计算
 */

public class ChildBirthActivity extends BasicActivity implements View.OnClickListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, ChildBirthActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }
    private static final String PREGNANT_TIME = "pregnant_time";

    private TextView time, time1, time2, time3;

    private LinearLayout mLl;
    private Button mButton;


    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_birth);

        sp = getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);


        initView();
    }

    private void initView() {
        super.initViewTitle();
        super.tv_title_name.setText("预产期计算");
        super.img_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        time = (TextView) findViewById(R.id.child_birth_time_tv);
        time1 = (TextView) findViewById(R.id.child_birth_time1_tv);
        time2 = (TextView) findViewById(R.id.child_birth_time2_tv);
        time3 = (TextView) findViewById(R.id.child_birth_time3_tv);
        mButton = (Button) findViewById(R.id.tv_sure);
        mLl = (LinearLayout) findViewById(R.id.linearLayout2);

        time.setOnClickListener(this);
        mButton.setOnClickListener(this);

        String date = sp.getString(PREGNANT_TIME, "");
        if (!TextUtils.isEmpty(date)) {
            time.setText(date);
            setResult(date);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.child_birth_time_tv) {
            String s = time.getText().toString();
            if (TextUtils.isEmpty(s)) {
                s = AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMD);
            }
            SelectDateDialog dia = new SelectDateDialog(this, s, 2);
            dia.setTitleTx("预产期计算");
            dia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dateStr = (String) v.getTag();
                    time.setText(dateStr);
                }
            }).show();
        }
        if (v.getId() == R.id.tv_sure) {
            String str = time.getText().toString();
            if (TextUtils.isEmpty(str)) {
                showToast("请选择末次月经时间");
                return;
            }
            setResult(str);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(PREGNANT_TIME, str);
            edit.commit();
        }


    }


    /**
     * 计算预产期
     *
     * @param date 末次月经
     */
    private void setResult(String date) {
        Date d = AbDateUtil.getDateByFormat(date, AbDateUtil.dateFormatYMD);

        Date dateByOffset = new AbDateUtil().getDateByOffset(d, Calendar.DATE, 280);//预产期
        String stringByFormat = AbDateUtil.getStringByFormat(dateByOffset, AbDateUtil.dateFormatYMD);
        time1.setText(stringByFormat);

        Calendar temp = Calendar.getInstance();
        temp.setTime(d);
        long f = Calendar.getInstance().getTimeInMillis() - temp.getTimeInMillis() / (1000 * 60 * 60 * 24) * (1000 * 60 * 60 * 24);
        int day = (int) (f / (1000 * 3600 * 24) - 1);
        if (day < 0) {
            showToast("末次月经请选择小于今天");
            mLl.setVisibility(View.GONE);
            return;
        } else if (day > 280) {
            showToast("宝宝已经出生");
            mLl.setVisibility(View.GONE);
            return;
        }
        if (day % 7 == 0) {
            time2.setText(day + "天(" + day / 7 + "周)");
        } else {
            time2.setText(day + "天(" + day / 7 + "周+" + day % 7 + "天)");
        }

        mLl.setVisibility(View.VISIBLE);

        time3.setText(280 - day + "天");
    }

}
