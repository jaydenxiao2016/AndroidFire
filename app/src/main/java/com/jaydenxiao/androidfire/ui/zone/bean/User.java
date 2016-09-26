package com.jaydenxiao.androidfire.ui.zone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * des:发说说者实体类
 * Created by xsf
 * on 2016.07.11:11
 */
public class User implements Parcelable {
	private String id;
	private String name;
	private String headUrl;
	private boolean isOpen=false;
	public User(String id, String name, String headUrl){
		this.id = id;
		this.name = name;
		this.headUrl = headUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean open) {
		isOpen = open;
	}

	@Override
	public String toString() {
		return "id = " + id
				+ "; name = " + name
				+ "; headUrl = " + headUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.name);
		dest.writeString(this.headUrl);
		dest.writeByte(isOpen ? (byte) 1 : (byte) 0);
	}

	protected User(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.headUrl = in.readString();
		this.isOpen = in.readByte() != 0;
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
