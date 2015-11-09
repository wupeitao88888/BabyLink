package com.shiliuke.bean;

/**
 *
 * 我的邻居
 * Created by wupeitao on 15/11/9.
 */
public class Neighbour {
    private String pic;//头像
    private String name;//姓名
    private String address;//地址

    public Neighbour(String pic, String name, String address) {
        this.pic = pic;
        this.name = name;
        this.address = address;
    }

    public Neighbour() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
