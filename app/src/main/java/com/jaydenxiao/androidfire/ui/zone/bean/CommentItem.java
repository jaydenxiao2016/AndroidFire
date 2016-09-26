package com.jaydenxiao.androidfire.ui.zone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * des:评论实体类
 * Created by xsf
 * on 2016.07.11:11
 */
public class CommentItem implements Parcelable {


    /**
     * appointUserNickname : string
     * appointUserid : 0
     * content : string
     * createTime : 2016-07-20T06:33:40.458Z
     * id : 0
     * pictures : string
     * publishId : 0
     * userId : 0
     * userNickname : string
     */

    private String appointUserNickname;
    private String appointUserid;
    private String content;
    private String createTime;
    private String id;
    private String pictures;
    private String publishId;
    private String userId;
    private String userNickname;

    public CommentItem(String appointUserNickname, String appointUserid, String content, String publishId, String userId, String userNickname) {
        this.appointUserNickname = appointUserNickname;
        this.appointUserid = appointUserid;
        this.content = content;
        this.publishId = publishId;
        this.userId = userId;
        this.userNickname = userNickname;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getAppointUserNickname() {
        return appointUserNickname;
    }

    public void setAppointUserNickname(String appointUserNickname) {
        this.appointUserNickname = appointUserNickname;
    }

    public String getAppointUserid() {
        return appointUserid;
    }

    public void setAppointUserid(String appointUserid) {
        this.appointUserid = appointUserid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CommentItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appointUserNickname);
        dest.writeString(this.appointUserid);
        dest.writeString(this.content);
        dest.writeString(this.createTime);
        dest.writeString(this.id);
        dest.writeString(this.pictures);
        dest.writeString(this.publishId);
        dest.writeString(this.userId);
        dest.writeString(this.userNickname);
    }

    protected CommentItem(Parcel in) {
        this.appointUserNickname = in.readString();
        this.appointUserid = in.readString();
        this.content = in.readString();
        this.createTime = in.readString();
        this.id = in.readString();
        this.pictures = in.readString();
        this.publishId = in.readString();
        this.userId = in.readString();
        this.userNickname = in.readString();
    }

    public static final Creator<CommentItem> CREATOR = new Creator<CommentItem>() {
        @Override
        public CommentItem createFromParcel(Parcel source) {
            return new CommentItem(source);
        }

        @Override
        public CommentItem[] newArray(int size) {
            return new CommentItem[size];
        }
    };
}
