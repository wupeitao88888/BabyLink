package com.shiliuke.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpt on 2015/10/27.
 */
public class Topic {
    private String name;
    private List<UserImgs> ui = new ArrayList<UserImgs>();
    private List<Comment> ct = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserImgs> getUi() {
        return ui;
    }

    public void setUi(List<UserImgs> ui) {
        this.ui = ui;
    }

    public List<Comment> getCt() {
        return ct;
    }

    public void setCt(List<Comment> ct) {
        this.ct = ct;
    }

    public Topic(String name, List<UserImgs> ui, List<Comment> ct) {
        this.name = name;
        this.ui = ui;
        this.ct = ct;
    }

    public Topic() {
    }
}
