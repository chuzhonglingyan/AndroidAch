package com.yuntian.androidarch.ui.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.blankj.utilcode.util.ScreenUtils;
import com.yuntian.androidarch.R;
import com.yuntian.basecomponent.dialog.BaseDialogFragment;
import androidx.annotation.Nullable;

/**
 * @author chulingyan
 * @time 2018/12/30 22:44
 * @describe {https://blog.csdn.net/zxwd2015/article/details/76283609}
 */
public class TestDialog2 extends BaseDialogFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test2;
    }

    @Override
    protected void initView() {
        setCancelable(true);
    }

    @Override
    protected void initListener() {

    }



    @Override
    protected void upDateWindow(Window window) {
        super.upDateWindow(window);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;

        wlp.dimAmount=0;//完全透明背景
//        wlp.width = Utils.dp2px(context,100); // 宽度
//        wlp.height = Utils.dp2px(context,100); // 高度
        //自适应
//        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        wlp.width = (int) (ScreenUtils.getScreenWidth()*0.5);
        wlp.height = (int) (ScreenUtils.getScreenHeight()*0.5);
        window.setAttributes(wlp);
    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {

    }
}
