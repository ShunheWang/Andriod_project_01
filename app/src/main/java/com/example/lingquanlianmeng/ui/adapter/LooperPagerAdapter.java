package com.example.lingquanlianmeng.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.lingquanlianmeng.model.bean.HomePagerContent;
import com.example.lingquanlianmeng.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

public class LooperPagerAdapter extends PagerAdapter {

    private List<HomePagerContent.DataBean> mDataBean = new ArrayList<>();
    private OnLooperPageItemClickListener mItemClickListener;

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public int getDataBeanSize(){
        return mDataBean.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //handle position
        int realPosition = position % mDataBean.size();
        HomePagerContent.DataBean dataBean = mDataBean.get(realPosition);
        int containerHeight = container.getMeasuredHeight();
        int containerWidth = container.getMeasuredWidth();
        int ivSize = (containerHeight>containerWidth?containerHeight:containerWidth)/2;
//        LogUtils.d(LooperPagerAdapter.this,"container height: --> " + containerHeight);
//        LogUtils.d(LooperPagerAdapter.this,"container width: --> " + containerWidth);
//        LogUtils.d(LooperPagerAdapter.this,"ivSize: --> " + ivSize);
        String coverUrl = UrlUtils.getCoverPath(dataBean.getPict_url(),ivSize);
//        LogUtils.d(LooperPagerAdapter.this,"coverUrl: --> " + coverUrl);
        ImageView iv = new ImageView(container.getContext());
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    HomePagerContent.DataBean item = mDataBean.get(realPosition);
                    mItemClickListener.onLooperItemClick(item);
                }
            }
        });
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(layoutParams);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(container.getContext()).load(coverUrl).into(iv);
        container.addView(iv);
        return iv;
    }


    public void setData(List<HomePagerContent.DataBean> categoryContents) {
        mDataBean.clear();
        mDataBean.addAll(categoryContents);
        notifyDataSetChanged();
    }
    
    public void setOnLooperPageItemClickListener(OnLooperPageItemClickListener listener){
        this.mItemClickListener = listener;
    }
    
    public interface  OnLooperPageItemClickListener{
        void onLooperItemClick(HomePagerContent.DataBean item);
    }
}

