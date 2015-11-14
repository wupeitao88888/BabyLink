package com.iloomo.utils;

import org.apache.http.HttpHost;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 手机网络监听工具类
 * @author davikchen
 */
public class NetworkUtils {
	public static int TYPE_NO = 0;
	public static int TYPE_MOBILE_CMNET = 1;
	public static int TYPE_MOBILE_CMWAP = 2;
	public static int TYPE_WIFI = 3;
	public static int TYPE_MOBILE_CTWAP = 4; // 移动梦网代理

	private static NetworkInfo ni = null;

	private final static String LOG = "NetworkUtils";

	/**
	 * 获取当前手机连接的网络类型
	 * @param context 上下文
	 * @return TYPE_MOBILE_CMNET： net网络 TYPE_MOBILE_CMWAP: cmwap uniwap 3gwap 网络 TYPE_WIFI：wifi网络 TYPE_MOBILE_CTWAP：移动梦网
	 */
	public static int getNetworkState(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获得当前网络信息
		ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isAvailable()) {
			int currentNetWork = ni.getType();
			// 手机网络
			if (currentNetWork == ConnectivityManager.TYPE_MOBILE) {
				if (ni.getExtraInfo() != null && ni.getExtraInfo().equals("cmwap")) {
					LogMessage.i(LOG, "当前网络为:cmwap网络");
					return TYPE_MOBILE_CMWAP;
				} else if (ni.getExtraInfo() != null && ni.getExtraInfo().equals("uniwap")) {
					LogMessage.i(LOG, "当前网络为:uniwap网络");
					return TYPE_MOBILE_CMWAP;
				} else if (ni.getExtraInfo() != null && ni.getExtraInfo().equals("3gwap")) {
					LogMessage.i(LOG, "当前网络为:3gwap网络");
					return TYPE_MOBILE_CMWAP;
				} else if (ni.getExtraInfo() != null && ni.getExtraInfo().contains("ctwap")) {
					LogMessage.i(LOG, "当前网络为:" + ni.getExtraInfo() + "网络");
					return TYPE_MOBILE_CTWAP;
				} else {
					LogMessage.i(LOG, "当前网络为:net网络");
					return TYPE_MOBILE_CMNET;
				}
				// wifi 网络
			} else if (currentNetWork == ConnectivityManager.TYPE_WIFI) {
				LogMessage.i(LOG, "当前网络为:WIFI网络");
				return TYPE_WIFI;
			}
		}
		LogMessage.i(LOG, "当前网络为:不是我们考虑的网络");
		return TYPE_NO;
	}

	/**
	 * 获得代理对象
	 * @return 代理对象
	 */
	public static HttpHost getHttpHostProxy() {
		int netType = getNetworkState(DavikActivityManager.getScreenManager().currentActivity());
		HttpHost proxy = null;
		if (netType == TYPE_MOBILE_CTWAP) { // 移动梦网
			proxy = new HttpHost("10.0.0.200", 80);
		} else { // cmwap uniwap 3gwap
			proxy = new HttpHost("10.0.0.172", 80);
		}

		return proxy;

	}
}
