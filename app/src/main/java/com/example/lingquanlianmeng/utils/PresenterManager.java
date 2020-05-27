package com.example.lingquanlianmeng.utils;

import com.example.lingquanlianmeng.presenter.ICategoryPagerPresenter;
import com.example.lingquanlianmeng.presenter.IHomePresenter;
import com.example.lingquanlianmeng.presenter.ITicketPresenter;
import com.example.lingquanlianmeng.presenter.impl.CategoryPagerPresenterImpl;
import com.example.lingquanlianmeng.presenter.impl.HomePresenterImpl;
import com.example.lingquanlianmeng.presenter.impl.TicketPresenterImpl;

public class PresenterManager {
    private static final PresenterManager outInstance = new PresenterManager();
    private final ICategoryPagerPresenter mCategoryPagerPresenter;
    private final IHomePresenter mHomePresenter;
    private final ITicketPresenter mTicketPresenter;

    private PresenterManager(){
        mCategoryPagerPresenter = new CategoryPagerPresenterImpl();
        mHomePresenter = new HomePresenterImpl();
        mTicketPresenter = new TicketPresenterImpl();
    }

    public static PresenterManager getInstance(){
        return outInstance;
    }

    public ICategoryPagerPresenter getCategoryPagerPresenter() {
        return mCategoryPagerPresenter;
    }

    public IHomePresenter getHomePresenter() {
        return mHomePresenter;
    }

    public ITicketPresenter getTicketPresenter() {
        return mTicketPresenter;
    }




}
