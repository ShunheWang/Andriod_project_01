package com.example.lingquanlianmeng.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class TbNextScrolView extends NestedScrollView {
    private int mHeaderHeight = 0;
    private int mOriginScrol = 0;

    public TbNextScrolView(@NonNull Context context) {
        super(context);
    }

    public TbNextScrolView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TbNextScrolView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHeaderHeight(int headerHeight){
        this.mHeaderHeight = headerHeight;
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        super.onNestedScroll(target,dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed, type, consumed);
    }


    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        //when oringinScrol value < headerHeight value --> scroll, otherwise don't scroll
        if (this.mOriginScrol < this.mHeaderHeight) {
            scrollBy(dx,dy);
            consumed[0]=dx;
            consumed[1]=dy;
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.mOriginScrol = t;
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
