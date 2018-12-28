package com.yuntian.androidarch.lifecycle;

import android.os.Handler;

import com.blankj.utilcode.util.LogUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author chulingyan
 * @time 2018/12/19 22:48
 * @describe
 */
public class MyObserver implements LifecycleObserver {

    private Lifecycle mLifecycle;

    private Handler handler;

    public MyObserver(LifecycleOwner mLifecycle) {
        this.mLifecycle = mLifecycle.getLifecycle(); // 添加监听
        this.mLifecycle.addObserver(this);
        this.handler = new Handler();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() { // 模拟网络请求，延时任务
        handler.postDelayed(() -> {
            // 检查当前activity的创建
            if (mLifecycle.getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                // onResume之后， onPause()之前
                LogUtils.d("currentState" + mLifecycle.getCurrentState().name());
            }
        }, 2000);
        LogUtils.d("currentState" + mLifecycle.getCurrentState().name());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny() {
        LogUtils.d("currentState" + mLifecycle.getCurrentState().name());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {

    }



}
