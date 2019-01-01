package com.yuntian.basecomponent.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yuntian.basecomponent.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author chulingyan
 * @time 2018/12/30 15:35
 * @describe
 */
public class DialogTipView  extends FrameLayout {

    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvOk;
    private TextView tvCancel;

    public DialogTipView(@NonNull Context context) {
        this(context,null);
    }

    public DialogTipView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,null,0);
    }

    public DialogTipView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void  initView(){
        View.inflate(getContext(), R.layout.dialog_customer_tip,this);

        tvTitle= findViewById(R.id.tv_title);
        tvContent=findViewById(R.id.tv_content);
        tvOk=findViewById(R.id.tv_ok);
        tvCancel=findViewById(R.id.tv_cancel);
    }

    private String title;
    private String content;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        tvTitle.setText(title);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        tvContent.setText(content);
    }

    public void setOkOnclick(OnClickListener onclick) {
          setOkOnclick(null,onclick);
    }

    public void setCancelOnclick( OnClickListener onclick) {
         setCancelOnclick(null,onclick);
    }


    public void setOkOnclick(String ok,OnClickListener onclick) {
        if (!TextUtils.isEmpty(ok)){
            tvOk.setText(ok);
        }
        tvOk.setOnClickListener(onclick);
    }

    public void setCancelOnclick(String cancel, OnClickListener onclick) {
        if (!TextUtils.isEmpty(cancel)){
            tvOk.setText(cancel);
        }
        tvCancel.setOnClickListener(onclick);
    }


}
