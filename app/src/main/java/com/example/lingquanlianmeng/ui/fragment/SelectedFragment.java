package com.example.lingquanlianmeng.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.base.BaseFragment;
import com.example.lingquanlianmeng.model.bean.SelectedContent;
import com.example.lingquanlianmeng.model.bean.SelectedPageCategories;
import com.example.lingquanlianmeng.presenter.ISelectedPresenter;
import com.example.lingquanlianmeng.presenter.ITicketPresenter;
import com.example.lingquanlianmeng.ui.activity.TicketActivity;
import com.example.lingquanlianmeng.ui.adapter.SelectedPageLeftAdapter;
import com.example.lingquanlianmeng.ui.adapter.SelectedPageRightContentAdapter;
import com.example.lingquanlianmeng.utils.PresenterManager;
import com.example.lingquanlianmeng.utils.SizeUtils;
import com.example.lingquanlianmeng.view.ISelectedPageCallback;

import java.util.List;

import butterknife.BindView;

public class SelectedFragment extends BaseFragment implements ISelectedPageCallback, SelectedPageLeftAdapter.OnLeftItemClickListener, SelectedPageRightContentAdapter.OnSelectedPageContentClickListener {

    private ISelectedPresenter mSelectedPagePresenter;

    @BindView(R.id.left_category_list)
    public RecyclerView mLeftCategoryList;

    @BindView(R.id.right_category_list)
    public RecyclerView mRightCategoryList;

    private SelectedPageLeftAdapter mLeftAdapter;
    private SelectedPageRightContentAdapter mSelectContentAdpater;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_selected;
    }

    @Override
    protected void initView(View rootView) {
        setupState(State.SUCCESS);
        mLeftCategoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        mLeftAdapter = new SelectedPageLeftAdapter();
        mLeftCategoryList.setAdapter(mLeftAdapter);

        mRightCategoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSelectContentAdpater = new SelectedPageRightContentAdapter();
        mRightCategoryList.setAdapter(mSelectContentAdpater);
        mRightCategoryList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = SizeUtils.dip2px(getContext(),4);
                outRect.bottom = SizeUtils.dip2px(getContext(),4);
                outRect.left = SizeUtils.dip2px(getContext(),3);
                outRect.right = SizeUtils.dip2px(getContext(),3);
            }
        });
    }

    @Override
    protected void initPresenter() {
        mSelectedPagePresenter = PresenterManager.getInstance().getSelectedPagePresenter();
        mSelectedPagePresenter.registerCallback(this);
        mSelectedPagePresenter.getCategories();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLeftAdapter.setOnLeftItemClickListener(this);
        mSelectContentAdpater.setOnSelectedPageContentClickListener(this);
    }

    @Override
    public void onCategoriesLoaded(SelectedPageCategories categories) {
        setupState(State.SUCCESS);
        mLeftAdapter.setData(categories);
        //data back
//        LogUtils.d(SelectedFragment.this, "categories --> " + categories);
        List<SelectedPageCategories.DataBean> data = categories.getData();
        mSelectedPagePresenter.getSelectContentByCategory(data.get(0));
    }

    @Override
    public void onContentLoaded(SelectedContent content) {
//        LogUtils.d(SelectedFragment.this, "content --> " + content);
        mSelectContentAdpater.setData(content);
        mRightCategoryList.scrollToPosition(0);
    }

    @Override
    public void onNetworkError() {
        setupState(State.ERROR);
    }

    @Override
    protected void onRetryClick() {
        //Retry
        if (mSelectedPagePresenter != null) {
            mSelectedPagePresenter.reloadCategory();
        }
    }

    @Override
    public void onLoading() {
        setupState(State.LOADING);
    }

    @Override
    public void onEmpty() {

    }

    @Override
    protected void release() {
        super.release();
        if (mSelectedPagePresenter != null) {
            mSelectedPagePresenter.unregisterCallback(this);
        }
    }

    @Override
    public void onLeftItemClick(SelectedPageCategories.DataBean item) {
        //left list click
        mSelectedPagePresenter.getSelectContentByCategory(item);
    }

    @Override
    public void onContentClickClick(SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean item) {
        //handle data
        String title = item.getTitle();
        String url = item.getClick_url();
        String cover = item.getPict_url();
        ITicketPresenter ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        ticketPresenter.getTicket(title,url,cover);
        startActivity(new Intent(getContext(), TicketActivity.class));

    }
}
