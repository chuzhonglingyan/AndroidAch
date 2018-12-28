package com.yuntian.baselibs.lifecycle;


import com.blankj.utilcode.util.LogUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;

/**
 * @author chulingyan
 * @time 2018/12/19  22:19
 * @describe setRetainInstance(true) 保证当界面旋转被销毁再重建时保证mViewModelStore 不被销毁
 */


public abstract class BaseViewModle<T extends BaseRepository> extends ViewModel  implements LifecycleObserver {


    protected T repo;

    private Lifecycle lifecycle;

    public T getRepo() {
        return repo;
    }

    public void setRepo(T repo) {
        this.repo = repo;
    }

    public Lifecycle getLifecycle() {
        return lifecycle;
    }


    public void  addObserver(Lifecycle lifecycle){
        if (this.lifecycle==null){
            this.lifecycle = lifecycle;
        }
        if (this.lifecycle==null){
            return;
        }
        this.lifecycle.addObserver(this);
    }

    @Override
    public void onCleared() {
        super.onCleared();
        repo.onCleared();
        LogUtils.d("onCleared");
    }

}
