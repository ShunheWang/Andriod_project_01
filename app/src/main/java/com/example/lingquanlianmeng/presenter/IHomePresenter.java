package com.example.lingquanlianmeng.presenter;

import com.example.lingquanlianmeng.base.IBasePresenter;
import com.example.lingquanlianmeng.view.IHomeCallback;

public interface IHomePresenter extends IBasePresenter<IHomeCallback> {

    /**
    set categories data
     */
    void getCategories();


}
