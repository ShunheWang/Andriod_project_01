package com.example.lingquanlianmeng.ui.fragment;

import android.view.View;

import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.base.BaseFragment;

public class SelectedFragment extends BaseFragment {


    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_selected;
    }

    @Override
    protected void initView(View rootView) {
        setupState(State.SUCCESS);
    }
}
