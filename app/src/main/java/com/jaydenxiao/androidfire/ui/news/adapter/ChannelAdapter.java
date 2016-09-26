package com.jaydenxiao.androidfire.ui.news.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.app.AppConstant;
import com.jaydenxiao.androidfire.bean.NewsChannelTable;
import com.jaydenxiao.androidfire.ui.news.event.ChannelItemMoveEvent;
import com.jaydenxiao.androidfire.widget.ItemDragHelperCallback;
import com.jaydenxiao.common.baserx.RxBus;
import com.jaydenxiao.common.commonwidget.OnNoDoubleClickListener;

import java.util.Collections;

/**
 * des:选择频道
 * Created by xsf
 * on 2016.09.11:27
 */
public class ChannelAdapter  extends CommonRecycleViewAdapter<NewsChannelTable>implements
        ItemDragHelperCallback.OnItemMoveListener{

    private ItemDragHelperCallback mItemDragHelperCallback;
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setItemDragHelperCallback(ItemDragHelperCallback itemDragHelperCallback) {
        mItemDragHelperCallback = itemDragHelperCallback;
    }
    public ChannelAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolderHelper helper, NewsChannelTable newsChannelTable) {
        helper.setText(R.id.news_channel_tv,newsChannelTable.getNewsChannelName());
        if (newsChannelTable.getNewsChannelFixed()) {
            helper.setTextColor(R.id.news_channel_tv,ContextCompat.getColor(mContext,R.color.gray));
        }else{
            helper.setTextColor(R.id.news_channel_tv,ContextCompat.getColor(mContext,R.color.gray_deep));
        }
        handleLongPress(helper,newsChannelTable);
        handleOnClick(helper,newsChannelTable);
    }


    private void handleLongPress(ViewHolderHelper helper,final NewsChannelTable newsChannelTable) {
        if (mItemDragHelperCallback != null) {
            helper.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mItemDragHelperCallback.setLongPressEnabled(newsChannelTable.getNewsChannelIndex()==0?false:true);
                    return false;
                }
            });
        }
    }

    private void handleOnClick(final ViewHolderHelper helper,final NewsChannelTable newsChannelTable) {
        if (mOnItemClickListener != null) {
            helper.itemView.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (!newsChannelTable.getNewsChannelFixed()) {
                        mOnItemClickListener.onItemClick(v, helper.getLayoutPosition());
                    }
                }
            });
        }
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (isChannelFixed(fromPosition, toPosition)) {
            return false;
        }
        Collections.swap(getAll(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        RxBus.getInstance().post(AppConstant.CHANNEL_SWAP,new ChannelItemMoveEvent(fromPosition, toPosition));
        return true;
    }

    private boolean isChannelFixed(int fromPosition, int toPosition) {
        return (getAll().get(fromPosition).getNewsChannelFixed() ||
                getAll().get(toPosition).getNewsChannelFixed())&&(fromPosition==0||toPosition==0);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
