package com.yuntian.androidarch.ui.dialog;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yuntian.androidarch.R;
import com.yuntian.basecomponent.dialog.BaseDialogFragment;
import com.yuntian.baselibs.util.UiUtils;
import com.yuntian.basecomponent.view.SafeEditText;

import androidx.annotation.Nullable;

/**
 * @author chulingyan
 * @time 2018/12/30 22:44
 * @describe {https://blog.csdn.net/zxwd2015/article/details/76283609}
 */
public class TestDialog1 extends BaseDialogFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test1;
    }


    @Override
    protected void initView() {
//        setCancelable(true);
        SafeEditText tv_ed=findViewById(R.id.tv_ed);
        tv_ed.setOnEditTextKeyBackListener(this::dismiss);

    }

    @Override
    protected void initListener() {

    }





    private boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }


    @Override
    protected void upDateWindow(Window window) {
        super.upDateWindow(window);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.y = UiUtils.dp2px(context,10); // 新位置Y坐标
        window.setAttributes(wlp);

    }


    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {

    }




}
