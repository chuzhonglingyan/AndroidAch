package com.yuntian.baselibs.util;

import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.StringUtils;

import java.util.Stack;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author chulingyan
 * @time 2018/12/18 21:41
 * @describe
 */
public class FragmentHelp {

    private static final String ARGUMENTS = "mArguments";

    private Stack<Fragment> fragmentStack = new Stack<>();

    private FragmentManager fragmentManager;

    private FragmentHelp(FragmentActivity activity) {
        this.fragmentManager = activity.getSupportFragmentManager();
    }

    public static FragmentHelp newIntance(FragmentActivity activity) {
        return new FragmentHelp(activity);
    }


    public <T extends Fragment> T newFragmentIntance(Class<T> clas) {
        return newFragmentIntance(clas, null);
    }


    public <T extends Fragment> T newFragmentIntance(Class<T> clas, Bundle bundle) {
        if (clas == null) {
            throw new NullPointerException("Fragment Class cannot be null");
        }
        try {
            T t = clas.newInstance();
            t.setArguments(bundle);
            return t;
        } catch (InstantiationException e) {
            throw new RuntimeException("Cannot create an instance of " + clas, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot create an instance of " + clas, e);
        }
    }

    public void add(@IdRes int containerViewId, Fragment fragment) {
        add(containerViewId, fragment, null);
    }


    public void add(@IdRes int containerViewId, Fragment fragment, String tag) {
        if (fragment == null) {
            throw new NullPointerException("Fragment  cannot be null");
        }
        if (fragment.isAdded()) {
            LogUtils.d("该" + fragment.toString() + "已经被添加过了");
            return;
        }
        if (StringUtils.isTrimEmpty(tag)) {
            tag = fragment.getClass().getSimpleName();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerViewId, fragment, tag);
        transaction.commit();
        fragmentStack.push(fragment);
    }


    public void show(Fragment fragment) {
        if (!fragment.isAdded()) {
            LogUtils.d("该" + fragment.toString() + "还未被添加");
            return;
        }
        if (!fragment.isHidden()) {
            LogUtils.d("该" + fragment.toString() + "已经显示");
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    public void showAndHide(Fragment fragment) {
        if (!fragment.isAdded()) {
            LogUtils.d("该" + fragment.toString() + "还未被添加");
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragmentTemp : fragmentStack) {
            transaction.hide(fragmentTemp);
        }
        transaction.show(fragment);
        transaction.commit();
        LogUtils.d("showAndHide执行完毕");
    }


    public void hide(Fragment fragment) {
        if (!fragment.isAdded()) {
            LogUtils.d("该" + fragment.toString() + "还未被添加");
            return;
        }
        if (fragment.isHidden()) {
            LogUtils.d("该" + fragment.toString() + "已经隐藏");
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
    }


    public void hideAll() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragmentTemp : fragmentStack) {
            transaction.hide(fragmentTemp);
        }
        transaction.commit();
    }


    public void remove(Fragment fragment) {
        if (!fragment.isAdded()) {
            LogUtils.d("该" + fragment.toString() + "还未被添加");
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    public void removeAll() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragmentTemp : fragmentStack) {
            transaction.remove(fragmentTemp);
        }
        transaction.commit();
        clean();
    }


    public void replace(@IdRes int containerViewId, Fragment fragment) {
        replace(containerViewId, fragment, null);

    }


    public void replace(@IdRes int containerViewId, Fragment fragment, String tag) {
        if (fragment == null) {
            throw new NullPointerException("Fragment  cannot be null");
        }
        if (fragment.isAdded()) {
            LogUtils.d("该" + fragment.toString() + "已经被添加过了");
            return;
        }
        if (StringUtils.isTrimEmpty(tag)) {
            tag = fragment.getClass().getSimpleName();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerViewId, fragment, tag);
        transaction.commit();
        fragmentStack.push(fragment);
    }


    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public Stack<Fragment> getFragmentStack() {
        return fragmentStack;
    }


    public Fragment findFragmentByTag(String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

    public Fragment findFragmentByTag(Class<? extends Fragment> clas) {
        return fragmentManager.findFragmentByTag(clas.getSimpleName());
    }

    public void pushFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        if (fragmentStack.contains(fragment)) {
            LogUtils.d("该" + fragment.toString() + "已经被添加过了");
        }
        fragmentStack.push(fragment);
    }

    public Fragment getStatckTop() {
        return fragmentStack.peek();
    }


    public void clean() {
        fragmentStack.clear();
    }

}
