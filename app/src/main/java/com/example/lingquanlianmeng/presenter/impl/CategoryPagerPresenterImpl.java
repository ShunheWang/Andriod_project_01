package com.example.lingquanlianmeng.presenter.impl;

import com.example.lingquanlianmeng.model.Api;
import com.example.lingquanlianmeng.model.bean.HomePagerContent;
import com.example.lingquanlianmeng.presenter.ICategoryPagerPresenter;
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

    private CategoryPagerPresenterImpl(){

    }

    private static ICategoryPagerPresenter sInstance = null;

    public static ICategoryPagerPresenter getInstance(){
        if (sInstance == null) {
            sInstance = new CategoryPagerPresenterImpl();
        }
        return sInstance;
    }


    @Override
    public void getContentByCategoryId(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {
                callback.onLoading();
            }
        }
        //根据分类id去加载内容
        // 加载分类数据
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Integer targetPage = pagesInfo.get(categoryId);
        if (targetPage == null) {
            targetPage = DEFAULT_PAGE;
//            LogUtils.d(this, "category id --> "+ categoryId);
//            LogUtils.d(this, "target page --> "+ targetPage);
            pagesInfo.put(categoryId, targetPage);
        }
        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, targetPage);
//        LogUtils.d(this, "home pager url --> "+ homePagerUrl);
        Call<HomePagerContent> task = api.getHomePagerContent(homePagerUrl);
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

    @Override
    public void loaderMore(int categoryId) {

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
