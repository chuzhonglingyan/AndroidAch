package com.yuntian.androidarch.ui.adapter;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @author chulingyan
 * @time 2018/12/20 21:36
 * @describe
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> data=new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentActivity fragmentActivity,List<Fragment> data) {
        super(fragmentActivity.getSupportFragmentManager());
        if (ObjectUtils.isEmpty(data)){
            return;
        }
        this.data.addAll(data);
    }


    public BaseFragmentPagerAdapter(FragmentActivity fragmentActivity) {
        this(fragmentActivity,null);
    }


    public List<Fragment> getData() {
        return data;
    }

    public void setData(List<Fragment> data) {
        if (ObjectUtils.isEmpty(data)){
            return;
        }
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }



}
