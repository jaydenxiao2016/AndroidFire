package com.jaydenxiao.common.commonutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jaydenxiao.common.baseapp.BaseApplication;


/**
 * 屏幕相关的辅助类
 */
public class DisplayUtil {
	private DisplayUtil() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}


	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 *
	 * @param pxValue
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(float pxValue) {
		final float scale = BaseApplication.getAppContext().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 *
	 * @param dipValue
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px( float dipValue) {
		final float scale = BaseApplication.getAppContext().getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 *
	 * @param pxValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(float pxValue) {
		final float fontScale = BaseApplication.getAppContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(float spValue) {
		final float fontScale = BaseApplication.getAppContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 直接获取控件的宽、高
	 * @param view
	 * @return int[]
	 */
	public static int[] getWidgetWH(final View view){
	    ViewTreeObserver vto2 = view.getViewTreeObserver();
	    vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
	        @Override
	        public void onGlobalLayout() {
	        	view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	        }
	    });
		return new int[]{view.getWidth(),view.getHeight()};
	}

	/**
	 * 直接获取控件的宽、高
	 * @param view
	 * @return int[]
	 */
	public static int getViewHeight(final View view){
		ViewTreeObserver vto2 = view.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
		return view.getHeight();
	}

	/**
	 * 直接获取控件的宽、高
	 * @param view
	 * @return int[]
	 */
	public static int getViewWidth(final View view){
		ViewTreeObserver vto2 = view.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
		return view.getWidth();
	}
	
	/**
	 * 获得屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
	
	/**
	 * 获得状态栏的高度
	 * 注意：该方法只能在Activity类中使用，在测试模式下失败
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int statusBarHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}
	
	/**
	 * 获取控件的宽
	 * @param view
	 * @return
	 */
	public static int getWidgetWidth(View view){
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);//先度量  
		int width = view.getMeasuredWidth();  
		return width;
	}
	/**
	 * 获取控件的高
	 * @param view
	 * @return
	 */
	public static int getWidgetHeight(View view){
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);//先度量  
		int height = view.getMeasuredHeight();
		return height;
	}
	/**
	 * 设置控件宽
	 * @param view
	 * @param width
	 */
	public static void setWidgetWidth(View view, int width){
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
		params.width = width;
		view.setLayoutParams(params);
	}
	/**
	 * 设置控件高
	 * @param view
	 * @param height
	 */
	public static void setWidgetHeight(View view, int height){
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
		params.height = height;
		view.setLayoutParams(params);
	}

	
	//----------------------------------------------
	
	/**
	 * 获取当前屏幕截图，包含状态栏（这个方法没测试通过）
	 * 
	 * @param activity
	 * @return Bitmap
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;
	}

	/**
	 * 获取当前屏幕截图，不包含状态栏（这个方法没测试通过）
	 * 
	 * @param activity
	 * @return Bitmap
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}
}
