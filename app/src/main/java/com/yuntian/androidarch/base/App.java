package com.yuntian.androidarch.base;


import com.blankj.utilcode.util.LogUtils;
import com.yuntian.baselibs.base.BaseApp;


/**
 * @author chulingyan
 * @time 2018/12/5 21:59
 * @describe 程序入口
 */
public class App extends BaseApp {


    @Override
    protected void initMain() {
        LogUtils.d("App启动了");
    }



}
