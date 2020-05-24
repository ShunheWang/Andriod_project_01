package com.example.lingquanlianmeng.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.base.BaseFragment;
import com.example.lingquanlianmeng.model.bean.Categories;
import com.example.lingquanlianmeng.model.bean.HomePagerContent;
import com.example.lingquanlianmeng.presenter.ICategoryPagerPresenter;
import com.example.lingquanlianmeng.presenter.impl.CategoryPagerPresenterImpl;
import com.example.lingquanlianmeng.ui.adapter.HomePagerContentAdpater;
import com.example.lingquanlianmeng.ui.adapter.LooperPagerAdapter;
import com.example.lingquanlianmeng.utils.Constants;
import com.example.lingquanlianmeng.utils.LogUtils;
import com.example.lingquanlianmeng.view.ICategoryPagerCallback;

import java.util.List;

import butterknife.BindView;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    private ICategoryPagerPresenter mCategoryPagerPresenter;
    private int mMaterialId;
    private HomePagerContentAdpater mContentAdpater;

    public static HomePagerFragment newInstance(Categories.DataBean category){
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE,category.getTitle());
        bundle.putInt(Constants.KEY_HOME_MATERIAL_ID,category.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @BindView(R.id.home_pager_content_list)
    public RecyclerView mContentList;

    @BindView(R.id.looper_pager)
    public ViewPager mLooperPager;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View rootView) {
        //set layout manager
        mContentList.setLayoutManager(new LinearLayoutManager(getContext()));
        //set gap between item view
        mContentList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 8;
                outRect.bottom = 8;
            }
        });
        //create recycle view adapter
        mContentAdpater = new HomePagerContentAdpater();
        //set recycle view adapter
        mContentList.setAdapter(mContentAdpater);

        //create looper pager adapter
        LooperPagerAdapter looperPagerAdapter = new LooperPagerAdapter();
        //set looper pager adapter
        mLooperPager.setAdapter(looperPagerAdapter);
//        setupState(State.SUCCESS);
    }

    @Override
    protected void initPresenter() {
        mCategoryPagerPresenter = CategoryPagerPresenterImpl.getInstance();
        mCategoryPagerPresenter.registerCallback(this);
    }

    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        mMaterialId = arguments.getInt(Constants.KEY_HOME_MATERIAL_ID);

        //加载数据
        LogUtils.d(this, "Title --> "+ title + "materialId --> "+ mMaterialId);
        if (mCategoryPagerPresenter != null) {
            mCategoryPagerPresenter.getContentByCategoryId(mMaterialId);
        }
    }

    @Override
    public void onContentLoaded(List<HomePagerContent.DataBean> categoryContents) {
        //data load back in there
        mContentAdpater.setData(categoryContents);
        setupState(State.SUCCESS);
    }

    @Override
    public void onLoading() {
        setupState(State.LOADING);
    }

    /**
     * network error
     */
    @Override
    public void onNetworkError() {
        setupState(State.ERROR);

    }

    @Override
    public int getCategoryId() {
        return mMaterialId;
    }

    @Override
    public void onEmpty() {
        setupState(State.EMPTY);
    }

    @Override
    public void onLoaderMoreError() {

    }

    @Override
    public void onLoaderMoreEmpty() {

    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean> categoryContents) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> categoryContents) {

    }

    @Override
    protected void release() {
        if (mCategoryPagerPresenter !=null) {
            mCategoryPagerPresenter.unregisterCallback(this);
        }
    }
}
