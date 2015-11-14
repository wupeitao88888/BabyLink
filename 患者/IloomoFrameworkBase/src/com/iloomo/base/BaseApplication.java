package com.iloomo.base;

import android.app.Application;
import android.content.Context;

import com.iloomo.utils.CrashHandler;
import com.iloomo.utils.DavikActivityManager;


/**
 * 
 * 创 建 人: DavikChen
 * 日    期：  2012-12-5  下午11:17:48
 * 修 改 人： 
 * 日   期： 
 * 描   述： app 全部信息类 此类可以修改成抽象类 添加些抽象函数
 * 版 本 号：1.0
 */
public class BaseApplication extends Application {
	/** 应用研发状态 0 ：研发阶段 1:上线状态  **/
	public static int app_sate = 0; // 
	/** 研发中 **/
	public static final int APP_DEVELOPING =0; 
	/** 上线中 **/
	public static final int APP_LINE =1; 
	/** 应用异常是否发送异常日志给开发者 0:发送 1:不发送 **/
	public static  int EXCEPTION_EMAIL_OFF = 0;
	/** 开发者联系方式 **/
	public static String developEmail = "davikzongxiang@gmail.com";
	/** app 名称 应用存在SharedPreferences用的名称 **/
	public static String mAppName = "default_app_name"; // 
	/**  网络状态 **/
	public static int mNetWorkState; 
	/** activity管理对象 **/
	public static DavikActivityManager activityManager = null;
	/** 应用程序上下文 **/
	public static Context mContext;
	

	@Override
	public void onCreate() {
		mContext = this;
		activityManager = DavikActivityManager.getScreenManager();
		if (app_sate == APP_LINE) { // 线上状态时 捕获异常 
			 CrashHandler crashHandler = CrashHandler.getInstance();
			 crashHandler.init(getApplicationContext());
		}
	}

	

	/**
	 * 
	 * 功能描述： 获得activity栈管理器对象
	 *  @return    DavikActivityManager 管理对象
	 * 创 建 人: DavikChen
	 * 日    期：  2012-12-5  下午11:18:38
	 * 修 改 人: 
	 * 日    期:
	 */
	public DavikActivityManager getActivityManager() {
		return activityManager;
	}
	
}
