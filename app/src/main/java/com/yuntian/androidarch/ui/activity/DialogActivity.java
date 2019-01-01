package com.yuntian.androidarch.ui.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.yuntian.androidarch.R;
import com.yuntian.androidarch.ui.dialog.PopDialog;
import com.yuntian.androidarch.ui.dialog.TestDialog1;
import com.yuntian.basecomponent.dialog.DialogFragmentHelper;
import com.yuntian.basecomponent.dialog.IDialogResultListener;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Created by Haoz on 2017/4/6 0006.
 */

public class DialogActivity extends BaseActivity {

    private DialogFragment mDialogFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        PopDialog dialog= fragmentHelp.newFragmentIntance(PopDialog.class);
        findViewById(R.id.tv_click).setOnClickListener(v->{
            dialog.showViewBottom(this,v);
            LogUtils.d(getdynamicStatusBarHeight());
//            dialog.show(this);
        });
    }

    private int getdynamicStatusBarHeight() {
        View view = this.getWindow().getDecorView();
        // 获取状态栏高度
        Rect frame = new Rect();
        // 测量屏幕宽和高
        view.getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }



    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_dialog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.showConfirmDialog:
                showConfirmDialog();
                break;

            case R.id.showDateDialog:
                showDateDialog();
                break;

            case R.id.showInsertDialog:
                showInsertDialog();
                break;

            case R.id.showIntervalInsertDialog:
                showIntervalInsertDialog();
                break;

            case R.id.showListDialog:
                showListDialog();
                break;

            case R.id.showPasswordInsertDialog:
                showPasswordInsertDialog();
                break;

            case R.id.showProgress:
                mDialogFragment = DialogFragmentHelper.showProgressbar(getSupportFragmentManager(), "正在加载中");
                break;

            case R.id.showTimeDialog:
                showTimeDialog();
                break;

            case R.id.showTips:
                DialogFragmentHelper.showTips(getSupportFragmentManager(), "你进入了无网的异次元中");
                break;

            default:break;
        }
        return true;
    }

    /**
     * 选择时间的弹出窗
     */
    private void showTimeDialog() {
        String titleTime = "请选择时间";
        Calendar calendarTime = Calendar.getInstance();
        DialogFragmentHelper.showTimeDialog(getSupportFragmentManager(), titleTime, calendarTime, new IDialogResultListener<Calendar>() {
            @Override
            public void onDataResult(Calendar result) {
               showToast(String.valueOf(result.getTime().getDate()));
            }
        }, true);
    }

    /**
     * 输入密码的弹出窗
     */
    private void showPasswordInsertDialog() {
        String titlePassword = "请输入密码";
        DialogFragmentHelper.showPasswordInsertDialog(getSupportFragmentManager(), titlePassword, new IDialogResultListener<String>() {
            @Override
            public void onDataResult(String result) {
               showToast("密码为：" + result);
            }
        }, true);
    }

    /**
     * 显示列表的弹出窗
     */
    private void showListDialog() {
        String titleList = "选择哪种方向？";
        final String [] languanges = new String[]{"Android", "iOS", "web 前端", "Web 后端", "老子不打码了"};

        DialogFragmentHelper.showListDialog(getSupportFragmentManager(), titleList, languanges, new IDialogResultListener<Integer>() {
            @Override
            public void onDataResult(Integer result) {
                showToast(languanges[result]);
            }
        }, true);
    }

    /**
     * 两个输入框的弹出窗
     */
    private void showIntervalInsertDialog() {
        String title = "请输入想输入的内容";
        DialogFragmentHelper.showIntervalInsertDialog(getSupportFragmentManager(), title, new IDialogResultListener<String[]>() {
            @Override
            public void onDataResult(String[] result) {
               showToast(result[0] + result[1]);
            }
        }, true);
    }

    private void showInsertDialog() {
        String titleInsert  = "请输入想输入的内容";
        DialogFragmentHelper.showInsertDialog(getSupportFragmentManager(), titleInsert, new IDialogResultListener<String>() {
            @Override
            public void onDataResult(String result) {
                showToast(result);
            }
        }, true);
    }

    /**
     * 选择日期的弹出窗
     */
    private void showDateDialog() {
        String titleDate = "请选择日期";
        Calendar calendar = Calendar.getInstance();
        mDialogFragment = DialogFragmentHelper.showDateDialog(getSupportFragmentManager(), titleDate, calendar, new IDialogResultListener<Calendar>() {
            @Override
            public void onDataResult(Calendar result) {
                showToast(String.valueOf(result.getTime().getDate()));
            }
        }, true);
    }

    /**
     * 确认和取消的弹出窗
     */
    private void showConfirmDialog() {
        DialogFragmentHelper.showCustomerConfirmDialog(getSupportFragmentManager(), "是否选择 Android 哈哈核二二我问问我问问二？", new IDialogResultListener<Integer>() {
            @Override
            public void onDataResult(Integer result) {
                showToast("You Click Ok");
            }
        }, true, () -> showToast("You Click Cancel"));
    }


    /**
     * 对 Toast 进行封藏，减少代码量
     *
     * @param message 想要显示的信息
     */
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}