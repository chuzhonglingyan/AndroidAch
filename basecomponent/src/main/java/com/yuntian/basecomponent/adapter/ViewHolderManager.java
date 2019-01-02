package com.yuntian.basecomponent.adapter;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.LayoutRes;

import com.blankj.utilcode.util.ReflectUtils;

/**
 * @author chulingyan
 * @time 2018/12/16 11:58
 * @describe
 */
public  class ViewHolderManager {

    //布局id对应ViewHolder
    private static SparseArray<Class<? extends BaseViewHolder>> viewHolderByViewType=new SparseArray<>();

    public static    Class<? extends BaseViewHolder>  getViewHolder (int viewType){
        Class<? extends BaseViewHolder> clas= viewHolderByViewType.get(viewType);
        if (clas==null){
            throw new RuntimeException("viewType of ViewHolder 还未注册");
        }
        return  clas;
    }

    public static  <T> BaseViewHolder<T>  createViewHolder(int viewType, View itemView){
        return createViewHolder(getViewHolder(viewType),itemView);
    }


    public static void  registerViewHolder(@LayoutRes int layoutId,Class<? extends BaseViewHolder> viewHolder){
        viewHolderByViewType.put(layoutId,viewHolder);
    }



    public static  <T> BaseViewHolder<T>  createViewHolder(Class<? extends BaseViewHolder> clas, View itemView){
       return  ReflectUtils.reflect(clas).newInstance(itemView).get();
    }


}
