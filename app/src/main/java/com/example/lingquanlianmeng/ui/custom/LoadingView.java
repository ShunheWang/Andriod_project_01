package com.example.lingquanlianmeng.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.lingquanlianmeng.R;

public class LoadingView extends AppCompatImageView {
    private float mDrgrees = 0;
    private boolean nIsRotate = true;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.mipmap.loading);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(mDrgrees,getWidth()/2,getHeight()/2);
        super.onDraw(canvas);
    }

    private void startRotate(){
        nIsRotate = true;
        post(new Runnable() {
            @Override
            public void run() {
                mDrgrees += 10;
                if(mDrgrees >= 360){
                    mDrgrees = 0;
                }
                invalidate();
                //check stop
                if (getVisibility() != VISIBLE && !nIsRotate) {
                    removeCallbacks(this);
                }else{
                    postDelayed(this,10);
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startRotate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRotate();
    }

    private void stopRotate() {
        nIsRotate = false;
    }
}
