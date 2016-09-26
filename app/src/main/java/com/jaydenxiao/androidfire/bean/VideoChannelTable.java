package com.jaydenxiao.androidfire.bean;

/**
 * des:视频分类
 * Created by xsf
 * on 2016.09.14:57
 */
public class VideoChannelTable {
    private String channelId;
    private String channelName;

    public VideoChannelTable(String channelId,String channelName){
        this.channelId=channelId;
        this.channelName=channelName;
    }
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
