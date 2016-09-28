package com.jaydenxiao.androidfire.ui.main.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.bean.PhotoGirl;
import com.jaydenxiao.androidfire.ui.news.activity.PhotosDetailActivity;
import com.jaydenxiao.androidfire.ui.news.contract.PhotoListContract;
import com.jaydenxiao.androidfire.ui.news.model.PhotosListModel;
import com.jaydenxiao.androidfire.ui.news.presenter.PhotosListPresenter;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;

import java.util.List;

import butterknife.Bind;

/**
 * des:图片首页
 * Created by xsf
 * on 2016.09.11:49
 */
public class PhotosMainFragment extends BaseFragment<PhotosListPresenter,PhotosListModel> implements PhotoListContract.View ,OnRefreshListener,OnLoadMoreListener{
    @Bind(R.id.ntb)
    NormalTitleBar ntb;
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private CommonRecycleViewAdapter<PhotoGirl>adapter;
    private static int SIZE = 20;
    private int mStartPage = 1;

    @Override
    protected int getLayoutResource() {
        return R.layout.act_photos_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        ntb.setTvLeftVisiable(false);
        ntb.setTitleText(getString(R.string.girl_title));
        adapter=new CommonRecycleViewAdapter<PhotoGirl>(getContext(),R.layout.item_photo) {
            @Override
            public void convert(ViewHolderHelper helper,final PhotoGirl photoGirl) {
                ImageView imageView=helper.getView(R.id.iv_photo);
                Glide.with(mContext).load(photoGirl.getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(com.jaydenxiao.common.R.drawable.ic_image_loading)
                        .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                        .centerCrop().override(1090, 1090*3/4)
                        .crossFade().into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhotosDetailActivity.startAction(mContext,photoGirl.getUrl());
                    }
                });
            }
        };
        irc.setAdapter(adapter);
        irc.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        irc.setOnLoadMoreListener(this);
        irc.setOnRefreshListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irc.smoothScrollToPosition(0);
            }
        });
        mPresenter.getPhotosListDataRequest(SIZE, mStartPage);
    }

    @Override
    public void returnPhotosListData(List<PhotoGirl> photoGirls) {
        if (photoGirls != null) {
            mStartPage +=1;
            if (adapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                adapter.replaceAll(photoGirls);
            } else {
                if (photoGirls.size() > 0) {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    adapter.addAll(photoGirls);
                } else {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    @Override
    public void showLoading(String title) {
        if(adapter.getPageBean().isRefresh())
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if( adapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
            irc.setRefreshing(false);
        }else{
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void onRefresh() {
        adapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getPhotosListDataRequest(SIZE, mStartPage);
    }
    @Override
    public void onLoadMore(View loadMoreView) {
        adapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getPhotosListDataRequest(SIZE, mStartPage);
    }

}
