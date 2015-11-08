package com.shiliuke.bean;

import java.io.Serializable;

/**
 * Created by wangzhi on 15/11/8.
 */
public class FindModel implements Serializable{
    private String topurl;//上图背景图片
    private String title;//名称
    private String price;//价格
    private String address;//地址
    private String distance;//地址

    public String getTopurl() {
        return topurl;
    }

    public void setTopurl(String topurl) {
        this.topurl = topurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public FindModel(String topurl, String title, String price, String address, String distance) {
        this.topurl = topurl;
        this.title = title;
        this.price = price;
        this.address = address;
        this.distance = distance;
    }
}
