package com.jaydenxiao.androidfire.ui.zone.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.ui.zone.DatasUtil;
import com.jaydenxiao.androidfire.ui.zone.adapter.CircleAdapter;
import com.jaydenxiao.androidfire.ui.zone.adapter.CommentAdapter;
import com.jaydenxiao.androidfire.ui.zone.adapter.FavortListAdapter;
import com.jaydenxiao.androidfire.ui.zone.bean.CircleItem;
import com.jaydenxiao.androidfire.ui.zone.bean.CommentConfig;
import com.jaydenxiao.androidfire.ui.zone.bean.CommentItem;
import com.jaydenxiao.androidfire.ui.zone.bean.FavortItem;
import com.jaydenxiao.androidfire.ui.zone.presenter.CircleZonePresenter;
import com.jaydenxiao.androidfire.ui.zone.spannable.ISpanClick;
import com.jaydenxiao.androidfire.ui.zone.widget.CommentDialog;
import com.jaydenxiao.androidfire.ui.zone.widget.CommentListView;
import com.jaydenxiao.androidfire.ui.zone.widget.ExpandableTextView;
import com.jaydenxiao.androidfire.ui.zone.widget.FavortListView;
import com.jaydenxiao.androidfire.ui.zone.widget.MultiImageView;
import com.jaydenxiao.common.baseapp.AppCache;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonutils.TimeUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.imagePager.BigImagePagerActivity;

import java.util.List;

/**
 * des:圈子viewholder
 * Created by xsf
 * on 2016.08.14:27
 */
public class ZoneViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private int type;
    private View itemView;
    private CircleZonePresenter mPresenter;
    private CircleItem circleItem;
    private int position;

    public ImageView headIv;
    public TextView nameTv;
    public TextView urlTipTv;
    /**
     * 动态的内容
     */
    public ExpandableTextView contentTv;
    public TextView timeTv;
    public TextView tvAddressOrDistance;
    public TextView deleteBtn;
    public TextView favortBtn;
    public TextView snsBtn;
    public LinearLayout ll_comment;
    /**
     * 点赞列表
     */
    public FavortListView favortListTv;

    public LinearLayout urlBody;
    public LinearLayout digCommentBody;
    public View digLine;

    /**
     * 评论列表
     */
    public CommentListView commentList;
    /**
     * 链接的图片
     */
    public ImageView urlImageIv;
    /**
     * 链接的标题
     */
    public TextView urlContentTv;
    /**
     * 图片
     */
    public MultiImageView multiImageView;
    //至关重要一步，复用自定义适配器
    public FavortListAdapter favortListAdapter;
    public CommentAdapter commentAdapter;

    public static ZoneViewHolder create(Context context, int type) {
        ZoneViewHolder imageViewHolder = new ZoneViewHolder(LayoutInflater.from(context).inflate(R.layout.item_circle_list, null), context,type );
        return imageViewHolder;
    }
    public ZoneViewHolder(View itemView, final Context context, int type) {
        super(itemView);
        this.itemView=itemView;
        this.type=type;
        this.mContext = context;
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        ViewStub linkOrImgViewStub = (ViewStub) itemView.findViewById(R.id.linkOrImgViewStub);
        switch (type) {
            case CircleAdapter.ITEM_VIEW_TYPE_URL:// 链接view
                linkOrImgViewStub.setLayoutResource(R.layout.item_circle_viewstub_urlbody);
                linkOrImgViewStub.inflate();
                LinearLayout urlBodyView = (LinearLayout) itemView.findViewById(R.id.urlBody);
                if (urlBodyView != null) {
                   urlBody = urlBodyView;
                   urlImageIv = (ImageView) itemView.findViewById(R.id.urlImageIv);
                   urlContentTv = (TextView) itemView.findViewById(R.id.urlContentTv);
                }
                break;
            case CircleAdapter.ITEM_VIEW_TYPE_IMAGE:// 图文view
            default:
                linkOrImgViewStub.setLayoutResource(R.layout.item_circle_viewstub_imgbody);
                linkOrImgViewStub.inflate();
                MultiImageView multiImageView = (MultiImageView) itemView.findViewById(R.id.multiImagView);
                if (multiImageView != null) {
                    this.multiImageView = multiImageView;
                }
                break;
        }
        headIv = (ImageView) itemView.findViewById(R.id.headIv);
        nameTv = (TextView) itemView.findViewById(R.id.nameTv);
        digLine = itemView.findViewById(R.id.lin_dig);

        contentTv = (ExpandableTextView) itemView.findViewById(R.id.contentTv);
        urlTipTv = (TextView) itemView.findViewById(R.id.urlTipTv);
        timeTv = (TextView) itemView.findViewById(R.id.timeTv);
        tvAddressOrDistance = (TextView) itemView.findViewById(R.id.tv_address_or_distance);
        deleteBtn = (TextView) itemView.findViewById(R.id.deleteBtn);
        favortBtn = (TextView) itemView.findViewById(R.id.favortBtn);
        snsBtn = (TextView) itemView.findViewById(R.id.commentBtn);
        ll_comment = (LinearLayout) itemView.findViewById(R.id.ll_comment);

        favortListTv = (FavortListView) itemView.findViewById(R.id.favortListTv);

        digCommentBody = (LinearLayout) itemView.findViewById(R.id.digCommentBody);

        commentList = (CommentListView) itemView.findViewById(R.id.commentList);
        commentAdapter = new CommentAdapter(mContext);
        favortListAdapter = new FavortListAdapter();

        favortListTv.setAdapter(favortListAdapter);
        commentList.setAdapter(commentAdapter);
    }

    /**
     * 设置数据
     * @param circleItem2
     * @param position2
     */
    public void setData(CircleZonePresenter mPresenter2, CircleItem circleItem2, final int position2){
        if(mPresenter2==null||circleItem2==null){
            return;
        }
        this.circleItem=circleItem2;
        this.mPresenter=mPresenter2;
        this.position=position2;
        final List<FavortItem> favortDatas = circleItem.getGoodjobs();
        final List<CommentItem> commentsDatas = circleItem.getReplys();
        boolean hasFavort = circleItem.getGoodjobs().size() > 0 ? true : false;
        boolean hasComment = circleItem.getReplys().size() > 0 ? true : false;
        //头像
        ImageLoaderUtils.displayRound(mContext, headIv, DatasUtil.getRandomPhotoUrl());
        nameTv.setText(circleItem.getNickName());
        timeTv.setText(TimeUtil.getfriendlyTime(circleItem.getCreateTime()));
        contentTv.setText(circleItem.getContent(), position);
        //距离
        tvAddressOrDistance.setText("广州 <7KM");
        contentTv.setVisibility(TextUtils.isEmpty(circleItem.getContent()) ? View.GONE : View.VISIBLE);
        //是否显示删除图标
        deleteBtn.setVisibility(AppCache.getInstance().getUserId().equals(circleItem.getUserId())?View.VISIBLE:View.GONE);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
                if (mPresenter != null) {
                    mPresenter.deleteCircle(circleItem.getId(), position);
                }
            }
        });
        //图片或链接显示
        switch (type) {
            case CircleAdapter.ITEM_VIEW_TYPE_URL:// 处理链接动态的链接内容和和图片
                String linkImg = circleItem.getLinkImg();
                String linkTitle = circleItem.getLinkTitle();
                ImageLoaderUtils.display(mContext, urlImageIv, linkImg);
                urlContentTv.setText(linkTitle);
                urlBody.setVisibility(View.VISIBLE);
                urlTipTv.setVisibility(View.VISIBLE);
                break;
            case CircleAdapter.ITEM_VIEW_TYPE_IMAGE:// 处理图片
            default:
                final List<String> photos = circleItem.getPictureList();
                if (photos != null && photos.size() > 0) {
                    multiImageView.setVisibility(View.VISIBLE);
                    multiImageView.setList(photos);
                    multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // 查看大图
                            BigImagePagerActivity.startImagePagerActivity((Activity) mContext, photos, position);
                        }
                    });
                } else {
                    multiImageView.setVisibility(View.GONE);
                }
                break;
        }

        //点赞和评论
        ll_comment.setVisibility(View.VISIBLE);
        if (hasFavort || hasComment) {
            //处理点赞列表
            if (hasFavort) {
                favortListTv.setSpanClickListener(new ISpanClick() {
                    @Override
                    public void onClick(int position) {
                        String userId = favortDatas.get(position).getUserId();
                        ToastUitl.showShort(userId);
                    }
                });
                favortListAdapter.setDatas(favortDatas);
                favortListAdapter.notifyDataSetChanged();
                favortListTv.setVisibility(View.VISIBLE);
                //favortBtn.setText(String.valueOf(favortDatas.size()));
            } else {
                favortListTv.setVisibility(View.GONE);
                favortBtn.setText("");
            }
            //处理评论列表
            if (hasComment) {
                //点击评论
                commentList.setOnItemClick(new CommentListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int commentPosition) {
                        CommentItem commentItem = commentsDatas.get(commentPosition);
                        if (AppCache.getInstance().getUserId().equals(commentItem.getUserId())) {//复制或者删除自己的评论
                            CommentDialog dialog = new CommentDialog(mContext, mPresenter, commentItem, position,commentPosition);
                            dialog.show();
                        } else {//回复别人的评论
                            if (mPresenter != null) {
                                CommentConfig config = new CommentConfig();
                                config.circlePosition = position;
                                config.commentPosition = commentPosition;
                                config.commentType = CommentConfig.Type.REPLY;
                                config.setPublishId(circleItem.getId());
                                config.setPublishUserId(circleItem.getUserId());//动态人userid
                                config.setId(commentItem.getUserId());//评论人userid
                                config.setName(commentItem.getUserNickname());//评论人nickname
                                mPresenter.showEditTextBody(config);
                            }
                        }
                    }
                });
                //长按评论
                commentList.setOnItemLongClick(new CommentListView.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(int commentPosition) {
                        //长按进行复制或者删除
                        CommentItem commentItem = commentsDatas.get(commentPosition);
                        CommentDialog dialog = new CommentDialog(mContext, mPresenter, commentItem, position,commentPosition);
                        dialog.show();
                    }
                });
                //snsBtn.setText(String.valueOf(commentsDatas.size()));
                commentAdapter.setDatas(commentsDatas);
                commentAdapter.notifyDataSetChanged();
                commentList.setVisibility(View.VISIBLE);
            } else {
                snsBtn.setText("");
                commentList.setVisibility(View.GONE);
            }
        } else {
            favortListTv.setVisibility(View.GONE);
            commentList.setVisibility(View.GONE);
            favortBtn.setText("");
            snsBtn.setText("");
        }
        digLine.setVisibility(hasFavort && hasComment ? View.VISIBLE : View.GONE);
        //评论
        snsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment(circleItem.getId(), circleItem.getUserId(), position);
            }
        });
        ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment(circleItem.getId(), circleItem.getUserId(), position);
            }
        });
        //点赞
        favortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否已点赞
                String curUserFavortId = circleItem.getCurUserFavortId();
                if (!TextUtils.isEmpty(curUserFavortId)) {
                    favort(circleItem.getId(), circleItem.getUserId(), position, "取消", view);
                } else {
                    favort(circleItem.getId(), circleItem.getUserId(), position, "赞", view);
                }
            }
        });
        //头像点击
        headIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳到个人朋友圈
                ToastUitl.showShort("头像点击了"+position);
            }
        });
        urlTipTv.setVisibility(View.GONE);

    }

    /**
     * //点赞、取消点赞
     */
    private long mLasttime = 0;
    private void favort(String publishId, String publishUserId, int circlePosition, String mTitle, View view) {
        if (System.currentTimeMillis() - mLasttime < 700)//防止快速点击操作
            return;
        mLasttime = System.currentTimeMillis();
        if (mPresenter != null) {
            if ("赞".equals(mTitle)) {
                mPresenter.addFavort(publishId, publishUserId, circlePosition, view);
            } else {//取消点赞
                mPresenter.deleteFavort(publishId, publishUserId, circlePosition);
            }
        }
    }
    /**
     * 评论
     */
    private void comment(String publishId, String publishUserId, int circlePosition) {
        if (mPresenter != null) {
            CommentConfig config = new CommentConfig();
            config.circlePosition = circlePosition;
            config.commentType = CommentConfig.Type.PUBLIC;
            config.setPublishId(publishId);
            config.setPublishUserId(publishUserId);
            mPresenter.showEditTextBody(config);
        }
    }
    public View getRootView(){
        return itemView.findViewById(R.id.ll_root);
    }
    public View getCommentListView(){
            return commentList;
    }
    public int getHeight(){
        if(itemView!=null){
        return itemView.getMeasuredHeight();
            }
            else{
            return 0;}
        }

}
