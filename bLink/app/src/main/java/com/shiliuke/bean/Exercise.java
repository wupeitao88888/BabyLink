package com.shiliuke.bean;

import java.util.List;

/**
 * Created by wpt on 2015/10/26.
 */
public class Exercise {
    private String exercise_pic;//活动图片
    private String exercise_title;//活动标题
    private String exercise_address;//活动地点
    private String exercise_usercount;//活动人数
    private String exercise_time;//活动时间
    private String exercise_author;//发起人
    private String exercise_authorPic;//发起人头像
    private String exercise_authorName;//发起人名字
    private List<UserInfo> exercise_signlist;//报名人

    public String getExercise_pic() {
        return exercise_pic;
    }

    public void setExercise_pic(String exercise_pic) {
        this.exercise_pic = exercise_pic;
    }

    public String getExercise_title() {
        return exercise_title;
    }

    public void setExercise_title(String exercise_title) {
        this.exercise_title = exercise_title;
    }

    public String getExercise_address() {
        return exercise_address;
    }

    public void setExercise_address(String exercise_address) {
        this.exercise_address = exercise_address;
    }

    public String getExercise_usercount() {
        return exercise_usercount;
    }

    public void setExercise_usercount(String exercise_usercount) {
        this.exercise_usercount = exercise_usercount;
    }

    public String getExercise_time() {
        return exercise_time;
    }

    public void setExercise_time(String exercise_time) {
        this.exercise_time = exercise_time;
    }

    public String getExercise_author() {
        return exercise_author;
    }

    public void setExercise_author(String exercise_author) {
        this.exercise_author = exercise_author;
    }

    public String getExercise_authorPic() {
        return exercise_authorPic;
    }

    public void setExercise_authorPic(String exercise_authorPic) {
        this.exercise_authorPic = exercise_authorPic;
    }

    public String getExercise_authorName() {
        return exercise_authorName;
    }

    public void setExercise_authorName(String exercise_authorName) {
        this.exercise_authorName = exercise_authorName;
    }

    public List<UserInfo> getExercise_signlist() {
        return exercise_signlist;
    }

    public void setExercise_signlist(List<UserInfo> exercise_signlist) {
        this.exercise_signlist = exercise_signlist;
    }

    public Exercise(String exercise_pic, String exercise_title, String exercise_address, String exercise_usercount, String exercise_time, String exercise_author, String exercise_authorPic, String exercise_authorName, List<UserInfo> exercise_signlist) {
        this.exercise_pic = exercise_pic;
        this.exercise_title = exercise_title;
        this.exercise_address = exercise_address;
        this.exercise_usercount = exercise_usercount;
        this.exercise_time = exercise_time;
        this.exercise_author = exercise_author;
        this.exercise_authorPic = exercise_authorPic;
        this.exercise_authorName = exercise_authorName;
        this.exercise_signlist = exercise_signlist;
    }

    public Exercise() {
    }
}
