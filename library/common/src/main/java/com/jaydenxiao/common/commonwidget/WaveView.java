package com.jaydenxiao.common.commonwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * des:波浪view
 * Created by xsf
 * on 2016.12.02
 */
public class WaveView extends View {

    private Path mAbovePath, mBelowWavePath;
    private Paint mAboveWavePaint, mBelowWavePaint;
    private DrawFilter mDrawFilter;
    private float φ;
    private static int rangeY = 8;//Y轴波动幅度

    private OnWaveAnimationListener mWaveAnimationListener;

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化路径
        mAbovePath = new Path();
        mBelowWavePath = new Path();
        //初始化画笔
        mAboveWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAboveWavePaint.setAntiAlias(true);
        mAboveWavePaint.setStyle(Paint.Style.FILL);
        mAboveWavePaint.setColor(Color.WHITE);

        mBelowWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBelowWavePaint.setAntiAlias(true);
        mBelowWavePaint.setStyle(Paint.Style.FILL);
        mBelowWavePaint.setColor(Color.WHITE);
        mBelowWavePaint.setAlpha(80);
        //画布抗锯齿
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.setDrawFilter(mDrawFilter);

        mAbovePath.reset();
        mBelowWavePath.reset();

        φ -= 0.1f;

        float y, y2;

        double ω = 2 * Math.PI / getWidth();

        mAbovePath.moveTo(getLeft(), getBottom());
        mBelowWavePath.moveTo(getLeft(), getBottom());
        for (float x = 0; x <= getWidth(); x += 20) {
            /**
             *  y=Asin(ωx+φ)+k
             *  A—振幅越大，波形在y轴上最大与最小值的差值越大
             *  ω—角速度， 控制正弦周期(单位角度内震动的次数)
             *  φ—初相，反映在坐标系上则为图像的左右移动。这里通过不断改变φ,达到波浪移动效果
             *  k—偏距，反映在坐标系上则为图像的上移或下移。
             */
            y = (float) (rangeY * Math.cos(ω * x + φ) + 8);
            y2 = (float) (rangeY * Math.sin(ω * x + φ));
            mAbovePath.lineTo(x, y);
            mBelowWavePath.lineTo(x, y2);
            //回调 把y坐标的值传出去(在activity里面接收让小机器人随波浪一起摇摆)
            if (mWaveAnimationListener != null)
                mWaveAnimationListener.OnWaveAnimation(y);
        }
        mAbovePath.lineTo(getRight(), getBottom());
        mBelowWavePath.lineTo(getRight(), getBottom());

        canvas.drawPath(mAbovePath, mAboveWavePaint);
        canvas.drawPath(mBelowWavePath, mBelowWavePaint);

        postInvalidateDelayed(20);

    }

    public void setOnWaveAnimationListener(OnWaveAnimationListener l) {
        this.mWaveAnimationListener = l;
    }

    public interface OnWaveAnimationListener {
        void OnWaveAnimation(float y);
    }

    /**
     * 设置波动幅度
     *
     * @param rangeY
     */
    public static void setRangeY(int rangeY) {
        WaveView.rangeY = rangeY;
    }
}


