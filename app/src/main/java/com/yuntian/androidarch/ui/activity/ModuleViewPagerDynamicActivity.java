package com.yuntian.androidarch.ui.activity;

import android.os.Bundle;
import android.util.SparseArray;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.yuntian.androidarch.R;
import com.yuntian.androidarch.ui.adapter.BaseFragmentDynamicPagerAdapter;
import com.yuntian.androidarch.ui.fragment.ModuleListFragment;
import com.yuntian.basecomponent.tablayout.SlidingTabLayout;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ModuleViewPagerDynamicActivity extends BaseActivity {


    public static final String POSITION = "position";
    private ViewPager viewPager;
    private SlidingTabLayout tabLayout;
    private TextView tvAdd;
    private TextView tvRemove;
    private BaseFragmentDynamicPagerAdapter fragmentPagerAdapter;

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
        tvAdd = findViewById(R.id.add);
        tvRemove = findViewById(R.id.remove);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("titles", (ArrayList<String>) tabLayout.getmTitles());
    }


    @Override
    protected void initListener() {
        tvAdd.setOnClickListener(v -> {
            int addPos = fragmentPagerAdapter.getCount();
            Bundle bundle = new Bundle();
            bundle.putInt(POSITION, addPos);
            Fragment fragment = fragmentHelp.newFragmentIntance(ModuleListFragment.class, bundle);
            fragmentPagerAdapter.addItem(fragment);
            tabLayout.addNewTab("Module" + (addPos + 1));
        });
        tvRemove.setOnClickListener(v -> {
            try {
                int removePos = fragmentPagerAdapter.getCount() - 1;
                fragmentPagerAdapter.removeItem(removePos);
                tabLayout.removeNewTab(removePos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {
        initTab(isInit, savedInstanceState);

    }

    private void initTab(boolean isInit, @Nullable Bundle savedInstanceState) {
        List<String> titles = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();
        if (isInit) {
            titles.add("Module1");
            titles.add("Module2");
            titles.add("Module3");
            titles.add("Module4");
        } else {
            if (savedInstanceState != null) {
                titles = savedInstanceState.getStringArrayList("titles");
            }
            if (titles == null) {
                titles = new ArrayList<>();
            }
        }

        for (int i = 0; i < titles.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt(POSITION, i);
            Fragment fragment = fragmentHelp.newFragmentIntance(ModuleListFragment.class, bundle);
            fragmentList.add(fragment);
        }
        fragmentPagerAdapter = new BaseFragmentDynamicPagerAdapter(this, fragmentList);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setViewPager(viewPager, titles);
    }


}
