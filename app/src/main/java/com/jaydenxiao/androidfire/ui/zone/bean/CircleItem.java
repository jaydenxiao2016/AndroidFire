package com.jaydenxiao.androidfire.ui.zone.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.jaydenxiao.androidfire.ui.zone.widget.ImageUtil;
import com.jaydenxiao.common.baseapp.AppCache;

import java.util.ArrayList;
import java.util.List;

/**
 * des:说说实体类
 * Created by xsf
 * on 2016.07.11:11
 */
public class CircleItem implements Parcelable {

//    动态 {
//        address (string, optional): 地址 ,
//                appointUserNickname (string, optional): @用户昵称 ,
//        appointUserid (integer, optional): @用户ID ,
//        content (string, optional): 发布内容 ,
//                createTime (string, optional): 发布时间 ,
//                goodjobCount (integer, optional): 赞的总数 ,
//                goodjobs (Array[动态点赞], optional),
//                id (integer, optional): ID ,
//                isvalid (string, optional): 是否有效(0：正常；1：删除；2：对外隐藏) ,
//                latitude (string, optional): 纬度 ,
//                longitude (string, optional): 经度 ,
//                pictures (string, optional): 发布图片(;分开) ,
//        replyCount (integer, optional): 评论总数 ,
//                replys (Array[动态回复], optional),
//                type (string, optional): 类型0：普通消息 1：分享链接 ,
//                userId (integer, optional): 用户ID -1为系统消息
//    }
//    动态点赞 {
//        createTime (string, optional): 点赞时间 ,
//                id (integer, optional): ID ,
//                publishId (integer, optional): 动态ID ,
//                userId (integer, optional): 点赞USERID ,
//                userNickname (string, optional): 点赞昵称
//    }
//    动态回复 {
//        appointUserNickname (string, optional): @用户昵称 ,
//        appointUserid (integer, optional): @用户ID ,
//        content (string, optional): 回复内容 ,
//                createTime (string, optional): 回复时间 ,
//                id (integer, optional): ID ,
//                pictures (string, optional): 回复图片 ,
//                publishId (integer, optional): 动态ID ,
//                userId (integer, optional): 回复USERID ,
//                userNickname (string, optional): 回复昵称
//    }

    private String address;
    private String appointUserNickname;
    private String appointUserid;
    private String content;
    private long createTime;
    private int goodjobCount;
    private String id;
    private String isvalid;
    private double latitude;
    private double longitude;
    private String pictures;
    private int replyCount;
    private int type;
    private String icon;
    private String userId;
    private String nickName;
    private List<FavortItem> goodjobs=new ArrayList<>();
    private List<CommentItem> replys=new ArrayList<>();
    private String linkImg;
    private String linkTitle;
    private int takeTimes;//接单总数

    public int getTakeTimes() {
        return takeTimes;
    }

    public void setTakeTimes(int takeTimes) {
        this.takeTimes = takeTimes;
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getGoodjobCount() {
        return goodjobCount;
    }

    public void setGoodjobCount(int goodjobCount) {
        this.goodjobCount = goodjobCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<FavortItem> getGoodjobs() {
        return goodjobs;
    }

    public void setGoodjobs(List<FavortItem> goodjobs) {
        this.goodjobs = goodjobs;
    }

    public List<CommentItem> getReplys() {
        return replys;
    }

    public void setReplys(List<CommentItem> replys) {
        this.replys = replys;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public CircleItem() {
    }

    public String getCurUserFavortId() {
        String userId = "";
        String myId = AppCache.getInstance().getUserId();
        if (goodjobs!=null&&!TextUtils.isEmpty(myId) && goodjobs.size() > 0) {
            for (FavortItem item : goodjobs) {
                if (myId.equals(item.getUserId())) {
                    userId = item.getUserId();
                    return userId;
                }
            }
        }
        return userId;
    }

    /**
     * 获取图片链接
     */
    public List<String> getPictureList() {
        if (!TextUtils.isEmpty(pictures)) {
            List<String> photos = new ArrayList<>();
            String[] strings = pictures.split(";");
            if (strings != null && strings.length > 0) {
                for (String str : strings) {
                    if (!TextUtils.isEmpty(str)) {
                        photos.add(ImageUtil.getImageUrl(str));
                    }
                }
                return photos;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.appointUserNickname);
        dest.writeString(this.appointUserid);
        dest.writeString(this.content);
        dest.writeLong(this.createTime);
        dest.writeInt(this.goodjobCount);
        dest.writeString(this.id);
        dest.writeString(this.isvalid);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.pictures);
        dest.writeInt(this.replyCount);
        dest.writeInt(this.type);
        dest.writeString(this.icon);
        dest.writeString(this.userId);
        dest.writeString(this.nickName);
        dest.writeTypedList(goodjobs);
        dest.writeTypedList(replys);
        dest.writeString(this.linkImg);
        dest.writeString(this.linkTitle);
        dest.writeInt(this.takeTimes);
    }

    protected CircleItem(Parcel in) {
        this.address = in.readString();
        this.appointUserNickname = in.readString();
        this.appointUserid = in.readString();
        this.content = in.readString();
        this.createTime = in.readLong();
        this.goodjobCount = in.readInt();
        this.id = in.readString();
        this.isvalid = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.pictures = in.readString();
        this.replyCount = in.readInt();
        this.type = in.readInt();
        this.icon = in.readString();
        this.userId = in.readString();
        this.nickName = in.readString();
        this.goodjobs = in.createTypedArrayList(FavortItem.CREATOR);
        this.replys = in.createTypedArrayList(CommentItem.CREATOR);
        this.linkImg = in.readString();
        this.linkTitle = in.readString();
        this.takeTimes = in.readInt();
    }

    public static final Creator<CircleItem> CREATOR = new Creator<CircleItem>() {
        @Override
        public CircleItem createFromParcel(Parcel source) {
            return new CircleItem(source);
        }

        @Override
        public CircleItem[] newArray(int size) {
            return new CircleItem[size];
        }
    };
}
