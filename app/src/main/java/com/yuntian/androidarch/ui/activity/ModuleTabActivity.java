package com.yuntian.androidarch.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.FrameLayout;
import com.blankj.utilcode.util.LogUtils;
import com.yuntian.androidarch.R;
import com.yuntian.androidarch.ui.fragment.ModuleAFragment;
import com.yuntian.androidarch.ui.fragment.ModuleBFragment;
import com.yuntian.androidarch.ui.fragment.ModuleCFragment;
import com.yuntian.androidarch.ui.fragment.ModuleDFragment;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;
import java.util.Stack;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ModuleTabActivity extends BaseActivity {

    private int tabPosition;
    private static final String SAVE_TABPOSITION = "tabPosition";

    private FrameLayout fl_container;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module;
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        fl_container=findViewById(R.id.fl_container);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(SAVE_TABPOSITION, tabPosition);
    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {
        Stack<Class<? extends Fragment>> fragmentClass = new Stack<>();
        fragmentClass.push(ModuleAFragment.class);
        fragmentClass.push(ModuleBFragment.class);
        fragmentClass.push(ModuleCFragment.class);
        fragmentClass.push(ModuleDFragment.class);

        if (isInit) {
            while (!fragmentClass.empty()) {
                Class<? extends Fragment> clas = fragmentClass.pop();
                fragmentHelp.add(R.id.fl_container, fragmentHelp.newFragmentIntance(clas));
            }
        } else {
            while (!fragmentClass.empty()) {
                Class<? extends Fragment> clas = fragmentClass.pop();
                fragmentHelp.pushFragment(fragmentHelp.findFragmentByTag(clas));
            }
            if (savedInstanceState != null) {
                tabPosition = savedInstanceState.getInt(SAVE_TABPOSITION);
            }
        }
         //view加载完成时回调
        fl_container.getViewTreeObserver().addOnGlobalLayoutListener(()->{
            LogUtils.d("getWidth="+fl_container.getWidth()+",getHeight="+fl_container.getHeight());
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        fragmentHelp.showAndHide(fragmentHelp.getStatckTop());
        LogUtils.d(fragmentHelp.getFragmentStack().size());
    }


    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d("getWidth="+fl_container.getWidth()+",getHeight="+fl_container.getHeight());
    }
}
