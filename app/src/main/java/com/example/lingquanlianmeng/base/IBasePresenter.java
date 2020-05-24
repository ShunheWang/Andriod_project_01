package com.example.lingquanlianmeng.base;

public interface IBasePresenter<T> {

    void registerCallback(T callback);

    void unregisterCallback(T callback);
}
