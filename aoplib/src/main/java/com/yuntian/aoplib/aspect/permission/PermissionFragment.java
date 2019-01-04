package com.yuntian.aoplib.aspect.permission;

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
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chulingyan
 * @time 2019/01/01 21:54
 * @describe 权限fragment
 */
public class PermissionFragment extends Fragment {

    private Context context;
    private Activity activity;
    private String[] permissions;
    private PermissionsUtil.IPermissions iPermissions;

    private List<String> hasGanPermission = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity) context;
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
            List<String> resultGRANTED = new ArrayList<>();
            List<String> resultDENIED = new ArrayList<>();
            for (String permission : permissions) {
                int hasWriteStoragePermission = ContextCompat.checkSelfPermission(context, permission);
                if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
                    resultGRANTED.add(permission);
                } else {
                    resultDENIED.add(permission);
                }
            }
            hasGanPermission.removeAll(resultGRANTED);
            hasGanPermission.addAll(resultGRANTED);

            if (resultDENIED.size() > 0) {
                String[] strings = new String[resultDENIED.size()];
                permissions = resultDENIED.toArray(strings);
                requestPermissions(permissions, PermissionsUtil.getRequsteCode(permissions[0]));
            } else {
                if (iPermissions != null) {
                    if (hasGanPermission.size()>0){
                        iPermissions.onPermissionGranted(hasGanPermission);
                    }
                    iPermissions.onPermissonReject(resultDENIED);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (permissions.length > 0 && requestCode == PermissionsUtil.getRequsteCode(permissions[0])) {
            List<String> resultGRANTED = new ArrayList<>();
            List<String> resultDENIED = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    resultGRANTED.add(permissions[i]);
                } else {
                    resultDENIED.add(permissions[i]);
                }
            }
            if (iPermissions != null) {
                //并集
                hasGanPermission.removeAll(resultGRANTED);
                hasGanPermission.addAll(resultGRANTED);
                if (hasGanPermission.size()>0){
                    iPermissions.onPermissionGranted(hasGanPermission);
                }
                iPermissions.onPermissonReject(resultDENIED);
            }
            //用户不同意，向用户展示该权限作用
            if (resultDENIED.size() > 0 && shouldShowRequestPermissionRationale(resultDENIED.get(0))) {
                iPermissions.shouldShowRational(resultDENIED.get(0));
            }
        }
    }


    private void showRationalDialog(FragmentActivity activity, String[] permissions) {
        if (permissions != null && permissions.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < permissions.length; i++) {
                stringBuilder.append(permissions[i]);
                if (i < permissions.length - 1) {
                    stringBuilder.append(",");
                }
            }
            new AlertDialog.Builder(activity)
                    .setTitle("权限申请")
                    .setMessage(String.format("%s权限申请", stringBuilder.toString()))
                    .setPositiveButton("ok", (dialog, which) -> {
                        gotoSetting();
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
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
            List<String> resultGRANTED = new ArrayList<>();
            List<String> resultDENIED = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                int hasWriteStoragePermission = ContextCompat.checkSelfPermission(context, permissions[i]);
                if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
                    resultGRANTED.add(permissions[i]);
                } else {
                    resultDENIED.add(permissions[i]);
                }
            }
            if (iPermissions != null) {
                //并集
                hasGanPermission.removeAll(resultGRANTED);
                hasGanPermission.addAll(resultGRANTED);
                if (hasGanPermission.size()>0){
                    iPermissions.onPermissionGranted(hasGanPermission);
                }
                iPermissions.onPermissonReject(resultDENIED);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
