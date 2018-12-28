package com.yuntian.androidarch.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.yuntian.androidarch.R;
import com.yuntian.baselibs.base.BaseFragment;
import com.yuntian.baselibs.di.component.AppComponent;

import androidx.annotation.Nullable;

import static com.yuntian.androidarch.ui.activity.ModuleViewPagerActivity.POSITION;

public class ModuleListFragment extends BaseFragment {


    private TextView tvShow;
    private int position;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_module;
    }


    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
         tvShow= findViewById(R.id.tv_show);
         LogUtils.d(this.toString()+",initView");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {
        if (ObjectUtils.isNotEmpty(getArguments())) {
            position=getArguments().getInt(POSITION);
            tvShow.setText("我是"+(position+1)+"号fragment");
        }

        LogUtils.d(this.toString()+",initData"+"加载数据:"+position);
    }


    @Override
    protected void loadInitData() {
        LogUtils.d(this.toString());
    }

}
