package com.yuntian.androidarch.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yuntian.androidarch.R;
import com.yuntian.androidarch.di.component.DaggerUserComponent;
import com.yuntian.androidarch.di.module.UserModule;
import com.yuntian.androidarch.viewmodel.CommunicateViewModel;
import com.yuntian.androidarch.viewmodel.GitHubViewModel;
import com.yuntian.baselibs.base.BaseFragment;
import com.yuntian.baselibs.di.component.AppComponent;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class UserProfileFragmentB extends BaseFragment {

    public static final String TAG = "UserProfileFragmentB";

    private static final String UID_KEY = "uid";

    private String userId = "";

    @Inject
    GitHubViewModel gitHubViewModel;


    private CommunicateViewModel mCommunicateViewModel;


    private TextView tvData;

    public static UserProfileFragmentB newIntance(String userId) {
        UserProfileFragmentB fragment = new UserProfileFragmentB();
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
        if (getArguments() != null) {
            userId = getArguments().getString(UID_KEY);
            gitHubViewModel.init(userId);
        }

        mCommunicateViewModel = ViewModelProviders.of( activity).get(CommunicateViewModel.class);

        mCommunicateViewModel.getName().observe(this, name ->
                Log.d(TAG, "来自A的问候" + name));

        tvData.setOnClickListener(v -> {
            gitHubViewModel.getRepoList(userId).observe2(this, repoList -> {
                Log.d(TAG, new Gson().toJson(repoList));
            }, ((msg, code) -> {

                Log.d(TAG, "code:" + code + ",msg:" + msg);
            }));

        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_profile2;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + gitHubViewModel.toString());
    }

}
