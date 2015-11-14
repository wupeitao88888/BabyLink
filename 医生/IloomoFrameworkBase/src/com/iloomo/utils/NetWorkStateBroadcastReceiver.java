package com.iloomo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.iloomo.base.BaseApplication;

/**
 * 
 * 创 建 人: DavikChen
 * 日    期：  2012-12-5  下午11:19:15
 * 修 改 人： 
 * 日   期： 
 * 描   述： 手机网络状态监听器
 * 版 本 号：1.0
 */
public class NetWorkStateBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
			// 刷新应用网络状态
			BaseApplication.mNetWorkState = NetworkUtils.getNetworkState(context);
		}
	}

}
