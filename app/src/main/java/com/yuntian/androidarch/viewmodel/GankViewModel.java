package com.yuntian.androidarch.viewmodel;

import com.blankj.utilcode.util.LogUtils;
import com.yuntian.androidarch.bean.GankInfo;
import com.yuntian.androidarch.contract.GankContract;
import com.yuntian.androidarch.repository.GankRepository;
import com.yuntian.baselibs.lifecycle.BaseResultLiveData;
import com.yuntian.baselibs.lifecycle.BaseViewModle;

import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class GankViewModel extends BaseViewModle<GankRepository> implements GankContract {


    @Override
    public BaseResultLiveData<List<GankInfo>> getGankInfoList(String dataType, int page) {
        return repo.getGankInfoList(dataType, page);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        LogUtils.d("onResume");
    }
}
