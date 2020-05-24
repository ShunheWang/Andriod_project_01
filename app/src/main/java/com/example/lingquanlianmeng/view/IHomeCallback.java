package com.example.lingquanlianmeng.view;

import com.example.lingquanlianmeng.base.IBaseCallback;
import com.example.lingquanlianmeng.model.bean.Categories;

public interface IHomeCallback extends IBaseCallback {

    void onCategoriesLoaded(Categories categories);

}
