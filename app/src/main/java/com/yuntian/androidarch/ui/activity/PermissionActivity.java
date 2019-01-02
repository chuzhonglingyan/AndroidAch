package com.yuntian.androidarch.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.yuntian.androidarch.R;
import com.yuntian.androidarch.annotation.CheckPermission;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

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
//        findViewById(R.id.tv).setOnClickListener(v -> {
//            LogUtils.d("申请");
//            PermissionsUtil.checkPermission(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE}, new PermissionsUtil.IPermissions() {
//                @Override
//                public void onPermissionGranted(List<String> result) {
//                    LogUtils.d(GsonUtil.toJson(result));
//                }
//
//                @Override
//                public void onPermissonReject(List<String> result) {
//                    LogUtils.d(GsonUtil.toJson(result));
//                }
//
//                @Override
//                public void shouldShowRational(String permisson) {
//                    ToastUtils.showShort("二次询问是否开启" + permisson);
//                }
//            });
//        });
        findViewById(R.id.tv).setOnClickListener(v -> {
            LogUtils.d("申请");
            wiriteToSd();
        });
    }

    protected void initListener() {
    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {


    }

    @CheckPermission(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void wiriteToSd() {
        LogUtils.d("可以使用了");
        writeFileToSD();
    }

    private void writeFileToSD() {
        String sdStatus = Environment.getExternalStorageState();
        if(!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        try {
            String pathName="/sdcard/test/";
            String fileName="file.txt";
            File path = new File(pathName);
            File file = new File(pathName + fileName);
            if( !path.exists()) {
                path.mkdir();
            }
            if( !file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            String s = "this is a test string writing to file.";
            byte[] buf = s.getBytes();
            stream.write(buf);
            stream.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

//
//    public void  checkPermission (){
//        //使用兼容库就无需判断系统版本
//        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
//            LogUtils.d("已经有sd卡权限");
//        }else{
//            //没有权限，向用户请求权限
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    PermissionsUtil.CODE_FOR_WRITE_PERMISSION);
//        }
//    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        //通过requestCode来识别是否同一个请求
//        if (requestCode == PermissionsUtil.CODE_FOR_WRITE_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //用户同意，执行操作
//                ToastUtils.showShort("申请成功");
//            } else {
//                //用户不同意，向用户展示该权限作用
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    new AlertDialog.Builder(this)
//                            .setMessage("sd卡权限申请")
//                            .setPositiveButton("ok", (dialog, which) -> {
//
//                            })
//                            .setNegativeButton("Cancel", null)
//                            .create()
//                            .show();
//                }else {
//                    gotoSetting();
//                    ToastUtils.showShort("请去设置界面打开sd卡权限");
//                }
//            }
//        }
//
//    }
//
//    // 跳转到设置界面；
//    private void gotoSetting() {
//        try {
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            intent.setData(Uri.fromParts("package", getPackageName(), null));
//            startActivityForResult(intent, PermissionsUtil.CODE_FOR_WRITE_PERMISSION);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PermissionsUtil.CODE_FOR_WRITE_PERMISSION) {
//            //使用兼容库就无需判断系统版本
//            int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
//                ToastUtils.showShort("申请成功");
//            }else{
//                ToastUtils.showShort("申请失败");
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//
//    }


}
