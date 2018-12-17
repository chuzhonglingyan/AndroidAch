package com.yuntian.androidarch.repository;

import com.yuntian.androidarch.net.service.GoodService;
import com.yuntian.androidarch.contract.GoodContract;
import com.yuntian.baselibs.lifecycle.BaseRepository;

/**
 * @author   chulingyan
 * @time     2018/12/07 20:38
 * @describe 接口约束
 */
public class GoodRepository extends BaseRepository<GoodService> implements GoodContract {



    public GoodRepository(GoodService service) {
        super(service);
    }









}


