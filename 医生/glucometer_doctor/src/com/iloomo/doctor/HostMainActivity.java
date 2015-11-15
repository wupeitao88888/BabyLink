package com.iloomo.doctor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.iloomo.glucometer.service.DataSync;
import com.umeng.update.UmengUpdateAgent;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

public class HostMainActivity extends TabHostActivity implements
		OnClickListener {
	public static HostMainActivity hma;
	public SharedPreferences mSharedPreferences; // 本地存储
	List<TabItem> mItems;
	private TabHost mTabHost;
	DBTools dbt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UmengUpdateAgent.update(this);
		int flags = getIntent().getIntExtra("notification", 0);
		if (flags == 1) {
//			setCurrentTab(2);
		}
		/***
		 * 2015 11/14 4:45  启动服务不要了暂时
		 */
//		Intent ds = new Intent(this, DataSync.class);
//		startService(ds);
		hma = this;
	}
	public void initNoty(){
		Intent intent = new Intent(HostMainActivity.this, SettingAlarmReceive.class);
		PendingIntent sender = PendingIntent.getBroadcast(HostMainActivity.this,
				0, intent, 0);
		// We want the alarm to go off 30 seconds from now.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 10);
		// Schedule the alarm!
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
	}
	protected void prepare() {
//		TabItem alert = new TabItem("提醒", R.drawable.home_tab1,
//				R.drawable.example_tab_item_bg,
//				new Intent(this, TabAlert.class));
		
		TabItem detection = new TabItem("消息", R.drawable.home_tab3,
				R.drawable.example_tab_item_bg, new Intent(this,
						TabDetection.class));
		TabItem data = new TabItem("饮食", R.drawable.home_tab2,
				R.drawable.example_tab_item_bg, new Intent(this,
						TabAlert.class));
		TabItem shop = new TabItem("患者", R.drawable.home_tab4,
				R.drawable.example_tab_item_bg, new Intent(this, TabShop.class));
		TabItem me = new TabItem("我", R.drawable.home_tab5,
				R.drawable.example_tab_item_bg, new Intent(this, TabMe.class));
		mItems = new ArrayList<TabItem>();
//		mItems.add(alert);
		mItems.add(detection);
		mItems.add(data);
		
		mItems.add(shop);
		mItems.add(me);
		// 设置分割线
		TabWidget tabWidget = getTabWidget();
		tabWidget.setDividerDrawable(null);
		mTabHost = getTabHost();
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {

			}
		});
	}

	protected void setTabItemTextView(TextView textView, int position) {
		// TODO Auto-generated method stub
		textView.setPadding(0, 0, 0, 0);
		textView.setText(mItems.get(position).getTitle());
		textView.setBackgroundResource(mItems.get(position).getBg());
		// 在textview上面设置一个图片
		textView.setCompoundDrawablesWithIntrinsicBounds(0, mItems
				.get(position).getIcon(), 0, 0);

	}

	/** tab唯一的id */
	@Override
	protected String getTabItemId(int position) {
		return mItems.get(position).getTitle(); // 我们使用title来作为id，你也可以自定
	}

	/** 点击tab时触发的事件 */
	@Override
	protected Intent getTabItemIntent(int position) {
		return mItems.get(position).getIntent();
	}

	@Override
	protected int getTabItemCount() {
		return mItems.size();
	}

	/** 自定义头部文件 */
	public void findViewById(Activity view) {
		if (mSharedPreferences.getBoolean(AppConstant.REMIND_SET_KEY, true)) {

		}

	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Intent ds = new Intent(this, DataSync.class);
		// stopService(ds);
	}

}
