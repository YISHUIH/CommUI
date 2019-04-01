package com.jkwy.commui.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jkwy.commui.R;

/**
 * Created by tangzejin on 2016/9/26.
 * 功能说明： 发现
 */

public class BMIDialog extends com.jkwy.commui.base.BaseDialog {

    public BMIDialog(Context context) {
        super(context, R.style.dialog_simple_style);
        setContentView(R.layout.dialog_bmi_info);
        TextView btnConfirm = (TextView) findViewById(R.id.tv_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
