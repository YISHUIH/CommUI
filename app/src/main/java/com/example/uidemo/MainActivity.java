package com.example.uidemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jkwy.commui.bim.BMIActivity;
import com.jkwy.commui.yimiao.VaccineListActivity;
import com.jkwy.commui.base.BasicActivity;
import com.jkwy.commui.yucanqi.ChildBirthActivity;

public class MainActivity extends BasicActivity {

    private TextView mTvYcq;
    private TextView mTvYmsj;
    private TextView mTvBimjs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mTvYcq = findViewById(R.id.tv_ycq);
        mTvYmsj = findViewById(R.id.tv_ymsj);
        mTvBimjs = findViewById(R.id.tv_bmijs);


        mTvYcq.setOnClickListener(click);
        mTvYmsj.setOnClickListener(click);
        mTvBimjs.setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.tv_ycq:
                    ChildBirthActivity.start(MainActivity.this);
                    break;
                case R.id.tv_ymsj:
                    VaccineListActivity.start(MainActivity.this);
                    break;
                case R.id.tv_bmijs:
                    BMIActivity.start(MainActivity.this);
                    break;

                default:
            }

        }
    };
}
