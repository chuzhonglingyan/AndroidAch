package com.yuntian.androidarch.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.yuntian.androidarch.R;
import com.yuntian.aoplib.annotation.CheckPermission;
import com.yuntian.aoplib.annotation.PermissionGrantedCallBack;
import com.yuntian.aoplib.annotation.PermissionDeniedCallBack;
import com.yuntian.aoplib.aspect.permission.PermissionResult;
import com.yuntian.aoplib.aspect.permission.PermissionsUtil;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;
import com.yuntian.baselibs.util.GsonUtil;

import java.io.File;
import java.io.FileOutputStream;

import static com.yuntian.androidarch.router.RouteUrl.PATH_PERMISSIONPAGE;

@Route(path = PATH_PERMISSIONPAGE)
public class PermissionActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        findViewById(R.id.tv1).setOnClickListener(v -> {
            LogUtils.d("申请sd");
            wiriteToSd();
        });
        findViewById(R.id.tv2).setOnClickListener(v -> {
            LogUtils.d("申请call");
            callPhone();
        });
        findViewById(R.id.tv3).setOnClickListener(v -> {
            LogUtils.d("申请Sd和call");
            wiriteToSdAndCall();
        });
    }

    protected void initListener() {
    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {

    }

    @CheckPermission(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE})
    public void wiriteToSdAndCall() {
        LogUtils.d("可以使用了WRITE_EXTERNAL_STORAGE And Call");
    }


    @CheckPermission(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void wiriteToSd() {
        LogUtils.d("可以使用了WRITE_EXTERNAL_STORAGE");
        new Thread(this::writeFileToSD).start();
    }

    @CheckPermission(value = {Manifest.permission.CALL_PHONE})
    public void callPhone() {
        LogUtils.d("可以使用了CALL_PHONE");
    }

    @PermissionGrantedCallBack(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void rantedSdCallBack(PermissionResult permissionResult) {
        LogUtils.d(GsonUtil.toJson(permissionResult));
    }

    @PermissionDeniedCallBack(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void rejectSdCallBack(PermissionResult permissionResult) {
        LogUtils.d(GsonUtil.toJson(permissionResult));
        PermissionsUtil.showRationalDialog(this, permissionResult.getList());
    }

    @PermissionGrantedCallBack
    public void rantedAllCallBack(PermissionResult permissionResult) {
        LogUtils.d(GsonUtil.toJson(permissionResult));
    }

    @PermissionDeniedCallBack
    public void rejectAllCallBack(PermissionResult permissionResult) {
        LogUtils.d(GsonUtil.toJson(permissionResult));
        PermissionsUtil.showRationalDialog(this, permissionResult.getList());
    }


    private void writeFileToSD() {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        try {
            String pathName = "/sdcard/test/";
            String fileName = "file.txt";
            File path = new File(pathName);
            File file = new File(pathName + fileName);
            if (!path.exists()) {
                path.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            String s = "this is a test hh string writing to file.";
            byte[] buf = s.getBytes();
            stream.write(buf);
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
