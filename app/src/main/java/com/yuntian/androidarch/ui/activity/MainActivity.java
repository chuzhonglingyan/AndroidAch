package com.yuntian.androidarch.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.yuntian.androidarch.R;
import com.yuntian.androidarch.di.component.DaggerUserComponent;
import com.yuntian.androidarch.di.module.UserModule;
import com.yuntian.androidarch.ui.fragment.UserProfileFragmentA;
import com.yuntian.androidarch.ui.fragment.UserProfileFragmentB;
import com.yuntian.androidarch.ui.view.flexboxLayout.FlexboxLayoutActivity;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity {

    private UserProfileFragmentA userProfileFragmentA;
    private UserProfileFragmentB userProfileFragmentB;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
        findViewById(R.id.tv_delete).setOnClickListener(v->{

            LogUtils.d("替换userProfileFragmentB");
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fl_b,UserProfileFragmentA.newIntance("octocat"));
            fragmentTransaction.commit();
        });
        findViewById(R.id.tv_goto_db).setOnClickListener(v->{
            startActivity(new Intent(context, PermissionActivity.class));
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isInit,@Nullable Bundle savedInstanceState) {
        initFrament();
    }


    public void  initFrament(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        userProfileFragmentA = (UserProfileFragmentA) getSupportFragmentManager().findFragmentByTag(UserProfileFragmentA.TAG);
        if (userProfileFragmentA ==null){
            userProfileFragmentA = UserProfileFragmentA.newIntance("octocat");
        }
        userProfileFragmentB = (UserProfileFragmentB) getSupportFragmentManager().findFragmentByTag(UserProfileFragmentA.TAG);
        if (userProfileFragmentB ==null){
            userProfileFragmentB = UserProfileFragmentB.newIntance("octocat");
        }

        fragmentTransaction.replace(R.id.fl_a, userProfileFragmentA);
        fragmentTransaction.replace(R.id.fl_b, userProfileFragmentB);

        fragmentTransaction.commit();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    /**
    * android8.0调用在onStop之后，之前在onStop之前，onPause前后不确定 默认保存有id的焦点视图
     * @param outState
     */
   @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState); //保存少量数据
        //禁止在该方法之后执行FragmentTransaction.commit()提交事务方法
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
