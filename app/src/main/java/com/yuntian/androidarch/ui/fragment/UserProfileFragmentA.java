package com.yuntian.androidarch.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.yuntian.androidarch.R;
import com.yuntian.androidarch.di.component.DaggerUserComponent;
import com.yuntian.androidarch.di.module.UserModule;
import com.yuntian.androidarch.viewmodel.CommunicateViewModel;
import com.yuntian.androidarch.viewmodel.GankViewModel;
import com.yuntian.androidarch.viewmodel.GitHubViewModel;
import com.yuntian.baselibs.base.BaseFragment;
import com.yuntian.baselibs.di.component.AppComponent;
import com.yuntian.baselibs.util.GsonUtil;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class UserProfileFragmentA extends BaseFragment {

    public static final String TAG = "UserProfileFragmentA";

    private static final String UID_KEY = "uid";
    private String userId = "";

    private TextView tvData;

    private CommunicateViewModel mCommunicateViewModel;
    @Inject
    GankViewModel gankViewModel;


    @Inject
    GitHubViewModel gitHubViewModel;


    public static UserProfileFragmentA newIntance(String userId) {
        UserProfileFragmentA fragment = new UserProfileFragmentA();
        Bundle bundle = new Bundle();
        bundle.putString(UID_KEY, userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void inject(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .userModule(new UserModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    protected void initView() {
        tvData = findViewById(R.id.tv_getData);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {
        tvData.setOnClickListener(v -> {
            mCommunicateViewModel.setName("Jane");
            //        一旦用户数据更新，onChanged回调将被调用然后UI会被刷新。
            gitHubViewModel.getRepoList(userId).observe2(this, repoList -> {
                Log.d(TAG, GsonUtil.toJson(repoList));
            }, ((msg, code) -> {
                Log.d(TAG, "code:" + code + ",msg:" + msg);
            }));


            gankViewModel.getGankInfoList("福利", 1).observe2(this, result -> {
                Log.d(TAG, GsonUtil.toJson(result));
            }, ((msg, code) -> {

                Log.d(TAG, "code:" + code + ",msg:" + msg);
            }));


        });
        mCommunicateViewModel = ViewModelProviders.of(activity).get(CommunicateViewModel.class);

        if (getArguments() != null) {
            userId = getArguments().getString(UID_KEY);
            gitHubViewModel.init(userId);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.user_profile;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + gitHubViewModel.toString());
    }
}
