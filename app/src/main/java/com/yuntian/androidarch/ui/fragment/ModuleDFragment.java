package com.yuntian.androidarch.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.yuntian.androidarch.R;
import com.yuntian.baselibs.base.BaseFragment;
import com.yuntian.baselibs.di.component.AppComponent;
import androidx.annotation.Nullable;

public class ModuleDFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_module;
    }

    @Override
    protected void loadInitData() {

    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        TextView tvShow= findViewById(R.id.tv_show);
        tvShow.setText("ModuleDFragment");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {


    }


}
