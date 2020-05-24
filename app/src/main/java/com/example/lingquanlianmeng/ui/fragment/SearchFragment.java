package com.example.lingquanlianmeng.ui.fragment;

import android.view.View;

import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.base.BaseFragment;

public class SearchFragment extends BaseFragment {

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View rootView) {
        setupState(State.SUCCESS);
    }
}
