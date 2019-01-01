package com.yuntian.androidarch.ui.dialog;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yuntian.androidarch.R;
import com.yuntian.basecomponent.dialog.BaseDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

/**
 * @author chulingyan
 * @time 2018/12/30 22:44
 * @describe {https://blog.csdn.net/zxwd2015/article/details/76283609}
 */
public class PopDialog extends BaseDialogFragment {


    private View showView;
    private int typePos;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test2;
    }

    @Override
    protected void initView() {
        setCancelable(true);
    }


    @Override
    protected void initListener() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("typePos",typePos);
    }

    public void showViewTop(FragmentActivity activity, View view) {
        show(activity.getSupportFragmentManager().beginTransaction());
        showView = view;
        typePos = 0;
    }

    public void showViewBottom(FragmentActivity activity, View view) {
        show(activity);
        showView = view;
        typePos = 1;
    }

    @Override
    protected void upDateWindow(Window window) {
        super.upDateWindow(window);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.TOP;
        wlp.dimAmount = 0;//完全透明背景
//        wlp.width = Utils.dp2px(context,100); // 宽度
//        wlp.height = Utils.dp2px(context,100); // 高度
        //自适应
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        if (showView != null && typePos == 0) {
            //获取view的位置和尺寸
            int[] pos = new int[2]; //到
            showView.getLocationOnScreen(pos);//获取当前屏幕位置的横坐标，获取当前屏幕位置的纵坐标
            wlp.y = pos[1] - parentView.getHeight() - getdynamicStatusBarHeight();
        } else if (showView != null && typePos == 1) {
            //获取view的位置和尺寸
            int[] pos = new int[2]; //到
            showView.getLocationOnScreen(pos);//获取当前屏幕位置的横坐标，获取当前屏幕位置的纵坐标
            wlp.y = pos[1] + showView.getHeight() - getdynamicStatusBarHeight();
        }
        window.setAttributes(wlp);
    }

    private int getFixStatusBarHeight() {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }


    private int getdynamicStatusBarHeight() {
        View view = activity.getWindow().getDecorView();
        // 获取状态栏高度
        Rect frame = new Rect();
        // 测量屏幕宽和高
        view.getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }


    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            typePos=savedInstanceState.getInt("typePos");
        }
    }





}
