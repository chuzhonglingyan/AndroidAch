package com.yuntian.basecomponent.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuntian.basecomponent.R;
import com.yuntian.basecomponent.progress.CircleProgressView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author chulingyan
 * @time 2018/12/30 15:35
 * @describe
 */
public class DialogProgressView extends FrameLayout {

    private TextView tvTitle;
    private TextView tvContent;
    private LinearLayout ll_opt;
    private TextView tvOk;
    private TextView tvCancel;

    private float mCurrentProess;

    private CircleProgressView progressView;

    public DialogProgressView(@NonNull Context context) {
        this(context,null);
    }

    public DialogProgressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,null,0);
    }

    public DialogProgressView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void  initView(){
        View.inflate(getContext(), R.layout.dialog_customer_progress,this);

        tvTitle= findViewById(R.id.tv_title);
        tvContent=findViewById(R.id.tv_content);
        ll_opt=findViewById(R.id.ll_opt);
        tvOk=findViewById(R.id.tv_ok);
        tvCancel=findViewById(R.id.tv_cancel);
        progressView=findViewById(R.id.progressView);
        ll_opt.setVisibility(GONE);
        tvContent.setVisibility(GONE);
        tvOk.setVisibility(GONE);
        tvCancel.setVisibility(GONE);

        progressView.setStartProgress(mCurrentProess);
        progressView.setEndProgress(80);
        progressView.setStartColor(Color.parseColor("#FF8F5D"));
        progressView.setEndColor(Color.parseColor("#F54EA2"));
        progressView.setCircleBroken(true);
        progressView.setTrackWidth(20);
        progressView.setProgressDuration(2000);
        progressView.setTrackEnabled(true);
        progressView.setFillEnabled(false);
        progressView.setProgressViewUpdateListener(new CircleProgressView.CircleProgressUpdateListener() {
            @Override
            public void onCircleProgressStart(View view) {

            }

            @Override
            public void onCircleProgressUpdate(View view, float progress) {
                mCurrentProess=progress;
            }

            @Override
            public void onCircleProgressFinished(View view) {

            }
        });
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

    public CircleProgressView getProgressView() {
        return progressView;
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


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (progressView!=null){
            progressView.startProgressAnimation();
        }
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putFloat("mCurrentProess", mCurrentProess);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentProess = bundle.getFloat("mCurrentProess");
            state = bundle.getParcelable("instanceState");
            progressView.setProgress(mCurrentProess);
        }
        super.onRestoreInstanceState(state);
    }




    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (progressView!=null){
            progressView.stopProgressAnimation();
        }
    }

    public void  onDestory(){
        if (progressView!=null){
            progressView.stopProgressAnimation();
        }
    }


}
