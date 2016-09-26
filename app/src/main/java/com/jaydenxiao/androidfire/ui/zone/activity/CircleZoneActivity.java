package com.jaydenxiao.androidfire.ui.zone.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.animation.ScaleInAnimation;
import com.aspsine.irecyclerview.bean.PageBean;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.ui.zone.adapter.CircleAdapter;
import com.jaydenxiao.androidfire.ui.zone.bean.CircleItem;
import com.jaydenxiao.androidfire.ui.zone.bean.CommentConfig;
import com.jaydenxiao.androidfire.ui.zone.bean.CommentItem;
import com.jaydenxiao.androidfire.ui.zone.bean.FavortItem;
import com.jaydenxiao.androidfire.ui.zone.contract.CircleZoneContract;
import com.jaydenxiao.androidfire.ui.zone.model.ZoneModel;
import com.jaydenxiao.androidfire.ui.zone.presenter.CircleZonePresenter;
import com.jaydenxiao.androidfire.ui.zone.widget.CommentListView;
import com.jaydenxiao.androidfire.ui.zone.widget.ZoneHeaderView;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.baseapp.AppCache;
import com.jaydenxiao.common.commonutils.DisplayUtil;
import com.jaydenxiao.common.commonutils.KeyBordUtil;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnLongClick;


/**
 * des:朋友圈
 * Created by xsf
 * on 2016.07.11:19
 */
public class CircleZoneActivity extends BaseActivity<CircleZonePresenter, ZoneModel> implements CircleZoneContract.View, View.OnClickListener {


    @Bind(R.id.ntb)
    NormalTitleBar ntb;
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    @Bind(R.id.circleEt)
    EditText circleEt;
    @Bind(R.id.sendIv)
    ImageView sendIv;
    @Bind(R.id.editTextBodyLl)
    LinearLayout editTextBodyLl;

    //朋友圈头部
    ZoneHeaderView zoneHeaderView;
    @Bind(R.id.fab1)
    FloatingActionButton fab1;
    @Bind(R.id.fab2)
    FloatingActionButton fab2;
    @Bind(R.id.fab3)
    FloatingActionButton fab3;
    @Bind(R.id.fab4)
    FloatingActionButton fab4;
    @Bind(R.id.fab5)
    FloatingActionButton fab5;
    @Bind(R.id.menu_red)
    FloatingActionMenu menuRed;

    private CircleAdapter mAdapter;
    private CommentConfig mCommentConfig;

    private int mScreenHeight;
    private int mEditTextBodyHeight;
    private int mCurrentKeyboardH;
    private int mSelectCircleItemH;
    private int mSelectCommentItemOffset;
    private LinearLayoutManager linearLayoutManager;

    /**
     * 启动入口
     *
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, CircleZoneActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fra_circle_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    /**
     * 初始化
     */
    public void initView() {
        menuRed.setClosedOnTouchOutside(true);
        //点赞效果初始化
        ntb.setTitleText(getString(R.string.circle_zone));
        //滑动列表关闭输入框
        irc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (editTextBodyLl.getVisibility() == View.VISIBLE)
                    updateEditTextBodyVisible(View.GONE, null);
                return false;
            }
        });
        //初始化头部未读消息
        zoneHeaderView = new ZoneHeaderView(this);
        zoneHeaderView.setData(getString(R.string.nick_name), AppCache.getInstance().getIcon());
        irc.addHeaderView(zoneHeaderView);

        mAdapter = new CircleAdapter(this, mPresenter);
        mAdapter.openLoadAnimation(new ScaleInAnimation());
        linearLayoutManager = new LinearLayoutManager(this);
        irc.setLayoutManager(linearLayoutManager);
        irc.setAdapter(mAdapter);
        //监听recyclerview滑动
        setViewTreeObserver();
        //上拉刷新
        irc.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.getPageBean().setRefresh(true);
                loadData();
            }
        });
        //下拉加载更多
        irc.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(View loadMoreView) {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.getPageBean().setRefresh(false);
                        loadData();
                    }
                }, 1000);

            }
        });
        //监听列表滑动
        irc.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isSignificantDelta = Math.abs(dy) > ViewConfiguration.getTouchSlop();
                if (isSignificantDelta) {
                    if (dy > 0) {
                        menuRed.hideMenuButton(true);
                    } else {
                        menuRed.showMenuButton(true);
                    }
                }
            }
        });
        //首次加载数据
        loadData();
    }

    /**
     * 初始化数据
     */
    private void loadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新时才查未读条数
                if (mAdapter.getPageBean().isRefresh()) {
                    mPresenter.getNotReadNewsCount();
                }
                mPresenter.getListData("0", AppCache.getInstance().getUserId(), mAdapter.getPageBean().getLoadPage(), mAdapter.getPageBean().getRows());

            }
        }, 500);


    }

    /**
     * 监听recyclerview滑动
     */
    private void setViewTreeObserver() {
        final ViewTreeObserver swipeRefreshLayoutVTO = irc.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                irc.getWindowVisibleDisplayFrame(r);
                int statusBarH = getStatusBarHeight();//状态栏高度
                int screenH = irc.getRootView().getHeight();
                if (r.top != statusBarH) {
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);
                LogUtils.logd("screenH＝ " + screenH + " &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);
                if (keyboardH == mCurrentKeyboardH) {//有变化时才处理，否则会陷入死循环
                    return;
                }
                mCurrentKeyboardH = keyboardH;
                mScreenHeight = screenH;//应用屏幕的高度
                mEditTextBodyHeight = editTextBodyLl.getHeight();

                //偏移listview
                if (irc != null && mCommentConfig != null) {
                    int index = mCommentConfig.circlePosition + irc.getHeaderContainer().getChildCount() + 1;
                    linearLayoutManager.scrollToPositionWithOffset(index, getListviewOffset(mCommentConfig));
                }
            }
        });
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.tv_back, R.id.tv_right, R.id.sendIv, R.id.menu_red, R.id.fab1, R.id.fab2, R.id.fab3, R.id.fab4, R.id.fab5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_right:
                break;
            //评论
            case R.id.sendIv:
                if (mPresenter != null) {
                    //发布评论
                    String content = circleEt.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        ToastUitl.showToastWithImg("评论内容不能为空", R.drawable.ic_warm);
                        return;
                    }
                    mPresenter.addComment(content, mCommentConfig);
                }
                updateEditTextBodyVisible(View.GONE, null);
                break;
            case R.id.menu_red:
                break;
            case R.id.fab1:
                menuRed.close(true);
            case R.id.fab2:
                menuRed.close(true);
            case R.id.fab3:
                menuRed.close(true);
            case R.id.fab4:
                menuRed.close(true);
            case R.id.fab5:
                menuRed.close(true);
                CirclePublishActivity.startAction(this);
                break;
        }
    }

    @OnLongClick({R.id.image_right})
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.image_right:
                //发文字朋友圈
                //CirclePublishActivity.startAction(this, false);
                break;
        }
        return false;
    }


    /***************************presenter回调*******************************************/

    /**
     * 未读消息总数
     *
     * @param count
     */
    @Override
    public void updateNotReadNewsCount(int count, String icon) {
        zoneHeaderView.setNotReadMsgData(count, icon);
    }

    @Override
    public void setListData(List<CircleItem> circleItems, PageBean pageBean) {
        if (mAdapter.getPageBean().isRefresh()) {
            mAdapter.reset(circleItems);
            irc.setRefreshing(false);
        } else {
            mAdapter.addAll(circleItems);
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
        }
        //判断是否还可以加载更多
        if (pageBean.getTotalPage() <= pageBean.getPage()) {
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
        }
        //加载完成
        if (mAdapter.getData().size() > 0) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        } else {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.empty);
        }

    }

    @Override
    public void setOnePublishData(CircleItem circleItem) {
        mAdapter.add(0, circleItem);
    }

    @Override
    public void update2DeleteCircle(String circleId, int position) {
        mAdapter.remove(position);
    }

    @Override
    public void update2AddFavorite(int circlePosition, FavortItem addItem) {
        if (addItem != null) {
            mAdapter.getData().get(circlePosition).getGoodjobs().add(addItem);
            mAdapter.notifyItemChanged(circlePosition);
        }
    }

    @Override
    public void update2DeleteFavort(int circlePosition, String userId) {
        List<FavortItem> items = mAdapter.getData().get(circlePosition).getGoodjobs();
        for (int i = 0; i < items.size(); i++) {
            if (userId.equals(items.get(i).getUserId())) {
                items.remove(i);
                mAdapter.notifyItemChanged(circlePosition);
                return;
            }
        }
    }

    @Override
    public void update2AddComment(int circlePosition, CommentItem addItem) {
        if (addItem != null) {
            mAdapter.getData().get(circlePosition).getReplys().add(addItem);
            mAdapter.notifyItemChanged(circlePosition);
        }
        //清空评论文本
        circleEt.setText("");
    }

    @Override
    public void update2DeleteComment(int circlePosition, String commentId, int commentPosition) {
        List<CommentItem> items = mAdapter.getData().get(circlePosition).getReplys();
        items.remove(commentPosition);
        mAdapter.notifyDataSetChanged();
        //调接口情况建议用id判断删除
//        for (int i = 0; i < items.size(); i++) {
//            if (commentId.equals(items.get(i).getId())) {
//                items.remove(i);
//                mAdapter.notifyDataSetChanged();
//                return;
//            }
//        }
    }

    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        mCommentConfig = commentConfig;
        editTextBodyLl.setVisibility(visibility);

        measureCircleItemHighAndCommentItemOffset(commentConfig);

        if (commentConfig != null && CommentConfig.Type.REPLY.equals(commentConfig.getCommentType())) {
            circleEt.setHint("回复" + commentConfig.getName() + ":");
        } else {
            circleEt.setHint("说点什么吧");
        }
        if (View.VISIBLE == visibility) {
            circleEt.requestFocus();
            //弹出键盘
            KeyBordUtil.showSoftKeyboard(circleEt);
            //隐藏菜单
            menuRed.hideMenuButton(true);
        } else if (View.GONE == visibility) {
            //隐藏键盘
            KeyBordUtil.hideSoftKeyboard(circleEt);
            //显示菜单
            menuRed.showMenuButton(true);
        }
    }
    /**
     * 测量偏移量
     *
     * @param commentConfig
     * @return
     */
    private int getListviewOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight - ntb.getMeasuredHeight();
        if (commentConfig.commentType == CommentConfig.Type.REPLY) {
            //回复评论的情况
            listviewOffset = listviewOffset + mSelectCommentItemOffset - ntb.getMeasuredHeight();
        }
        return listviewOffset;
    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return;

        int headViewCount = irc.getHeaderContainer().getChildCount();
        //当前选中的view
        int selectPostion = commentConfig.circlePosition + headViewCount + 1;
        View selectCircleItem = linearLayoutManager.findViewByPosition(selectPostion);

        if (selectCircleItem != null) {
            mSelectCircleItemH = selectCircleItem.getHeight() - DisplayUtil.dip2px(48);
            //获取评论view,计算出该view距离所属动态底部的距离
            if (commentConfig.commentType == CommentConfig.Type.REPLY) {
                //回复评论的情况
                CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
                if (commentLv != null) {
                    //找到要回复的评论view,计算出该view距离所属动态底部的距离
                    View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                    if (selectCommentItem != null) {
                        //选择的commentItem距选择的CircleItem底部的距离
                        mSelectCommentItemOffset = 0;
                        View parentView = selectCommentItem;
                        do {
                            int subItemBottom = parentView.getBottom();
                            parentView = (View) parentView.getParent();
                            if (parentView != null) {
                                mSelectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                            }
                        }
                        while (parentView != null && parentView != selectCircleItem);
                    }
                }
            }
        }

    }
    @Override
    public void showLoading(String title) {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    @Override
    public void stopLoading() {
        //listviewFrame.refreshComplete();
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        //listviewFrame.refreshComplete();
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
        loadedTip.setTips(msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (editTextBodyLl != null && editTextBodyLl.getVisibility() == View.VISIBLE) {
                editTextBodyLl.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

