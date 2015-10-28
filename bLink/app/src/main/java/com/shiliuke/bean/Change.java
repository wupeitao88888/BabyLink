package com.shiliuke.bean;

/**
 * Created by wpt on 2015/10/28.
 */
public class Change {
    private String changeName;//交换人姓名
    private String changAddress;//交换地址
    private String changePic;//交换人头像
    private String changeUrl;//交换图片
    private String mineGoods;//我的物品
    private String exchangeGoods;//换的物品

    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }

    public String getChangAddress() {
        return changAddress;
    }

    public void setChangAddress(String changAddress) {
        this.changAddress = changAddress;
    }

    public String getChangePic() {
        return changePic;
    }

    public void setChangePic(String changePic) {
        this.changePic = changePic;
    }

    public String getChangeUrl() {
        return changeUrl;
    }

    public void setChangeUrl(String changeUrl) {
        this.changeUrl = changeUrl;
    }

    public String getMineGoods() {
        return mineGoods;
    }

    public void setMineGoods(String mineGoods) {
        this.mineGoods = mineGoods;
    }

    public String getExchangeGoods() {
        return exchangeGoods;
    }

    public void setExchangeGoods(String exchangeGoods) {
        this.exchangeGoods = exchangeGoods;
    }


    public Change() {
    }

    public Change(String changeName, String changAddress, String changePic, String changeUrl, String mineGoods, String exchangeGoods) {
        this.changeName = changeName;
        this.changAddress = changAddress;
        this.changePic = changePic;
        this.changeUrl = changeUrl;
        this.mineGoods = mineGoods;
        this.exchangeGoods = exchangeGoods;
    }
}
