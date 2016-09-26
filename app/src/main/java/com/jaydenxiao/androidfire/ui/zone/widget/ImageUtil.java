package com.jaydenxiao.androidfire.ui.zone.widget;

import android.text.TextUtils;

import java.io.File;


/**
 * des:
 * Created by xsf
 * on 2016.04.10:36
 */
public class ImageUtil {
private static String BASE_PHOTO_URL="";
    /**
     * @param url
     * @return
     */
    public static String getImageUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("http")||new File(url).isFile()) {
                return url;
            } else {
                return BASE_PHOTO_URL+url;
            }
        } else {
            return "";
        }
    }

}

