package com.aspsine.irecyclerview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.jaydenxiao.common.commonutils.LogUtils;

/**
 * Created by aspsine on 16/3/3.
 */
public class IRecyclerView extends RecyclerView {
    private static final String TAG = IRecyclerView.class.getSimpleName();

    private static final int STATUS_DEFAULT = 0;

    private static final int STATUS_SWIPING_TO_REFRESH = 1;

    private static final int STATUS_RELEASE_TO_REFRESH = 2;

    private static final int STATUS_REFRESHING = 3;

    private static final boolean DEBUG = false;

    private int mStatus;

    private boolean mIsAutoRefreshing;

    private boolean mRefreshEnabled;

    private boolean mLoadMoreEnabled;

    private int mRefreshFinalMoveOffset;

    private OnRefreshListener mOnRefreshListener;

    private OnLoadMoreListener mOnLoadMoreListener;

    private OnLoadMoreScrollListener mOnLoadMoreScrollListener;

    private RefreshHeaderLayout mRefreshHeaderContainer;

    private FrameLayout mLoadMoreFooterContainer;

    private LinearLayout mHeaderViewContainer;

    private LinearLayout mFooterViewContainer;

    private View mRefreshHeaderView;

    private View mLoadMoreFooterView;

    @LayoutRes
    int refreshHeaderLayoutRes = -1;
    @LayoutRes
    int loadMoreFooterLayoutRes = -1;

    public IRecyclerView(Context context) {
        this(context, null);
    }

    public IRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IRecyclerView, defStyle, 0);

        int refreshFinalMoveOffset = -1;
        boolean refreshEnabled;
        boolean loadMoreEnabled;

        try {
            refreshEnabled = a.getBoolean(R.styleable.IRecyclerView_refreshEnabled, false);
            loadMoreEnabled = a.getBoolean(R.styleable.IRecyclerView_loadMoreEnabled, false);
            refreshHeaderLayoutRes = a.getResourceId(R.styleable.IRecyclerView_refreshHeaderLayout, -1);
            loadMoreFooterLayoutRes = a.getResourceId(R.styleable.IRecyclerView_loadMoreFooterLayout, -1);
            refreshFinalMoveOffset = a.getDimensionPixelOffset(R.styleable.IRecyclerView_refreshFinalMoveOffset, -1);

        } finally {
            a.recycle();
        }

        setRefreshEnabled(refreshEnabled);

        setLoadMoreEnabled(loadMoreEnabled);

        if (refreshHeaderLayoutRes != -1) {
            setRefreshHeaderView(refreshHeaderLayoutRes);
        } else if (refreshEnabled) {
            refreshHeaderLayoutRes = R.layout.layout_irecyclerview_classic_refresh_header;
            setRefreshHeaderView(refreshHeaderLayoutRes);
        }
        if (loadMoreFooterLayoutRes != -1) {
            setLoadMoreFooterView(loadMoreFooterLayoutRes);
        } else if (loadMoreEnabled) {
            loadMoreFooterLayoutRes = R.layout.layout_irecyclerview_load_more_footer;
            setLoadMoreFooterView(loadMoreFooterLayoutRes);
        }
        if (refreshFinalMoveOffset != -1) {
            setRefreshFinalMoveOffset(refreshFinalMoveOffset);
        }
        setStatus(STATUS_DEFAULT);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (mRefreshHeaderView != null) {
            if (mRefreshHeaderView.getMeasuredHeight() > mRefreshFinalMoveOffset) {
                mRefreshFinalMoveOffset = 0;
            }
        }
    }

    /**
     * 设置是否支持下拉刷新 默认：支持
     *
     * @param enabled
     */
    public void setRefreshEnabled(boolean enabled) {
        this.mRefreshEnabled = enabled;
    }

    /**
     * 设置是否支持上拉加载更多 默认：支持
     *
     * @param enabled
     */
    public void setLoadMoreEnabled(boolean enabled) {
        this.mLoadMoreEnabled = enabled;
        if (mLoadMoreEnabled) {
            if (mOnLoadMoreScrollListener == null) {
                mOnLoadMoreScrollListener = new OnLoadMoreScrollListener() {
                    @Override
                    public void onLoadMore(RecyclerView recyclerView) {

                        if (mOnLoadMoreListener != null && mStatus == STATUS_DEFAULT) {
                            mOnLoadMoreListener.onLoadMore(mLoadMoreFooterView);
                        }
                    }
                };
            } else {
                removeOnScrollListener(mOnLoadMoreScrollListener);
            }
            addOnScrollListener(mOnLoadMoreScrollListener);
        } else {

            if (mLoadMoreFooterView != null) {
                removeLoadMoreFooterView();
            }

            if (mOnLoadMoreScrollListener != null) {
                removeOnScrollListener(mOnLoadMoreScrollListener);

            }
        }
    }

    /**
     * 设置刷新监听
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }

    /**
     * 设置加载更多监听
     *
     * @param listener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
    }

    /**
     * 设置刷新状态
     *
     * @param refreshing
     */
    public void setRefreshing(boolean refreshing) {
        if (mStatus == STATUS_DEFAULT && refreshing) {
            this.mIsAutoRefreshing = true;
            setStatus(STATUS_SWIPING_TO_REFRESH);
            startScrollDefaultStatusToRefreshingStatus();
        } else if (mStatus == STATUS_REFRESHING && !refreshing) {
            this.mIsAutoRefreshing = false;
            startScrollRefreshingStatusToDefaultStatus();
        } else {
            this.mIsAutoRefreshing = false;
            LogUtils.loge(TAG, "isRefresh = " + refreshing + " current status = " + mStatus);
        }
    }

    /**
     * 设置下拉触动刷新距离
     *
     * @param refreshFinalMoveOffset
     */
    public void setRefreshFinalMoveOffset(int refreshFinalMoveOffset) {
        this.mRefreshFinalMoveOffset = refreshFinalMoveOffset;
    }

    /**
     * 添加刷新header view
     *
     * @param refreshHeaderView
     */
    public void setRefreshHeaderView(View refreshHeaderView) {
        if (!isRefreshTrigger(refreshHeaderView)) {
            throw new ClassCastException("Refresh header view must be an implement of RefreshTrigger");
        }

        if (mRefreshHeaderView != null) {
            removeRefreshHeaderView();
        }
        if (mRefreshHeaderView != refreshHeaderView) {
            this.mRefreshHeaderView = refreshHeaderView;
            ensureRefreshHeaderContainer();
            mRefreshHeaderContainer.addView(refreshHeaderView);
        }
    }

    /**
     * 添加刷新header layout
     *
     * @param refreshHeaderLayoutRes
     */
    public void setRefreshHeaderView(@LayoutRes int refreshHeaderLayoutRes) {
        ensureRefreshHeaderContainer();
        final View refreshHeader = LayoutInflater.from(getContext()).inflate(refreshHeaderLayoutRes, mRefreshHeaderContainer, false);
        if (refreshHeader != null) {
            setRefreshHeaderView(refreshHeader);
        }
    }

    /**
     * 添加加载更多footer view
     *
     * @param loadMoreFooterView
     */
    public void setLoadMoreFooterView(View loadMoreFooterView) {
        if (mLoadMoreFooterView != null) {
            removeLoadMoreFooterView();
        }
        if (mLoadMoreFooterView != loadMoreFooterView) {
            this.mLoadMoreFooterView = loadMoreFooterView;
            ensureLoadMoreFooterContainer();
            mLoadMoreFooterContainer.addView(loadMoreFooterView);
        }
    }

    /**
     * 添加加载更多footer layout
     *
     * @param loadMoreFooterLayoutRes
     */
    public void setLoadMoreFooterView(@LayoutRes int loadMoreFooterLayoutRes) {
        ensureLoadMoreFooterContainer();
        final View loadMoreFooter = LayoutInflater.from(getContext()).inflate(loadMoreFooterLayoutRes, mLoadMoreFooterContainer, false);
        if (loadMoreFooter != null) {
            setLoadMoreFooterView(loadMoreFooter);
        }
    }

    public View getRefreshHeaderView() {
        return mRefreshHeaderView;
    }

    public View getLoadMoreFooterView() {
        return mLoadMoreFooterView;
    }

    public LinearLayout getHeaderContainer() {
        ensureHeaderViewContainer();
        return mHeaderViewContainer;
    }

    public LinearLayout getFooterContainer() {
        ensureFooterViewContainer();
        return mFooterViewContainer;
    }

    public void removeHeaderAllView() {
        if(mHeaderViewContainer!=null) {
            mHeaderViewContainer.removeAllViews();
            Adapter adapter = getAdapter();
            if (adapter != null) {
                adapter.notifyItemChanged(1);
            }
        }
    }

    public void addHeaderView(View headerView) {
        ensureHeaderViewContainer();
        mHeaderViewContainer.addView(headerView);
        Adapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyItemChanged(1);
        }
    }
    public void removeFooterView() {
        if(mFooterViewContainer!=null) {
            mFooterViewContainer.removeAllViews();
            Adapter adapter = getAdapter();
            if (adapter != null) {
                adapter.notifyItemChanged(adapter.getItemCount() - 2);
            }
        }
    }
    public void addFooterView(View footerView) {
        ensureFooterViewContainer();
        mFooterViewContainer.addView(footerView);
        Adapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyItemChanged(adapter.getItemCount() - 2);
        }
    }

    public RecyclerView.Adapter getIAdapter() {
        final WrapperAdapter wrapperAdapter = (WrapperAdapter) getAdapter();
        return wrapperAdapter.getAdapter();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        ensureRefreshHeaderContainer();
        ensureHeaderViewContainer();
        ensureFooterViewContainer();
        ensureLoadMoreFooterContainer();
        super.setAdapter(new WrapperAdapter(adapter, mRefreshHeaderContainer, mHeaderViewContainer, mFooterViewContainer, mLoadMoreFooterContainer));
    }

    private void ensureRefreshHeaderContainer() {
        if (mRefreshHeaderContainer == null) {
            mRefreshHeaderContainer = new RefreshHeaderLayout(getContext());
            mRefreshHeaderContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        }
    }

    private void ensureLoadMoreFooterContainer() {
        if (mLoadMoreFooterContainer == null) {
            mLoadMoreFooterContainer = new FrameLayout(getContext());
            mLoadMoreFooterContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void ensureHeaderViewContainer() {
        if (mHeaderViewContainer == null) {
            mHeaderViewContainer = new LinearLayout(getContext());
            mHeaderViewContainer.setOrientation(LinearLayout.VERTICAL);
            mHeaderViewContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void ensureFooterViewContainer() {
        if (mFooterViewContainer == null) {
            mFooterViewContainer = new LinearLayout(getContext());
            mFooterViewContainer.setOrientation(LinearLayout.VERTICAL);
            mFooterViewContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private boolean isRefreshTrigger(View refreshHeaderView) {
        return refreshHeaderView instanceof RefreshTrigger;
    }

    private void removeRefreshHeaderView() {
        if (mRefreshHeaderContainer != null) {
            mRefreshHeaderContainer.removeView(mRefreshHeaderView);
        }
    }

    private void removeLoadMoreFooterView() {
        if (mLoadMoreFooterContainer != null) {
            mLoadMoreFooterContainer.removeView(mLoadMoreFooterView);
        }
    }

    private int mActivePointerId = -1;
    private int mLastTouchX = 0;
    private int mLastTouchY = 0;

    /**
     * 监听滑动手势
     *
     * @param e
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        final int action = android.support.v4.view.MotionEventCompat.getActionMasked(e);
        final int actionIndex = android.support.v4.view.MotionEventCompat.getActionIndex(e);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mActivePointerId = android.support.v4.view.MotionEventCompat.getPointerId(e, 0);
                mLastTouchX = (int) (android.support.v4.view.MotionEventCompat.getX(e, actionIndex) + 0.5f);
                mLastTouchY = (int) (android.support.v4.view.MotionEventCompat.getY(e, actionIndex) + 0.5f);
            }
            break;

            case MotionEvent.ACTION_POINTER_DOWN: {
                mActivePointerId = android.support.v4.view.MotionEventCompat.getPointerId(e, actionIndex);
                mLastTouchX = (int) (android.support.v4.view.MotionEventCompat.getX(e, actionIndex) + 0.5f);
                mLastTouchY = (int) (android.support.v4.view.MotionEventCompat.getY(e, actionIndex) + 0.5f);
            }
            break;

            case android.support.v4.view.MotionEventCompat.ACTION_POINTER_UP: {
                onPointerUp(e);
            }
            break;
        }

        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        final int action = android.support.v4.view.MotionEventCompat.getActionMasked(e);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final int index = android.support.v4.view.MotionEventCompat.getActionIndex(e);
                mActivePointerId = android.support.v4.view.MotionEventCompat.getPointerId(e, 0);
                mLastTouchX = getMotionEventX(e, index);
                mLastTouchY = getMotionEventY(e, index);
            }
            break;

            case MotionEvent.ACTION_MOVE: {
                final int index = android.support.v4.view.MotionEventCompat.findPointerIndex(e, mActivePointerId);
                if (index < 0) {
                    LogUtils.loge(TAG, "Error processing scroll; pointer index for id " + index + " not found. Did any MotionEvents get skipped?");
                    return false;
                }

                final int x = getMotionEventX(e, index);
                final int y = getMotionEventY(e, index);

                final int dx = x - mLastTouchX;
                final int dy = y - mLastTouchY;

                mLastTouchX = x;
                mLastTouchY = y;

                final boolean triggerCondition = isEnabled() && mRefreshEnabled && mRefreshHeaderView != null && isFingerDragging() && canTriggerRefresh();
                if (DEBUG) {
                    LogUtils.logi(TAG, "triggerCondition = " + triggerCondition + "; mStatus = " + mStatus + "; dy = " + dy);
                }
                if (triggerCondition) {

                    final int refreshHeaderContainerHeight = mRefreshHeaderContainer.getMeasuredHeight();
                    final int refreshHeaderViewHeight = mRefreshHeaderView.getMeasuredHeight();

                    if (dy > 0 && mStatus == STATUS_DEFAULT) {
                        setStatus(STATUS_SWIPING_TO_REFRESH);
                        mRefreshTrigger.onStart(false, refreshHeaderViewHeight, mRefreshFinalMoveOffset);
                    } else if (dy < 0) {
                        if (mStatus == STATUS_SWIPING_TO_REFRESH && refreshHeaderContainerHeight <= 0) {
                            setStatus(STATUS_DEFAULT);
                        }
                        if (mStatus == STATUS_DEFAULT) {
                            break;
                        }
                    }

                    if (mStatus == STATUS_SWIPING_TO_REFRESH || mStatus == STATUS_RELEASE_TO_REFRESH) {
                        if (refreshHeaderContainerHeight >= refreshHeaderViewHeight) {
                            setStatus(STATUS_RELEASE_TO_REFRESH);
                        } else {
                            setStatus(STATUS_SWIPING_TO_REFRESH);
                        }
                        fingerMove(dy);
                        return true;
                    }
                }
            }
            break;

            case android.support.v4.view.MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = android.support.v4.view.MotionEventCompat.getActionIndex(e);
                mActivePointerId = android.support.v4.view.MotionEventCompat.getPointerId(e, index);
                mLastTouchX = getMotionEventX(e, index);
                mLastTouchY = getMotionEventY(e, index);
            }
            break;

            case android.support.v4.view.MotionEventCompat.ACTION_POINTER_UP: {
                onPointerUp(e);
            }
            break;

            case MotionEvent.ACTION_UP: {
                onFingerUpStartAnimating();
            }
            break;

            case MotionEvent.ACTION_CANCEL: {
                onFingerUpStartAnimating();
            }
            break;
        }
        return super.onTouchEvent(e);
    }

    private boolean isFingerDragging() {
        return getScrollState() == SCROLL_STATE_DRAGGING;
    }

    public boolean canTriggerRefresh() {
        final Adapter adapter = getAdapter();
        if (adapter == null || adapter.getItemCount() <= 0) {
            return true;
        }
        View firstChild = getChildAt(0);
        int position = getChildLayoutPosition(firstChild);
        if (position == 0) {
            if (firstChild.getTop() == mRefreshHeaderContainer.getTop()) {
                return true;
            }
        }
        return false;
    }

    private int getMotionEventX(MotionEvent e, int pointerIndex) {
        return (int) (android.support.v4.view.MotionEventCompat.getX(e, pointerIndex) + 0.5f);
    }

    private int getMotionEventY(MotionEvent e, int pointerIndex) {
        return (int) (android.support.v4.view.MotionEventCompat.getY(e, pointerIndex) + 0.5f);
    }

    private void fingerMove(int dy) {
        int ratioDy = (int) (dy * 0.5f + 0.5);
        int offset = mRefreshHeaderContainer.getMeasuredHeight();
        int finalDragOffset = mRefreshFinalMoveOffset;

        int nextOffset = offset + ratioDy;
        if (finalDragOffset > 0) {
            if (nextOffset > finalDragOffset) {
                ratioDy = finalDragOffset - offset;
            }
        }

        if (nextOffset < 0) {
            ratioDy = -offset;
        }
        move(ratioDy);
    }

    private void move(int dy) {
        if (dy != 0) {
            int height = mRefreshHeaderContainer.getMeasuredHeight() + dy;
            setRefreshHeaderContainerHeight(height);
            mRefreshTrigger.onMove(false, false, height);
        }
    }

    private void setRefreshHeaderContainerHeight(int height) {
        mRefreshHeaderContainer.getLayoutParams().height = height;
        mRefreshHeaderContainer.requestLayout();
    }

    private void startScrollDefaultStatusToRefreshingStatus() {
        mRefreshTrigger.onStart(true, mRefreshHeaderView.getMeasuredHeight(), mRefreshFinalMoveOffset);

        int targetHeight = mRefreshHeaderView.getMeasuredHeight();
        int currentHeight = mRefreshHeaderContainer.getMeasuredHeight();
        startScrollAnimation(400, new AccelerateInterpolator(), currentHeight, targetHeight);
    }

    private void startScrollSwipingToRefreshStatusToDefaultStatus() {
        final int targetHeight = 0;
        final int currentHeight = mRefreshHeaderContainer.getMeasuredHeight();
        startScrollAnimation(300, new DecelerateInterpolator(), currentHeight, targetHeight);
    }

    private void startScrollReleaseStatusToRefreshingStatus() {
        mRefreshTrigger.onRelease();

        final int targetHeight = mRefreshHeaderView.getMeasuredHeight();
        final int currentHeight = mRefreshHeaderContainer.getMeasuredHeight();
        startScrollAnimation(300, new DecelerateInterpolator(), currentHeight, targetHeight);
    }

    private void startScrollRefreshingStatusToDefaultStatus() {
        mRefreshTrigger.onComplete();

        final int targetHeight = 0;
        final int currentHeight = mRefreshHeaderContainer.getMeasuredHeight();
        startScrollAnimation(400, new DecelerateInterpolator(), currentHeight, targetHeight);
    }

    ValueAnimator mScrollAnimator;

    private void startScrollAnimation(final int time, final Interpolator interpolator, int value, int toValue) {
        if (mScrollAnimator == null) {
            mScrollAnimator = new ValueAnimator();
        }
        //cancel
        mScrollAnimator.removeAllUpdateListeners();
        mScrollAnimator.removeAllListeners();
        mScrollAnimator.cancel();

        //reset new value
        mScrollAnimator.setIntValues(value, toValue);
        mScrollAnimator.setDuration(time);
        mScrollAnimator.setInterpolator(interpolator);
        mScrollAnimator.addUpdateListener(mAnimatorUpdateListener);
        mScrollAnimator.addListener(mAnimationListener);
        mScrollAnimator.start();
    }

    ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            final int height = (Integer) animation.getAnimatedValue();
            setRefreshHeaderContainerHeight(height);
            switch (mStatus) {
                case STATUS_SWIPING_TO_REFRESH: {
                    mRefreshTrigger.onMove(false, true, height);
                }
                break;

                case STATUS_RELEASE_TO_REFRESH: {
                    mRefreshTrigger.onMove(false, true, height);
                }
                break;

                case STATUS_REFRESHING: {
                    mRefreshTrigger.onMove(true, true, height);
                }
                break;
            }

        }
    };

    Animator.AnimatorListener mAnimationListener = new SimpleAnimatorListener() {
        @Override
        public void onAnimationEnd(Animator animation) {
            int lastStatus = mStatus;

            switch (mStatus) {
                case STATUS_SWIPING_TO_REFRESH: {
                    if (mIsAutoRefreshing) {
                        mRefreshHeaderContainer.getLayoutParams().height = mRefreshHeaderView.getMeasuredHeight();
                        mRefreshHeaderContainer.requestLayout();
                        setStatus(STATUS_REFRESHING);
                        if (mOnRefreshListener != null) {
                            mOnRefreshListener.onRefresh();
                            mRefreshTrigger.onRefresh();
                        }
                    } else {
                        mRefreshHeaderContainer.getLayoutParams().height = 0;
                        mRefreshHeaderContainer.requestLayout();
                        setStatus(STATUS_DEFAULT);
                    }
                }
                break;

                case STATUS_RELEASE_TO_REFRESH: {
                    mRefreshHeaderContainer.getLayoutParams().height = mRefreshHeaderView.getMeasuredHeight();
                    mRefreshHeaderContainer.requestLayout();
                    setStatus(STATUS_REFRESHING);
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onRefresh();
                        mRefreshTrigger.onRefresh();
                    }
                }
                break;

                case STATUS_REFRESHING: {
                    mIsAutoRefreshing = false;
                    mRefreshHeaderContainer.getLayoutParams().height = 0;
                    mRefreshHeaderContainer.requestLayout();
                    setStatus(STATUS_DEFAULT);
                    mRefreshTrigger.onReset();
                }
                break;
            }
            if (DEBUG) {
                LogUtils.logi(TAG, "onAnimationEnd " + getStatusLog(lastStatus) + " -> " + getStatusLog(mStatus) + " ;refresh view height:" + mRefreshHeaderContainer.getMeasuredHeight());
            }
        }
    };

    private void onFingerUpStartAnimating() {
        if (mStatus == STATUS_RELEASE_TO_REFRESH) {
            startScrollReleaseStatusToRefreshingStatus();
        } else if (mStatus == STATUS_SWIPING_TO_REFRESH) {
            startScrollSwipingToRefreshStatusToDefaultStatus();
        }
    }

    private void onPointerUp(MotionEvent e) {
        final int actionIndex = android.support.v4.view.MotionEventCompat.getActionIndex(e);
        if (android.support.v4.view.MotionEventCompat.getPointerId(e, actionIndex) == mActivePointerId) {
            // Pick a new pointer to pick up the slack.
            final int newIndex = actionIndex == 0 ? 1 : 0;
            mActivePointerId = android.support.v4.view.MotionEventCompat.getPointerId(e, newIndex);
            mLastTouchX = getMotionEventX(e, newIndex);
            mLastTouchY = getMotionEventY(e, newIndex);
        }
    }

    RefreshTrigger mRefreshTrigger = new RefreshTrigger() {
        @Override
        public void onStart(boolean automatic, int headerHeight, int finalHeight) {
            if (mRefreshHeaderView != null && mRefreshHeaderView instanceof RefreshTrigger) {
                RefreshTrigger trigger = (RefreshTrigger) mRefreshHeaderView;
                trigger.onStart(automatic, headerHeight, finalHeight);
            }
        }

        @Override
        public void onMove(boolean finished, boolean automatic, int moved) {
            if (mRefreshHeaderView != null && mRefreshHeaderView instanceof RefreshTrigger) {
                RefreshTrigger trigger = (RefreshTrigger) mRefreshHeaderView;
                trigger.onMove(finished, automatic, moved);
            }
        }

        @Override
        public void onRefresh() {
            if (mRefreshHeaderView != null && mRefreshHeaderView instanceof RefreshTrigger) {
                RefreshTrigger trigger = (RefreshTrigger) mRefreshHeaderView;
                trigger.onRefresh();
            }
        }

        @Override
        public void onRelease() {
            if (mRefreshHeaderView != null && mRefreshHeaderView instanceof RefreshTrigger) {
                RefreshTrigger trigger = (RefreshTrigger) mRefreshHeaderView;
                trigger.onRelease();
            }
        }

        @Override
        public void onComplete() {
            if (mRefreshHeaderView != null && mRefreshHeaderView instanceof RefreshTrigger) {
                RefreshTrigger trigger = (RefreshTrigger) mRefreshHeaderView;
                trigger.onComplete();
            }
        }

        @Override
        public void onReset() {
            if (mRefreshHeaderView != null && mRefreshHeaderView instanceof RefreshTrigger) {
                RefreshTrigger trigger = (RefreshTrigger) mRefreshHeaderView;
                trigger.onReset();
            }
        }
    };

    private void setStatus(int status) {
        this.mStatus = status;
        if (DEBUG) {
            printStatusLog();
        }
    }

    private void printStatusLog() {
        LogUtils.logi(TAG, getStatusLog(mStatus));
    }

    private String getStatusLog(int status) {
        final String statusLog;
        switch (status) {
            case STATUS_DEFAULT:
                statusLog = "status_default";
                break;

            case STATUS_SWIPING_TO_REFRESH:
                statusLog = "status_swiping_to_refresh";
                break;

            case STATUS_RELEASE_TO_REFRESH:
                statusLog = "status_release_to_refresh";
                break;

            case STATUS_REFRESHING:
                statusLog = "status_refreshing";
                break;
            default:
                statusLog = "status_illegal!";
                break;
        }
        return statusLog;
    }


    /**
     * 设置上拉加载更多状态
     *
     * @param status
     */
    public void setLoadMoreStatus(LoadMoreFooterView.Status status) {
        if (mLoadMoreFooterView != null && mLoadMoreFooterView instanceof LoadMoreFooterView) {
            ((LoadMoreFooterView) mLoadMoreFooterView).setStatus(status);
        }
    }

    /**
     * 获取上拉加载更多状态
     *
     * @return
     */
    public boolean canLoadMore() {
        if (mLoadMoreFooterView != null && mLoadMoreFooterView instanceof LoadMoreFooterView) {
            return ((LoadMoreFooterView) mLoadMoreFooterView).canLoadMore();
        } else {
            return false;
        }
    }


}
