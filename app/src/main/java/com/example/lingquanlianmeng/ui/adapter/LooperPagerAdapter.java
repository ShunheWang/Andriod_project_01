package com.example.lingquanlianmeng.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class LooperPagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }


    //todo:
    //this method was old!!!
    @NonNull
    @Override
    public Object instantiateItem(@NonNull View container, int position) {
        return super.instantiateItem(container, position);
    }
}
