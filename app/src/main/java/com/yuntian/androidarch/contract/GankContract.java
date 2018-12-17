package com.yuntian.androidarch.contract;

import com.yuntian.androidarch.bean.GankInfo;
import com.yuntian.baselibs.lifecycle.BaseResultLiveData;

import java.util.List;

public interface GankContract {


    BaseResultLiveData<List<GankInfo>> getGankInfoList(String dataType,int page);

}
