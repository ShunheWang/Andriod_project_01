package com.example.lingquanlianmeng.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.base.BaseFragment;
import com.example.lingquanlianmeng.model.bean.Categories;
import com.example.lingquanlianmeng.model.bean.HomePagerContent;
import com.example.lingquanlianmeng.presenter.ICategoryPagerPresenter;
import com.example.lingquanlianmeng.presenter.ITicketPresenter;
import com.example.lingquanlianmeng.ui.activity.TicketActivity;
import com.example.lingquanlianmeng.ui.adapter.HomePagerContentAdpater;
import com.example.lingquanlianmeng.ui.adapter.LooperPagerAdapter;
import com.example.lingquanlianmeng.ui.custom.AutoLooperViewPager;
import com.example.lingquanlianmeng.ui.custom.TbNextScrolView;
import com.example.lingquanlianmeng.utils.Constants;
import com.example.lingquanlianmeng.utils.LogUtils;
import com.example.lingquanlianmeng.utils.PresenterManager;
import com.example.lingquanlianmeng.utils.SizeUtils;
import com.example.lingquanlianmeng.view.ICategoryPagerCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback, HomePagerContentAdpater.OnListenerItemClickListener, LooperPagerAdapter.OnLooperPageItemClickListener {

    private ICategoryPagerPresenter mCategoryPagerPresenter;
    private int mMaterialId;
    private HomePagerContentAdpater mContentAdpater;
    private LooperPagerAdapter mLooperPagerAdapter;

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
    public AutoLooperViewPager mLooperPager;

    @BindView(R.id.home_pager_title)
    public TextView mCurrentTitleTv;

    @BindView(R.id.looper_point_container)
    public LinearLayout mlooperPointContainer;

    @BindView(R.id.home_pager_nested_scroller)
    public TbNextScrolView mHomePagerNestedView;

    @BindView(R.id.home_pager_header_container)
    public LinearLayout mHomePagerHeaderContainer;

    @BindView(R.id.home_pager_refresh)
    public TwinklingRefreshLayout mhomePagerRefresh;

    @BindView(R.id.home_pager_parent)
    public LinearLayout mHomePagerParent;



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
        mLooperPagerAdapter = new LooperPagerAdapter();
        //set looper pager adapter
        mLooperPager.setAdapter(mLooperPagerAdapter);
        //set refresh
        mhomePagerRefresh.setEnableRefresh(false);
        mhomePagerRefresh.setEnableLoadmore(true);
//        setupState(State.SUCCESS);
    }

    @Override
    protected void initListener() {
        //view change observer
        mHomePagerParent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int homePagerContainerHeight = mHomePagerHeaderContainer.getMeasuredHeight();
                mHomePagerNestedView.setHeaderHeight(homePagerContainerHeight);
                int measuredHeight = mHomePagerParent.getMeasuredHeight();
                //LogUtils.d(HomePagerFragment.this, "measuredHeight: --> " + measuredHeight);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mContentList.getLayoutParams();
                layoutParams.height = measuredHeight;
                mContentList.setLayoutParams(layoutParams);
                if (measuredHeight != 0) {
                    mHomePagerParent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

        //looper listener
        mLooperPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mLooperPagerAdapter.getDataBeanSize() != 0) {
                    int targetPosition = position % mLooperPagerAdapter.getDataBeanSize();
                    //switch point
                    updateLooperIndicator(targetPosition);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //refresh listener
        mhomePagerRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                LogUtils.d(HomePagerFragment.this, "load more ...");
                //load more data
                if (mCategoryPagerPresenter != null) {
                    mCategoryPagerPresenter.loaderMore(mMaterialId);
                }
            }
        });

        mContentAdpater.setOnListenerItemClickListener(this);
        mLooperPagerAdapter.setOnLooperPageItemClickListener(this);
    }

    private void updateLooperIndicator(int targetPosition) {
        for (int i = 0; i < mlooperPointContainer.getChildCount(); i++) {
            View point = mlooperPointContainer.getChildAt(i);
            if(i == targetPosition){
                point.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            }else{
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
        }
    }

    @Override
    protected void initPresenter() {
        mCategoryPagerPresenter = PresenterManager.getInstance().getCategoryPagerPresenter();
        mCategoryPagerPresenter.registerCallback(this);
    }

    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        mMaterialId = arguments.getInt(Constants.KEY_HOME_MATERIAL_ID);

        //load data
//        LogUtils.d(this, "Title --> "+ title + "materialId --> "+ mMaterialId);
        if (mCategoryPagerPresenter != null) {
            mCategoryPagerPresenter.getContentByCategoryId(mMaterialId);
        }

        //set title
        if (mCurrentTitleTv != null) {
            mCurrentTitleTv.setText(title);
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
    public void onResume() {
        super.onResume();
        //start auto loop
        mLooperPager.startLoop();
    }

    @Override
    public void onPause() {
        super.onPause();
        //start auto loop
        mLooperPager.stopLoop();
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
        Toast.makeText(getContext(),"Network Error, please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderMoreEmpty() {
        Toast.makeText(getContext(),"No extra data load", Toast.LENGTH_SHORT).show();
    }

    /**
     * load extra data into relative adapter
     * @param categoryContents
     */
    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean> categoryContents) {
        mContentAdpater.setExtraData(categoryContents);
        if (mhomePagerRefresh != null) {
            mhomePagerRefresh.finishLoadmore();
        }
        Toast.makeText(getContext(), "loaded " + categoryContents.size(), Toast.LENGTH_SHORT).show();
    }

    /**
     * looper view list
     * @param categoryContents
     */
    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> categoryContents) {
        mLooperPagerAdapter.setData(categoryContents);
        //set middle point
        int dx =(Integer.MAX_VALUE/2) % categoryContents.size();
        int targetCurrentPosition = (Integer.MAX_VALUE/2) - dx;
        mLooperPager.setCurrentItem(targetCurrentPosition);
        mlooperPointContainer.removeAllViews();
        //add looper points
        for (int i = 0; i < categoryContents.size(); i++) {
            View point = new View(getContext());
            int size = SizeUtils.dip2px(getContext(),8);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
            layoutParams.leftMargin = SizeUtils.dip2px(getContext(),5);
            layoutParams.rightMargin = SizeUtils.dip2px(getContext(),5);
            point.setLayoutParams(layoutParams);
            if(i == 0 ){
                point.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            }else{
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
            mlooperPointContainer.addView(point);
        }
    }

    @Override
    protected void release() {
        if (mCategoryPagerPresenter !=null) {
            mCategoryPagerPresenter.unregisterCallback(this);
        }
    }

    @Override
    public void onItemClick(HomePagerContent.DataBean item) {
        //clicked list data
        LogUtils.d(HomePagerFragment.this, "recycle view data: --> " + item.getTitle());
        handleItemClick(item);
    }

    /**
     * switch to ticket page
     * @param item
     */
    private void handleItemClick(HomePagerContent.DataBean item) {
        //handle data
        String title = item.getTitle();
        String url = item.getClick_url();
        String cover = item.getPict_url();
        ITicketPresenter ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        ticketPresenter.getTicket(title,url,cover);
        startActivity(new Intent(getContext(), TicketActivity.class));
    }

    @Override
    public void onLooperItemClick(HomePagerContent.DataBean item) {
        //clicked list data
        LogUtils.d(HomePagerFragment.this, "looper view data: --> " + item.getTitle());
        handleItemClick(item);
    }
}
