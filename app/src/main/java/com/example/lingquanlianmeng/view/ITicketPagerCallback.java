package com.example.lingquanlianmeng.view;

import com.example.lingquanlianmeng.base.IBaseCallback;
import com.example.lingquanlianmeng.model.bean.TicketResult;

public interface ITicketPagerCallback extends IBaseCallback {

    void onTicketLoaded(String cover, TicketResult result);

}
