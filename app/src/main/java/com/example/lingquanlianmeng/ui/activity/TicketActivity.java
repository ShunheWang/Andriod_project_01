package com.example.lingquanlianmeng.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lingquanlianmeng.R;
import com.example.lingquanlianmeng.base.BaseActivity;
import com.example.lingquanlianmeng.model.bean.TicketResult;
import com.example.lingquanlianmeng.presenter.ITicketPresenter;
import com.example.lingquanlianmeng.utils.LogUtils;
import com.example.lingquanlianmeng.utils.PresenterManager;
import com.example.lingquanlianmeng.utils.UrlUtils;
import com.example.lingquanlianmeng.view.ITicketPagerCallback;

import butterknife.BindView;

public class TicketActivity extends BaseActivity implements ITicketPagerCallback {

    private ITicketPresenter mTicketPresenter;

    @BindView(R.id.ticket_cover)
    public ImageView mTicketCover;

    @BindView(R.id.ticket_code)
    public EditText mTicketCode;

    @BindView(R.id.ticket_btn)
    public TextView mTicketBtn;

    @BindView(R.id.cover_loading)
    public View mCoverLoading;

    @BindView(R.id.ticket_load_retry)
    public View mTicketLoadRetry;

    @BindView(R.id.ticket_back_btn)
    public View mTicketBackBtn;


    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_ticket;
    }

    @Override
    protected void initPresenter() {
        mTicketPresenter = PresenterManager.getInstance().getTicketPresenter();
        mTicketPresenter.registerCallback(this);
    }

    @Override
    protected void initEvent() {
        mTicketBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTicketBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //todo: check TaoBao Apps was installed in phone
                //todo: if install, open TaoBao app from there with taobao code
                //emulator cannot install TaoBao app, thus do that when has a real Phone
            }
        });
    }

    @Override
    public void onTicketLoaded(String cover, TicketResult result) {
        if (mTicketLoadRetry != null) {
            mTicketLoadRetry.setVisibility(View.GONE);
        }
        LogUtils.d(this,"cover: --> " + cover);
        if (mTicketCover != null && !TextUtils.isEmpty(cover)) {
            String coverPath = UrlUtils.getCoverPath(cover);
            Glide.with(this).load(coverPath).into(mTicketCover);
        }

        if (result != null && result.getData().getTbk_tpwd_create_response() != null) {
            mTicketCode.setText(result.getData().getTbk_tpwd_create_response().getData().getModel());
        }
        if (mCoverLoading != null) {
            mCoverLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNetworkError() {
        if (mCoverLoading != null) {
            mCoverLoading.setVisibility(View.GONE);
        }
        if (mTicketLoadRetry != null) {
            mTicketLoadRetry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoading() {
        if (mTicketLoadRetry != null) {
            mTicketLoadRetry.setVisibility(View.GONE);
        }
        if (mCoverLoading != null) {
            mCoverLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onEmpty() {
    }

    @Override
    protected void release() {
        mTicketPresenter.unregisterCallback(this);
    }
}
