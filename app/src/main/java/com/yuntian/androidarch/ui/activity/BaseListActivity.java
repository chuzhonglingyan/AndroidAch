package com.yuntian.androidarch.ui.activity;

import android.os.Bundle;

import com.blankj.utilcode.util.ObjectUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yuntian.androidarch.R;
import com.yuntian.androidarch.ui.adapter.BaseRvAdapter;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
/**
 * @author   chulingyan
 * @time     2018/12/29 18:36
 * @describe 列表例子
 */

public abstract class BaseListActivity<T> extends BaseActivity {

    protected RefreshLayout refreshLayout;
    protected RecyclerView rv;
    protected BaseRvAdapter<T>  baseRvAdapter;
    protected static final int FISTPAGE =1;
    protected static final int PAGESIZE =10;
    protected  int startPage=FISTPAGE;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_re_list;
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        baseRvAdapter=getRvAdapter();
        rv.setAdapter(baseRvAdapter);
    }

   protected abstract   BaseRvAdapter<T> getRvAdapter();


   protected abstract void   getListData();



    @Override
    protected void initListener() {
//        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            startPage= FISTPAGE;
            getListData();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            getListData();
        });
    }


    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {
        //触发自动刷新
        refreshLayout.autoRefresh();
    }

    /**
     * 可以重写
     * @param list
     */
    public void  setData( List<T> list){
        if (startPage== FISTPAGE){
            refreshLayout.finishRefresh();
            baseRvAdapter.setData(list);
            startPage++;
        }else {
            if (ObjectUtils.isEmpty(list)){
                refreshLayout.finishLoadMoreWithNoMoreData();
            }else {
                baseRvAdapter.addData(list);
                refreshLayout.finishLoadMore();
                startPage++;
            }
        }
    }


}
