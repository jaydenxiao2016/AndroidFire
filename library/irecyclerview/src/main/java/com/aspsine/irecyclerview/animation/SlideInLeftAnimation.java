package com.aspsine.irecyclerview.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * 左侧划入动画
 */
public class SlideInLeftAnimation implements BaseAnimation {


  @Override
  public Animator[] getAnimators(View view) {
    return new Animator[] {
        ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)
    };
  }
}
