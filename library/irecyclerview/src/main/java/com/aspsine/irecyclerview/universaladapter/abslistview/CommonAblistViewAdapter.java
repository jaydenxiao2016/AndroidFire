package com.aspsine.irecyclerview.universaladapter.abslistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aspsine.irecyclerview.bean.PageBean;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.DataIO;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonAblistViewAdapter<T> extends BaseAdapter implements DataIO<T>
{
	protected Context mContext;
	protected List<T> mDatas;
	protected LayoutInflater mInflater;
	protected PageBean pageBean;
	private int layoutId;

	public CommonAblistViewAdapter(Context context, int layoutId, List<T> datas )
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = datas;
		this.layoutId = layoutId;
		pageBean=new PageBean();
	}
	public CommonAblistViewAdapter(Context context, int layoutId)
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = new ArrayList<>();
		this.layoutId = layoutId;
		pageBean=new PageBean();
	}

	@Override
	public int getCount()
	{
		return mDatas.size();
	}

	@Override
	public T getItem(int position)
	{
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolderHelper holder = ViewHolderHelper.get(mContext, convertView, parent,
				layoutId, position);
		convert(holder, getItem(position));
		return holder.getConvertView();
	}

	public abstract void convert(ViewHolderHelper holder, T t);





	public int getItemCount()
	{
		return mDatas.size();
	}

	@Override
	public void add(T elem) {
		mDatas.add(elem);
		notifyDataSetChanged();
	}

	@Override
	public void addAt(int location, T elem) {
		mDatas.add(location, elem);
		notifyDataSetChanged();
	}

	@Override
	public void addAll(List<T> elements) {
		mDatas.addAll(elements);
		notifyDataSetChanged();
	}

	@Override
	public void addAllAt(int location, List<T> elements) {
		mDatas.addAll(location, elements);
		notifyDataSetChanged();
	}

	@Override
	public void remove(T elem) {
		mDatas.remove(elem);
		notifyDataSetChanged();
	}

	@Override
	public void removeAt(int index) {
		mDatas.remove(index);
		notifyDataSetChanged();
	}

	@Override
	public void removeAll(List<T> elements) {
		mDatas.removeAll(elements);
		notifyDataSetChanged();
	}

	@Override
	public void clear() {
		if (mDatas != null && mDatas.size() > 0) {
			mDatas.clear();
			notifyDataSetChanged();
		}
	}

	@Override
	public void replace(T oldElem, T newElem) {
		replaceAt(mDatas.indexOf(oldElem), newElem);
	}

	@Override
	public void replaceAt(int index, T elem) {
		mDatas.set(index, elem);
		notifyDataSetChanged();
	}

	@Override
	public void replaceAll(List<T> elements) {
		if (mDatas.size() > 0) {
			mDatas.clear();
		}
		mDatas.addAll(elements);
		notifyDataSetChanged();
	}

	@Override
	public T get(int position) {
		if (position >= mDatas.size())
			return null;
		return mDatas.get(position);
	}

	@Override
	public List<T> getAll() {
		return mDatas;
	}

	@Override
	public int getSize() {
		return mDatas.size();
	}

	@Override
	public boolean contains(T elem) {
		return mDatas.contains(elem);
	}
	/**
	 * 分页
	 * @return
	 */
	public PageBean getPageBean() {
		return pageBean;
	}
}
