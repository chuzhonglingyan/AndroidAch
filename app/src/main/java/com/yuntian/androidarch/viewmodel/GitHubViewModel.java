package com.yuntian.androidarch.viewmodel;

import com.blankj.utilcode.util.LogUtils;
import com.yuntian.androidarch.bean.Repo;
import com.yuntian.androidarch.contract.GitHubContract;
import com.yuntian.androidarch.repository.GitHubRepository;
import com.yuntian.baselibs.lifecycle.BaseResultLiveData;
import com.yuntian.baselibs.lifecycle.BaseViewModle;
import java.util.List;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;


public class GitHubViewModel extends BaseViewModle<GitHubRepository> implements GitHubContract {


    public void init(String userId) {

    }

    @Override
    public BaseResultLiveData<List<Repo>> getRepoList(String userId) {
        return repo.getRepoList(userId);
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        LogUtils.d("onResume");
    }

}
