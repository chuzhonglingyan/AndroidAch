package com.yuntian.androidarch.contract;

import com.yuntian.androidarch.bean.Repo;
import com.yuntian.baselibs.lifecycle.BaseResultLiveData;

import java.util.List;

public interface GitHubContract {


    BaseResultLiveData<List<Repo>> getRepoList(String userId);


}
