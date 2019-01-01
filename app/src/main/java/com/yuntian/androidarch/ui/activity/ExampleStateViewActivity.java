package com.yuntian.androidarch.ui.activity;

import android.os.Bundle;
import android.widget.TextView;
import com.yuntian.androidarch.R;
import java.util.Random;
import androidx.annotation.Nullable;

/**
 * @author   chulingyan
 * @time     2018/12/29 18:36
 * @describe 列表例子
 */

public  class ExampleStateViewActivity extends BaseStateViewActivity {

    private TextView tvShow;

    @Override
    protected int getContenLayoutId() {
        return R.layout.layout_content_test;
    }


    @Override
    protected void initView() {
        super.initView();
        tvShow=findViewById(R.id.tv_show);
        tvShow.setText("我是测试的内容布局");
    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void refresh() {
        tvShow.postDelayed(()->{
            refreshLayout.finishRefresh();
            Random random=new Random();
            switchStatusView(random.nextInt(6));
        },1000);
    }
}
