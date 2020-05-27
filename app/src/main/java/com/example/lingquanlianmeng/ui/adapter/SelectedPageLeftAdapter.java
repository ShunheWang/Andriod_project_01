package com.example.lingquanlianmeng.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.model.bean.SelectedPageCategories;

import java.util.ArrayList;
import java.util.List;

public class SelectedPageLeftAdapter extends RecyclerView.Adapter<SelectedPageLeftAdapter.InnerHolder> {

    private List<SelectedPageCategories.DataBean> mDataBeans = new ArrayList<>();

    private int mCurrentSelectedPostion = 0;
    private OnLeftItemClickListener mItemClickListener = null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_page_left, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TextView itemTv = holder.itemView.findViewById(R.id.left_category_tv);
        if(mCurrentSelectedPostion == position){
            itemTv.setBackgroundColor(Color.parseColor("#EFEEEE"));
        }else{
            itemTv.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        SelectedPageCategories.DataBean data = mDataBeans.get(position);
        itemTv.setText(data.getFavorites_title());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //modify current selected postion
                    if (mItemClickListener != null && mCurrentSelectedPostion != position) {
                        mCurrentSelectedPostion = position;
                        mItemClickListener.onLeftItemClick(data);
                        notifyDataSetChanged();
                    }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mDataBeans.size();
    }

    //set data
    public void setData(SelectedPageCategories categories) {
        List<SelectedPageCategories.DataBean> data = categories.getData();
        if (data != null) {
            this.mDataBeans.clear();
            this.mDataBeans.addAll(data);
            notifyDataSetChanged();
        }

        if (mDataBeans.size() > 0) {
            mItemClickListener.onLeftItemClick(mDataBeans.get(mCurrentSelectedPostion));
        }
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setOnLeftItemClickListener(OnLeftItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public interface OnLeftItemClickListener{
        void onLeftItemClick(SelectedPageCategories.DataBean item);
    }
}
