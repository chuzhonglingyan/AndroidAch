package com.yuntian.aoplib.aspect.permission;

import java.util.List;

/**
 * @author chulingyan
 * @time 2019/01/05 23:24
 * @describe
 */
public class PermissionResult {

    private  int requestCode;
    private List<String>  list;
    private boolean granted;

    public boolean isGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
