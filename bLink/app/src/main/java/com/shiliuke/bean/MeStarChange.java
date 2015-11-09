package com.shiliuke.bean;

/**
 *我发起的置换
 * Created by wupeitao on 15/11/5.
 */
public class MeStarChange {
    private String change_pic;//z置换图片
    private String change_name;//置换物品
    private String change_time;//发起时间
    private String change_status;//活动状态


    public MeStarChange(String change_pic, String change_name, String change_time, String change_status) {
        this.change_pic = change_pic;
        this.change_name = change_name;
        this.change_time = change_time;
        this.change_status = change_status;

    }

    public MeStarChange() {
    }

    public String getchange_pic() {
        return change_pic;
    }

    public void setchange_pic(String change_pic) {
        this.change_pic = change_pic;
    }

    public String getchange_name() {
        return change_name;
    }

    public void setchange_name(String change_name) {
        this.change_name = change_name;
    }

    public String getchange_time() {
        return change_time;
    }

    public void setchange_time(String change_time) {
        this.change_time = change_time;
    }

    public String getchange_status() {
        return change_status;
    }

    public void setchange_status(String change_status) {
        this.change_status = change_status;
    }


}
