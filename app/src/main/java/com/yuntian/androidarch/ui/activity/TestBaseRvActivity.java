package com.yuntian.androidarch.ui.activity;

import android.os.Bundle;

import com.yuntian.androidarch.R;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class TestBaseRvActivity extends BaseActivity {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {


    }


}
