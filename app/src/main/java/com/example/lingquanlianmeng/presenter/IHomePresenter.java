package com.example.lingquanlianmeng.presenter;

import com.example.lingquanlianmeng.view.IHomeCallback;

public interface IHomePresenter {

    /**
    set categories data
     */
    void getCategories();

    void registerCallback(IHomeCallback iHomeCallback);

    void unregisterCallback(IHomeCallback iHomeCallback);
}
