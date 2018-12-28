package com.yuntian.baselibs.base;

import android.content.Context;
import android.os.Bundle;

import com.yuntian.baselibs.di.component.AppComponent;
import com.yuntian.baselibs.util.FragmentHelp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

public abstract class BaseActivity extends AppCompatActivity implements LifecycleOwner, IView {

    protected Context context;
    protected FragmentHelp fragmentHelp;
    protected boolean isInitCreate;//是否初次创建


    protected AppComponent getApplicationComponent(AppCompatActivity context) {
        return ((BaseApp) context.getApplication()).component();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init(savedInstanceState);
        inject(getApplicationComponent(this));
        initView();
        initListener();
        initData(savedInstanceState==null,savedInstanceState);

    }

    private void  init(@Nullable Bundle savedInstanceState){
        this.context = this;
        fragmentHelp=FragmentHelp.newIntance(this);
        isInitCreate=savedInstanceState==null;
    }


    protected abstract int getLayoutId();


    public abstract void inject(AppComponent appComponent);


    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData(boolean isInit,@Nullable Bundle savedInstanceState);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentHelp.clean();
    }
}
