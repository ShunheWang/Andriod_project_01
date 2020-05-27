package com.example.lingquanlianmeng.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.base.BaseFragment;
import com.example.lingquanlianmeng.model.bean.Categories;
import com.example.lingquanlianmeng.presenter.IHomePresenter;
import com.example.lingquanlianmeng.ui.adapter.HomePagerAdapter;
import com.example.lingquanlianmeng.utils.LogUtils;
import com.example.lingquanlianmeng.utils.PresenterManager;
import com.example.lingquanlianmeng.view.IHomeCallback;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements IHomeCallback {

    @BindView(R.id.home_indicator)
    public TabLayout mTableLayout;
    @BindView(R.id.home_pager)
    public ViewPager mHomePager;

    private IHomePresenter mHomePresenter;
    private HomePagerAdapter mHomePagerAdapter;

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_home_fragment_layout,container,false);
    }

    @Override
    protected int getRootViewResId() {
        Log.d("BaseFragment", "HomeFragment");
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mTableLayout.setupWithViewPager(mHomePager);
        //给ViewPager设置adapter
        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        //设置适配器
        mHomePager.setAdapter(mHomePagerAdapter);
    }

    @Override
    protected void initPresenter() {
        //创建presenter
        mHomePresenter = PresenterManager.getInstance().getHomePresenter();
        mHomePresenter.registerCallback(this);
    }

    @Override
    protected void loadData() {
        //加载数据
        mHomePresenter.getCategories();
    }

    @Override
    public void onCategoriesLoaded(Categories categories) {
        setupState(State.SUCCESS);
        // 加载的数据从这里回来
        if (mHomePagerAdapter != null) {
            mHomePagerAdapter.setCategories(categories);
        }
    }

    @Override
    public void onNetworkError() {
        setupState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setupState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setupState(State.EMPTY);
    }

    @Override
    protected void release() {
        if (mHomePresenter != null) {
            mHomePresenter.unregisterCallback(this);
        }
    }

    /**
     * Network error, reload the load
     */
    @Override
    protected void onRetryClick() {
        if (mHomePresenter != null) {
            mHomePresenter.getCategories();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d(this,"on create view... ");
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onDestroy() {
        LogUtils.d(this,"on destroy view... ");
        super.onDestroy();
    }
}
