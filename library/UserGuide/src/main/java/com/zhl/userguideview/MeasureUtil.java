package com.zhl.userguideview;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * * des:测量工具类
 * Created by xsf
 * on 2016.08.11:59
 */
public final class MeasureUtil {
	public static final int RATION_WIDTH = 0;
	public static final int RATION_HEIGHT = 1;
	
	/**
	 * 获取屏幕尺寸
	 * 
	 * @param activity
	 *            Activity
	 * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
	 */
	public static int[] getScreenSize(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return new int[] { metrics.widthPixels, metrics.heightPixels };
	}

	/**
	 * 获取状态栏高度
	 * @param context
	 * @return
     */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resId > 0) {
			result = context.getResources().getDimensionPixelOffset(resId);
		}
		return result;
	}

}
