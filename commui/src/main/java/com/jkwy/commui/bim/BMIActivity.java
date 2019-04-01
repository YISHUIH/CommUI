package com.jkwy.commui.bim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jkwy.commui.R;
import com.jkwy.commui.view.dialog.BMIDialog;

import java.math.BigDecimal;

/**
 * 发现页 BIM计算
 */
public class BMIActivity extends com.jkwy.commui.base.BasicActivity implements View.OnClickListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, BMIActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";


    private EditText mHeight;
    private EditText mWeight;
    private TextView mWhate;
    private Button mButton;
    private LinearLayout mLl;
    private TextView mResultLeve;
    private TextView mResult;

    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        sp = getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);


        initView();
    }

    private void initView() {
        super.initViewTitle();
        super.tv_title_name.setText("体重指数计算器");
        super.img_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mHeight = (EditText) findViewById(R.id.bmi_height_et);
        mWeight = (EditText) findViewById(R.id.bmi_weight_et);
        mWhate = (TextView) findViewById(R.id.bmi_whate_tv);
        mButton = (Button) findViewById(R.id.tv_sure);

        mLl = (LinearLayout) findViewById(R.id.linearLayout3);
        mResultLeve = (TextView) findViewById(R.id.bmi_height_result_leve_tv);
        mResult = (TextView) findViewById(R.id.bmi_height_result_tv);
        mWhate.setOnClickListener(this);
        mButton.setOnClickListener(this);

        float height = sp.getFloat(HEIGHT, -1);
        float width = sp.getFloat(WIDTH, -1);
        if (height > 0 && width > 0) {
            mHeight.setText(height + "");
            mWeight.setText(width + "");
            setResult(width, height);
            mLl.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bmi_whate_tv) {
            new BMIDialog(this).show();
        }
        if (v.getId() == R.id.tv_sure) {
            String h = mHeight.getText().toString();
            String w = mWeight.getText().toString();
            if (TextUtils.isEmpty(h)) {
                showToast("请输入身高");
                return;
            }
            if (TextUtils.isEmpty(w)) {
                showToast("请输入体重");
                return;
            }
            try {
                closeSoftInput(this);
                float fh = Float.valueOf(h);
                float fw = Float.valueOf(w);
                setResult(fw, fh);
                SharedPreferences.Editor edit = sp.edit();
                edit.putFloat(HEIGHT, fh);
                edit.putFloat(WIDTH, fw);
                edit.commit();
            } catch (Exception e) {
                showToast("身高或体重输入有误");
            }
        }


    }


    private void setResult(float w, float h) {
        float bmi = getBMI(w, h);
        String bmiStr = getBMIStr(bmi);
        mResultLeve.setText(bmiStr);
        mResult.setText("体重质量指数（BMI）为：" + bmi);
        mLl.setVisibility(View.VISIBLE);
    }


    /**
     * @param w 体重KG
     * @param h 身高 cm
     */
    private float getBMI(float w, float h) {
        h = h / 100;
        float value = w / (h * h);
        BigDecimal b = new BigDecimal(value);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 最理想的体重指数是22
     *
     * @param bmi
     */
    private String getBMIStr(float bmi) {
        if (bmi < 18.5) {
            return "偏瘦";
        } else if (18.5 <= bmi && bmi < 24) {
            return "正常";
        } else if (24 <= bmi && bmi < 28) {
            return "偏胖";
        } else if (28 <= bmi) {
            return "肥胖";
        }
        return "输入有误";
    }
}
