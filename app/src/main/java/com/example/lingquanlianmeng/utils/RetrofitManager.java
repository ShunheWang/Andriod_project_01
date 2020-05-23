package com.example.lingquanlianmeng.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static final RetrofitManager outInstance = new RetrofitManager();
    private final Retrofit mRetrofit;

    public static RetrofitManager getInstance(){
        return outInstance;
    }

    private RetrofitManager(){
        // 创建retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit(){
        return mRetrofit;
    }
}
