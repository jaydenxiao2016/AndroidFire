package com.jaydenxiao.androidfire.ui.zone.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.jaydenxiao.androidfire.ui.zone.adapter.CommentAdapter;


/**
 * des:评论列表
 * Created by xsf
 * on 2016.07.11:11
 */
public class CommentListView extends LinearLayout {

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs){
        super(context, attrs);

    }

    public CommentListView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }


    public void setAdapter(CommentAdapter adapter){
        adapter.bindListView(this);
    }

    public void setOnItemClick(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClick(OnItemLongClickListener listener){
        mOnItemLongClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return mOnItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener(){
        return mOnItemLongClickListener;
    }


    public static interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public static interface OnItemLongClickListener{
        public void onItemLongClick(int position);
    }
}
