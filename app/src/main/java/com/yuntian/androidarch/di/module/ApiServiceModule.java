package com.yuntian.androidarch.di.module;

import com.yuntian.androidarch.net.service.GankService;
import com.yuntian.androidarch.net.service.GitHubservice;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiServiceModule {

    @Provides
    public GitHubservice provideGitHubservice(Retrofit retrofit) {
        return retrofit.create(GitHubservice.class);
    }

    @Provides
    public GankService provideGankService(Retrofit retrofit) {
        return retrofit.create(GankService.class);
    }

}
