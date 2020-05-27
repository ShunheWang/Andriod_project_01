package com.example.lingquanlianmeng.presenter.impl;

import com.example.lingquanlianmeng.model.Api;
import com.example.lingquanlianmeng.model.bean.SelectedContent;
import com.example.lingquanlianmeng.model.bean.SelectedPageCategories;
import com.example.lingquanlianmeng.presenter.ISelectedPresenter;
import com.example.lingquanlianmeng.utils.LogUtils;
import com.example.lingquanlianmeng.utils.RetrofitManager;
import com.example.lingquanlianmeng.utils.UrlUtils;
import com.example.lingquanlianmeng.view.ISelectedPageCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SelectedPagePresenterImpl implements ISelectedPresenter {
    private ISelectedPageCallback mViewCallback;
    private Api mApi;

    public SelectedPagePresenterImpl(){
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        mApi = retrofit.create(Api.class);
    }

    @Override
    public void getCategories() {
        if (mViewCallback != null) {
            mViewCallback.onLoading();
        }
        Call<SelectedPageCategories> task = mApi.getSelectedPageCategories();
        task.enqueue(new Callback<SelectedPageCategories>() {
            @Override
            public void onResponse(Call<SelectedPageCategories> call, Response<SelectedPageCategories> response) {
                int code = response.code();
                LogUtils.d(SelectedPagePresenterImpl.this, "result code is --> " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    //请求成功
                    SelectedPageCategories result = response.body();
                    //LogUtils.d(HomePresenterImpl.this,categories.toString());
                    if (mViewCallback != null) {
                        mViewCallback.onCategoriesLoaded(result);
                        //categories = null;
//                        if (result == null || result.getData().size() == 0) {
//                            mViewCallback.onEmpty();
//                        }else{
//                            mViewCallback.onCategoriesLoaded(result);
//                        }
                    }else{
                        handleNetworkError();
                    }
                }
            }

            @Override
            public void onFailure(Call<SelectedPageCategories> call, Throwable t) {
                handleNetworkError();
            }
        });
    }

    private void handleNetworkError() {
        if (mViewCallback != null) {
            mViewCallback.onNetworkError();
        }
    }

    @Override
    public void getSelectContentByCategory(SelectedPageCategories.DataBean item) {
        String selectedPageContentUrl = UrlUtils.getSelectedPageContentUrl(item.getFavorites_id());
        Call<SelectedContent> task = mApi.getSelectedPageContent(selectedPageContentUrl);
        task.enqueue(new Callback<SelectedContent>() {
            @Override
            public void onResponse(Call<SelectedContent> call, Response<SelectedContent> response) {
                int code = response.code();
//                LogUtils.d(SelectedPagePresenterImpl.this, "SelectContentByCategory code is --> " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    //请求成功
                    SelectedContent result = response.body();
                    //LogUtils.d(HomePresenterImpl.this,categories.toString());
                    if (mViewCallback != null) {
                        mViewCallback.onContentLoaded(result);
                        //categories = null;
//                        if (result == null || result.getData().size() == 0) {
//                            mViewCallback.onEmpty();
//                        }else{
//                            mViewCallback.onCategoriesLoaded(result);
//                        }
                    }else{
                        handleNetworkError();
                    }
                }
            }

            @Override
            public void onFailure(Call<SelectedContent> call, Throwable t) {
                handleNetworkError();
            }
        });

    }

    @Override
    public void reloadCategory() {
        this.getCategories();
    }

    @Override
    public void registerCallback(ISelectedPageCallback callback) {
        this.mViewCallback = callback;
    }

    @Override
    public void unregisterCallback(ISelectedPageCallback callback) {
        this.mViewCallback = null;
    }
}
