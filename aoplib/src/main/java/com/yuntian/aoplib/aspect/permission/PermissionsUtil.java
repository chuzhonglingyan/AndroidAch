package com.yuntian.aoplib.aspect.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * @author chulingyan
 * @time 2019/01/01 20:42
 * @describe
 */
public class PermissionsUtil {

    private static final String TAG = "PermissionsUtil";

    private static ArrayMap<String, Integer> permissionsRequsteCode = new ArrayMap<>();
    private static ArrayMap<String, String> permissionsDesc= new ArrayMap<>();
    public static final int MORES_PERMISSION_CODE = 0x1000;
    private static final int SD_PERMISSION_CODE = 0x1001;
    private static final int CAMERA_PERMISSION_CODE = 0x1002;
    private static final int CALL_PHONE_PERMISSION_CODE = 0x1003;

    static {
        permissionsRequsteCode.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, SD_PERMISSION_CODE);
        permissionsDesc.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "Sd卡");

        permissionsRequsteCode.put(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
        permissionsDesc.put(Manifest.permission.CAMERA, "相机");

        permissionsRequsteCode.put(Manifest.permission.CALL_PHONE, CALL_PHONE_PERMISSION_CODE);
        permissionsDesc.put(Manifest.permission.CALL_PHONE, "打电话");
    }

    public void initPermissionsTip() {

    }

    public static Integer getRequsteCode(String permisson) {
        if (permissionsRequsteCode.get(permisson) != null) {
            return permissionsRequsteCode.get(permisson);
        }
        return 0;
    }

    public interface IPermissions {
        void onPermissionGranted(List<String> result);

        void onPermissonReject(List<String> result);

        void shouldShowRational(String permisson);
    }

    public static void checkPermission(FragmentActivity activity, String[] permissions, IPermissions iPermissions) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissions == null || permissions.length <= 0) {
                    Log.d(TAG, "权限不能为空");
                    return;
                }
                PermissionFragment fragment = (PermissionFragment) activity.getSupportFragmentManager().findFragmentByTag(PermissionFragment.class.getSimpleName());
                if (fragment == null) {
                    fragment = new PermissionFragment();
                }
                fragment.setiPermission(iPermissions, permissions);
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                if (fragment.isAdded()) {
                    fragment.requestPermissions();
                    return;
                }
                transaction.add(fragment, PermissionFragment.class.getSimpleName());
                transaction.hide(fragment);
                transaction.commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showRationalDialog(FragmentActivity activity, String[] permissions) {
        if (permissions != null && permissions.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < permissions.length; i++) {
                if (permissionsDesc.containsKey(permissions[i])){
                    stringBuilder.append(permissionsDesc.get(permissions[i]));
                }else {
                    stringBuilder.append(permissions[i]);
                }
                if (i < permissions.length - 1) {
                    stringBuilder.append(",");
                }
            }
            new AlertDialog.Builder(activity)
                    .setTitle("权限申请")
                    .setMessage(String.format("%s权限申请", stringBuilder.toString()))
                    .setPositiveButton("确认", (dialog, which) -> {
                        gotoSetting(activity, permissions);
                    })
                    .setNegativeButton("取消", null)
                    .create()
                    .show();
        }
    }


    /**
     * 跳转到设置界面；
     *
     * @param activity
     * @param permissions
     */
    public static void gotoSetting(Activity activity, String[] permissions) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
            activity.startActivityForResult(intent, PermissionsUtil.getRequsteCode(permissions[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
