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
    List<HomePagerContent.DataBean> mData = new ArrayList<>();
    private OnListenerItemClickListener mItemClickListener;

    @NonNull
    @Override
    public Innerholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pager_content, parent, false);
        //LogUtils.d(HomePagerContentAdpater.this, "onCreateViewHolder: --> " + viewType);
        return new Innerholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Innerholder holder, int position) {
        HomePagerContent.DataBean dataBean = mData.get(position);
        //LogUtils.d(HomePagerContentAdpater.this, "onBindViewHolder: --> " + position);
        //set Data
        holder.setData(dataBean);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener != null){
                    HomePagerContent.DataBean item = mData.get(position);
                    mItemClickListener.onItemClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<HomePagerContent.DataBean> categoryContents) {
        mData.clear();
        mData.addAll(categoryContents);
        notifyDataSetChanged();

    }

    public void setExtraData(List<HomePagerContent.DataBean> categoryContents) {
        //get current mData size
        int currentSize = mData.size();
        mData.addAll(categoryContents);
        //update UI
        notifyItemRangeChanged(currentSize,mData.size());
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
            ViewGroup.LayoutParams layoutParams = cover.getLayoutParams();
            int coverWidth = layoutParams.width;
            int coverHeight = layoutParams.height;
            int coverSize = (coverWidth>coverHeight?coverWidth:coverHeight)/2;
            // LogUtils.d(this, "url --> "+ dataBean.getPict_url());
            String finalPrice = dataBean.getZk_final_price();
            long couponAmount = dataBean.getCoupon_amount();
            float resultPrice = Float.parseFloat(finalPrice) - couponAmount;
            finalPriceTv.setText(String.format("%.2f",resultPrice));
            offPriceTv.setText(String.format(context.getString(R.string.goods_off_price_text), couponAmount));
            orginalPriceTv.setText(String.format(context.getString(R.string.goods_original_price_text), finalPrice));
            sellCounts.setText(String.valueOf(String.format(context.getString(R.string.goods_sell_count_text), dataBean.getVolume())));
            String coverPath = UrlUtils.getCoverPath(dataBean.getPict_url(), coverSize);
            Glide.with(context).load(coverPath).into(cover);
        }
    }

    public void setOnListenerItemClickListener(OnListenerItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public interface OnListenerItemClickListener{
        void onItemClick(HomePagerContent.DataBean item);
    }
}
