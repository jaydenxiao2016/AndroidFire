package com.yuyh.library.imgsel.common;

import java.io.File;
import java.io.Serializable;

/**
 * @author yuyh.
 * @date 2016/8/5.
 */
public interface Callback extends Serializable {

    void onSingleImageSelected(String path);

    void onImageSelected(String path);

    void onImageUnselected(String path);

    void onCameraShot(File imageFile);
}
