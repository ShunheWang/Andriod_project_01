package com.example.lingquanlianmeng.presenter;

import com.example.lingquanlianmeng.base.IBasePresenter;
import com.example.lingquanlianmeng.model.bean.SelectedPageCategories;
import com.example.lingquanlianmeng.view.ISelectedPageCallback;

public interface ISelectedPresenter extends IBasePresenter<ISelectedPageCallback> {

    /**
     * get categories for select page
     */
    void getCategories();

    /**
     * get relative selected content
     * @param item
     */
    void getSelectContentByCategory(SelectedPageCategories.DataBean item);


    /**
     * reload category content
     */
    void reloadCategory();
}
