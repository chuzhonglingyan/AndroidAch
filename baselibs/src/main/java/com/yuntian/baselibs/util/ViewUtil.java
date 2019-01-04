package com.yuntian.baselibs.util;

import android.view.MotionEvent;
import android.view.View;

/**
 * @author chulingyan
 * @time 2018/12/19 19:54
 * @describe
 */
public class ViewUtil {


    /**
     * 设置当前是否需要隐藏 * * @param view * @param visibility
     */
    public static void setViewVisibility(View view, int visibility) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() == visibility) {
            return;
        }
        view.setVisibility(visibility);
    }


    /**
     * 判断点击屏幕的位置是不是在指定的view上
     *
     * @param view
     * @param ev
     * @return
     */
    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y
                || ev.getY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }


    /**
     * 获取view左上角坐标
     *
     * @param view
     * @return
     */
    public static int[] getViewLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }


}
