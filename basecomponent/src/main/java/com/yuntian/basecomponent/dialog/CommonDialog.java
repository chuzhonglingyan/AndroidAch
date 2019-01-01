package com.yuntian.basecomponent.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Haoz on 2017/4/6 0006.
 */

public class CommonDialog extends DialogFragment {


    private SaveViewModel saveViewModel;

    /**
     * 监听弹出窗是否被取消
     */
    private OnDialogCancelListener mCancelListener;
    /**
     * 回调获得需要显示的 dialog
     */
    private OnCallDialog mOnCallDialog;


    public static CommonDialog newInstance(OnCallDialog callDialog, boolean cancelable) {
        return newInstance(callDialog, cancelable, null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    public static CommonDialog newInstance(OnCallDialog callDialog, boolean cancelable, OnDialogCancelListener cancelListener) {
        CommonDialog instance = new CommonDialog();
        instance.setCancelable(cancelable);
        instance.mCancelListener = cancelListener;
        instance.mOnCallDialog = callDialog;
        return instance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() != null) {
            saveViewModel = ViewModelProviders.of(getActivity()).get(SaveViewModel.class);
            if (savedInstanceState != null) {
                mOnCallDialog = saveViewModel.getmOnCallDialog();
            }
        }
        if (mOnCallDialog == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        saveViewModel.setmOnCallDialog(mOnCallDialog);
        return mOnCallDialog.getDialog(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {

            Window window = dialog.getWindow();

            // 在 5.0 以下的版本会出现白色背景边框，若在 5.0 以上设置则会造成文字部分的背景也变成透明
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                // 目前只有这两个 dialog 会出现边框
                if (dialog instanceof ProgressDialog || dialog instanceof DatePickerDialog) {
                    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            dialog.getWindow().setLayout((int) (ScreenUtils.getScreenWidth() * 0.5), ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setAttributes(windowParams);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mCancelListener != null) {
            mCancelListener.onCancel();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

