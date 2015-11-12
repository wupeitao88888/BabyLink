package com.shiliuke.bean;

/**
 * *
 * Created by wpt on 2015/10/28.
 */
public class Comment {
    private String commend_id;
    private String activity_id;
    private String member_id;
    private String add_time;
    private String member_name;
    private String member_avar;
    private String info;//与
    private String commentReplyName;

    public String getCommentReplyName() {
        return commentReplyName;
    }

    public void setCommentReplyName(String commentReplyName) {
        this.commentReplyName = commentReplyName;
    }

    public Comment(String commend_id, String activity_id, String member_id, String add_time, String member_name, String member_avar, String info) {
        this.commend_id = commend_id;
        this.activity_id = activity_id;
        this.member_id = member_id;
        this.add_time = add_time;
        this.member_name = member_name;
        this.member_avar = member_avar;
        this.info = info;
    }

    public Comment() {
    }

    public String getCommend_id() {
        return commend_id;
    }

    public void setCommend_id(String commend_id) {
        this.commend_id = commend_id;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_avar() {
        return member_avar;
    }

    public void setMember_avar(String member_avar) {
        this.member_avar = member_avar;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
