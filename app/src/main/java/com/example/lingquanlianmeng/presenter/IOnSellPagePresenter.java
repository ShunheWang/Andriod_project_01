package com.example.lingquanlianmeng.presenter;

import com.example.lingquanlianmeng.base.IBasePresenter;
import com.example.lingquanlianmeng.view.IOnSellPageCallback;

public interface IOnSellPagePresenter extends IBasePresenter<IOnSellPageCallback> {

    /**
     * load sell content
     */
    void getOnSellContent();

    /**
     * reload content
     *
     * @call network error
     */
    void reload();


    /**
     * load more content
     */
    void loadMore();
}
