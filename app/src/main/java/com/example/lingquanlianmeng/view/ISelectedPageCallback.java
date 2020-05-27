package com.example.lingquanlianmeng.view;

import com.example.lingquanlianmeng.base.IBaseCallback;
import com.example.lingquanlianmeng.model.bean.SelectedContent;
import com.example.lingquanlianmeng.model.bean.SelectedPageCategories;

public interface ISelectedPageCallback extends IBaseCallback {

    /**
     * UI get selected page data
     * @param categories
     */
    void onCategoriesLoaded(SelectedPageCategories categories);

    void onContentLoaded(SelectedContent content);
}
