package com.yuntian.androidarch.di.component;

import com.yuntian.androidarch.di.module.ApiServiceModule;
import com.yuntian.androidarch.di.module.UserModule;
import com.yuntian.androidarch.ui.activity.MainActivity;
import com.yuntian.androidarch.ui.fragment.UserProfileFragmentA;
import com.yuntian.androidarch.ui.fragment.UserProfileFragmentB;
import com.yuntian.baselibs.di.component.AppComponent;
import com.yuntian.baselibs.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {ApiServiceModule.class,UserModule.class })
public interface UserComponent {

    void inject(UserProfileFragmentA fragment);

    void inject(UserProfileFragmentB fragment);

    void inject(MainActivity activity);


}
