package com.aspsine.irecyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by aspsine on 16/3/12.
 */
public class WrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected static final int REFRESH_HEADER = Integer.MIN_VALUE;
    protected static final int HEADER = Integer.MIN_VALUE + 1;
    protected static final int FOOTER = Integer.MAX_VALUE - 1;
    protected static final int LOAD_MORE_FOOTER = Integer.MAX_VALUE;

    private final RecyclerView.Adapter mAdapter;

    private final RefreshHeaderLayout mRefreshHeaderContainer;

    private final FrameLayout mLoadMoreFooterContainer;

    private final LinearLayout mHeaderContainer;

    private final LinearLayout mFooterContainer;

    //private View emptyView;

    private RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            WrapperAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            WrapperAdapter.this.notifyItemRangeChanged(positionStart + 2, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            WrapperAdapter.this.notifyItemRangeChanged(positionStart + 2, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            WrapperAdapter.this.notifyItemRangeInserted(positionStart + 2, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            WrapperAdapter.this.notifyItemRangeRemoved(positionStart + 2, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            WrapperAdapter.this.notifyDataSetChanged();
        }
    };

    public WrapperAdapter(RecyclerView.Adapter adapter, RefreshHeaderLayout refreshHeaderContainer, LinearLayout headerContainer, LinearLayout footerContainer, FrameLayout loadMoreFooterContainer) {
        this.mAdapter = adapter;
        this.mRefreshHeaderContainer = refreshHeaderContainer;
        this.mHeaderContainer = headerContainer;
        this.mFooterContainer = footerContainer;
        this.mLoadMoreFooterContainer = loadMoreFooterContainer;
        mAdapter.registerAdapterDataObserver(mObserver);
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    WrapperAdapter wrapperAdapter = (WrapperAdapter) recyclerView.getAdapter();
                    if (isFullSpanType(wrapperAdapter.getItemViewType(position))) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;

                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        int type = getItemViewType(position);
        if (isFullSpanType(type)) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                lp.setFullSpan(true);
            }
        }
    }

    private boolean isFullSpanType(int type) {
        return type == REFRESH_HEADER || type == HEADER || type == FOOTER || type == LOAD_MORE_FOOTER;
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return REFRESH_HEADER;
        } else if (position == 1) {
            return HEADER;
        } else if (1 < position && position < mAdapter.getItemCount() + 2) {
            return mAdapter.getItemViewType(position - 2);
        } else if (position == mAdapter.getItemCount() + 2) {
            return FOOTER;
        } else if (position == mAdapter.getItemCount() + 3) {
            return LOAD_MORE_FOOTER;
        }

        throw new IllegalArgumentException("Wrong type! Position = " + position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == REFRESH_HEADER) {
            return new RefreshHeaderContainerViewHolder(mRefreshHeaderContainer);
        } else if (viewType == HEADER) {
            return new HeaderContainerViewHolder(mHeaderContainer);
        } else if (viewType == FOOTER) {
            return new FooterContainerViewHolder(mFooterContainer);
        } else if (viewType == LOAD_MORE_FOOTER) {
            return new LoadMoreFooterContainerViewHolder(mLoadMoreFooterContainer);
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (1 < position && position < mAdapter.getItemCount() + 2) {
            mAdapter.onBindViewHolder(holder, position - 2);
        }
    }

    static class RefreshHeaderContainerViewHolder extends RecyclerView.ViewHolder {

        public RefreshHeaderContainerViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class HeaderContainerViewHolder extends RecyclerView.ViewHolder {

        public HeaderContainerViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class FooterContainerViewHolder extends RecyclerView.ViewHolder {

        public FooterContainerViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class LoadMoreFooterContainerViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreFooterContainerViewHolder(View itemView) {
            super(itemView);
        }
    }

//    public void setEmptyView(View emptyView)
//    {
//        this.emptyView = emptyView;
//        checkIfEmpty();
//    }
//    /**
//     * 检查数据是否为空
//     */
//    void checkIfEmpty()
//    {
//        if (emptyView != null)
//        {
//            emptyView.setVisibility(mAdapter.getItemCount() > 4 ? View.GONE : View.VISIBLE);
//        }
//    }
}
