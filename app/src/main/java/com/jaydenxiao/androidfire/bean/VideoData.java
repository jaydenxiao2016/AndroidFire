package com.jaydenxiao.androidfire.bean;

/**
 * des:视频
 * Created by xsf
 * on 2016.09.14:12
 */
public class VideoData {

    /**
     * topicImg : http://vimg3.ws.126.net/image/snapshot/2016/8/5/M/VBUQ2NJ5M.jpg
     * videosource : 新媒体
     * mp4Hd_url : http://flv2.bn.netease.com/videolib3/1609/06/UnuGW1312/HD/UnuGW1312-mobile.mp4
     * topicDesc : 利用动漫形象财哥的口吻，将枯燥乏味的财经知识用幽默风趣的形式展现出来，视频时间在1分半左右
     * topicSid : VBUQ2NJ5I
     * cover : http://vimg2.ws.126.net/image/snapshot/2016/9/S/2/VBVBITNS2.jpg
     * title : 马云：当初你对我爱搭不理，如今你高攀不起
     * playCount : 0
     * replyBoard : video_bbs
     * videoTopic : {"alias":"听财哥讲财经","tname":"30秒懂财经","ename":"T1472544642251","tid":"T1472544642251"}
     * sectiontitle :
     * replyid : BVBITNS1008535RB
     * description : 创业初期，他经历了4次失败，但是他没有放弃，坚持到成就了自己的商业帝国
     * mp4_url : http://flv2.bn.netease.com/videolib3/1609/06/UnuGW1312/SD/UnuGW1312-mobile.mp4
     * length : 164
     * playersize : 1
     * m3u8Hd_url : http://flv2.bn.netease.com/videolib3/1609/06/UnuGW1312/HD/movie_index.m3u8
     * vid : VBVBITNS1
     * m3u8_url : http://flv2.bn.netease.com/videolib3/1609/06/UnuGW1312/SD/movie_index.m3u8
     * ptime : 2016-09-08 13:55:17
     * topicName : 30秒懂财经
     */

    private String topicImg;
    private String videosource;
    private String mp4Hd_url;
    private String topicDesc;
    private String topicSid;
    private String cover;
    private String title;
    private int playCount;
    private String replyBoard;
    /**
     * alias : 听财哥讲财经
     * tname : 30秒懂财经
     * ename : T1472544642251
     * tid : T1472544642251
     */

    private VideoTopicBean videoTopic;
    private String sectiontitle;
    private String replyid;
    private String description;
    private String mp4_url;
    private int length;
    private int playersize;
    private String m3u8Hd_url;
    private String vid;
    private String m3u8_url;
    private String ptime;
    private String topicName;

    public String getTopicImg() {
        return topicImg;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public String getVideosource() {
        return videosource;
    }

    public void setVideosource(String videosource) {
        this.videosource = videosource;
    }

    public String getMp4Hd_url() {
        return mp4Hd_url;
    }

    public void setMp4Hd_url(String mp4Hd_url) {
        this.mp4Hd_url = mp4Hd_url;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public String getTopicSid() {
        return topicSid;
    }

    public void setTopicSid(String topicSid) {
        this.topicSid = topicSid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getReplyBoard() {
        return replyBoard;
    }

    public void setReplyBoard(String replyBoard) {
        this.replyBoard = replyBoard;
    }

    public VideoTopicBean getVideoTopic() {
        return videoTopic;
    }

    public void setVideoTopic(VideoTopicBean videoTopic) {
        this.videoTopic = videoTopic;
    }

    public String getSectiontitle() {
        return sectiontitle;
    }

    public void setSectiontitle(String sectiontitle) {
        this.sectiontitle = sectiontitle;
    }

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMp4_url() {
        return mp4_url;
    }

    public void setMp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPlayersize() {
        return playersize;
    }

    public void setPlayersize(int playersize) {
        this.playersize = playersize;
    }

    public String getM3u8Hd_url() {
        return m3u8Hd_url;
    }

    public void setM3u8Hd_url(String m3u8Hd_url) {
        this.m3u8Hd_url = m3u8Hd_url;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getM3u8_url() {
        return m3u8_url;
    }

    public void setM3u8_url(String m3u8_url) {
        this.m3u8_url = m3u8_url;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public static class VideoTopicBean {
        private String alias;
        private String tname;
        private String ename;
        private String tid;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }
}
