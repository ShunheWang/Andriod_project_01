package com.example.lingquanlianmeng.ui.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.model.bean.SelectedContent;
import com.example.lingquanlianmeng.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedPageRightContentAdapter extends RecyclerView.Adapter<SelectedPageRightContentAdapter.InnerHolder> {

    private List<SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> mDataBean = new ArrayList<>();

    private OnSelectedPageContentClickListener mItemListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_page_right_content, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean itemData = mDataBean.get(position);
        holder.setData(itemData);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onContentClickClick(itemData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mDataBean.size();
    }

    public void setData(SelectedContent content) {
        if (content.getCode() == Constants.STATE_SUCCESS_CODE) {
            List<SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> uatm_tbk_item = content.getData().getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item();
            this.mDataBean.clear();
            this.mDataBean.addAll(uatm_tbk_item);
            notifyDataSetChanged();
        }
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.selected_cover)
        public ImageView cover;

        @BindView(R.id.selected_save_description)
        public TextView savePrice;

        @BindView(R.id.selected_title)
        public TextView title;

        @BindView(R.id.selected_purchase_btn)
        public TextView purchaseBtn;

        @BindView(R.id.selected_origin_price)
        public TextView originPrice;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean data){
            title.setText(data.getTitle());
            if(TextUtils.isEmpty(data.getCoupon_click_url())){
                originPrice.setText("Discount codes ran out");
                purchaseBtn.setVisibility(View.GONE);
            }else{
                originPrice.setText("Price: " + data.getZk_final_price());
                purchaseBtn.setVisibility(View.GONE);
            }
            if(TextUtils.isEmpty(data.getCoupon_info())){
                savePrice.setVisibility(View.GONE);
            }else{
                savePrice.setVisibility(View.VISIBLE);
                savePrice.setText(data.getCoupon_info());
            }

            Glide.with(itemView.getContext()).load(data.getPict_url()).into(cover);
        }
    }


    public void setOnSelectedPageContentClickListener(OnSelectedPageContentClickListener listener){
        this.mItemListener = listener;
    }

    public interface OnSelectedPageContentClickListener{
        void onContentClickClick(SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean item);
    }
}
