package com.shiliuke.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Dong Fuhai on 2014-07-22 16:42.
 *
 * @modify:
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI wxApi;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxApi = WXAPIFactory.createWXAPI(this, AppConfig.WX_APP_ID, true);
        wxApi.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            default:
                break;
        }

        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                MApplication.getInstance().getBus().post(new Intent(AppConfig.ACTION_WXSHARE_SUCCESS));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                MApplication.getInstance().getBus().post(new Intent(AppConfig.ACTION_WXSHARE_FAILD));
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                MApplication.getInstance().getBus().post(new Intent(AppConfig.ACTION_WXSHARE_FAILD));
                break;
            default:
                break;
        }
        finish();
    }
}
