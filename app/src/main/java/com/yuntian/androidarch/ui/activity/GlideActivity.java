package com.yuntian.androidarch.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import com.yuntian.androidarch.R;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;
import com.yuntian.baselibs.imageloader.GlideImageView;

public class GlideActivity extends BaseActivity {

   private     GlideImageView glideImageView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_glide;
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        glideImageView=findViewById(R.id.iv1);
        glideImageView.load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546341197127&di=15ee96653e98517fce9fcc6f99418f68&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Fback_pic%2F00%2F03%2F80%2F81561fbfead25e5.jpg",
                R.mipmap.image_loading);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {


    }

}
