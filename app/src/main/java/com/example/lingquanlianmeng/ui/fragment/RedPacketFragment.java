package com.example.lingquanlianmeng.ui.fragment;

import android.util.Log;

import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.base.BaseFragment;

public class RedPacketFragment extends BaseFragment {
    @Override
    protected int getRootViewResId() {
        Log.d("BaseFragment", "RedPacketFragment");
        return R.layout.fragment_red_packet;
    }
}
