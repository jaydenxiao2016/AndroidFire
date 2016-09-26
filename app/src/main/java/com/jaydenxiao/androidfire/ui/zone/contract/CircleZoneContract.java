package com.jaydenxiao.androidfire.ui.zone.contract;


import com.aspsine.irecyclerview.bean.PageBean;
import com.jaydenxiao.androidfire.bean.Result;
import com.jaydenxiao.androidfire.ui.zone.bean.CircleItem;
import com.jaydenxiao.androidfire.ui.zone.bean.CommentConfig;
import com.jaydenxiao.androidfire.ui.zone.bean.CommentItem;
import com.jaydenxiao.androidfire.ui.zone.bean.FavortItem;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * des:朋友圈契约接口
 * Created by xsf
 * on 2016.07.15:52
 */
public interface CircleZoneContract {

    interface Model extends BaseModel {
        Observable<String> getZoneNotReadNews();

        Observable<Result> getListDatas(String type, String userId, final int page, int rows);

        Observable<Result> deleteCircle(String circleId, int position);

        Observable<Result> addFavort(String publishId, String publishUserId);

        Observable<Result> deleteFavort(String publishId, String publishUserId);

        Observable<Result> addComment(String publishUserId,CommentItem circleItem);

        Observable<Result> deleteComment(String commentId);
    }

    interface View extends BaseView {
        //获取未读总数
        void updateNotReadNewsCount(int count, String icon);

        void setListData(List<CircleItem> circleItems, PageBean pageBean);

        void setOnePublishData(CircleItem circleItem);

        void update2DeleteCircle(String circleId, int position);

        void update2AddFavorite(int circlePosition, FavortItem addItem);

        void update2DeleteFavort(int circlePosition, String UserId);

        void update2AddComment(int circlePosition, CommentItem addItem);

        void update2DeleteComment(int circlePosition, String commentId,int commentPosition);

        void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);

        void startProgressDialog();

        void stopProgressDialog();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        //获取未读总数
        public abstract void getNotReadNewsCount();

        //获取数据
        public abstract void getListData(String type, String userId, int page, int rows);

        //删除
        public abstract void deleteCircle(final String circleId, int position);

        //点赞
        public abstract void addFavort(final String publishId, final String publishUserId, final int circlePosition, final android.view.View view);

        //取消点赞
        public abstract void deleteFavort(final String publishId, final String publishUserId, final int circlePosition);

        //增加评论
        public abstract void addComment(final String content, final CommentConfig config);

        //删除评论
        public abstract void deleteComment(final int circlePosition, final String commentId,int commentPosition);

        //显示评论输入框
        public abstract void showEditTextBody(CommentConfig commentConfig);

    }


}
