package com.iloomo.glucometer;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.iloomo.glucometer.view.DefaultDialogBuilder;
import com.umeng.analytics.MobclickAgent;
public abstract class TabHostActivity extends TabActivity {

	private TabHost mTabHost; // 主控件
	private TabWidget mTabWidget; // 导航栏
	private LayoutInflater mLayoutflater; // 布局管理器
	private TextView tabCount ;//下载中的任务数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 去掉阴影
		setTheme(R.style.Theme_Tabhost);
		setContentView(R.layout.api_tab_host);
		mLayoutflater = getLayoutInflater();
		mTabHost = getTabHost();
		mTabWidget = getTabWidget();
		prepare();
		initTop();
		initTabSpec();
		
	}

	/**
	 * 初始化页面title布局 子页面必须实现 getTop方法
	 */
	private void initTop() {
		View child = getTop();
		LinearLayout layout = (LinearLayout) findViewById(R.id.tab_top);
		if(child!=null)
		layout.addView(child);
	}

	/**
	 * 初始化导航栏
	 */
	private void initTabSpec() {
		int count = getTabItemCount(); // 获得总数
		for (int i = 0; i < count; i++) {
			// 每个导航标签 都是一个textview 上面是图片 下面是文字
			View tabItem = mLayoutflater.inflate(R.layout.api_tab_item, null);
			TextView tvTabItem = (TextView) tabItem.findViewById(R.id.tab_item_tv);
		
			setTabItemTextView(tvTabItem, i);
			// set id
			String tabItemId = getTabItemId(i);
			// set tab spec
			TabSpec tabSpec = mTabHost.newTabSpec(tabItemId);
			tabSpec.setIndicator(tabItem); // Specify a label as the tab indicator.
			tabSpec.setContent(getTabItemIntent(i)); // Specify an intent to use to launch an activity as the tab content
			if (i== 4) {
				tabCount = (TextView) tabItem.findViewById(R.id.tabCount);
				tabCount.setVisibility(View.GONE);
			}
			mTabHost.addTab(tabSpec);
		}

	}
	
	public void refreshTabIcon(){
		int count = getTabItemCount(); // 获得总数
		for (int i = 0; i < count; i++) {
			// 每个导航标签 都是一个textview 上面是图片 下面是文字
			View tabItem = mLayoutflater.inflate(R.layout.api_tab_item, null);
			TextView tvTabItem = (TextView) tabItem.findViewById(R.id.tab_item_tv);
		
			setTabItemTextView(tvTabItem, i);
		}
	}

	/** 在初始化界面之前调用 */
	protected void prepare() {
		// do nothing or you override it
	}

	/** 自定义头部布局 */
	protected View getTop() {
		// do nothing or you override it
		return null;
	}

	/**
	 * 返回导航栏个数
	 * @return 导航栏个数
	 */
	protected int getTabCount() {
		return mTabHost.getTabWidget().getTabCount();
	}

	/** 设置TabItem的图标和标题等 子页面实现 */
	abstract protected void setTabItemTextView(TextView textView, int position);

	/** 设置导航栏唯一id 子页面实现 **/
	abstract protected String getTabItemId(int position);

	/** 设置导航栏跳转intent 子页面制定 **/
	abstract protected Intent getTabItemIntent(int position);

	/** 设置导航栏总数　 **/
	abstract protected int getTabItemCount();

	/**
	 * 设置选择当前导航栏索引
	 * @param index 导航栏索引
	 */
	public void setCurrentTab(int index) {
		mTabHost.setCurrentTab(index);
	}

	/**
	 * 设置焦点给导航栏
	 * @param index 导航栏索引
	 */
	protected void focusCurrentTab(int index) {
		mTabWidget.focusCurrentTab(index);
		
	}

	/**
	 * 获得当前导航位置
	 * @return
	 */
	protected int getCurrentIndex() {

		return mTabHost.getCurrentTab();
	}
	
	private DefaultDialogBuilder ib; // 进度条
	/**
	 * show the processDialog
	 */
	public void showProcessDialog(String content) {

		if (ib == null) {
			ib = new DefaultDialogBuilder(TabHostActivity.this, 1);
		} else {
			ib = new DefaultDialogBuilder(TabHostActivity.this, 1);
		}

		ib.setTitle(R.string.dialog_title);
		if (content.equals("")) {
			ib.setMessage(getString(R.string.progress_content_message) + "...");
		} else
			ib.setMessage(content);
		ib.show();
	}

	/**
	 * cancle the progressDialog
	 */
	public void cancleProcessDialog() {
		if (ib != null) {
			ib.cancle();
		}
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
