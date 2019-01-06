package com.yuntian.androidarch.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.yuntian.androidarch.R;
import com.yuntian.androidarch.ui.fragment.ModuleAFragment;
import com.yuntian.androidarch.ui.fragment.ModuleBFragment;
import com.yuntian.androidarch.ui.fragment.ModuleCFragment;
import com.yuntian.androidarch.ui.fragment.ModuleDFragment;
import com.yuntian.basecomponent.tablayout.CommonTabLayout;
import com.yuntian.basecomponent.tablayout.listener.CustomTab;
import com.yuntian.basecomponent.tablayout.listener.CustomTabEntity;
import com.yuntian.basecomponent.tablayout.listener.OnTabSelectListener;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.yuntian.androidarch.router.RouteUrl.PATH_MODULETAB;

@Route(path = PATH_MODULETAB)
public class ModuleTabActivity extends BaseActivity {


    private CommonTabLayout tabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module;
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        tabLayout=findViewById(R.id.tab_layout);
    }

    @Override
    protected void initListener() {
    }


    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {
        Stack<Class<? extends Fragment>> fragmentClass = new Stack<>();
        fragmentClass.push(ModuleAFragment.class);
        fragmentClass.push(ModuleBFragment.class);
        fragmentClass.push(ModuleCFragment.class);
        fragmentClass.push(ModuleDFragment.class);

        if (isInit) {
            while (!fragmentClass.empty()) {
                Class<? extends Fragment> clas = fragmentClass.pop();
                fragmentHelp.add(R.id.fl_container, fragmentHelp.newFragmentIntance(clas));
            }
        } else {
            while (!fragmentClass.empty()) {
                Class<? extends Fragment> clas = fragmentClass.pop();
                fragmentHelp.pushFragment(fragmentHelp.findFragmentByTag(clas));
            }
        }
        initTab();
    }


    private void  initTab(){
        List<CustomTabEntity> tabEntitys=new ArrayList<>();
        tabEntitys.add(new CustomTab("页面A"));
        tabEntitys.add(new CustomTab("页面B"));
        tabEntitys.add(new CustomTab("页面C"));
        tabEntitys.add(new CustomTab("页面D"));
        tabLayout.setTabData(tabEntitys);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                //切换fragment
                fragmentHelp.showAndHide(fragmentHelp.getFragmentAtPos(position));
            }
            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isInitCreate){
            fragmentHelp.showAndHide(fragmentHelp.getStatckTop());
        }
        LogUtils.d(fragmentHelp.getFragmentStack().size());
    }


}
