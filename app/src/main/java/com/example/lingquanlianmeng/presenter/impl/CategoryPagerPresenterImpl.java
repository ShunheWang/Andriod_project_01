package com.example.lingquanlianmeng.presenter.impl;

import com.example.lingquanlianmeng.model.Api;
import com.example.lingquanlianmeng.model.bean.HomePagerContent;
import com.example.lingquanlianmeng.presenter.ICategoryPagerPresenter;
import com.example.lingquanlianmeng.utils.LogUtils;
import com.example.lingquanlianmeng.utils.RetrofitManager;
import com.example.lingquanlianmeng.utils.UrlUtils;
import com.example.lingquanlianmeng.view.ICategoryPagerCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryPagerPresenterImpl implements ICategoryPagerPresenter {

    private List<ICategoryPagerCallback> callbacks = new ArrayList<>();
    private Map<Integer,Integer> pagesInfo = new HashMap<>();

    public static final int DEFAULT_PAGE = 1;
    private Integer mCurrentPageId;

    @Override
    public void getContentByCategoryId(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {
                callback.onLoading();
            }
        }
        //根据分类id去加载内容
        Integer targetPage = pagesInfo.get(categoryId);
        if (targetPage == null) {
            targetPage = DEFAULT_PAGE;
//            LogUtils.d(this, "category id --> "+ categoryId);
//            LogUtils.d(this, "target page --> "+ targetPage);
            pagesInfo.put(categoryId, targetPage);
        }
        Call<HomePagerContent> task = createTask(categoryId, targetPage);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
//                LogUtils.d(CategoryPagerPresenterImpl.this, "code --> " + code);
                if(code == HttpURLConnection.HTTP_OK){
                    HomePagerContent pagerContent = response.body();
//                    LogUtils.d(CategoryPagerPresenterImpl.this, "page content --> " + pagerContent);
                    //把数据给到UI更新
                    handleHomePagerContentResult(categoryId ,pagerContent);
                }else{
                    handleNetworkError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {

            }
        });

    }

    private Call<HomePagerContent> createTask(int categoryId, Integer targetPage) {
        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, targetPage);
//        LogUtils.d(this, "home pager url --> "+ homePagerUrl);
        // 加载分类数据
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        return api.getHomePagerContent(homePagerUrl);
    }

    private void handleNetworkError(int categoryId) {
        for(ICategoryPagerCallback callback : callbacks){
            if (callback.getCategoryId() == categoryId) {
                callback.onNetworkError();
            }
        }
    }


    /**
     * inform ui to update content data
     * @param pagerContent
     */
    private void handleHomePagerContentResult(int categoryId, HomePagerContent pagerContent) {
        List<HomePagerContent.DataBean> dataBean = pagerContent.getData();
        for(ICategoryPagerCallback callback : callbacks){
            if (callback.getCategoryId() == categoryId) {
                if (pagerContent == null || pagerContent.getData().size() == 0) {
                    callback.onEmpty();
                }else{
                    List<HomePagerContent.DataBean> looperData = dataBean.subList(dataBean.size() - 5, dataBean.size());
                    //load looper data
                    callback.onLooperListLoaded(looperData);
                    //load item view data
                    callback.onContentLoaded(dataBean);
                }
            }
        }
    }

    /**
     * load more data logic
     * @param categoryId
     */
    @Override
    public void loaderMore(int categoryId) {
        LogUtils.d(CategoryPagerPresenterImpl.this,"CategoryPagerPresenterImpl");
        LogUtils.d(CategoryPagerPresenterImpl.this," categoryId --> " + categoryId);
        //get current page
        mCurrentPageId = pagesInfo.get(categoryId);
        //page++
        if (mCurrentPageId == null) {
            mCurrentPageId = 1;
        }
        mCurrentPageId++;
        LogUtils.d(CategoryPagerPresenterImpl.this," mCurrentPageId --> " + mCurrentPageId);
        //load more data
        Call<HomePagerContent> task = createTask(categoryId, mCurrentPageId);
        //handle extra data result
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                //handle to result
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    HomePagerContent result = response.body();
                    handleLoadMoreResult(categoryId,result);
                }else{
                    handleLoadMoreError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                handleLoadMoreError(categoryId);
            }
        });
    }

    private void handleLoadMoreResult(int categoryId, HomePagerContent result) {
        for(ICategoryPagerCallback callback : callbacks){
            if (callback.getCategoryId() == categoryId) {
                if (result == null || result.getData().size() == 0) {
                    callback.onLoaderMoreEmpty();
                }else{
                    pagesInfo.put(categoryId, mCurrentPageId);
                    LogUtils.d(CategoryPagerPresenterImpl.this,"size --> " + result.getData().size());
                    callback.onLoaderMoreLoaded(result.getData());
                }
            }
        }
    }

    private void handleLoadMoreError(int categoryId) {
        mCurrentPageId--;
        pagesInfo.put(categoryId,mCurrentPageId);
        for(ICategoryPagerCallback callback : callbacks){
            if (callback.getCategoryId() == categoryId) {
                callback.onLoaderMoreError();
            }
        }
    }

    @Override
    public void reload(int categoryId) {

    }

    @Override
    public void registerCallback(ICategoryPagerCallback callback) {
        if(!this.callbacks.contains(callback)){
            this.callbacks.add(callback);
        }
    }

    @Override
    public void unregisterCallback(ICategoryPagerCallback callback) {
        callbacks.remove(callback);
    }
}
