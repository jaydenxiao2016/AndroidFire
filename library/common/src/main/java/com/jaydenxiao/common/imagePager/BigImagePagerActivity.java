package com.jaydenxiao.common.imagePager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jaydenxiao.common.R;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonwidget.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 查看大图 glide
 * Created by jaydenxiao
 */
public class BigImagePagerActivity extends BaseActivity{
    public static final String INTENT_IMGURLS = "imgurls";
    public static final String INTENT_POSITION = "position";
    private List<View> guideViewList = new ArrayList<View>();
    private LinearLayout guideGroup;

    public static void startImagePagerActivity(Activity activity, List<String> imgUrls, int position){
        Intent intent = new Intent(activity, BigImagePagerActivity.class);
        intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<String>(imgUrls));
        intent.putExtra(INTENT_POSITION, position);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            BigImagePagerActivity.this.finish();
            BigImagePagerActivity.this.overridePendingTransition(R.anim.fade_in,
                    R.anim.fade_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_image_pager;
    }

    @Override
    public void initView() {
        //设置透明状态栏
        SetTranslanteBar();

        ViewPager viewPager = (ViewPagerFixed) findViewById(R.id.pager);
        guideGroup = (LinearLayout) findViewById(R.id.guideGroup);

        int startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
        ArrayList<String> imgUrls = getIntent().getStringArrayListExtra(INTENT_IMGURLS);

        ImageAdapter mAdapter = new ImageAdapter(this);
        mAdapter.setDatas(imgUrls);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<guideViewList.size(); i++){
                    guideViewList.get(i).setSelected(i==position ? true : false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(startPos);

        addGuideView(guideGroup, startPos, imgUrls);
    }

    @Override
    public void initPresenter() {

    }

    private void addGuideView(LinearLayout guideGroup, int startPos, ArrayList<String> imgUrls) {
        if(imgUrls!=null && imgUrls.size()>0){
            guideViewList.clear();
            for (int i=0; i<imgUrls.size(); i++){
                View view = new View(this);
                view.setBackgroundResource(R.drawable.selector_guide_bg);
                view.setSelected(i==startPos ? true : false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.gudieview_width),
                        getResources().getDimensionPixelSize(R.dimen.gudieview_heigh));
                layoutParams.setMargins(10, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
            }
        }
    }

    private  class ImageAdapter extends PagerAdapter{

        private List<String> datas = new ArrayList<String>();
        private LayoutInflater inflater;
        private Context context;

        public void setDatas(List<String> datas) {
            if(datas != null )
                this.datas = datas;
        }

        public ImageAdapter(Context context){
            this.context = context;
            this.inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            if(datas == null) return 0;
            return datas.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = inflater.inflate(R.layout.item_pager_image, container, false);
            if(view != null){
                final PhotoView imageView = (PhotoView) view.findViewById(R.id.image);

                //单击图片退出
                imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float x, float y) {
                        BigImagePagerActivity.this.finish();
                        BigImagePagerActivity.this.overridePendingTransition(R.anim.act_fade_in_center,
                                R.anim.act_fade_out_center);
                    }
                });

                //loading
                final ProgressBar loading = new ProgressBar(context);
                FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingLayoutParams.gravity = Gravity.CENTER;
                loading.setLayoutParams(loadingLayoutParams);
                ((FrameLayout)view).addView(loading);

                final String imgurl = datas.get(position);

                loading.setVisibility(View.VISIBLE);
                Glide.with(context).load(imgurl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_empty_picture)
                        .thumbnail(0.1f)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                loading.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                loading.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(imageView);

                container.addView(view, 0);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


    }
}
