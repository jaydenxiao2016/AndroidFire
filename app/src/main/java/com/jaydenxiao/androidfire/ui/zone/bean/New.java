package com.jaydenxiao.androidfire.ui.zone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * des:消息
 * Created by xsf
 * on 2016.07.16:44
 */
public class New implements Parcelable {

    /**
     * createTime : 2016-07-21T05:57:12.133Z
     * id : 0
     * isRead : string
     * publishId : 0
     * readTime : 2016-07-21T05:57:12.133Z
     * replyId : 0
     * source : string
     * userId : 0
     */

    private long createTime;
    private String id;
    private String isRead;
    private String publishId;
    private String readTime;
    private String replyId;
    private String source;
    private String userId;
    private String content;
    private String operaUserIcon;

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperaUserIcon() {
        return operaUserIcon;
    }

    public void setOperaUserIcon(String operaUserIcon) {
        this.operaUserIcon = operaUserIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.createTime);
        dest.writeString(this.id);
        dest.writeString(this.isRead);
        dest.writeString(this.publishId);
        dest.writeString(this.readTime);
        dest.writeString(this.replyId);
        dest.writeString(this.source);
        dest.writeString(this.userId);
        dest.writeString(this.content);
        dest.writeString(this.operaUserIcon);
    }

    public New() {
    }

    protected New(Parcel in) {
        this.createTime = in.readLong();
        this.id = in.readString();
        this.isRead = in.readString();
        this.publishId = in.readString();
        this.readTime = in.readString();
        this.replyId = in.readString();
        this.source = in.readString();
        this.userId = in.readString();
        this.content = in.readString();
        this.operaUserIcon = in.readString();
    }

    public static final Creator<New> CREATOR = new Creator<New>() {
        @Override
        public New createFromParcel(Parcel source) {
            return new New(source);
        }

        @Override
        public New[] newArray(int size) {
            return new New[size];
        }
    };
}
