package com.yuntian.basecomponent.dialog;

import androidx.lifecycle.ViewModel;

public class SaveViewModel extends ViewModel {

    private static final String TAG = "SaveViewModel";

    private OnCallDialog mOnCallDialog;

    public OnCallDialog getmOnCallDialog() {
        return mOnCallDialog;
    }

    public void setmOnCallDialog(OnCallDialog mOnCallDialog) {
        this.mOnCallDialog = mOnCallDialog;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
