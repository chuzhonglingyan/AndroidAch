package com.yuntian.androidarch.ui.view.flexboxLayout;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.google.android.flexbox.FlexboxLayout;
import com.yuntian.androidarch.R;
import com.yuntian.baselibs.util.UiUtils;

/**
 * @author chulingyan
 * @time 2019/01/01 12:20
 * @describe
 */
public class TagUtil {

    private static final String TAG = "TagUtil";

    /**
     * 动态创建TextView
     * @param book
     * @return
     */
    public static TextView createNewFlexItemTextView(Context context,final Tag book) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(book.getName());
        textView.setTextSize(12);
        textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
        textView.setBackgroundResource(R.drawable.tag_states);
        textView.setTag(book.getId());
        textView.setOnClickListener(view -> Log.d(TAG, book.getName()));
        int padding = UiUtils.dp2px(context, 4);
        int paddingLeftAndRight = UiUtils.dp2px(context, 8);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = UiUtils.dp2px(context, 6);
        int marginTop = UiUtils.dp2px(context, 16);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }


}
