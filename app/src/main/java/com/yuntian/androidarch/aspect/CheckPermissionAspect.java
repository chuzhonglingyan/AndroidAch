package com.yuntian.androidarch.aspect;


import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.LogUtils;
import com.yuntian.androidarch.annotation.CheckPermission;
import com.yuntian.androidarch.ui.activity.PermissionsUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;
import java.util.List;

@Aspect
public class CheckPermissionAspect {

    //注解作用的方法
    private static final String POINTCUT_METHOD =
            "execution(@com.yuntian.androidarch.annotation.CheckPermission * *(..))";

    //注解作用的构造方法
    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.yuntian.androidarch.annotation.CheckPermission *.new(..))";

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

        Object object = joinPoint.getTarget();
        String methodName = methodSignature.getName();
        final Object[] results = {null};
        LogUtils.d("className:" + className + ",methodName:" + methodName + ",CheckPermission" + Arrays.toString(annotation.value()));
        if (object instanceof FragmentActivity&&annotation.value().length>0) {
            PermissionsUtil.checkPermission((FragmentActivity) object, annotation.value(), new PermissionsUtil.IPermissions() {
                @Override
                public void onPermissionGranted(List<String> result) {
                    try {
                        if (result.containsAll(Arrays.asList(annotation.value()))){
                            LogUtils.d("onPermissionGranted:"+ result);
                            results[0] =joinPoint.proceed();  //连接点: 一个方法调用或者方法入口。
                        }
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }

                }

                @Override
                public void onPermissonReject(List<String> result) {
                    LogUtils.d("onPermissonReject:"+ result);
                }

                @Override
                public void shouldShowRational(String permisson) {

                }
            });
        }
        return results[0] ;
    }



}
