package com.jkwy.commui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jkwy.commui.R;


/**
 * Created by user on 2016/5/17.
 */
public class BaseDialog extends Dialog implements View.OnClickListener {
    protected View mRoot;
    protected View.OnClickListener listener;

    public BaseDialog(Context context) {
        this(context, R.style.dialog_simple_style);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected void init() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.windowAnimations = R.style.mydialog;
        window.getDecorView().setPadding(0, 0, 0, 0);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的位置在底部
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        window.setGravity(Gravity.BOTTOM);
    }


    protected void setGravity(int gravity) {
        getWindow().setGravity(gravity);
    }

    protected void setPadding(int l, int t, int r, int b) {
        getWindow().getDecorView().setPadding(l, t, r, b);
    }

    protected void fromButton() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.windowAnimations = R.style.mydialog;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    protected void clearBack() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND | WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void onClick(View v) {

    }

    public void finish() {
        dismiss();
        Context context = getContext();
        if (context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    public BaseDialog setOnClickListener(View.OnClickListener l) {
        listener = l;
        return this;
    }


    public BaseDialog setDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
        return this;
    }
}
