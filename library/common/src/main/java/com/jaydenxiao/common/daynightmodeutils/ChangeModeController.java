package com.jaydenxiao.common.daynightmodeutils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaydenxiao.common.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 夜间模式控制器
 *
 */
public class ChangeModeController {
    /**
     * 属性背景
     */
    private static final String ATTR_BACKGROUND = "dayNightBackgroundAttr";
    /**
     * 属性背景图片
     */
    private static final String ATTR_BACKGROUND_DRAWABLE = "dayNightBackgroundDrawableAttr";
    /**
     * 属性一级字体颜色
     */
    private static final String ATTR_TEXTCOLOR = "dayNightOneTextColorAttr";
    /**
     * 属性二级字体颜色
     */
    private static final String ATTR_TWO_TEXTCOLOR = "dayNightTwoTextColorAttr";
    /**
     * 属性三级字体颜色
     */
    private static final String ATTR_THREE_TEXTCOLOR = "dayNightThreeTextColorAttr";

    private static List<AttrEntity<View>> mBackGroundViews;
    private static List<AttrEntity<View>> mBackGroundDrawableViews;
    private static List<AttrEntity<TextView>> mOneTextColorViews;
    private static List<AttrEntity<TextView>> mTwoTextColorViews;
    private static List<AttrEntity<TextView>> mThreeTextColorViews;

    private static ChangeModeController mChangeModeController;

    private ChangeModeController(){}
    public static ChangeModeController getInstance(){
        if(mChangeModeController == null){
            mChangeModeController = new ChangeModeController();
        }
        return mChangeModeController;
    }

    /**
     * 初始化保存集合
     */
    private void init(){
        mBackGroundViews = new ArrayList<>();
        mOneTextColorViews = new ArrayList<>();
        mTwoTextColorViews=new ArrayList<>();
        mThreeTextColorViews=new ArrayList<>();
        mBackGroundDrawableViews = new ArrayList<>();
    }

    /**
     * 初始化夜间控制器
     * @param activity 上下文
     * @return
     */
    public ChangeModeController init(final Activity activity,final Class mClass){
        init();
        LayoutInflaterCompat.setFactory(LayoutInflater.from(activity), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                View view = null;
                try {
                    if(name.indexOf('.') == -1){
                        if ("View".equals(name)) {
                            view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
                        }
                        if (view == null) {
                            view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
                        }
                        if (view == null) {
                            view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
                        }

                    }else{
                        if (view == null){
                            view = LayoutInflater.from(context).createView(name, null, attrs);
                        }
                    }
                    if(view != null){
                   // Log.e("TAG", "name = " + name);
                        for (int i = 0; i < attrs.getAttributeCount(); i++) {
//                            Log.e("TAG", attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
                            if (attrs.getAttributeName(i).equals(ATTR_BACKGROUND)) {
                                mBackGroundViews.add(new AttrEntity<View>(view,getAttr(mClass,attrs.getAttributeValue(i))));
                            }
                            if (attrs.getAttributeName(i).equals(ATTR_TEXTCOLOR)) {
                                mOneTextColorViews.add(new AttrEntity<TextView>((TextView)view,getAttr(mClass,attrs.getAttributeValue(i))));
                            }
                            if (attrs.getAttributeName(i).equals(ATTR_TWO_TEXTCOLOR)) {
                                mOneTextColorViews.add(new AttrEntity<TextView>((TextView)view,getAttr(mClass,attrs.getAttributeValue(i))));
                            }
                            if (attrs.getAttributeName(i).equals(ATTR_THREE_TEXTCOLOR)) {
                                mOneTextColorViews.add(new AttrEntity<TextView>((TextView)view,getAttr(mClass,attrs.getAttributeValue(i))));
                            }
                            if (attrs.getAttributeName(i).equals(ATTR_BACKGROUND_DRAWABLE)) {
                                mBackGroundDrawableViews.add(new AttrEntity<View>(view,getAttr(mClass,attrs.getAttributeValue(i))));
                            }

                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return view;
            }
        });
        return this;
    }

    /**
     * 反射获取文件id
     * @param attrName 属性名称
     * @return  属性id
     */
    public static int getAttr(Class draw,String attrName) {
        if (attrName == null || attrName.trim().equals("") || draw == null) {
            return R.attr.colorPrimary;
        }
        try {
            Field field = draw.getDeclaredField(attrName);
            field.setAccessible(true);
            return field.getInt(attrName);
        } catch (Exception e) {
            return R.attr.colorPrimary;
        }
    }

    /**
     * 设置当前主题
     * @param ctx  上下文
     * @param Style_Day  白天
     * @param Style_Night 夜间
     */
    public static void setTheme(Context ctx,int Style_Day,int Style_Night){
        if(ChangeModeHelper.getChangeMode(ctx) == ChangeModeHelper.MODE_DAY){
            ctx.setTheme(Style_Day);
        }else if(ChangeModeHelper.getChangeMode(ctx) == ChangeModeHelper.MODE_NIGHT){
            ctx.setTheme(Style_Night);
        }
    }


    /**
     * 动态切换主题
     */
    public static void toggleThemeSetting(Activity ctx) {
        if(ChangeModeHelper.getChangeMode(ctx) == ChangeModeHelper.MODE_DAY){
            changeNight(ctx,R.style.NightTheme);
        }else if(ChangeModeHelper.getChangeMode(ctx) == ChangeModeHelper.MODE_NIGHT){
            changeDay(ctx,R.style.DayTheme);
        }
    }

    /**
     *
     * @param ctx 上下文
     * @param style 切换style
     */
    public static void changeNight(Activity ctx,int style) {
        if(mBackGroundDrawableViews == null || mOneTextColorViews == null || mBackGroundViews == null){
            throw new RuntimeException("请先调用init()初始化方法!");
        }
        ChangeModeHelper.setChangeMode(ctx, ChangeModeHelper.MODE_NIGHT);
        ctx.setTheme(style);
        showAnimation(ctx);
        refreshUI(ctx);
    }

    /**
     *
     * @param ctx 上下文
     * @param style 切换style
     */
    public static void changeDay(Activity ctx,int style) {
        if(mBackGroundDrawableViews == null || mOneTextColorViews == null || mTwoTextColorViews == null ||mThreeTextColorViews == null ||mBackGroundViews == null){
            throw new RuntimeException("请先调用init()初始化方法!");
        }
        ChangeModeHelper.setChangeMode(ctx, ChangeModeHelper.MODE_DAY);
        ctx.setTheme(style);
        showAnimation(ctx);
        refreshUI(ctx);
    }



    /**
     * 刷新UI界面
     * @param ctx  上下文
     */
    private static void refreshUI(Activity ctx) {

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = ctx.getTheme();

        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        View view = ctx.findViewById(R.id.action_bar);
        if(view!=null){
            view.setBackgroundResource(typedValue.resourceId);
        }
        for(AttrEntity<View> entity:mBackGroundViews){
            theme.resolveAttribute(entity.colorId, typedValue, true);
            entity.v.setBackgroundResource(typedValue.resourceId);
        }

        for(AttrEntity<View> entity:mBackGroundDrawableViews){
            theme.resolveAttribute(entity.colorId, typedValue, true);
            entity.v.setBackgroundResource(typedValue.resourceId);
        }

        for (AttrEntity<TextView> entity: mOneTextColorViews){
            theme.resolveAttribute(entity.colorId, typedValue, true);
            entity.v.setTextColor(ctx.getResources().getColor(typedValue.resourceId));
        }
        for (AttrEntity<TextView> entity: mOneTextColorViews){
            theme.resolveAttribute(entity.colorId, typedValue, true);
            entity.v.setTextColor(ctx.getResources().getColor(typedValue.resourceId));
        }
        //refreshStatusBar(ctx);
    }


    /**
     * 获取某个属性的TypedValue
     * @param ctx 上下文
     * @param attr  属性id
     * @return
     */
    public static TypedValue getAttrTypedValue(Activity ctx,int attr){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = ctx.getTheme();
        theme.resolveAttribute(attr, typedValue, true);
        return typedValue;
    }


    /**
     * 刷新 StatusBar
     * @param ctx  上下文
     */
    private static void refreshStatusBar(Activity ctx) {
    	if (Build.VERSION.SDK_INT >= 21) {
    		TypedValue typedValue = new TypedValue();
    		Resources.Theme theme = ctx.getTheme();
    		theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
            ctx.getWindow().setStatusBarColor(ctx.getResources().getColor(typedValue.resourceId));
        }
    }
    /**
     * 展示切换动画
     */
    private static void showAnimation(Activity ctx) {
        final View decorView = ctx.getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(ctx);
            view.setBackgroundDrawable(new BitmapDrawable(ctx.getResources(), cacheBitmap));

            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);

            ValueAnimator objectAnimator = ValueAnimator.ofFloat(1f, 0f);//view, "alpha",
            objectAnimator.setDuration(500);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);

                }
            });
            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float alpha = (Float) animation.getAnimatedValue();
                    view.setAlpha(alpha);
                }
            });
            objectAnimator.start();
        }
    }
    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    private static Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    /**
     * 视图销毁时调用
     */
    public static void onDestory(){
        mBackGroundViews.clear();
        mOneTextColorViews.clear();
        mTwoTextColorViews.clear();
        mThreeTextColorViews.clear();
        mBackGroundDrawableViews.clear();
        mBackGroundViews = null;
        mOneTextColorViews = null;
        mTwoTextColorViews=null;
        mThreeTextColorViews=null;
        mBackGroundDrawableViews = null;
        mChangeModeController = null;
    }

    /**
     * 添加背景颜色属性
     * @param view
     * @param colorId
     * @return
     */
    public ChangeModeController addBackgroundColor(View view, int colorId) {
        mBackGroundViews.add(new AttrEntity(view,colorId));
        return this;
    }

    /**
     *添加背景图片属性
     * @param view
     * @param drawableId  属性id
     * @return
     */
    public ChangeModeController addBackgroundDrawable(View view, int drawableId) {
        mBackGroundDrawableViews.add(new AttrEntity(view,drawableId));
        return this;
    }

    /**
     * 添加一级字体颜色属性
     * @param view
     * @param colorId 属性id
     * @return
     */
    public ChangeModeController addTextColor(View view, int colorId) {
       mOneTextColorViews.add(new AttrEntity<TextView>((TextView) view,colorId));
        return this;
    }
    /**
     * 添加二级字体颜色属性
     * @param view
     * @param colorId 属性id
     * @return
     */
    public ChangeModeController addTwoTextColor(View view, int colorId) {
        mTwoTextColorViews.add(new AttrEntity<TextView>((TextView) view,colorId));
        return this;
    }
    /**
     * 添加三级字体颜色属性
     * @param view
     * @param colorId 属性id
     * @return
     */
    public ChangeModeController addThreeTextColor(View view, int colorId) {
        mThreeTextColorViews.add(new AttrEntity<TextView>((TextView) view,colorId));
        return this;
    }

    class AttrEntity<T>{
        T v;//控件
        int colorId;//属性id
        public AttrEntity(T v, int colorId) {
            this.v = v;
            this.colorId = colorId;
        }
    }
}
