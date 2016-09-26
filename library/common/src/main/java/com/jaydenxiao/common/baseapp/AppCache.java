package com.jaydenxiao.common.baseapp;

import android.content.Context;

/**
 * App内存缓存
 */
public class AppCache {
    private Context context;//应用实例
    private volatile static AppCache instance;
    private String token;
    private String userId="10000";
    private String userName="锋";
    private String icon="Image/20160819/1471570856669.jpeg";

    private AppCache() {
    }
    public static AppCache getInstance() {
        if (null == instance) {
            synchronized (AppCache.class) {
                if (instance == null) {
                    instance = new AppCache();
                }
            }
        }
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
