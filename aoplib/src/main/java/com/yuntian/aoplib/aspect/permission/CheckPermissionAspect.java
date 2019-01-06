package com.yuntian.aoplib.aspect.permission;

import com.yuntian.aoplib.annotation.CheckPermission;
import com.yuntian.aoplib.annotation.PermissionDeniedCallBack;
import com.yuntian.aoplib.annotation.PermissionGrantedCallBack;
import com.yuntian.aoplib.util.DebugLog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
public class CheckPermissionAspect {

    private static final String TAG = "CheckPermissionAspect";

    //注解作用的方法
    private static final String POINTCUT_METHOD =
            "execution(@com.yuntian.aoplib.annotation.CheckPermission * *(..))";

    //注解作用的构造方法
    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.yuntian.aoplib.annotation.CheckPermission *.new(..))";

    //方法切入点
    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithCheckPermission() {
    }

    //构造方法切入点
    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedCheckPermission() {
    }


    @Around("methodAnnotatedWithCheckPermission() || constructorAnnotatedCheckPermission()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //获得方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        CheckPermission annotation = methodSignature.getMethod().getAnnotation(CheckPermission.class);
        Object hostObject = joinPoint.getTarget();
        String methodName = methodSignature.getName();
        final Object[] results = {null};

        DebugLog.log(TAG, "className:" + className + ",methodName:" + methodName + ",CheckPermission" + Arrays.toString(annotation.value()));
        PermissionsUtil.checkPermission(hostObject, annotation.value(), new PermissionsUtil.IPermissions() {
            @Override
            public void onGrantedPermissionGranted(List<String> result) {
                boolean isAllGANEND = result.containsAll(Arrays.asList(annotation.value()));
                try {
                    if (isAllGANEND) {
                        DebugLog.log(TAG, "onGrantedPermissionGranted:" + result);
                        results[0] = joinPoint.proceed();  //连接点: 一个方法调用或者方法入口。
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                getCallbackMethods(hostObject, PermissionsUtil.GANEND_TYPE, annotation, isAllGANEND, result);
            }

            @Override
            public void onDeniedPermisson(List<String> result) {
                DebugLog.log(TAG, "onDeniedPermisson:" + result);
                getCallbackMethods(hostObject, PermissionsUtil.Denied_TYPE, annotation, false, result);
            }

            @Override
            public void shouldShowRational(String permisson) {

            }
        });
        return results[0];
    }

    private void getCallbackMethods(Object object, int type, CheckPermission checkPermission, boolean isGranted, List<String> result) {
        List<Method> methodList ;
        if (type == PermissionsUtil.GANEND_TYPE) {
            methodList = PermissionsUtil.getGanendCallBackMethod(object.getClass());
            if (methodList == null || methodList.size() == 0) {
                methodList = Arrays.asList(object.getClass().getDeclaredMethods());
            }
            for (Method method : methodList) {
                if (method.getAnnotation(PermissionGrantedCallBack.class) != null) {
                    invokePermissionResult(object, method, checkPermission, isGranted, result);
                }
            }
        } else if (type == PermissionsUtil.Denied_TYPE) {
            methodList = PermissionsUtil.getRejectCallBackMethod(object.getClass());
            if (methodList == null || methodList.size() == 0) {
                methodList = Arrays.asList(object.getClass().getDeclaredMethods());
            }
            for (Method method : methodList) {
                if (method.getAnnotation(PermissionDeniedCallBack.class) != null) {
                    invokePermissionResult(object, method, checkPermission, isGranted, result);
                }
            }
        }
    }


    private void invokePermissionResult(Object object, Method method, CheckPermission checkPermission, boolean isGranted, List<String> result) {
        boolean isEquale = false;
        boolean isCallBackVaule = false;
        PermissionResult permissionResult = new PermissionResult();
        if (method.getAnnotation(PermissionGrantedCallBack.class) != null) {
            PermissionGrantedCallBack permissionGrantedCallBack = method.getAnnotation(PermissionGrantedCallBack.class);
            isEquale = PermissionsUtil.getRequsteCode(checkPermission.value()).equals(PermissionsUtil.getRequsteCode(permissionGrantedCallBack.value()));
            permissionResult.setGranted(isGranted);
            isCallBackVaule = permissionGrantedCallBack.value().length == 0;
            PermissionsUtil.putGanendCallBackMethod(object.getClass(), method);
        } else if (method.getAnnotation(PermissionDeniedCallBack.class) != null) {
            PermissionDeniedCallBack permissionRejectCallBack = method.getAnnotation(PermissionDeniedCallBack.class);
            isEquale = PermissionsUtil.getRequsteCode(checkPermission.value()).equals(PermissionsUtil.getRequsteCode(permissionRejectCallBack.value()));
            permissionResult.setGranted(isGranted);
            isCallBackVaule = permissionRejectCallBack.value().length == 0;
            PermissionsUtil.putRejectCallBackMethod(object.getClass(), method);
        }

        if (isEquale || isCallBackVaule) {
            permissionResult.setList(result);
            permissionResult.setRequestCode(PermissionsUtil.getRequsteCode(checkPermission.value()));
            try {
                method.invoke(object, permissionResult);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
