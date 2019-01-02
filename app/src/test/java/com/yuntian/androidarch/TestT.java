package com.yuntian.androidarch;

import com.yuntian.androidarch.annotation.CheckPermission;

/**
 * @author chulingyan
 * @time 2019/01/02 22:48
 * @describe
 */
public class TestT {

    @CheckPermission(value = "哈哈")
    public void wiriteToSd() {
        System.out.println("你好");
    }

}
