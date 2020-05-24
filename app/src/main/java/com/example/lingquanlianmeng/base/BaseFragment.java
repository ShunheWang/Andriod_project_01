package com.example.lingquanlianmeng.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lingquanlianmeng.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private State currentState = State.NONE;
    private View mSuccessView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

    public enum State{
        NONE, SUCCESS, LOADING, ERROR, EMPTY
    }

    private Unbinder mBind;
    private FrameLayout mBaseContainer;

    @OnClick(R.id.network_error_tips)
    public void retry(){
        //点击重新加载内容
        //LogUtils.d(this,"onRetry()");
        onRetryClick();
    }

    /**
     * subclass to handle retry method to get data
     */
    protected void onRetryClick() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = loadRootView(inflater,container);
        mBaseContainer = rootView.findViewById(R.id.base_container);
        loadStatesView(inflater,container);
        mBind = ButterKnife.bind(this, rootView);
        initView(rootView);
        initPresenter();
        loadData();
        return rootView;
    }

    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_fragment_layout,container,false);
    }

    /**
     * 加载各种状态的View
     * @param inflater
     * @param container
     */
    private void loadStatesView(LayoutInflater inflater, ViewGroup container) {
        //success View
        mSuccessView = loadSuccessView(inflater,container);
        mBaseContainer.addView(mSuccessView);

        //loading view
        mLoadingView = loadloadingView(inflater,container);
        mBaseContainer.addView(mLoadingView);

        //error view
        mErrorView = loadErrorView(inflater,container);
        mBaseContainer.addView(mErrorView);

        //empty view
        mEmptyView = loadEmptyView(inflater,container);
        mBaseContainer.addView(mEmptyView);

        setupState(State.NONE);
    }

    /**
     * subclass switch page state via this method
     * @param state
     */
    public void setupState(State state){
        this.currentState = state;
        mSuccessView.setVisibility(currentState == state.SUCCESS?View.VISIBLE:View.GONE);
        mLoadingView.setVisibility(currentState == state.LOADING?View.VISIBLE:View.GONE);
        mErrorView.setVisibility(currentState == state.ERROR?View.VISIBLE:View.GONE);
        mEmptyView.setVisibility(currentState == state.EMPTY?View.VISIBLE:View.GONE);
    }

    /**
     * 加载error view
     * @param inflater
     * @param container
     * @return
     */
    protected View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_error, container, false);
    }

    /**
     * 加载loading view
     * @param inflater
     * @param container
     * @return
     */
    protected View loadloadingView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }


    /**
     * 加载empty view
     * @param inflater
     * @param container
     * @return
     */
    protected View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_empty, container, false);
    }

    /**
     * 加载success view
     * @param inflater
     * @param container
     * @return
     */
    protected View loadSuccessView(LayoutInflater inflater, @Nullable ViewGroup container){
        Log.d("BaseFragment", "loadRootView");
        int resId = getRootViewResId();
        return inflater.inflate(resId,container,false);
    }

    protected void initView(View rootView) {
        //初始化相关View
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
        release();
    }

    protected void release() {
        //释放资源
    }

    protected void initPresenter(){
        //创建presenter
    }

    protected void loadData(){
        //加载数据
    }

    protected abstract int getRootViewResId();


}
