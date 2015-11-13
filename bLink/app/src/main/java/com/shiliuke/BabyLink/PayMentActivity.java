package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.WXPayItem;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.squareup.otto.Subscribe;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 支付页
 * Created by wupeitao on 15/11/8.
 */
public class PayMentActivity extends ActivitySupport {
    private IWXAPI mWXApi;
    public static final String PARAMS_TITLE = "params_title";
    public static final String PARAMS_PRICE = "params_price";
    private TextView tv_payment_paytitle;
    private TextView tv_payment_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mWXApi = WXAPIFactory.createWXAPI(this, AppConfig.WX_APP_ID, true);
        mWXApi.registerApp(AppConfig.WX_APP_ID);
        MApplication.getInstance().getBus().register(this);
        setCtenterTitle("支付");
        initView();
        initData();
    }

    private void initView() {
        tv_payment_paytitle = (TextView) findViewById(R.id.tv_payment_paytitle);
        tv_payment_price = (TextView) findViewById(R.id.tv_payment_price);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        tv_payment_paytitle.setText(intent.getStringExtra(PARAMS_TITLE));
        tv_payment_price.setText(intent.getStringExtra(PARAMS_PRICE));
    }


    /**
     * 微信支付
     *
     * @param item
     */
    protected void wxPayment(WXPayItem item) {
        if (item == null) {
//            MMAlert.showToast(this, "参数为空");
            return;
        }
        PayReq req = new PayReq();
        req.appId = AppConfig.WX_APP_ID;
        req.partnerId = item.partnerid;
        req.nonceStr = item.noncestr;
        req.packageValue = item.packageValue;
        req.prepayId = item.prepayid;
        req.timeStamp = item.timestamp;
        req.sign = item.sign;
        mWXApi.sendReq(req);
    }

    /**
     * 支付宝
     */
    protected void aLiPayment() {

    }

    @Subscribe
    public void handleMessage(Intent intent) {
        if (AppConfig.ACTION_WXSHARE_SUCCESS.equalsIgnoreCase(intent.getAction())) {
//            MMAlert.showToast(this, "分享成功");
        } else if (AppConfig.ACTION_WXSHARE_FAILD.equalsIgnoreCase(intent.getAction())) {
//            MMAlert.showToast(this, "分享失败");
        } else if (AppConfig.ACTION_WXPAY_SUCCESS.equalsIgnoreCase(intent.getAction())) {
//            MMAlert.showToast(this, "微信支付成功");


        } else if (AppConfig.ACTION_WXPAY_CANCLE.equalsIgnoreCase(intent.getAction())) {
//            MMAlert.showToast(this, "微信支付取消");
        } else if (AppConfig.ACTION_WXPAY_UNSUPPORT.equalsIgnoreCase(intent.getAction())) {
//            MMAlert.showToast(this, "您的微信不支持支付");
        } else if (AppConfig.ACTION_WXPAY_FAILD.equalsIgnoreCase(intent.getAction())) {
//            MMAlert.showToast(this, "微信支付失败");
        }
    }

    @Override
    protected void onDestroy() {
        MApplication.getInstance().getBus().unregister(this);
        mWXApi.unregisterApp();
        super.onDestroy();
    }
}
