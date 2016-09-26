package com.aspsine.irecyclerview.universaladapter.abslistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;

import java.util.List;

public abstract class MultiItemAblistViewAdapter<T> extends CommonAblistViewAdapter<T>
{

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemAblistViewAdapter(Context context, List<T> datas,
                                      MultiItemTypeSupport<T> multiItemTypeSupport)
    {
        super(context, -1, datas);
        mMultiItemTypeSupport = multiItemTypeSupport;
        if (mMultiItemTypeSupport == null)
            throw new IllegalArgumentException("the mMultiItemTypeSupport can not be null.");
    }
    public MultiItemAblistViewAdapter(Context context,
                                      MultiItemTypeSupport<T> multiItemTypeSupport)
    {
        super(context, -1);
        mMultiItemTypeSupport = multiItemTypeSupport;
        if (mMultiItemTypeSupport == null)
            throw new IllegalArgumentException("the mMultiItemTypeSupport can not be null.");
    }

    @Override
    public int getViewTypeCount()
    {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getViewTypeCount();
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getItemViewType(position,
                    mDatas.get(position));
        return super.getItemViewType(position);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (mMultiItemTypeSupport == null)
            return super.getView(position, convertView, parent);

        int layoutId = mMultiItemTypeSupport.getLayoutId(position,
                getItem(position));
        ViewHolderHelper viewHolder = ViewHolderHelper.get(mContext, convertView, parent,
                layoutId, position);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

}
