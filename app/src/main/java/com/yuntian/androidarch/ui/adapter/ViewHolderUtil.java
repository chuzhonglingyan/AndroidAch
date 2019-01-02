package com.yuntian.androidarch.ui.adapter;

import com.yuntian.androidarch.R;
import com.yuntian.basecomponent.adapter.ViewHolderManager;

/**
 * @author chulingyan
 * @time 2019/01/01 11:33
 * @describe
 */
public class ViewHolderUtil {


    public static void  initViewHolder(){
        ViewHolderManager.registerViewHolder(R.layout.item_gank, GankViewHolder.class);
        ViewHolderManager.registerViewHolder(R.layout.item_test, TestViewHolder.class);
    }


}
