package com.yuntian.androidarch.ui.activity;

import android.view.ViewGroup;

import com.classic.common.MultipleStatusView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yuntian.androidarch.R;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;

/**
 * @author   chulingyan
 * @time     2018/12/29 18:36
 * @describe 多状态页面
 */

public abstract class BaseStateViewActivity extends BaseActivity {

    protected RefreshLayout refreshLayout;
    protected MultipleStatusView multipleStatusView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_state_view_list;
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        multipleStatusView = findViewById(R.id.multiple_status_view);
        multipleStatusView.showContent(getContenLayoutId(),new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        refreshLayout.setEnableLoadMore(false);
    }

    protected abstract int getContenLayoutId();


    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(refreshLayout -> {
             refresh();
        });
        multipleStatusView.setOnRetryClickListener(v -> {
             refreshLayout.autoRefresh();
        });
    }


    protected void  switchStatusView(int status){
       if(MultipleStatusView.STATUS_EMPTY==status){
            multipleStatusView.showEmpty();
        }else  if(MultipleStatusView.STATUS_ERROR==status){
            multipleStatusView.showError();
        }else  if(MultipleStatusView.STATUS_LOADING==status){
            multipleStatusView.showLoading();
        } else  if(MultipleStatusView.STATUS_NO_NETWORK==status){
            multipleStatusView.showNoNetwork();
        }else {
            multipleStatusView.showContent();
       }
    }


    public abstract void refresh();



}
