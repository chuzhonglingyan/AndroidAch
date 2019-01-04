package com.yuntian.androidarch.base;


import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.yuntian.androidarch.ui.adapter.ViewHolderUtil;
import com.yuntian.basecomponent.smartRefresh.SmartRefreshLayoutUtil;
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
        SmartRefreshLayoutUtil.init();
        ViewHolderUtil.initViewHolder();
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

    }



}
