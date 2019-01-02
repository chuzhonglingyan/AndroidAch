package com.yuntian.androidarch.ui.view.flexboxLayout;

import android.os.Bundle;

import androidx.annotation.Nullable;
import com.google.android.flexbox.FlexboxLayout;
import com.yuntian.androidarch.R;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;

/**
 * @author chulingyan
 * @time 2019/01/01 12:18
 * @describe
 */
public class FlexboxLayoutActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_flexboxlayout;
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        FlexboxLayout flexboxLayout=findViewById(R.id.flexboxLayout);
        String[] tags = {"婚姻育儿", "散文", "设计", "上班这点事儿", "影视天堂", "大学生活", "美人说", "运动和健身", "工具癖", "生活家", "程序员", "想法", "短篇小说", "美食", "教育", "心理", "奇思妙想", "美食", "摄影"};
        for (int i = 0; i < tags.length; i++) {
            Tag model = new Tag();
            model.setId(i);
            model.setName(tags[i]);
            flexboxLayout.addView(TagUtil.createNewFlexItemTextView(flexboxLayout.getContext(),model));
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {

    }
}
