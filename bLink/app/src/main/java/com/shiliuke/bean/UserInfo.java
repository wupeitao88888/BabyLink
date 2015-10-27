package com.shiliuke.bean;

/**
 * Created by wupeitao on 15/10/26.
 */
public class UserInfo {
    private String userpic;
    private String username;

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserInfo(String userpic, String username) {
        this.userpic = userpic;
        this.username = username;
    }

    public UserInfo() {
    }
}
