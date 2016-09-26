package com.weavey.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.MutableChar;
import android.view.WindowManager;

/**
 * create by Weavey
 * on date 2016-01-06
 * TODO 单例模式 获取屏幕宽高的帮助类
 */

public class ScreenSizeUtils {

    private WindowManager manager;
    private DisplayMetrics dm;
    private static ScreenSizeUtils instance = null;
    private int screenWidth, screenHeigth;

    public static ScreenSizeUtils getInstance(Context mContext) {

        if (instance == null) {
            synchronized (ScreenSizeUtils.class) {

                if (instance == null)
                    instance = new ScreenSizeUtils(mContext);

            }
        }
        return instance;
    }

    private ScreenSizeUtils(Context mContext) {

        manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);

        screenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
        screenHeigth = dm.heightPixels;

    }

    //获取屏幕宽度
    public int getScreenWidth() {

        return screenWidth;
    }

    //获取屏幕高度
    public int getScreenHeight() {

        return screenHeigth;
    }


}
