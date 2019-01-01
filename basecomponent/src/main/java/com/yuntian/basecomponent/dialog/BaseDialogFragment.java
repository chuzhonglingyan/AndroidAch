package com.yuntian.basecomponent.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.LogUtils;
import com.yuntian.basecomponent.R;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author chulingyan
 * @time 2018/12/31  12:36
 * @describe 适合网络请求
 */

public abstract class BaseDialogFragment extends DialogFragment {

    /**
     * 监听弹出窗是否被取消
     */
    private OnDialogCancelListener mCancelListener;
    protected AppCompatActivity activity;
    protected Context context;
    protected View parentView;

    protected boolean isViewInit;
    protected boolean isInitCreate;//是否初次创建

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (AppCompatActivity) context;
        this.context = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        isViewInit = true;
        parentView = inflater.inflate(getLayoutId(), container, false);
        return parentView;
    }

    private void initWindow() {
        // 设置宽度为屏宽
        final Window window = getDialog().getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
    }

    public Window getWindow() {
        if (getDialog() != null) {
            return getDialog().getWindow();
        }
        return null;
    }

    protected void upDateWindow(Window window) {

    }

    public void show(FragmentActivity activity) {
        super.show(activity.getSupportFragmentManager(), this.getClass().getSimpleName());
    }

    public void show(FragmentTransaction fragmentTransaction) {
        super.show(fragmentTransaction, this.getClass().getSimpleName());
    }


    protected abstract int getLayoutId();



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWindow();
        initView();
        initListener();
        // 向 ViewTreeObserver 注册方法，以获取控件尺寸
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int h = view.getHeight();
                LogUtils.d("view.h="+h);
                // 成功调用一次后，移除 Hook 方法，防止被反复调用
                // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
                // 使用新方法 removeOnGlobalLayoutListener() 代替
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isInitCreate = savedInstanceState == null;
        initData(savedInstanceState == null, savedInstanceState);
        upDateWindow(getWindow());
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    protected <T extends View> T findViewById(@IdRes int id) {
        return parentView.findViewById(id);
    }


    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData(boolean isInit, @Nullable Bundle savedInstanceState);


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mCancelListener != null) {
            mCancelListener.onCancel();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

