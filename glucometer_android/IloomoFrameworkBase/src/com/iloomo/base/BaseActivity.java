package com.iloomo.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
/**
 * 
 * 创 建 人: DavikChen
 * 日    期：  2012-12-5  下午11:04:05
 * 修 改 人： 
 * 日   期： 
 * 描   述： 基类
 * 版 本 号：1.0
 */
public abstract class BaseActivity extends Activity {
	public SharedPreferences mSharedPreferences; // 本地存储
	private boolean isPushStack = true; // 是否压栈
	private boolean isPopStack = true; // 是否出栈
	private boolean isNoTitle =true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSharedPreferences = getSharedPreferences(BaseApplication.mAppName, Context.MODE_PRIVATE);
		if (isNoTitle) { // 没有标题栏
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		// 加载页面布局
		setContentLayout();
		// 初始化页面控件 
		findViewById();

	}

	/**
	 * 
	 * 功能描述：   加载页面布局  
	 * 创 建 人: DavikChen
	 * 日    期：  2012-12-5  下午11:07:52
	 * 修 改 人: 
	 * 日    期:
	 */
	public abstract void setContentLayout();

	/**
	 * 
	 * 功能描述：    初始化页面控件 
	 * 创 建 人: DavikChen
	 * 日    期：  2012-12-5  下午11:08:13
	 * 修 改 人: 
	 * 日    期:
	 */
	public abstract void findViewById();

	/**
	 * 
	 * 功能描述：     获取服务器数据
	 * 创 建 人: DavikChen
	 * 日    期：  2012-12-5  下午11:08:47
	 * 修 改 人: 
	 * 日    期:
	 */
	public abstract void getDataFromServer();

	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (isPopStack) {
			((BaseApplication) getApplication()).getActivityManager().popActivity(this);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isPushStack) {
			((BaseApplication) getApplication()).getActivityManager().pushActivity(this);
		}

	}
	
	/**
	 * 
	 * 功能描述： 页面是否入栈
	 *  @return    true: 入栈 false:不入栈
	 * 创 建 人: DavikChen
	 * 日    期：  2012-12-5  下午11:09:45
	 * 修 改 人: 
	 * 日    期:
	 */
	public boolean isPushStack() {
		return isPushStack;
	}

	/**
	 * 
	 * 功能描述： 设置页面是否要入栈 由栈统一来管理所有页面
	 *  @param isPushStack   true:入栈 false:不入栈 
	 * 创 建 人: DavikChen
	 * 日    期：  2012-12-5  下午11:10:46
	 * 修 改 人: 
	 * 日    期:
	 */
	public void setPushStack(boolean isPushStack) {
		this.isPushStack = isPushStack;
	}

	/**
	 * 
	 * 功能描述： 页面是否出栈
	 *  @return    true:出栈 false:不出栈
	 * 创 建 人: DavikChen
	 * 日    期：  2012-12-5  下午11:12:03
	 * 修 改 人: 
	 * 日    期:
	 */
	public boolean isPopStack() {
		return isPopStack;
	}

	/**
	 * 
	 * 功能描述： 设置页面是否要出栈 由栈统一来管理所有页面
	 *  @param isPopStack  true:出栈 false:不出栈
	 * 创 建 人: DavikChen
	 * 日    期：  2012-12-5  下午11:13:03
	 * 修 改 人: 
	 * 日    期:
	 */
	public void setPopStack(boolean isPopStack) {
		this.isPopStack = isPopStack;
	}

	/**
	 * 
	 * 功能描述： 页面是否需要标题栏
	 *  @return    
	 * 创 建 人: DavikChen
	 * 日    期：  2012-12-5  下午11:13:49
	 * 修 改 人: 
	 * 日    期:
	 */
	public boolean isNoTitle() {
		return isNoTitle;
	}

	/**
	 * 
	 * 功能描述： 设置页面是否需要标题栏
	 *  @param isNoTitle  true:不需要 false:需要
	 * 创 建 人: DavikChen
	 * 日    期：  2012-12-5  下午11:13:54
	 * 修 改 人: 
	 * 日    期:
	 */
	public void setNoTitle(boolean isNoTitle) {
		this.isNoTitle = isNoTitle;
	}
	
	

}