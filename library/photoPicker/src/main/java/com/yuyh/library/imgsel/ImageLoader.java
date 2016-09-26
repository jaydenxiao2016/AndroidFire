package com.yuyh.library.imgsel;

import android.content.Context;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * @author yuyh.
 * @date 2016/8/5.
 */
public interface ImageLoader extends Serializable {
    void displayImage(Context context, String path, ImageView imageView);
}