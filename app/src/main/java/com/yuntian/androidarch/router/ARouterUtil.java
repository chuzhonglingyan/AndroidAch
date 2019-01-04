package com.yuntian.androidarch.router;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author chulingyan
 * @time 2019/01/03 23:43
 * @describe
 */
public class ARouterUtil {

    public static void  navigation(String path){
        ARouter.getInstance().build(path).navigation();
    }


}
