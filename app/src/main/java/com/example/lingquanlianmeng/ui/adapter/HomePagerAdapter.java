package com.example.lingquanlianmeng.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.lingquanlianmeng.model.bean.Categories;
import com.example.lingquanlianmeng.ui.fragment.HomePagerFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Categories.DataBean> categoriesData = new ArrayList<>();

    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.categoriesData.get(position).getTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Categories.DataBean dataBean = categoriesData.get(position);
        HomePagerFragment homePagerFragment = HomePagerFragment.newInstance(dataBean);
        return homePagerFragment;
    }

    @Override
    public int getCount() {
        return this.categoriesData.size();
    }

    public void setCategories(Categories categories) {
        this.categoriesData.clear();
        List<Categories.DataBean> data = categories.getData();
        this.categoriesData.addAll(data);
        notifyDataSetChanged();
    }
}
