package com.yuntian.androidarch.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.yuntian.androidarch.R;
import com.yuntian.androidarch.bean.GankInfo;
import com.yuntian.androidarch.bean.TestBean;

import androidx.annotation.NonNull;

/**
 * @author chulingyan
 * @time 2018/12/16 12:10
 * @describe
 */
public class TestViewHolder extends BaseViewHolder<TestBean> {



    TestViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    void bindData(TestBean testBean, int position) {
        TextView tv_id = getView(R.id.tv_id);
        TextView textView = getView(R.id.tv_name);
        tv_id.setText(String.valueOf(position));
        textView.setText(testBean.getName());
    }

    void clear() {

    }
}

