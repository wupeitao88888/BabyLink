package com.shiliuke.bean;

/**
 * 我的好友
 * Created by wupeitao on 15/11/9.
 */
public class Friends {
    private String pic;//好友头像
    private String name;//好友姓名

    public Friends(String pic, String name) {
        this.pic = pic;
        this.name = name;
    }

    public Friends() {
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
