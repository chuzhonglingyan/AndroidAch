package com.yuntian.androidarch.net.service;

import com.yuntian.androidarch.bean.GankInfo;
import com.yuntian.baselibs.data.BaseResult;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankService {

    /**
     * 数据类型：福利 | Android | iOS | 休息视频 | 拓展资源 |前端
     * 获取福利图片
     * eg: http://gank.io/api/data/福利/10/1
     * @param page 页码
     * @return
     */
    @GET("https://gank.io/api/data/{dataType}/10/{page}")
    Flowable<BaseResult<List<GankInfo>>> getGankInfoList(@Path("dataType") String dataType, @Path("page") int page);


    @GET("https://gank.io/api/data/{dataType}/{pageSize}/{page}")
    Flowable<BaseResult<List<GankInfo>>> getGankInfoPageList(@Path("dataType") String dataType, @Path("pageSize") int pageSize, @Path("page") int page);


}
