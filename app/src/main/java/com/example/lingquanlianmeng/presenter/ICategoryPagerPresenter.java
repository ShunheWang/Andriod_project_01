package com.example.lingquanlianmeng.presenter;

import com.example.lingquanlianmeng.base.IBasePresenter;
import com.example.lingquanlianmeng.view.ICategoryPagerCallback;

public interface ICategoryPagerPresenter extends IBasePresenter<ICategoryPagerCallback> {

    /**
     * get category contend by category id
     * @param categoryId
     */
    void getContentByCategoryId(int categoryId);

    void loaderMore(int categoryId);

    void reload(int categoryId);
}
