package com.shiliuke.bean;

/**
 *我发起的
 * Created by wupeitao on 15/11/5.
 */
public class MeInitateActivity {
    private String initate_pic;//活动图片
    private String initate_name;//活动主题
    private String initate_time;//活动时间
    private String initate_status;//活动状态
    private String initate_count;//活动人数

    public MeInitateActivity(String initate_pic, String initate_name, String initate_time, String initate_status, String initate_count) {
        this.initate_pic = initate_pic;
        this.initate_name = initate_name;
        this.initate_time = initate_time;
        this.initate_status = initate_status;
        this.initate_count = initate_count;
    }

    public MeInitateActivity() {
    }

    public String getInitate_pic() {
        return initate_pic;
    }

    public void setInitate_pic(String initate_pic) {
        this.initate_pic = initate_pic;
    }

    public String getInitate_name() {
        return initate_name;
    }

    public void setInitate_name(String initate_name) {
        this.initate_name = initate_name;
    }

    public String getInitate_time() {
        return initate_time;
    }

    public void setInitate_time(String initate_time) {
        this.initate_time = initate_time;
    }

    public String getInitate_status() {
        return initate_status;
    }

    public void setInitate_status(String initate_status) {
        this.initate_status = initate_status;
    }

    public String getInitate_count() {
        return initate_count;
    }

    public void setInitate_count(String initate_count) {
        this.initate_count = initate_count;
    }
}
