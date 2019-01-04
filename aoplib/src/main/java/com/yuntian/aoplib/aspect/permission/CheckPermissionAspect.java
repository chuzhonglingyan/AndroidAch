package com.yuntian.aoplib.aspect.permission;

import androidx.fragment.app.FragmentActivity;
import com.yuntian.aoplib.annotation.CheckPermission;
import com.yuntian.aoplib.util.DebugLog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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

        Object object = joinPoint.getTarget();
        String methodName = methodSignature.getName();
        final Object[] results = {null};
        DebugLog.log(TAG,"className:" + className + ",methodName:" + methodName + ",CheckPermission" + Arrays.toString(annotation.value()));
        if (object instanceof FragmentActivity) {
            PermissionsUtil.checkPermission((FragmentActivity) object, annotation.value(), new PermissionsUtil.IPermissions() {
                @Override
                public void onPermissionGranted(List<String> result) {
                    try {
                        if (result.containsAll(Arrays.asList(annotation.value()))) {
                            DebugLog.log(TAG,"onPermissionGranted:" + result);
                            results[0] = joinPoint.proceed();  //连接点: 一个方法调用或者方法入口。
                        }
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }

                }

                @Override
                public void onPermissonReject(List<String> result) {
                    DebugLog.log(TAG,"onPermissonReject:" + result);
                    PermissionsUtil.showRationalDialog((FragmentActivity) object, CollectionUtil.listToArray(result));
                }

                @Override
                public void shouldShowRational(String permisson) {

                }
            });
        }
        return results[0];
    }


}
