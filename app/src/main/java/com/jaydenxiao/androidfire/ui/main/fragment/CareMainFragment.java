package com.jaydenxiao.androidfire.ui.main.fragment;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.ui.news.activity.AboutActivity;
import com.jaydenxiao.androidfire.ui.zone.activity.CircleZoneActivity;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;
import com.jaydenxiao.common.daynightmodeutils.ChangeModeController;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * des:关注主页
 * Created by xsf
 * on 2016.09.17:07
 */
public class CareMainFragment extends BaseFragment {
    @Bind(R.id.ll_friend_zone)
    LinearLayout llFriendZone;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.ntb)
    NormalTitleBar ntb;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_care_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        ntb.setTvLeftVisiable(false);
        ntb.setTitleText(getContext().getString(R.string.care_main_title));
    }
    @OnClick(R.id.ll_friend_zone)
    public void friendZone(){
        CircleZoneActivity.startAction(getContext());
    }
    @OnClick(R.id.ll_daynight_toggle)
    public void dayNightToggle(){
        ChangeModeController.toggleThemeSetting(getActivity());
    }
    @OnClick(R.id.ll_daynight_about)
    public void about(){
        AboutActivity.startAction(getContext());
    }
}
