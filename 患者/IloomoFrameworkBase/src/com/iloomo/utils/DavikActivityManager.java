package com.iloomo.utils;

import java.util.Stack;

import android.app.Activity;

/**
 * 用来管理app 所有的页面 程序退出 出现异常时 关闭所有页面
 * @author DavikChen
 */
public class DavikActivityManager {
	private static Stack<Activity> activityStack = new Stack<Activity>(); // 管理的 activity stack
	private static DavikActivityManager instance; // 单例模式

	private DavikActivityManager() {
	}

	/**
	 * 获得activityManager 对象
	 * @return
	 */
	public static DavikActivityManager getScreenManager() {
		if (instance == null) {
			instance = new DavikActivityManager();
		}
		return instance;
	}

	// 退出栈顶Activity
	public void popActivity(Activity activity) {
		if (activity != null) {
			// 在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}

	// 获得当前栈顶Activity
	public Activity currentActivity() {
		Activity activity = null;
		if (!activityStack.empty())
			activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 获得当前activity的类名
	 * @return
	 */
	public String getCurrentActivityName() {
		Activity activity = null;
		if (!activityStack.empty())
			activity = activityStack.lastElement();
		return activity!=null?activity.getClass().getSimpleName():"";
	}

	// 将当前Activity推入栈中
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	// 退出栈中所有Activity
	public void exitApp(Class<?> cls) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}

}
