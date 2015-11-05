package com.shiliuke.bean;

/**
 * Created by wupeitao on 15/11/5.
 */
public class Unconsume {
    private String unconsume_title;
    private String unconsume_endtime;
    private String unconsume_count;

    public String getUnconsume_title() {
        return unconsume_title;
    }

    public void setUnconsume_title(String unconsume_title) {
        this.unconsume_title = unconsume_title;
    }

    public String getUnconsume_endtime() {
        return unconsume_endtime;
    }

    public void setUnconsume_endtime(String unconsume_endtime) {
        this.unconsume_endtime = unconsume_endtime;
    }

    public String getUnconsume_count() {
        return unconsume_count;
    }

    public void setUnconsume_count(String unconsume_count) {
        this.unconsume_count = unconsume_count;
    }


    public Unconsume(String unconsume_title, String unconsume_endtime, String unconsume_count) {
        this.unconsume_title = unconsume_title;
        this.unconsume_endtime = unconsume_endtime;
        this.unconsume_count = unconsume_count;
    }

    public Unconsume() {
    }
}
