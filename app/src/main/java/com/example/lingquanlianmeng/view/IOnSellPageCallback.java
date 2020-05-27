package com.example.lingquanlianmeng.view;

import com.example.lingquanlianmeng.base.IBaseCallback;
import com.example.lingquanlianmeng.model.bean.OnSellContent;

public interface IOnSellPageCallback extends IBaseCallback {

    /**
     * complete load content successfully
     * @param content
     */
    void onContentLoadedSuccess(OnSellContent content);

    /**
     * complete load more content
     * @param moreContent
     */
    void onMoreLoaded(OnSellContent moreContent);

    /**
     * load more error
     */
    void onMoreLoadedError();

    /**
     * load more error
     */
    void onLoadedMoreEmpty();
}
