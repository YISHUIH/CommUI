package com.jkwy.commui.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jkwy.commui.R;

/**
 * Copyright , 2015-2019, 健康无忧网络科技有限公司
 * Author: 陈刘磊 1070379530@qq.com
 * Date: 2019/4/1 11:21
 * Description: 基类
 */
public class BasicActivity extends Activity {

    /**
     * TITLE
     */
    protected TextView tv_title_name; // title tx

    protected ImageView img_title_back;

    protected TextView reg_title_left;
    protected TextView reg_title_right;
    /**
     * zhaoxuan 是否需要初始化title
     */
    protected void initViewTitle() {
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        img_title_back = (ImageView) findViewById(R.id.img_title_back);
        img_title_back.setVisibility(View.VISIBLE);
    }

    public void showToast(String content){
        Toast.makeText(this,content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 关闭键盘
     *
     * @param context
     */
    public static void closeSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity) context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
