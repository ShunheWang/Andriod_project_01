package com.example.lingquanlianmeng.view;

import com.example.lingquanlianmeng.base.IBaseCallback;
import com.example.lingquanlianmeng.model.bean.HomePagerContent;

import java.util.List;

public interface ICategoryPagerCallback extends IBaseCallback {

    /**
     * load category content
     */
    void onContentLoaded(List<HomePagerContent.DataBean> categoryContents);

    void onLoaderMoreError();

    void onLoaderMoreEmpty();

    void onLoaderMoreLoaded(List<HomePagerContent.DataBean> categoryContents);

    int getCategoryId();

    /**
     * looper load
     * @param categoryContents
     */
    void onLooperListLoaded(List<HomePagerContent.DataBean> categoryContents);
}
