package com.example.lingquanlianmeng.presenter.impl;

import com.example.lingquanlianmeng.model.Api;
import com.example.lingquanlianmeng.model.bean.TicketParams;
import com.example.lingquanlianmeng.model.bean.TicketResult;
import com.example.lingquanlianmeng.presenter.ITicketPresenter;
import com.example.lingquanlianmeng.utils.RetrofitManager;
import com.example.lingquanlianmeng.utils.UrlUtils;
import com.example.lingquanlianmeng.view.ITicketPagerCallback;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TicketPresenterImpl implements ITicketPresenter {
    private ITicketPagerCallback mViewCallback;
    private String mCover = null;
    private TicketResult mTicketResult;

    enum LoadState{
        LOADING,SUCCESS,ERROR,NONE
    }

    private LoadState mCurrentState = LoadState.NONE;

    @Override
    public void getTicket(String title, String url, String cover) {
        onTicketLaoding();
        this.mCover = cover;
        // 加载分类数据
        String targetUrl = UrlUtils.getTicketUrl(url);
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        TicketParams ticketParams = new TicketParams(targetUrl, title);
        Call<TicketResult> task = api.getTicket(ticketParams);
        task.enqueue(new Callback<TicketResult>() {
            @Override
            public void onResponse(Call<TicketResult> call, Response<TicketResult> response) {
                int code = response.code();
//                LogUtils.d(TicketPresenterImpl.this,"result code: --> " + code);
                if (code == HttpsURLConnection.HTTP_OK) {
                    mTicketResult = response.body();
//                    LogUtils.d(TicketPresenterImpl.this,"result : --> " + ticketResult.toString());
                    //update UI
                    onTicketLoadedSuccess();
                }else{
                    //response fail
                    onLoadedTicketError();
                }
            }

            @Override
            public void onFailure(Call<TicketResult> call, Throwable t) {
                onLoadedTicketError();
                mCurrentState = LoadState.ERROR;
            }
        });
    }

    private void onTicketLoadedSuccess() {
        if (mViewCallback != null) {
            mViewCallback.onTicketLoaded(mCover, mTicketResult);
        }else{
            mCurrentState = LoadState.SUCCESS;
        }
    }

    private void onLoadedTicketError() {
        if (mViewCallback != null) {
            mViewCallback.onNetworkError();
        }else{
            mCurrentState = LoadState.ERROR;
        }
    }

    @Override
    public void registerCallback(ITicketPagerCallback callback) {
        if (mCurrentState != LoadState.NONE) {
            //if current state is not null, that means state was changed
            //update UI
            if (mCurrentState == LoadState.SUCCESS) {
                onTicketLoadedSuccess();
            }else if (mCurrentState == LoadState.ERROR){
                onLoadedTicketError();
            }else if (mCurrentState == LoadState.LOADING){
                onTicketLaoding();
            }
        }
        this.mViewCallback = callback;
    }

    @Override
    public void unregisterCallback(ITicketPagerCallback callback) {
        this.mViewCallback = null;
    }

    private void onTicketLaoding() {
        if (mViewCallback != null) {
            mViewCallback.onLoading();
        }else{
            mCurrentState = LoadState.LOADING;
        }
    }
}
