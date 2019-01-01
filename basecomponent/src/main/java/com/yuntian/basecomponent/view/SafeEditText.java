package com.yuntian.basecomponent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import androidx.appcompat.widget.AppCompatEditText;

/**
 * @author chulingyan
 * @time 2018/12/31 0:48
 * @describe
 */
public class SafeEditText extends AppCompatEditText {
    public SafeEditText(Context context) {
        super(context);
    }

    public SafeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SafeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performLongClick() {
        try {
            return super.performLongClick();
        } catch (Exception e) {
            return true;
        }
    }



    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (listener != null){
                listener.onKeyBack();
                //这里根据自己项目的实际需要选择返回true或者false
                return super.onKeyPreIme(keyCode, event);
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    private OnEditTextKeyBackListener listener;
    public void setOnEditTextKeyBackListener(OnEditTextKeyBackListener listener){
        this.listener = listener;
    }
    public interface OnEditTextKeyBackListener{
        void onKeyBack();
    }

}
