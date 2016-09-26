package com.yuyh.library.imgsel.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SquareRelativeLayout extends RelativeLayout {
  
    public SquareRelativeLayout(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);  
    }  
  
    public SquareRelativeLayout(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public SquareRelativeLayout(Context context) {  
        super(context);  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),  
                getDefaultSize(0, heightMeasureSpec));  
  
        int childWidthSize = getMeasuredWidth();  
        // 高度和宽度一样  
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(  
                childWidthSize, MeasureSpec.EXACTLY);  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
    }  
  
}