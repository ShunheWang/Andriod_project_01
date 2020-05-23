package com.example.lingquanlianmeng.ui.fragment;

import android.util.Log;

import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.base.BaseFragment;
import com.example.lingquanlianmeng.model.bean.Categories;
import com.example.lingquanlianmeng.presenter.impl.HomePresenterImpl;
import com.example.lingquanlianmeng.view.IHomeCallback;

public class HomeFragment extends BaseFragment implements IHomeCallback {

    private HomePresenterImpl mHomePresenter;

    @Override
    protected int getRootViewResId() {
        Log.d("BaseFragment", "HomeFragment");
        return R.layout.fragment_home;
    }

    @Override
    protected void initPresenter() {
        //创建presenter
        mHomePresenter = new HomePresenterImpl();
        mHomePresenter.registerCallback(this);
    }

    @Override
    protected void loadData() {
        //加载数据
        mHomePresenter.getCategories();
    }

    @Override
    public void onCategoriesLoaded(Categories categories) {
        // 加载的数据从这里回来
    }

    @Override
    protected void release() {
        if (mHomePresenter != null) {
            mHomePresenter.unregisterCallback(this);
        }
    }
}
