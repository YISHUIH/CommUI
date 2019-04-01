package com.jkwy.commui.yimiao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jkwy.commui.R;
import com.jkwy.commui.base.BasicActivity;
import com.jkwy.commui.yimiao.entry.VaccineList;

/**
 * Created by tangzejin on 2016/9/22.
 * 功能说明：疫苗详情页
 */

public class VaccineDetActivity extends BasicActivity {
    public static void start(Activity ctx, String time, VaccineList.Vaccine v) {
        Intent intent = new Intent(ctx, VaccineDetActivity.class);
        intent.putExtra("time", time);
        intent.putExtra("Vaccine", v);
        ctx.startActivity(intent);
    }

    private String timeStr = "";
    private VaccineList.Vaccine v;


    private TextView considerationsPrompt, nursingPrompt, reminderPrompt, replacePrompt;
    private TextView name, count, briefing, effect, time, considerations, nursing, reminder, replace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_det);

        initData();
        initView();
    }


    private void initData() {
        v = getIntent().getParcelableExtra("Vaccine");
        timeStr = getIntent().getStringExtra("time");
    }


    private void initView() {
        super.initViewTitle();
        super.tv_title_name.setText("疫苗详情");
        super.img_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name = (TextView) findViewById(R.id.vaccine_name_tv);
        count = (TextView) findViewById(R.id.vaccine_count_tv);
        briefing = (TextView) findViewById(R.id.vaccine_briefing_tv);
        effect = (TextView) findViewById(R.id.vaccine_effect_tv);
        time = (TextView) findViewById(R.id.vaccine_time_tv);

        considerationsPrompt = (TextView) findViewById(R.id.vaccine_considerations_tv_prompt);
        considerations = (TextView) findViewById(R.id.vaccine_considerations_tv);
        nursingPrompt = (TextView) findViewById(R.id.vaccine_nursing_tv_prompt);
        reminderPrompt = (TextView) findViewById(R.id.vaccine_reminder_tv_prompt);
        nursing = (TextView) findViewById(R.id.vaccine_nursing_tv);
        reminder = (TextView) findViewById(R.id.vaccine_reminder_tv);
        replacePrompt = (TextView) findViewById(R.id.vaccine_replace_tv_prompt);
        replace = (TextView) findViewById(R.id.vaccine_replace_tv);


        name.setText(v.getName());
        count.setText(v.getCount());
        briefing.setText(v.getBriefing());
        effect.setText(v.getEffect());
        time.setText(timeStr);

        if (!TextUtils.isEmpty(v.getConsiderations())) {
            considerationsPrompt.setVisibility(View.VISIBLE);
            considerations.setVisibility(View.VISIBLE);
            considerations.setText(v.getConsiderations());
        }
        if (!TextUtils.isEmpty(v.getNursing())) {
            nursingPrompt.setVisibility(View.VISIBLE);
            nursing.setVisibility(View.VISIBLE);
            nursing.setText(v.getNursing());
        }
        if (!TextUtils.isEmpty(v.getReminder())) {
            reminderPrompt.setVisibility(View.VISIBLE);
            reminder.setVisibility(View.VISIBLE);
            reminder.setText(v.getReminder());
        }
        if (!TextUtils.isEmpty(v.getReplace())) {
            replacePrompt.setVisibility(View.VISIBLE);
            replace.setVisibility(View.VISIBLE);
            replace.setText(v.getReplace());
        }
    }


}
