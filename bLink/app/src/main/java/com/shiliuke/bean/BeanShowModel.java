package com.shiliuke.bean;

import com.shiliuke.view.stickerview.StickerImageModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 秀逗页面list实体
 * Created by wangzhi on 15/10/30.
 */
public class BeanShowModel implements Serializable {
    private String headurl;//发布人头像地址
    private String name;//发布人姓名
    private String time;//发布时间
    private String contenturl;//发布图片地址
    private String totaldou;//总逗数
    private String msg;//签名
    private ArrayList<StickerImageModel> stickerlist;//贴纸数据
    private boolean canAnim = true;//
    private boolean isAniming = false;//


    public BeanShowModel(String headurl, String name, String time, String contenturl, String totaldou, String msg, ArrayList<StickerImageModel> stickerlist) {
        this.headurl = headurl;
        this.name = name;
        this.time = time;
        this.contenturl = contenturl;
        this.totaldou = totaldou;
        this.msg = msg;
        this.stickerlist = stickerlist;
    }

    public String getHeadurl() {
        return headurl;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getContenturl() {
        return contenturl;
    }

    public String getTotaldou() {
        return totaldou;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<StickerImageModel> getStickerlist() {
        return stickerlist;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContenturl(String contenturl) {
        this.contenturl = contenturl;
    }

    public void setTotaldou(String totaldou) {
        this.totaldou = totaldou;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStickerlist(ArrayList<StickerImageModel> stickerlist) {
        this.stickerlist = stickerlist;
    }

    public void setCanAnim(boolean canAnim) {
        this.canAnim = canAnim;
    }

    public boolean isCanAnim() {

        return canAnim;
    }

    public boolean isAniming() {
        return isAniming;
    }

    public void setIsAniming(boolean isAniming) {
        this.isAniming = isAniming;
    }
}
