package com.yuntian.androidarch.bean;

/**
 * @author chulingyan
 * @time 2018/12/28 22:55
 * @describe
 */
public class TestBean {

    public TestBean(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
