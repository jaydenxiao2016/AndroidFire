package com.jaydenxiao.androidfire.ui.news.contract;

import com.jaydenxiao.androidfire.bean.NewsDetail;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import rx.Observable;

/**
 * des:新闻详情contract
 * Created by xsf
 * on 2016.09.14:38
 */
public interface NewsDetailContract {
    interface Model extends BaseModel {
        //请求获取新闻
        Observable <NewsDetail> getOneNewsData(String postId);
    }

    interface View extends BaseView {
        //返回获取的新闻
        void returnOneNewsData(NewsDetail newsDetail);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取单条新闻请求
        public abstract void getOneNewsDataRequest(String postId);
    }
}
