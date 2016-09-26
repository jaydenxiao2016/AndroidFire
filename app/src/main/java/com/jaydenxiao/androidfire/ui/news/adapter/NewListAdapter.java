package com.jaydenxiao.androidfire.ui.news.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemRecycleViewAdapter;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemTypeSupport;
import com.jaydenxiao.androidfire.app.AppApplication;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.bean.NewsPhotoDetail;
import com.jaydenxiao.androidfire.bean.NewsSummary;
import com.jaydenxiao.androidfire.ui.news.activity.NewsDetailActivity;
import com.jaydenxiao.androidfire.ui.news.activity.NewsPhotoDetailActivity;
import com.jaydenxiao.common.commonutils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * des:新闻列表适配器
 * Created by xsf
 * on 2016.09.10:49
 */
public class NewListAdapter  extends MultiItemRecycleViewAdapter<NewsSummary>
{
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_PHOTO_ITEM =1;

    public NewListAdapter(Context context,final List<NewsSummary> datas)
    {
        super(context, datas, new MultiItemTypeSupport<NewsSummary>()
        {

            @Override
            public int getLayoutId(int type) {
               if(type==TYPE_PHOTO_ITEM){
                   return R.layout.item_news_photo;
               }else{
                   return R.layout.item_news;
               }
            }

            @Override
            public int getItemViewType(int position, NewsSummary msg)
            {
                if (!TextUtils.isEmpty(msg.getDigest()))
                {
                    return TYPE_ITEM;
                }
                return TYPE_PHOTO_ITEM;
            }
        });
    }

    @Override
    public void convert(ViewHolderHelper holder, NewsSummary newsSummary) {
        switch (holder.getLayoutId())
        {
            case R.layout.item_news:
                setItemValues(holder,newsSummary,getPosition(holder));
                break;
            case R.layout.item_news_photo:
                setPhotoItemValues(holder,newsSummary,getPosition(holder));
                break;
        }
    }

    /**
     * 普通样式
     * @param holder
     * @param newsSummary
     * @param position
     */
    private void setItemValues(final ViewHolderHelper holder, final NewsSummary newsSummary, final int position) {
        String title = newsSummary.getLtitle();
        if (title == null) {
            title = newsSummary.getTitle();
        }
        String ptime = newsSummary.getPtime();
        String digest = newsSummary.getDigest();
        String imgSrc = newsSummary.getImgsrc();

        holder.setText(R.id.news_summary_title_tv,title);
        holder.setText(R.id.news_summary_ptime_tv,ptime);
        holder.setText(R.id.news_summary_digest_tv,digest);
        holder.setImageUrl(R.id.news_summary_photo_iv,imgSrc);
        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsDetailActivity.startAction(mContext,holder.getView(R.id.news_summary_photo_iv),newsSummary.getPostid(),newsSummary.getImgsrc());
            }
        });
    }


    /**
     * 图文样式
     * @param holder
     * @param position
     */
    private void setPhotoItemValues(ViewHolderHelper holder, final NewsSummary newsSummary, int position) {
        String title = newsSummary.getTitle();
        String ptime = newsSummary.getPtime();
        holder.setText(R.id.news_summary_title_tv,title);
        holder.setText(R.id.news_summary_ptime_tv,ptime);
        setImageView(holder, newsSummary);
        holder.setOnClickListener(R.id.ll_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsPhotoDetailActivity.startAction(mContext,getPhotoDetail(newsSummary));
            }
        });
    }
    private NewsPhotoDetail getPhotoDetail( NewsSummary newsSummary) {
        NewsPhotoDetail newsPhotoDetail = new NewsPhotoDetail();
        newsPhotoDetail.setTitle(newsSummary.getTitle());
        setPictures(newsSummary, newsPhotoDetail);
        return newsPhotoDetail;
    }

    private void setPictures(NewsSummary newsSummary, NewsPhotoDetail newsPhotoDetail) {
        List<NewsPhotoDetail.Picture> pictureList = new ArrayList<>();
        if (newsSummary.getAds() != null) {
            for (NewsSummary.AdsBean entity : newsSummary.getAds()) {
                setValuesAndAddToList(pictureList, entity.getTitle(), entity.getImgsrc());
            }
        } else if (newsSummary.getImgextra() != null) {
            for (NewsSummary.ImgextraBean entity : newsSummary.getImgextra()) {
                setValuesAndAddToList(pictureList, null, entity.getImgsrc());
            }
        } else {
            setValuesAndAddToList(pictureList, null, newsSummary.getImgsrc());
        }

        newsPhotoDetail.setPictures(pictureList);
    }
    private void setValuesAndAddToList(List<NewsPhotoDetail.Picture> pictureList, String title, String imgsrc) {
        NewsPhotoDetail.Picture picture = new NewsPhotoDetail.Picture();
        if (title != null) {
            picture.setTitle(title);
        }
        picture.setImgSrc(imgsrc);

        pictureList.add(picture);
    }

    private void setImageView(ViewHolderHelper holder, NewsSummary newsSummary) {
        int PhotoThreeHeight = (int) DisplayUtil.dip2px(90);
        int PhotoTwoHeight = (int) DisplayUtil.dip2px(120);
        int PhotoOneHeight = (int)DisplayUtil.dip2px(150);

        String imgSrcLeft = null;
        String imgSrcMiddle = null;
        String imgSrcRight = null;
        LinearLayout news_summary_photo_iv_group=holder.getView(R.id.news_summary_photo_iv_group);
        ViewGroup.LayoutParams layoutParams = news_summary_photo_iv_group.getLayoutParams();

        if (newsSummary.getAds() != null) {
            List<NewsSummary.AdsBean> adsBeanList = newsSummary.getAds();
            int size = adsBeanList.size();
            if (size >= 3) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();
                imgSrcMiddle = adsBeanList.get(1).getImgsrc();
                imgSrcRight = adsBeanList.get(2).getImgsrc();
                layoutParams.height = PhotoThreeHeight;
                holder.setText(R.id.news_summary_title_tv, AppApplication.getAppContext()
                        .getString(R.string.photo_collections, adsBeanList.get(0).getTitle()));
            } else if (size >= 2) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();
                imgSrcMiddle = adsBeanList.get(1).getImgsrc();

                layoutParams.height = PhotoTwoHeight;
            } else if (size >= 1) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();

                layoutParams.height = PhotoOneHeight;
            }
        } else if (newsSummary.getImgextra() != null) {
            int size = newsSummary.getImgextra().size();
            if (size >= 3) {
                imgSrcLeft = newsSummary.getImgextra().get(0).getImgsrc();
                imgSrcMiddle = newsSummary.getImgextra().get(1).getImgsrc();
                imgSrcRight = newsSummary.getImgextra().get(2).getImgsrc();

                layoutParams.height = PhotoThreeHeight;
            } else if (size >= 2) {
                imgSrcLeft = newsSummary.getImgextra().get(0).getImgsrc();
                imgSrcMiddle = newsSummary.getImgextra().get(1).getImgsrc();

                layoutParams.height = PhotoTwoHeight;
            } else if (size >= 1) {
                imgSrcLeft = newsSummary.getImgextra().get(0).getImgsrc();

                layoutParams.height = PhotoOneHeight;
            }
        } else {
            imgSrcLeft = newsSummary.getImgsrc();

            layoutParams.height = PhotoOneHeight;
        }

        setPhotoImageView(holder, imgSrcLeft, imgSrcMiddle, imgSrcRight);
        news_summary_photo_iv_group.setLayoutParams(layoutParams);
    }

    private void setPhotoImageView(ViewHolderHelper holder, String imgSrcLeft, String imgSrcMiddle, String imgSrcRight) {
        if (imgSrcLeft != null) {
            holder.setVisible(R.id.news_summary_photo_iv_left,true);
            holder.setImageUrl(R.id.news_summary_photo_iv_left,imgSrcLeft);
        } else {
            holder.setVisible(R.id.news_summary_photo_iv_left,false);
        }
        if (imgSrcMiddle != null) {
            holder.setVisible(R.id.news_summary_photo_iv_middle,true);
            holder.setImageUrl(R.id.news_summary_photo_iv_middle,imgSrcMiddle);
        } else {
            holder.setVisible(R.id.news_summary_photo_iv_middle,false);
        }
        if (imgSrcRight != null) {
            holder.setVisible(R.id.news_summary_photo_iv_right,true);
            holder.setImageUrl(R.id.news_summary_photo_iv_right,imgSrcRight);
        } else {
            holder.setVisible(R.id.news_summary_photo_iv_right,false);
        }
    }
}
