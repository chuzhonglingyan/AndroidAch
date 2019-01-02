package com.yuntian.androidarch.ui.activity;

import com.blankj.utilcode.util.TimeUtils;
import com.yuntian.androidarch.R;
import com.yuntian.androidarch.bean.TestBean;
import com.yuntian.basecomponent.adapter.BaseRvAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author   chulingyan
 * @time     2018/12/29 18:36
 * @describe 列表例子
 */

public  class ExampleListActivity extends BaseListActivity<TestBean> {

    @Override
    protected   BaseRvAdapter<TestBean> getRvAdapter() {
        return new BaseRvAdapter<TestBean>() {
            @Override
            protected int getLayoutId(int position) {
                return R.layout.item_test;
            }
        };
    }

    @Override
    public void  getListData(){
        refreshLayout.getLayout().postDelayed(() -> {
            List<TestBean> list=getPageList();
            setData(list);
        }, 1000);
    }


    public List<TestBean>  getPageList(){
        if (startPage>3){
            return null;
        }
        List<TestBean> testBeans=new ArrayList<>();
        for (int i = 0; i < PAGESIZE; i++) {
            testBeans.add(new TestBean("我是"+i+","+ TimeUtils.getNowString()));
        }
        return  testBeans;
    }


}
