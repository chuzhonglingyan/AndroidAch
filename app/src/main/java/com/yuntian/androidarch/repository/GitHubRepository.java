package com.yuntian.androidarch.repository;

import android.util.Log;

import com.yuntian.androidarch.bean.Repo;
import com.yuntian.androidarch.net.service.GitHubservice;
import com.yuntian.androidarch.contract.GitHubContract;
import com.yuntian.baselibs.data.BaseResult;
import com.yuntian.baselibs.lifecycle.BaseRepository;
import com.yuntian.baselibs.lifecycle.BaseResultLiveData;
import com.yuntian.baselibs.net.NetCallback;

import java.util.List;

import retrofit2.Call;

public class GitHubRepository extends BaseRepository<GitHubservice> implements GitHubContract {

    private static final String TAG = "GitHubRepository";

    public GitHubRepository(GitHubservice service) {
        super(service);
    }


    private BaseResultLiveData<List<Repo>> baseResultLiveData;


    @Override
    public BaseResultLiveData<List<Repo>> getRepoList(String userId) {
        if (baseResultLiveData == null) {
            baseResultLiveData = BaseResultLiveData.newIntance();
        }
        Call<List<Repo>> call = service.listRepos(userId);
        call.enqueue(new NetCallback<List<Repo>>() {
            @Override
            public void onResponse(BaseResult<List<Repo>> baseResult) {
                Log.d(TAG, "回调一次" + Thread.currentThread().getName());
                baseResultLiveData.setValue(baseResult);
            }
        });
        return baseResultLiveData;
    }


}
