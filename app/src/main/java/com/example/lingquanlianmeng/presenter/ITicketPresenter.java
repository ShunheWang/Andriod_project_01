package com.example.lingquanlianmeng.presenter;

import com.example.lingquanlianmeng.base.IBasePresenter;
import com.example.lingquanlianmeng.view.ITicketPagerCallback;

public interface ITicketPresenter extends IBasePresenter<ITicketPagerCallback> {

    /**
     * create TaoBao codes
     * @param title
     * @param url
     * @param cover
     */
    void getTicket(String title,String url,String cover);
}
