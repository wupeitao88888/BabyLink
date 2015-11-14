package com.iloomo.glucometer;

import android.content.Intent;

/**
 * 标签类
 * @author iloomo
 *
 */
public class TabItem {
	private String title;		// 标签文字
	private int icon;			// 标签图标
	private int bg;			// 标签点击效果
	private Intent intent;	// 页面跳转对象
	
	/**
	 * 标签类构造函数
	 * @param title 标签文字
	 * @param icon 标签图标
	 * @param bg 标签点击效果
	 * @param intent 页面跳转对象
	 */
	public TabItem(String title, int icon, int bg, Intent intent) {
		super();
		this.title = title;
		this.icon = icon;
		this.bg = bg;
		this.intent = intent;
	}
	
	/**
	 * 获得标签文字
	 * @return 标签文字
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标签文字
	 * @param title 标签文字
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获得标签图标
	 * @return 标签图标资源id
	 */
	public int getIcon() {
		return icon;
	}

	/**
	 * 设置标签图标
	 * @param icon 标签图标资源id
	 */
	public void setIcon(int icon) {
		this.icon = icon;
	}

	/**
	 * 设置标签点击效果
	 * @return
	 */
	public int getBg() {
		return bg;
	}

	/**
	 * 设置标签点击效果
	 * @param bg
	 */
	public void setBg(int bg) {
		this.bg = bg;
	}

	/**
	 * 获得页面跳转对象 intent
	 * @return 页面跳转intent
	 */
	public Intent getIntent() {
		return intent;
	}

	/**
	 * 设置页面跳转intent
	 * @param intent 页面跳转intent
	 */
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
}
