package com.example.lingquanlianmeng.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.lingquanlianmeng.R;

/**
 * Auto Looper View
 */
public class AutoLooperViewPager extends ViewPager {
    public AutoLooperViewPager(@NonNull Context context) {
        this(context,null);
    }

    public AutoLooperViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //read relative attrs
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AutoLoopStyle);
        //get attrs
        mDuration = ta.getInteger(R.styleable.AutoLoopStyle_duration, (int) DEFAULT_DURATION);
        //recycle value
        ta.recycle();
    }

    private static final long DEFAULT_DURATION = 3000;
    private long mDuration = DEFAULT_DURATION;
    private boolean isLoop = false;

    public void startLoop(){
        isLoop = true;
        post(mTask);
    }

    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            int currentItem = getCurrentItem();
            currentItem++;
            setCurrentItem(currentItem);
            if (isLoop) {
                postDelayed(this,3000);
            }
        }
    };

    /**
     * set auto looper duration
     * @param duration
     */
    public void setDuration(long duration){
        this.mDuration = duration;
    }

    public void stopLoop(){
        isLoop = false;
    }

}
