package com.yuntian.androidarch.ui.activity;

import android.os.Bundle;
import android.util.SparseArray;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.yuntian.androidarch.R;
import com.yuntian.basecomponent.adapter.BaseFragmentPagerAdapter;
import com.yuntian.androidarch.ui.fragment.ModuleListFragment;
import com.yuntian.basecomponent.tablayout.SlidingTabLayout;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import static com.yuntian.androidarch.router.RouteUrl.PATH_MODULEVIEWPAGER;
import static com.yuntian.androidarch.router.RouteUrl.PATH_PERMISSIONPAGE;

@Route(path = PATH_MODULEVIEWPAGER)
public class ModuleViewPagerActivity extends BaseActivity {


    public static final String POSITION = "position";
    private ViewPager viewPager;
    private SlidingTabLayout tabLayout;
    private String[] titles;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_viewpager_module;
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab_layout);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {
        initTab(isInit);

    }

    private void initTab(boolean isInit) {
        titles = new String[]{"ModuleA", "ModuleB", "ModuleC", "ModuleD"};
        List<Fragment> fragmentList = new ArrayList<>();
        SparseArray<Fragment> fragmentSparseArray = null;
        if (!isInit) {
            fragmentSparseArray = findModuleListFragments();
        }
        for (int i = 0; i < titles.length; i++) {
            Fragment fragment;
            if (ObjectUtils.isNotEmpty(fragmentSparseArray) && ObjectUtils.isNotEmpty(fragmentSparseArray.get(i))) {
                fragment = fragmentSparseArray.get(i);
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt(POSITION, i);
                fragment = fragmentHelp.newFragmentIntance(ModuleListFragment.class, bundle);
            }
            fragmentList.add(fragment);
        }

        BaseFragmentPagerAdapter fragmentPagerAdapter = new BaseFragmentPagerAdapter(this, fragmentList);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setViewPager(viewPager, titles);
    }

    public SparseArray<Fragment> findModuleListFragments() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        SparseArray<Fragment> fragmentModules = new SparseArray<>();
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment fragment = fragmentList.get(i);
            if (fragment.getClass() == ModuleListFragment.class) {
                if (ObjectUtils.isNotEmpty(fragment.getArguments())) {
                    int position = fragment.getArguments().getInt(POSITION);
                    fragmentModules.put(position, fragment);
                    LogUtils.d(position);
                } else {
                    fragmentModules.put(i, fragment);
                }
            }
        }
        return fragmentModules;
    }

}
