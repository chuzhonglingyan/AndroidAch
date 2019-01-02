package com.yuntian.androidarch.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chulingyan
 * @time 2019/01/01 21:54
 * @describe
 */
public class PermissionFragmentTemp extends Fragment {

    private Context context;
    private Activity activity;
    private String[] permissions;
    private PermissionsUtil.IPermission iPermission;
    private PermissionsUtil.IPermissions iPermissions;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity) context;
    }


    public void setiPermission(PermissionsUtil.IPermission iPermission, String[] permissions) {
        this.iPermission = iPermission;
        this.permissions = permissions;
    }

    public void setiPermission(PermissionsUtil.IPermissions iPermissions, String[] permissions) {
        this.iPermissions = iPermissions;
        this.permissions = permissions;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    public void requestPermissions() {
        //没有权限，向用户请求权限
        if (permissions != null && permissions.length > 0) {
            requestPermissions(permissions, PermissionsUtil.getRequsteCode(permissions[0]));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (permissions.length > 0 && requestCode == PermissionsUtil.getRequsteCode(permissions[0])) {
            if (permissions.length == 1) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意，执行操作
                    LogUtils.d("申请成功");
                    if (iPermission != null) {
                        iPermission.onPermissionGranted(permissions[0]);
                    }
                } else {
                    //用户不同意，向用户展示该权限作用
                    if (shouldShowRequestPermissionRationale(permissions[0])) {
                        new AlertDialog.Builder(activity)
                                .setMessage(String.format("%s卡权限申请", permissions[0]))
                                .setPositiveButton("ok", (dialog, which) -> {
                                    gotoSetting();
                                })
                                .setNegativeButton("Cancel", null)
                                .create()
                                .show();
                        if (iPermission != null) {
                            iPermission.shouldShowRational(permissions[0]);
                        }
                    } else {
                        if (iPermission != null) {
                            iPermission.onPermissonReject(permissions[0]);
                        }
                        LogUtils.d("放弃申请 ");
                        //                    gotoSetting();
                        ToastUtils.showShort("请去设置界面打开sd卡权限");
                    }
                }
            } else {
                List<String> resultGRANTED = new ArrayList<>();
                List<String> resultDENIED = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    LogUtils.d("grantResults[" + i + "]:" + grantResults[i]);
                    LogUtils.d("permissions[" + i + "]:" + permissions[i]);
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        resultGRANTED.add(permissions[i]);
                    } else {
                        resultDENIED.add(permissions[i]);
                    }
                }
                if (iPermissions != null) {
                    iPermissions.onPermissionGranted(resultGRANTED);
                    iPermissions.onPermissonReject(resultDENIED);
                }
            }
        }
    }


    // 跳转到设置界面；
    private void gotoSetting() {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
            startActivityForResult(intent, PermissionsUtil.getRequsteCode(permissions[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PermissionsUtil.getRequsteCode(permissions[0])) {
            if (permissions.length == 1) {
                int hasWriteStoragePermission = ContextCompat.checkSelfPermission(context, permissions[0]);
                if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
                    LogUtils.d("申请成功");
                    if (iPermission != null) {
                        iPermission.onPermissionGranted(permissions[0]);
                    }
                } else {
                    if (iPermission != null) {
                        iPermission.onPermissonReject(permissions[0]);
                    }
                    LogUtils.d("申请失败");
                }
            } else {
                List<String> resultGRANTED = new ArrayList<>();
                List<String> resultDENIED = new ArrayList<>();
                for (int i = 0; i < permissions.length; i++) {
                    LogUtils.d("permissions[" + i + "]:" + permissions[i]);
                    int hasWriteStoragePermission = ContextCompat.checkSelfPermission(context, permissions[i]);
                    if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
                        resultGRANTED.add(permissions[i]);
                    } else {
                        resultDENIED.add(permissions[i]);
                    }
                }
                if (iPermissions != null) {
                    iPermissions.onPermissionGranted(resultGRANTED);
                    iPermissions.onPermissonReject(resultDENIED);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
