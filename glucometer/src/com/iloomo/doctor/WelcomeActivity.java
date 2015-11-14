package com.iloomo.doctor;

/**
 * 欢迎页面
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.iloomo.base.BaseApplication;
import com.iloomo.glucometer.modle.User;
import com.iloomo.utils.LogMessage;

public class WelcomeActivity extends GameBaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BaseApplication.mAppName = "xuetangyi_v1";
		User.loadData(WelcomeActivity.this, false);
		LogMessage.isDebug = false;
		new CountDownTimer(1000, 500) {
			@Override
			public void onTick(long millisUntilFinished) {

			}

			@Override
			public void onFinish() {
				Intent intent = new Intent();
				if (!User.guid(WelcomeActivity.this)) {
					intent.setClass(WelcomeActivity.this, GuidActivity.class);
				} else {
					intent.setClass(WelcomeActivity.this,
							HostMainActivity.class);
				}

				startActivity(intent);
				finish();
			}
		}.start();
	}

	@Override
	public void setContentLayout() {
		setContentView(R.layout.welcome_activity);
	}

	@Override
	public void findViewById() {

	}

	@Override
	public void getDataFromServer() {
	}
}
