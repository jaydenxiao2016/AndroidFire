package com.yuyh.library.imgsel.bean;

/**
 * Image bean
 * Created by Yancy on 2015/12/2.
 */
public class Image {

    public String path;
    public String name;
    public long time;

    public boolean isCamera = false;

    public Image(String path, String name, long time) {
        this.path = path;
        this.name = name;
        this.time = time;
    }

    public Image(){
        isCamera = true;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}