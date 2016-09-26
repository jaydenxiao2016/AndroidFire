package com.jaydenxiao.common.commonwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * description:不会滚动的gridview
 * Created by xsf
 * on 2016.04.15:04
 */
public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NoScrollGridView(Context context) {
        super(context);
    }
    public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
