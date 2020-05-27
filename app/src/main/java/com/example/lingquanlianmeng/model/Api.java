package com.example.lingquanlianmeng.model;

import com.example.lingquanlianmeng.model.bean.Categories;
import com.example.lingquanlianmeng.model.bean.HomePagerContent;
import com.example.lingquanlianmeng.model.bean.OnSellContent;
import com.example.lingquanlianmeng.model.bean.SelectedContent;
import com.example.lingquanlianmeng.model.bean.SelectedPageCategories;
import com.example.lingquanlianmeng.model.bean.TicketParams;
import com.example.lingquanlianmeng.model.bean.TicketResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {

    @GET("discovery/categories")
    Call<Categories> getCategories();

    @GET
    Call<HomePagerContent> getHomePagerContent(@Url String url);

    @POST("tpwd")
    Call<TicketResult> getTicket(@Body TicketParams ticketPrams);

    @GET("recommend/categories")
    Call<SelectedPageCategories> getSelectedPageCategories();

    @GET
    Call<SelectedContent> getSelectedPageContent(@Url String url);

    @GET
    Call<OnSellContent> getOnSellPageContent(@Url String url);
}
