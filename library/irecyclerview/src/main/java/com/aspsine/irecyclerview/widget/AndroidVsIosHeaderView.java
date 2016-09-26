package com.aspsine.irecyclerview.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aspsine.irecyclerview.R;
import com.aspsine.irecyclerview.RefreshTrigger;

/**
 * Created by jaydenxiao on 16/9/7.
 */
public class AndroidVsIosHeaderView extends FrameLayout implements RefreshTrigger {

    private ImageView ivBatMan;

    private ImageView ivSuperMan;

    private ImageView ivVs;

    private int mHeight;

    AnimatorSet btnSexAnimatorSet ;

    public AndroidVsIosHeaderView(Context context) {
        this(context, null);
    }

    public AndroidVsIosHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AndroidVsIosHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_irecyclerview_bat_vs_supper_refresh_header_view, this);
        ivBatMan = (ImageView) findViewById(R.id.ivBatMan);
        ivSuperMan = (ImageView) findViewById(R.id.ivSuperMan);
        ivVs = (ImageView) findViewById(R.id.imageView);

        PropertyValuesHolder translationX1 = PropertyValuesHolder.ofFloat("translationX",0.0f,100.0f,0.0f);
        PropertyValuesHolder rotate = PropertyValuesHolder.ofFloat("rotationY",0.0f,380.0f,0.0f);
        PropertyValuesHolder translationX2 = PropertyValuesHolder.ofFloat("translationX",0.0f,-100.0f,0.0f);

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(ivBatMan,  translationX1);
        objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator1.setRepeatMode(ValueAnimator.INFINITE);

        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(ivSuperMan,translationX2);
        objectAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator2.setRepeatMode(ValueAnimator.INFINITE);

        ObjectAnimator objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(ivVs,  rotate);
        objectAnimator3.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator3.setRepeatMode(ValueAnimator.INFINITE);

         Animation animation =new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        Animation animation1 =new RotateAnimation(0f,-360f, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setFillAfter(true);
        animation.setDuration(2000);
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.setRepeatMode(ValueAnimator.INFINITE);
        animation1.setFillAfter(true);
        animation1.setDuration(2000);
        animation1.setRepeatCount(ValueAnimator.INFINITE);
        animation1.setRepeatMode(ValueAnimator.INFINITE);
        ivBatMan.setAnimation(animation);
        ivSuperMan.setAnimation(animation1);

        btnSexAnimatorSet = new AnimatorSet();
        btnSexAnimatorSet.playTogether(objectAnimator1, objectAnimator2,objectAnimator3);
        btnSexAnimatorSet.setDuration(2000);
        btnSexAnimatorSet.start();

    }

    @Override
    public void onStart(boolean automatic, int headerHeight, int finalHeight) {
        mHeight = headerHeight;
    }

    @Override
    public void onMove(boolean finished, boolean automatic, int moved) {
        if (!finished) {
            ivVs.setRotationY(moved / (float) mHeight * 360);
        } else {
            ivVs.setRotationY(moved / (float) mHeight * 360);
        }
    }

    @Override
    public void onRefresh() {
      if(!btnSexAnimatorSet.isStarted()){
          btnSexAnimatorSet.start();
      }

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onReset() {
        ivVs.setRotationY(0);
    }
}
