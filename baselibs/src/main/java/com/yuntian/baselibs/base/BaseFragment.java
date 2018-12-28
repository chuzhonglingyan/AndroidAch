package com.yuntian.baselibs.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.yuntian.baselibs.di.component.AppComponent;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

public abstract class BaseFragment extends Fragment implements LifecycleOwner,IView {

    protected AppCompatActivity activity;
    protected Context context;
    protected View parentView;

    protected boolean isViewInit;
    protected boolean hasInitData;
    protected boolean isInitCreate;//是否初次创建

    protected AppComponent getApplicationComponent(AppCompatActivity context) {
        return ((BaseApp) context.getApplication()).component();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (AppCompatActivity) context;
        this.context = context;
        inject(getApplicationComponent(activity));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isViewInit=true;

        parentView=inflater.inflate(getLayoutId(), container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isInitCreate=savedInstanceState==null;
        initData(savedInstanceState==null,savedInstanceState);
        if (getUserVisibleHint()&&!hasInitData){
            loadInitData();
        }
    }

    protected abstract int getLayoutId();


    protected  <T extends View> T findViewById(@IdRes int id){
        return  parentView.findViewById(id);
    }


    /**
     * 此处可见
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.d(this.toString()+",isVisibleToUser："+isVisibleToUser);
        if (isVisibleToUser&&isViewInit&&!hasInitData){
            loadInitData();
            hasInitData=true;
        }
    }



    /**
     * 此处真正可见
     */
    protected abstract void loadInitData();


    public abstract void inject(AppComponent appComponent);


    protected abstract void  initView();

    protected abstract void initListener();

    protected abstract void initData(boolean isInit,@Nullable Bundle savedInstanceState);


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
