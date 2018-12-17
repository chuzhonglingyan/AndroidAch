package com.yuntian.androidarch.repository;

import com.yuntian.androidarch.bean.GankInfo;
import com.yuntian.androidarch.contract.GankContract;
import com.yuntian.androidarch.net.service.GankService;
import com.yuntian.baselibs.data.BaseResult;
import com.yuntian.baselibs.lifecycle.BaseRepository;
import com.yuntian.baselibs.lifecycle.BaseResultLiveData;
import com.yuntian.baselibs.rxjava.CustomSubscriber;
import com.yuntian.baselibs.rxjava.RxHandleResult;
import java.util.List;
import io.reactivex.Flowable;

public class GankRepository extends BaseRepository<GankService> implements GankContract {


    private final static int PAGE_SIZE = 10;

    private static final String TAG = "GankRepository";


    public GankRepository(GankService service) {
        super(service);
    }


    @Override
    public BaseResultLiveData<List<GankInfo>> getGankInfoList(String dataType, int page) {
        final BaseResultLiveData<List<GankInfo>> data = BaseResultLiveData.newIntance();
        Flowable<List<GankInfo>> flowable = service.getGankInfoList(dataType, page).compose(RxHandleResult.handleFlowableResult());
        flowable.subscribe(new CustomSubscriber<List<GankInfo>>(getRxManager()) {
            @Override
            public void onResponse(BaseResult<List<GankInfo>> baseResult) {
                data.setValue(baseResult);
            }
        });
        return data;
    }

    public Flowable<List<GankInfo>> getGankInfoPageList(String dataType, int pageSize, int page) {
        return service.getGankInfoPageList(dataType, pageSize, page).compose(RxHandleResult.handleFlowableResult());
    }


}
