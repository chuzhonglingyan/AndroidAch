package com.yuntian.baselibs.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import com.yuntian.baselibs.R;
import com.yuntian.baselibs.imageloader.progress.GlideApp;
import com.yuntian.baselibs.imageloader.progress.GlideRequest;
import com.yuntian.baselibs.imageloader.progress.OnProgressListener;
import com.yuntian.baselibs.imageloader.progress.ProgressManager;
import java.io.File;


public class ImageLoader {

    private static final String ANDROID_RESOURCE = "android.resource://";


    private static int PLACEHOLDER_DEFAULT = R.mipmap.image_loading;
    private static int PLACEHOLDER_ERR = R.mipmap.image_load_err;


    public static void initImageLoader(Context context) {
    }

    public static void initImageLoader(Context context, int defaultImage) {
        PLACEHOLDER_DEFAULT = defaultImage;
    }

    public static void load(String url, ImageView imageView) {
        load(url, imageView, null);
    }

    public static void load(String url, ImageView imageView, OnProgressListener onProgressListener) {
        GlideRequest<Drawable> glideRequest = GlideApp.with(imageView.getContext())
                .load(url)
                .placeholder(PLACEHOLDER_DEFAULT)
                .error(PLACEHOLDER_ERR)
                .fallback(PLACEHOLDER_ERR);
        if (onProgressListener != null) {
            ProgressManager.addListener(url, onProgressListener);
            glideRequest.into(new GlideImageViewTarget(url, imageView));
        } else {
            glideRequest.into(imageView);
        }
    }

    public static void loadGif(String url, ImageView imageView) {
        GlideApp.with(imageView.getContext())
                .asGif()
                .load(url)
                .placeholder(PLACEHOLDER_DEFAULT)
                .error(PLACEHOLDER_ERR)
                .fallback(PLACEHOLDER_ERR)
                .into(imageView);
    }

    protected Uri resId2Uri(Context context,@DrawableRes int resId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + File.separator + resId);
    }


}
