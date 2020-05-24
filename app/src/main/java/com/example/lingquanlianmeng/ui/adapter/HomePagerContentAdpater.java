package com.example.lingquanlianmeng.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.model.bean.HomePagerContent;
import com.example.lingquanlianmeng.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePagerContentAdpater extends RecyclerView.Adapter<HomePagerContentAdpater.Innerholder>{
    List<HomePagerContent.DataBean> data = new ArrayList<>();

    @NonNull
    @Override
    public Innerholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pager_content, parent, false);
        return new Innerholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Innerholder holder, int position) {
        HomePagerContent.DataBean dataBean = data.get(position);
        //set Data
        holder.setData(dataBean);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<HomePagerContent.DataBean> categoryContents) {
        data.clear();
        data.addAll(categoryContents);
        notifyDataSetChanged();

    }

    public class Innerholder extends RecyclerView.ViewHolder {

        @BindView(R.id.goods_cover)
        public ImageView cover;

        @BindView(R.id.goods_title)
        public TextView title;

        @BindView(R.id.goods_off_price)
        public TextView offPriceTv;

        @BindView(R.id.goods_after_off_price)
        public TextView finalPriceTv;

        @BindView(R.id.goods_orginal_price)
        public TextView orginalPriceTv;

        @BindView(R.id.sell_counts)
        public TextView sellCounts;

        public Innerholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(HomePagerContent.DataBean dataBean) {
            Context context = itemView.getContext();
            title.setText(dataBean.getTitle());
            // LogUtils.d(this, "url --> "+ dataBean.getPict_url());
            String finalPrice = dataBean.getZk_final_price();
            long couponAmount = dataBean.getCoupon_amount();
            float resultPrice = Float.parseFloat(finalPrice) - couponAmount;
            finalPriceTv.setText(String.format("%.2f",resultPrice));
            offPriceTv.setText(String.format(context.getString(R.string.goods_off_price_text), couponAmount));
            orginalPriceTv.setText(String.format(context.getString(R.string.goods_original_price_text), finalPrice));
            sellCounts.setText(String.valueOf(String.format(context.getString(R.string.goods_sell_count_text), dataBean.getVolume())));
            Glide.with(context).load(UrlUtils.getCoverPath(dataBean.getPict_url())).into(cover);
        }
    }
}
