package com.jaydenxiao.androidfire.ui.zone.bean;

/**
 * des:动态eventbus传递对象
 * Created by xsf
 * on 2016.07.9:35
 */
public class ZoneEvent {
    private CircleItem circleItem;
    private CommentItem commentItem;
    private String circleId;
    private int circlrPosition;
    private FavortItem addItem;
    private String commentId;
    private String userId;
    private String addOrDelete;
    private String type;
    //新增动态
    public ZoneEvent(CircleItem circleItem,String type){
        this.circleItem=circleItem;
        this.type=type;
    }
    //删除动态
    public ZoneEvent(String circleId, int position,String type){
        this.circleId=circleId;
        this.circlrPosition=position;
        this.type=type;
    }
    //新增评论
    public ZoneEvent(CommentItem commentItem,int circlrPosition,String type){
        this.commentItem=commentItem;
        this.circlrPosition=circlrPosition;
        this.type=type;
    }
    //删除评论
    public ZoneEvent(int circlePosition, String commentId,String type){
        this.circlrPosition=circlePosition;
        this.commentId=commentId;
        this.type=type;
    }
    //新增点赞
    public ZoneEvent(int circlePosition, FavortItem addItem,String addOrDelete,String type){
        this.addItem=addItem;
        this.circlrPosition=circlePosition;
        this.addOrDelete=addOrDelete;
        this.type=type;
    }
    //删除点赞
    public ZoneEvent(int circlePosition,String userId,String addOrDelete,String type){
        this.circlrPosition=circlePosition;
        this.userId=userId;
        this.addOrDelete=addOrDelete;
        this.type=type;
    }
    //所有信息已为已读
    public ZoneEvent(String type){
        this.type=type;
    }

    public FavortItem getAddItem() {
        return addItem;
    }

    public void setAddItem(FavortItem addItem) {
        this.addItem = addItem;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddOrDelete() {
        return addOrDelete;
    }

    public void setAddOrDelete(String addOrDelete) {
        this.addOrDelete = addOrDelete;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public CommentItem getCommentItem() {
        return commentItem;
    }

    public void setCommentItem(CommentItem commentItem) {
        this.commentItem = commentItem;
    }

    public int getCirclrPosition() {
        return circlrPosition;
    }

    public void setCirclrPosition(int circlrPosition) {
        this.circlrPosition = circlrPosition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CircleItem getCircleItem() {
        return circleItem;
    }

    public void setCircleItem(CircleItem circleItem) {
        this.circleItem = circleItem;
    }
}
