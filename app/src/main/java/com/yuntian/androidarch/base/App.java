package com.yuntian.androidarch.base;


import com.blankj.utilcode.util.LogUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.yuntian.baselibs.BuildConfig;
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
        // appid 8e6ec15855  appkey ca286fa7-d601-46f7-9891-9e6e89ddf578
        CrashReport.initCrashReport(getApplicationContext(), "8e6ec15855", BuildConfig.DEBUG);
    }



}
