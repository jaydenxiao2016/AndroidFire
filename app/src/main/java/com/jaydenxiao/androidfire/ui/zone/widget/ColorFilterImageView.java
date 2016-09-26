package com.jaydenxiao.androidfire.ui.zone.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

/**
 * des:实现图像根据按下抬起动作变化颜色
 * Created by xsf
 * on 2016.07.11:14
 */
public class ColorFilterImageView extends ImageView implements OnTouchListener {
    public ColorFilterImageView(Context context) {
        this(context, null, 0);
    }

    public ColorFilterImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFilterImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:  // 按下时图像变灰
                setColorFilter(Color.GRAY, Mode.MULTIPLY);
                break;
            case MotionEvent.ACTION_UP:   // 手指离开或取消操作时恢复原色
            case MotionEvent.ACTION_CANCEL:
                setColorFilter(Color.TRANSPARENT);
                break;
            default:
                break;
        }
        return false;
    }
}
