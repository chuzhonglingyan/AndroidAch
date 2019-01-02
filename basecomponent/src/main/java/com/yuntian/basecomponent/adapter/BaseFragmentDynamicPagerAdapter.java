package com.yuntian.basecomponent.adapter;

import android.view.ViewGroup;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

/**
 * @author chulingyan
 * @time 2018/12/20 21:36
 * @describe
 * @see {https://blog.csdn.net/chen_xi_hao/article/details/82152503}
 */
public class BaseFragmentDynamicPagerAdapter extends BaseFragmentStatePagerAdapter {


    public BaseFragmentDynamicPagerAdapter(FragmentActivity fragmentActivity) {
        this(fragmentActivity, null);
    }

    public BaseFragmentDynamicPagerAdapter(FragmentActivity fragmentActivity, List<Fragment> data) {
        super(fragmentActivity, data);
    }


    public void addItem(Fragment fragment) {
        if (fragment != null) {
            data.add(fragment);
            notifyDataSetChanged();
        }
    }

    public void removeItem(Fragment fragment) {
        if (fragment != null) {
            data.remove(fragment);
            notifyDataSetChanged();
        }
    }

    public void removeItem(int  pos) {
        if (pos>=0&&pos<data.size()) {
            data.remove(pos);
            notifyDataSetChanged();
        }
    }


    public void addItems(List<Fragment> fragments) {
        if (fragments != null) {
            data.addAll(fragments);
            notifyDataSetChanged();
        }
    }

    public void removeItems(List<Fragment> fragments) {
        if (fragments != null) {
            data.removeAll(fragments);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (!((Fragment) object).isAdded() || !data.contains(object)) {
            return PagerAdapter.POSITION_NONE;
        }
        return data.indexOf(object);
    }


    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment instantiateItem = ((Fragment) super.instantiateItem(container, position));
        Fragment item = data.get(position);
        if (instantiateItem == item) {
            return instantiateItem;
        } else {
            //如果集合中对应下标的fragment和fragmentManager中的对应下标的fragment对象不一致，那么就是新添加的，所以自己add进入；
            // 这里为什么不直接调用super方法呢，因为fragment的mIndex搞的鬼，以后有机会再补一补。
            fragmentManager.beginTransaction().add(container.getId(), item).commitNowAllowingStateLoss();
            return item;
        }
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) object; //如果getItemPosition中的值为PagerAdapter.POSITION_NONE，就执行该方法。
        if (data.contains(fragment)) {
            super.destroyItem(container, position, fragment);
            return;
        } //自己执行移除。因为mFragments在删除的时候就把某个fragment对象移除了，所以一般都得自己移除在fragmentManager中的该对象。
        fragmentManager.beginTransaction().remove(fragment).commitNowAllowingStateLoss();
    }


}
