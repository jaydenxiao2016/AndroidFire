package com.jaydenxiao.common.compressorutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

import rx.Observable;
import rx.functions.Func0;

/**
 * 压缩图片类
 */

//**********************************************************使用方法******************************************
//方法一：
//File compressedImage = new Compressor.Builder(this)
//        .setMaxWidth(640)
//        .setMaxHeight(480)
//        .setQuality(75)
//        .setCompressFormat(Bitmap.CompressFormat.JPEG)
//        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//        Environment.DIRECTORY_PICTURES).getAbsolutePath())
//        .build()
//        .compressToFile(actualImage);


//方法二

//Compressor.getDefault(this)
//        .compressToFileAsObservable(actualImage)
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(new Action1<File>() {
//@Override
//public void call(File file) {
//        compressedImage = file;
//        setCompressedImage();
//        }
//        }, new Action1<Throwable>() {
//@Override
//public void call(Throwable throwable) {
//        showError(throwable.getMessage());
//        }
//        });

public class Compressor {
    private static volatile Compressor INSTANCE;
    private Context context;
    //max width and height values of the compressed image is taken as 612x816
    private float maxWidth = 612.0f;
    private float maxHeight = 816.0f;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private int quality = 80;
    private String destinationDirectoryPath;

    private Compressor(Context context) {
        this.context = context;
        destinationDirectoryPath = context.getCacheDir().getPath() + File.pathSeparator + FileUtil.FILES_PATH;
    }

    public static Compressor getDefault(Context context) {
        if (INSTANCE == null) {
            synchronized (Compressor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Compressor(context);
                }
            }
        }
        return INSTANCE;
    }

    public File compressToFile(File file) {
        return ImageUtil.compressImage(context, Uri.fromFile(file), maxWidth, maxHeight, compressFormat, quality, destinationDirectoryPath);
    }

    public Bitmap compressToBitmap(File file) {
        return ImageUtil.getScaledBitmap(context, Uri.fromFile(file), maxWidth, maxHeight);
    }

    public Observable<File> compressToFileAsObservable(final File file) {
        return Observable.defer(new Func0<Observable<File>>() {
            @Override
            public Observable<File> call() {
                return Observable.just(compressToFile(file));
            }
        });
    }

    public Observable<Bitmap> compressToBitmapAsObservable(final File file) {
        return Observable.defer(new Func0<Observable<Bitmap>>() {
            @Override
            public Observable<Bitmap> call() {
                return Observable.just(compressToBitmap(file));
            }
        });
    }

    public static class Builder {
        private Compressor compressor;

        public Builder(Context context) {
            compressor = new Compressor(context);
        }

        public Builder setMaxWidth(float maxWidth) {
            compressor.maxWidth = maxWidth;
            return this;
        }

        public Builder setMaxHeight(float maxHeight) {
            compressor.maxHeight = maxHeight;
            return this;
        }

        public Builder setCompressFormat(Bitmap.CompressFormat compressFormat) {
            compressor.compressFormat = compressFormat;
            return this;
        }

        public Builder setQuality(int quality) {
            compressor.quality = quality;
            return this;
        }

        public Builder setDestinationDirectoryPath(String destinationDirectoryPath) {
            compressor.destinationDirectoryPath = destinationDirectoryPath;
            return this;
        }

        public Compressor build() {
            return compressor;
        }
    }
}
