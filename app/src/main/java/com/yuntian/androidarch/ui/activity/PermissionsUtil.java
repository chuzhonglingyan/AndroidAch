package com.yuntian.androidarch.ui.activity;

import android.Manifest;
import android.os.Build;

import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

/**
 * @author chulingyan
 * @time 2019/01/01 20:42
 * @describe
 */
public class PermissionsUtil {

    private static ArrayMap<String,Integer> permissionsRequsteCode=new ArrayMap<>();
    public static final int MORES_PERMISSION_CODE = 0x1000;
    private static final int SD_PERMISSION_CODE = 0x1001;
    private static final int CAMERA_PERMISSION_CODE = 0x1002;

    static {
        permissionsRequsteCode.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, SD_PERMISSION_CODE);
        permissionsRequsteCode.put(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
    }


    public static Integer  getRequsteCode(String permisson){
        if (permissionsRequsteCode.get(permisson)!=null){
            return permissionsRequsteCode.get(permisson);
        }
        return 0;
    }


    public  interface IPermission{
        void onPermissionGranted(String permisson);

        void shouldShowRational(String permisson);

        void onPermissonReject(String permisson);
    }

    public  interface IPermissions{
        void onPermissionGranted(List<String> result);

        void onPermissonReject(List<String> result);

        void shouldShowRational(String permisson);
    }


//    public static void  checkPermission (FragmentActivity activity,String permission,IPermission iPermission){
//        try {
//            if (TextUtils.isEmpty(permission)){
//                LogUtils.d("权限不合法");
//                return;
//            }
//            //使用兼容库就无需判断系统版本
//            int hasWriteStoragePermission = ContextCompat.checkSelfPermission(activity, permission);
//            if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
//                LogUtils.d("已经有权限"+permission);
//                if (iPermission!=null){
//                    iPermission.onPermissionGranted(permission);
//                }
//            }else{
//                PermissionFragment fragment= (PermissionFragment) activity.getSupportFragmentManager().findFragmentByTag(PermissionFragment.class.getSimpleName());
//                if (fragment==null){
//                    fragment=new PermissionFragment();
//                }
//                fragment.setiPermission(iPermission,new String[]{permission});
//                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
//                if (fragment.isAdded()) {
//                    LogUtils.d("该" + fragment.toString() + "已经被添加过了");
//                    fragment.requestPermissions();
//                    return;
//                }
//                transaction.add(fragment, PermissionFragment.class.getSimpleName());
//                transaction.hide(fragment);
//                transaction.commitAllowingStateLoss();
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void  checkPermission (FragmentActivity activity,String[] permissions,IPermissions iPermissions){
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                LogUtils.d("低于版本");
            }else{
                if (permissions==null||permissions.length<=0){
                    LogUtils.d("权限不合法");
                    return;
                }
                PermissionFragment fragment= (PermissionFragment) activity.getSupportFragmentManager().findFragmentByTag(PermissionFragment.class.getSimpleName());
                if (fragment==null){
                    fragment=new PermissionFragment();
                }
                fragment.setiPermission(iPermissions,permissions);
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                if (fragment.isAdded()) {
//                    LogUtils.d("该" + fragment.toString() + "已经被添加过了");
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


}
