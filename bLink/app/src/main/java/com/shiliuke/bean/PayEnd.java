package com.shiliuke.bean;

/**
 * 支付尾款
 * Created by wupeitao on 15/11/7.
 */
public class PayEnd {
    private String pay_title;//商品名称
    private String pay_deposit;//订金
    private String already;//已付
    private String not_yet;//未付=实付
    private String pay_url;//商品图
    private String pay_status;//支付状态

    public String getPay_status() {
        return pay_status;
    }
    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public PayEnd() {
    }

    public PayEnd(String pay_title, String pay_deposit, String already, String not_yet, String pay_url) {
        this.pay_title = pay_title;
        this.pay_deposit = pay_deposit;
        this.already = already;
        this.not_yet = not_yet;
        this.pay_url = pay_url;
    }

    public String getPay_title() {
        return pay_title;
    }

    public void setPay_title(String pay_title) {
        this.pay_title = pay_title;
    }

    public String getPay_deposit() {
        return pay_deposit;
    }

    public void setPay_deposit(String pay_deposit) {
        this.pay_deposit = pay_deposit;
    }

    public String getAlready() {
        return already;
    }

    public void setAlready(String already) {
        this.already = already;
    }

    public String getNot_yet() {
        return not_yet;
    }

    public void setNot_yet(String not_yet) {
        this.not_yet = not_yet;
    }

    public String getPay_url() {
        return pay_url;
    }

    public void setPay_url(String pay_url) {
        this.pay_url = pay_url;
    }
}
