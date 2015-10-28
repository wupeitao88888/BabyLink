package com.shiliuke.bean;

/**
 * Created by wpt on 2015/10/28.
 */
public class Comment {
    private String  commentName;
    private String  commentContent;
    private String  commentReplyName;


    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentReplyName() {
        return commentReplyName;
    }

    public void setCommentReplyName(String commentReplyName) {
        this.commentReplyName = commentReplyName;
    }



    public Comment(String commentName, String commentContent, String commentReplyName) {
        this.commentName = commentName;
        this.commentContent = commentContent;
        this.commentReplyName = commentReplyName;
    }

    public Comment() {
    }
}
