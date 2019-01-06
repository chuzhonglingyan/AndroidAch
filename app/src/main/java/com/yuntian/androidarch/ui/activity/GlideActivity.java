package com.yuntian.androidarch.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.yuntian.androidarch.R;
import com.yuntian.baselibs.base.BaseActivity;
import com.yuntian.baselibs.di.component.AppComponent;
import com.yuntian.baselibs.imageloader.ImageLoader;
import com.yuntian.baselibs.imageloader.progress.GlideApp;
import com.yuntian.baselibs.util.UiUtils;

import java.text.MessageFormat;

import static com.yuntian.androidarch.router.RouteUrl.PATH_GLIDE;

@Route(path = PATH_GLIDE)
public class GlideActivity extends BaseActivity {

   private ImageView iv1;
   private ImageView iv2;
   private ImageView iv3;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_glide;
    }

    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        //1,200px × 562px
        iv1=findViewById(R.id.iv1);
        iv2=findViewById(R.id.iv2);
        iv3=findViewById(R.id.iv3);

        testNoFixSizefitCenter(test1);
        testNoFixSizecenterCrop(test1);

        testFixSizeScaleUrl(test2,iv3);

        iv2.postDelayed(()->{
            LogUtils.d("iv1 width"+iv1.getWidth());
            LogUtils.d("iv1 height"+iv1.getHeight());

            LogUtils.d("iv2 width"+iv2.getWidth());
            LogUtils.d("iv2 height"+iv2.getHeight());

            LogUtils.d("iv3 width"+iv3.getWidth());
            LogUtils.d("iv3 height"+iv3.getHeight());
        },2000);

    }

    String test1="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546341197127&di=15ee96653e98517fce9fcc6f99418f68&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Fback_pic%2F00%2F03%2F80%2F81561fbfead25e5.jpg";

    /**
     * fitCenter() 是裁剪技术，即缩放图像让图像都测量出来等于或小于 ImageView 的边界范围。
     * 该图像将会完全显示，但可能不会填满整个 ImageView。
     * @param requestUrl
     */
    public void  testNoFixSizefitCenter(String requestUrl){
        int w=UiUtils.dp2px(this,300);
        int h=UiUtils.dp2px(this,200);
        GlideApp.with(this)
                .load(requestUrl)
                .override(w,h)
                .fitCenter()
                .into(iv1);
    }

    /**
     * CenterCrop()是一个裁剪技术，即缩放图像让它填充到 ImageView 界限内并且裁剪额外的部分。
     * ImageView 可能会完全填充，但图像可能不会完整显示。
     * @param requestUrl
     */
    public void  testNoFixSizecenterCrop(String requestUrl){
        int w=UiUtils.dp2px(this,300);
        int h=UiUtils.dp2px(this,200);
        GlideApp.with(this)
                .load(requestUrl)
                .override(w,h)
                .centerCrop()
                .into(iv2);
    }


    String test2="https://odum9helk.qnssl.com/resource/Ship.jpg";


    /**
     * 按照需要加载对应尺寸的图片
     * @param requestUrl
     */
    public void  testFixSizeScaleUrl(String requestUrl,ImageView iv){
        int w=UiUtils.dp2px(this,100);
        int h=UiUtils.dp2px(this,100);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageLoader.load(getRequsetUrl(requestUrl, w,h),iv);
    }


    public String getRequsetUrl(String requestUrl,int width,int height){
        String str=requestUrl+"?imageView2/0/w/{0}/h/{1}";
        str=MessageFormat.format(str, width,height);
        LogUtils.d(str);
        return str;
    }



    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isInit, @Nullable Bundle savedInstanceState) {


    }

}
