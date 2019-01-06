package com.yuntian.aoplib.aspect.permission;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chulingyan
 * @time 2019/01/01 20:42
 * @describe
 */
public class PermissionsUtil {

    private static final String TAG = "PermissionsUtil";

    public static final int GANEND_TYPE = PackageManager.PERMISSION_GRANTED;
    public static final int Denied_TYPE = PackageManager.PERMISSION_DENIED;

    private static ArrayMap<String, Integer> permissionsRequsteCode = new ArrayMap<>();
    private static ArrayMap<String, String> permissionsDesc = new ArrayMap<>();

    static {
        permissionsDesc.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "Sd卡");
        permissionsDesc.put(Manifest.permission.CAMERA, "相机");
        permissionsDesc.put(Manifest.permission.CALL_PHONE, "打电话");
    }

    private static Map<Class, List<Method>> mapGanendCallBack = new ConcurrentHashMap<>();
    private static Map<Class, List<Method>> mapRejectCallBack = new ConcurrentHashMap<>();

    public static void putGanendCallBackMethod(Class c, Method method) {
        List<Method> methods = mapGanendCallBack.get(c);
        if (methods == null) {
            methods = new ArrayList<>();
        }
        if (!methods.contains(method)){
            methods.add(method);
            mapGanendCallBack.put(c, methods);
        }
    }

    public static List<Method> getGanendCallBackMethod(Class c) {
        return mapGanendCallBack.get(c);
    }

    public static void putRejectCallBackMethod(Class c, Method method) {
        List<Method> methods = mapRejectCallBack.get(c);
        if (methods == null) {
            methods = new ArrayList<>();
        }
        if (!methods.contains(method)){
            methods.add(method);
            mapRejectCallBack.put(c, methods);
        }
    }

    public static List<Method> getRejectCallBackMethod(Class c) {
        return mapRejectCallBack.get(c);
    }

    public void initPermissionsTip() {

    }

    public static Integer getRequsteCode(List<String> permissons) {
        return getRequsteCode(CollectionUtil.listToArray(permissons));
    }


    public static Integer getRequsteCode(String permisson) {
        return getRequsteCode(new String[]{permisson});
    }

    public static Integer getRequsteCode(String[] permissons) {
        String permissonStr = getPermissionsStr(permissons);
        if (permissionsRequsteCode.get(permissonStr) != null) {
            return permissionsRequsteCode.get(permissonStr);
        }
        return 0;
    }


    public interface IPermissions {
        void onGrantedPermissionGranted(List<String> result);

        void onDeniedPermisson(List<String> result);

        void shouldShowRational(String permisson);
    }

    public static void checkPermission(Object o, String[] permissions, IPermissions iPermissions) {
        if (o instanceof FragmentActivity) {
            checkPermission((FragmentActivity) o, permissions, iPermissions);
        } else if (o instanceof Fragment) {
            checkPermission((Fragment) o, permissions, iPermissions);
        } else if (o instanceof View) {
            View view = (View) o;
            if (view.getContext() instanceof FragmentActivity) {
                checkPermission((FragmentActivity) view.getContext(), permissions, iPermissions);
            }
        } else if (o instanceof Dialog) {
            Dialog dialog = (Dialog) o;
            if (dialog.getContext() instanceof FragmentActivity) {
                checkPermission((FragmentActivity) dialog.getContext(), permissions, iPermissions);
            }
        }
    }

    public static void createPemissonsRequestCode(String[] permissions) {
        String permissionsStr = getPermissionsStr(permissions);
        if (permissionsRequsteCode.containsKey(permissionsStr)) {
            return;
        }
        permissionsRequsteCode.put(permissionsStr, createPemissonRequestCode(permissionsStr));
    }

    public static String getPermissionsStr(String[] permissions) {
        StringBuilder permissionsBuilder = new StringBuilder();
        if (permissions != null) {
            for (String permission : permissions) {
                permissionsBuilder.append(permission);
            }
        }
        return permissionsBuilder.toString();
    }


    public static int createPemissonRequestCode(String permission) {
        return permission.hashCode() >>> 16;
    }

    public static void checkPermission(FragmentActivity host, String permission, IPermissions iPermissions) {
        checkPermission(host, new String[]{permission}, iPermissions);
    }


    public static void checkPermission(FragmentActivity host, String[] permissions, IPermissions iPermissions) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                createPemissonsRequestCode(permissions);

                PermissionFragment fragment = (PermissionFragment) host.getSupportFragmentManager().findFragmentByTag(PermissionFragment.class.getSimpleName());
                if (fragment == null) {
                    fragment = new PermissionFragment();
                }
                fragment.setiPermission(iPermissions, permissions);
                FragmentTransaction transaction = host.getSupportFragmentManager().beginTransaction();
                if (fragment.isAdded()) {
                    fragment.requestPermissions();
                    return;
                }
                transaction.add(fragment, PermissionFragment.class.getSimpleName());
                transaction.hide(fragment);
                transaction.commitAllowingStateLoss();
            } else {
                if (iPermissions != null) {
                    iPermissions.onGrantedPermissionGranted(Arrays.asList(permissions));
                    iPermissions.onDeniedPermisson(new ArrayList<>());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkPermission(Fragment host, String permission, IPermissions iPermissions) {
        checkPermission(host, new String[]{permission}, iPermissions);
    }

    public static void checkPermission(Fragment host, String[] permissions, IPermissions iPermissions) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                createPemissonsRequestCode(permissions);
                PermissionFragment fragment = (PermissionFragment) host.getChildFragmentManager().findFragmentByTag(PermissionFragment.class.getSimpleName());
                if (fragment == null) {
                    fragment = new PermissionFragment();
                }
                fragment.setiPermission(iPermissions, permissions);
                FragmentTransaction transaction = host.getChildFragmentManager().beginTransaction();
                if (fragment.isAdded()) {
                    fragment.requestPermissions();
                    return;
                }

                transaction.add(fragment, PermissionFragment.class.getSimpleName());
                transaction.hide(fragment);
                transaction.commitAllowingStateLoss();
            } else {
                if (iPermissions != null) {
                    iPermissions.onGrantedPermissionGranted(Arrays.asList(permissions));
                    iPermissions.onDeniedPermisson(new ArrayList<>());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showRationalDialog(FragmentActivity activity, List<String> permissions) {
        if (permissions != null && permissions.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < permissions.size(); i++) {
                if (permissionsDesc.containsKey(permissions.get(i))) {
                    stringBuilder.append(permissionsDesc.get(permissions.get(i)));
                } else {
                    stringBuilder.append(permissions.get(i));
                }
                if (i < permissions.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            new AlertDialog.Builder(activity)
                    .setTitle("权限申请")
                    .setMessage(String.format("%s权限申请", stringBuilder.toString()))
                    .setPositiveButton("确认", (dialog, which) -> {
                        gotoSetting(activity, PermissionsUtil.getRequsteCode(permissions.get(0)));
                    })
                    .setNegativeButton("取消", null)
                    .create()
                    .show();
        }
    }

    public static void showRationalDialog(FragmentActivity activity, String[] permissions) {
        showRationalDialog(activity, Arrays.asList(permissions));
    }


    /**
     * 跳转到设置界面；
     *
     * @param activity
     */
    public static void gotoSetting(Activity activity, int resuqestCode) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
            activity.startActivityForResult(intent, resuqestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
