package com.jaydenxiao.androidfire.ui.zone.bean;

/**
 * des:未读消息传递对象
 * Created by xsf
 * on 2016.07.9:35
 */
public class NewEvent {
    private int count;
    private String type;
    public NewEvent(int count, String type){
        this.count=count;
        this.type=type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
