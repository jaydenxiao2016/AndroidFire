package com.jaydenxiao.androidfire.ui.zone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * des:点赞分类
 * Created by xsf
 * on 2016.07.11:11
 */
public class FavortItem implements Parcelable {


	/**
	 * createTime : 2016-07-20T06:33:40.458Z
	 * id : 0
	 * publishId : 0
	 * userId : 0
	 * userNickname : string
	 */

	private String createTime;
	private String id;
	private String publishId;
	private String userId;
	private String userNickname;

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
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

	public FavortItem() {
	}
	public FavortItem(String publishId, String userId,String userNickname) {
		this.publishId=publishId;
		this.userId=userId;
		this.userNickname=userNickname;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.createTime);
		dest.writeString(this.id);
		dest.writeString(this.publishId);
		dest.writeString(this.userId);
		dest.writeString(this.userNickname);
	}

	protected FavortItem(Parcel in) {
		this.createTime = in.readString();
		this.id = in.readString();
		this.publishId = in.readString();
		this.userId = in.readString();
		this.userNickname = in.readString();
	}

	public static final Creator<FavortItem> CREATOR = new Creator<FavortItem>() {
		@Override
		public FavortItem createFromParcel(Parcel source) {
			return new FavortItem(source);
		}

		@Override
		public FavortItem[] newArray(int size) {
			return new FavortItem[size];
		}
	};
}
