package com.yuntian.baselibs.imageloader;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yuntian.baselibs.imageloader.progress.OnProgressListener;
import com.yuntian.baselibs.imageloader.progress.ProgressManager;

/**
 * @author chulingyan
 * @time 2019/01/06 22:21
 * @describe
 */
public class GlideImageViewTarget extends DrawableImageViewTarget {

    private String url;

    GlideImageViewTarget(String url, ImageView view) {
        super(view);
        this.url = url;
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        super.onLoadStarted(placeholder);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        OnProgressListener onProgressListener = ProgressManager.getProgressListener(url);
        if (onProgressListener != null) {
            onProgressListener.onProgress(true, 100, 0, 0);
            ProgressManager.removeListener(url);
        }
        super.onLoadFailed(errorDrawable);
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        OnProgressListener onProgressListener = ProgressManager.getProgressListener(url);
        if (onProgressListener != null) {
            onProgressListener.onProgress(true, 100, 0, 0);
            ProgressManager.removeListener(url);
        }
        super.onResourceReady(resource, transition);
    }
}
