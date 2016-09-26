/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jaydenxiao.androidfire.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * des:新闻消息实体类
 * Created by xsf
 * on 2016.06.13:05
 */
public class NewsSummary implements Parcelable {
    private String postid;
    private boolean hasCover;
    private int hasHead;
    private int replyCount;
    private int hasImg;
    private String digest;
    private boolean hasIcon;
    private String docid;
    private String title;
    private String ltitle;
    private int order;
    private int priority;
    private String lmodify;
    private String boardid;
    private String photosetID;
    private String template;
    private int votecount;
    private String skipID;
    private String alias;
    private String skipType;
    private String cid;
    private int hasAD;
    private String source;
    private String ename;
    private String imgsrc;
    private String tname;
    private String ptime;
    /**
     * title : "悬崖村" 孩子上学需爬800米悬崖
     * tag : photoset
     * imgsrc : http://img1.cache.netease.com/3g/2016/5/24/2016052421435478ea5.jpg
     * subtitle :
     * url : 00AP0001|119327
     */

    private List<AdsBean> ads;
    /**
     * imgsrc : http://img3.cache.netease.com/3g/2016/5/24/2016052416484243560.jpg
     */

    private List<ImgextraBean> imgextra;

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public boolean isHasCover() {
        return hasCover;
    }

    public void setHasCover(boolean hasCover) {
        this.hasCover = hasCover;
    }

    public int getHasHead() {
        return hasHead;
    }

    public void setHasHead(int hasHead) {
        this.hasHead = hasHead;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getHasImg() {
        return hasImg;
    }

    public void setHasImg(int hasImg) {
        this.hasImg = hasImg;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLtitle() {
        return ltitle;
    }

    public void setLtitle(String ltitle) {
        this.ltitle = ltitle;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public String getPhotosetID() {
        return photosetID;
    }

    public void setPhotosetID(String photosetID) {
        this.photosetID = photosetID;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public int getVotecount() {
        return votecount;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public String getSkipID() {
        return skipID;
    }

    public void setSkipID(String skipID) {
        this.skipID = skipID;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getHasAD() {
        return hasAD;
    }

    public void setHasAD(int hasAD) {
        this.hasAD = hasAD;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public List<ImgextraBean> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<ImgextraBean> imgextra) {
        this.imgextra = imgextra;
    }

    public static class AdsBean {
        private String title;
        private String tag;
        private String imgsrc;
        private String subtitle;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ImgextraBean {
        private String imgsrc;

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.postid);
        dest.writeByte(hasCover ? (byte) 1 : (byte) 0);
        dest.writeInt(this.hasHead);
        dest.writeInt(this.replyCount);
        dest.writeInt(this.hasImg);
        dest.writeString(this.digest);
        dest.writeByte(hasIcon ? (byte) 1 : (byte) 0);
        dest.writeString(this.docid);
        dest.writeString(this.title);
        dest.writeString(this.ltitle);
        dest.writeInt(this.order);
        dest.writeInt(this.priority);
        dest.writeString(this.lmodify);
        dest.writeString(this.boardid);
        dest.writeString(this.photosetID);
        dest.writeString(this.template);
        dest.writeInt(this.votecount);
        dest.writeString(this.skipID);
        dest.writeString(this.alias);
        dest.writeString(this.skipType);
        dest.writeString(this.cid);
        dest.writeInt(this.hasAD);
        dest.writeString(this.source);
        dest.writeString(this.ename);
        dest.writeString(this.imgsrc);
        dest.writeString(this.tname);
        dest.writeString(this.ptime);
        dest.writeList(this.ads);
        dest.writeList(this.imgextra);
    }

    public NewsSummary() {
    }

    protected NewsSummary(Parcel in) {
        this.postid = in.readString();
        this.hasCover = in.readByte() != 0;
        this.hasHead = in.readInt();
        this.replyCount = in.readInt();
        this.hasImg = in.readInt();
        this.digest = in.readString();
        this.hasIcon = in.readByte() != 0;
        this.docid = in.readString();
        this.title = in.readString();
        this.ltitle = in.readString();
        this.order = in.readInt();
        this.priority = in.readInt();
        this.lmodify = in.readString();
        this.boardid = in.readString();
        this.photosetID = in.readString();
        this.template = in.readString();
        this.votecount = in.readInt();
        this.skipID = in.readString();
        this.alias = in.readString();
        this.skipType = in.readString();
        this.cid = in.readString();
        this.hasAD = in.readInt();
        this.source = in.readString();
        this.ename = in.readString();
        this.imgsrc = in.readString();
        this.tname = in.readString();
        this.ptime = in.readString();
        this.ads = new ArrayList<AdsBean>();
        in.readList(this.ads, AdsBean.class.getClassLoader());
        this.imgextra = new ArrayList<ImgextraBean>();
        in.readList(this.imgextra, ImgextraBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<NewsSummary> CREATOR = new Parcelable.Creator<NewsSummary>() {
        @Override
        public NewsSummary createFromParcel(Parcel source) {
            return new NewsSummary(source);
        }

        @Override
        public NewsSummary[] newArray(int size) {
            return new NewsSummary[size];
        }
    };
}
