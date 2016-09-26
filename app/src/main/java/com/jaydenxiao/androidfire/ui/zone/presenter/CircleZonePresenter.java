package com.jaydenxiao.androidfire.ui.zone.presenter;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.aspsine.irecyclerview.bean.PageBean;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.app.AppConstant;
import com.jaydenxiao.androidfire.bean.Result;
import com.jaydenxiao.androidfire.ui.zone.DatasUtil;
import com.jaydenxiao.androidfire.ui.zone.bean.CircleItem;
import com.jaydenxiao.androidfire.ui.zone.bean.CommentConfig;
import com.jaydenxiao.androidfire.ui.zone.bean.CommentItem;
import com.jaydenxiao.androidfire.ui.zone.bean.FavortItem;
import com.jaydenxiao.androidfire.ui.zone.contract.CircleZoneContract;
import com.jaydenxiao.androidfire.ui.zone.widget.GoodView;
import com.jaydenxiao.common.baseapp.AppCache;
import com.jaydenxiao.common.commonutils.JsonUtils;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.MDAlertDialog;

import java.util.List;
import java.util.Random;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * des:朋友圈presenter
 * Created by xsf
 * on 2016.07.15:57
 */
public class CircleZonePresenter extends CircleZoneContract.Presenter {
    //点赞效果
    private GoodView mGoodView;

    /**
     * 监听
     */
    @Override
    public void onStart() {
        super.onStart();
        LogUtils.logd("dfsdfsd");
        //新增说说监听
        mRxManage.on(AppConstant.ZONE_PUBLISH_ADD, new Action1<CircleItem>() {
            @Override
            public void call(CircleItem circleItem) {
                if (circleItem != null) {
                    mView.setOnePublishData(circleItem);
                }
            }
        });
    }

    /**
     * 获取未读总数
     */
    @Override
    public void getNotReadNewsCount() {
        mRxManage.add(mModel.getZoneNotReadNews().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String icon) {
                mView.updateNotReadNewsCount(10, icon);
            }
        }));
    }

    /**
     * 获取列表
     */
    @Override
    public void getListData(String type, String userId, final int page, int rows) {
        //加载更多不显示加载条
        if (page <= 1)
            mView.showLoading("加载中...");
        mRxManage.add(mModel.getListDatas(type, userId, page, rows).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                mView.stopLoading();
            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorTip("" + e.getMessage());
            }

            @Override
            public void onNext(Result result) {
                if (result != null) {
                    try {
                        List<CircleItem> circleItems = JSON.parseArray(JsonUtils.getValue(result.getMsg(), "list"), CircleItem.class);
                        for (int i = 0; i < circleItems.size(); i++) {
                            circleItems.get(i).setPictures(DatasUtil.getRandomPhotoUrlString(new Random().nextInt(9)));
                        }
                        PageBean pageBean = JSON.parseObject(JsonUtils.getValue(result.getMsg(), "page"), PageBean.class);
                        mView.setListData(circleItems, pageBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }));

    }

    /**
     * 删除朋友圈
     *
     * @param circleId
     */
     MDAlertDialog mdAlertDialog;
    @Override
    public void deleteCircle(final String circleId, final int position) {
       mdAlertDialog = new MDAlertDialog.Builder(mContext)
                .setHeight(0.25f)  //屏幕高度*0.3
                .setWidth(0.7f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("温馨提示")
                .setTitleTextColor(R.color.black_light)
                .setContentText("确定删除该条说说吗？")
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText("不删除")
                .setLeftButtonTextColor(R.color.black_light)
                .setRightButtonText("删除")
                .setRightButtonTextColor(R.color.gray)
                .setTitleTextSize(16)
                .setContentTextSize(14)
                .setButtonTextSize(14)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        mdAlertDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        mdAlertDialog.dismiss();
                        mView.startProgressDialog();
                        mRxManage.add(mModel.deleteCircle(circleId, position).subscribe(new Subscriber<Result>() {
                            @Override
                            public void onCompleted() {
                                mView.stopProgressDialog();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.startProgressDialog();
                                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
                            }

                            @Override
                            public void onNext(Result result) {
                                mView.update2DeleteCircle(circleId, position);
                            }
                        }));
                    }
                })
                .build();
        mdAlertDialog.show();
    }

    /**
     * 点赞
     *
     * @param circlePosition
     */

    @Override
    public void addFavort(final String publishId, final String publishUserId, final int circlePosition, final View view) {
        mView.startProgressDialog();
        mRxManage.add(mModel.addFavort(publishId, publishUserId).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                mView.stopProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
            }

            @Override
            public void onNext(Result result) {
                if (result != null) {
                    if (mGoodView == null) {
                        mGoodView = new GoodView(mContext);
                    }
                    //mGoodView.setTextInfo("点赞成功", ContextCompat.getColor(mContext, R.color.main_color), 12);
                    mGoodView.setImage(R.drawable.dianzan);
                    mGoodView.show(view);
                    FavortItem item = new FavortItem(publishId, AppCache.getInstance().getUserId(), "jayden");
                    mView.update2AddFavorite(circlePosition, item);
                }
            }
        }));
    }

    /**
     * 取消点赞
     *
     * @param circlePosition
     */
    @Override
    public void deleteFavort(final String publishId, final String publishUserId, final int circlePosition) {
        mView.startProgressDialog();
        mRxManage.add(mModel.deleteFavort(publishId, publishUserId).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                mView.stopProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
            }

            @Override
            public void onNext(Result result) {
                if (result != null) {
                    mView.update2DeleteFavort(circlePosition, AppCache.getInstance().getUserId());
                }
            }
        }));
    }

    /**
     * 增加评论
     *
     * @param content
     * @param config
     */
    @Override
    public void addComment(final String content, final CommentConfig config) {
        if (config == null) {
            return;
        }
        mView.startProgressDialog();
        mRxManage.add(mModel.addComment(config.getPublishUserId(), new CommentItem(config.getName(), config.getId(), content, config.getPublishId(), AppCache.getInstance().getUserId(), "jayden")).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                mView.stopProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                mView.stopProgressDialog();
                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
            }

            @Override
            public void onNext(Result result) {
                if (result != null) {
                    mView.update2AddComment(config.circlePosition, new CommentItem(config.getName(), config.getId(), content, config.getPublishId(), AppCache.getInstance().getUserId(), "锋"));
                }
            }
        }));
    }

    /**
     * 删除评论
     *
     * @param circlePosition
     * @param commentId
     */
    @Override
    public void deleteComment(final int circlePosition, final String commentId, final int commentPosition) {
        mView.startProgressDialog();
        mRxManage.add(mModel.deleteComment(commentId).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                mView.stopProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                mView.stopProgressDialog();
                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
            }

            @Override
            public void onNext(Result result) {
                mView.update2DeleteComment(circlePosition, commentId, commentPosition);
            }
        }));
    }

    /**
     * 显示输入框
     *
     * @param commentConfig
     */
    @Override
    public void showEditTextBody(CommentConfig commentConfig) {
        mView.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
    }

}