package com.example.lingquanlianmeng.model;

import com.example.lingquanlianmeng.model.bean.Categories;
import com.example.lingquanlianmeng.model.bean.HomePagerContent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {

    @GET("discovery/categories")
    Call<Categories> getCategories();

    @GET("")
    Call<HomePagerContent> getHomePagerContent(@Url String url);
}
