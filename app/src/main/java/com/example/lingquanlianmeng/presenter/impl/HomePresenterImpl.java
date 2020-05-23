package com.example.lingquanlianmeng.presenter.impl;

import com.example.lingquanlianmeng.model.Api;
import com.example.lingquanlianmeng.model.bean.Categories;
import com.example.lingquanlianmeng.presenter.IHomePresenter;
import com.example.lingquanlianmeng.utils.LogUtils;
import com.example.lingquanlianmeng.utils.RetrofitManager;
import com.example.lingquanlianmeng.view.IHomeCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePresenterImpl implements IHomePresenter {

    private IHomeCallback mIHomeCallBack;

    @Override
    public void getCategories() {
        // 加载分类数据
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Call<Categories> task = api.getCategories();
        task.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                //数据结果
                int code = response.code();
                LogUtils.d(HomePresenterImpl.this, "result code is --> " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    //请求成功
                    Categories categories = response.body();
                    LogUtils.d(HomePresenterImpl.this,categories.toString());
                    if (mIHomeCallBack != null) {
                        mIHomeCallBack.onCategoriesLoaded(categories);
                    }
                }else{
                    //请求失败
                    LogUtils.d(HomePresenterImpl.this,"失败");
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {

            }
        });
    }

    @Override
    public void registerCallback(IHomeCallback iHomeCallback) {
        this.mIHomeCallBack =iHomeCallback;
    }

    @Override
    public void unregisterCallback(IHomeCallback iHomeCallback) {
        if (this.mIHomeCallBack != null) {
            this.mIHomeCallBack = null;
        }
    }
}
