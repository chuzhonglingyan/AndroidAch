package com.yuntian.androidarch.net.service;

import com.yuntian.androidarch.bean.GitHubUser;
import com.yuntian.androidarch.bean.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubservice {

    /**
     * @GET declares an HTTP GET request
     * @Path("user") annotation on the userId parameter marks it as a
     * replacement for the {user} placeholder in the @GET path
     */
    @GET("/users/{user}")
    Call<GitHubUser> getUser(@Path("user") String userId);



    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);


}
