package com.example.lingquanlianmeng.presenter.impl;

import com.example.lingquanlianmeng.model.Api;
import com.example.lingquanlianmeng.model.bean.OnSellContent;
import com.example.lingquanlianmeng.presenter.IOnSellPagePresenter;
import com.example.lingquanlianmeng.utils.RetrofitManager;
import com.example.lingquanlianmeng.utils.UrlUtils;
import com.example.lingquanlianmeng.view.IOnSellPageCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OnSellPagePresenterImpl implements IOnSellPagePresenter {

    public static final int DEFAULT_PAGE = 1;
    private int mCurrentPage = DEFAULT_PAGE;
    private boolean mIsLoading =false;
    private IOnSellPageCallback mCallback;
    private final Api mApi;

    public OnSellPagePresenterImpl(){
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        mApi = retrofit.create(Api.class);
    }

    @Override
    public void getOnSellContent() {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        //UI state set onLoading
        if (mCallback != null) {
            mCallback.onLoading();
        }
        String targetUrl = UrlUtils.getOnSellPageUrl(mCurrentPage);
        Call<OnSellContent> task = mApi.getOnSellPageContent(targetUrl);
        task.enqueue(new Callback<OnSellContent>() {
            @Override
            public void onResponse(Call<OnSellContent> call, Response<OnSellContent> response) {
                mIsLoading = false;
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    OnSellContent result = response.body();
                    handleSuccess(result);
                }else{
                    handleError();
                }
            }

            @Override
            public void onFailure(Call<OnSellContent> call, Throwable t) {
                handleError();
            }
        });
    }

    private void handleSuccess(OnSellContent result) {
        if (mCallback != null) {
            try {
                if (isEmpty(result)) {
                    handleEmpty();
                }else{
                    mCallback.onContentLoadedSuccess(result);
                }
            }catch (Exception e){
                e.printStackTrace();
                handleEmpty();
            }
        }
    }

    public boolean isEmpty(OnSellContent content){
        int size = content.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size();
        return size == 0;
    }

    private void handleError() {
        mIsLoading = false;
        if (mCallback != null) {
            mCallback.onNetworkError();
        }
    }

    private void handleEmpty() {
        if (mCallback != null) {
            mCallback.onEmpty();
        }
    }

    @Override
    public void reload() {
        this.getOnSellContent();
    }

    /**
     * load more
     */
    @Override
    public void loadMore() {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        mCurrentPage++;
        String targetUrl = UrlUtils.getOnSellPageUrl(mCurrentPage);
        Call<OnSellContent> task = mApi.getOnSellPageContent(targetUrl);
        task.enqueue(new Callback<OnSellContent>() {
            @Override
            public void onResponse(Call<OnSellContent> call, Response<OnSellContent> response) {
                mIsLoading = false;
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    OnSellContent result = response.body();
                    handleLoadMoreSuccess(result);
                }else{
                    handleLoadMoreError();
                }
            }

            @Override
            public void onFailure(Call<OnSellContent> call, Throwable t) {
                handleLoadMoreError();
            }
        });

    }

    private void handleLoadMoreError() {
        mIsLoading = false;
        this.mCurrentPage--;
        mCallback.onMoreLoadedError();
    }

    private void handleLoadMoreSuccess(OnSellContent result) {
        if (isEmpty(result)) {
            mCallback.onMoreLoaded(result);
        }else{
            mCallback.onLoadedMoreEmpty();
        }
    }

    @Override
    public void registerCallback(IOnSellPageCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void unregisterCallback(IOnSellPageCallback callback) {
        this.mCallback = null;
    }
}
