package com.iloomo.glucometer;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.iloomo.glucometer.modle.User;
import com.iloomo.glucometer.view.AlertScheme;

public class AlertActivity extends GameBaseActivity implements OnClickListener {
	AlertScheme rtv;

	@Override
	public void setContentLayout() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.alert_setting);
	
	}

	ToggleButton tbSelect;
	int schemeIndex = 0;
	boolean isSetting = false;

	public void settingScheme(boolean flag) {
		if (flag) {
			User.saveScheme(AlertActivity.this, schemeIndex);
			Intent intent1 = new Intent(AlertActivity.this,
					SettingAlarmReceive.class);
			PendingIntent sender = PendingIntent.getBroadcast(
					AlertActivity.this, 0, intent1, 0);
			Calendar calendar = SettingAlarmReceive.getCurrentPointTime();
			if (calendar == null) {
				Toast.makeText(this, "没有可以提醒的时间点", Toast.LENGTH_SHORT).show();
				return;
			}
			// Schedule the alarm!
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
		} else {
			User.saveScheme(AlertActivity.this, -1);
			Intent intent1 = new Intent(AlertActivity.this,
					SettingAlarmReceive.class);
			PendingIntent sender = PendingIntent.getBroadcast(
					AlertActivity.this, 0, intent1, 0);
			// Schedule the alarm!
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.cancel(sender);
		}
	}

	@Override
	public void findViewById() {
		if (User.schemeIndex != -1) {
			isSetting = true;
		}

		schemeIndex = User.schemeIndex;
		// TODO Auto-generated method stub
		tbSelect = (ToggleButton) findViewById(R.id.toggleButton1);
		tbSelect.setChecked(isSetting);
		tbSelect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean flag) {
				if (flag) {
					Toast.makeText(AlertActivity.this, "提醒设置成功",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(AlertActivity.this, "提醒关闭",
							Toast.LENGTH_SHORT).show();

				}
				settingScheme(flag);
			}
		});
		rtv = ((AlertScheme) findViewById(R.id.alertScheme));
		rtv.initData(AlertScheme.POINT7);
		findViewById(R.id.back).setOnClickListener(this);
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.spinner, R.id.contentTextView);
		adapter.add("5点法");
		adapter.add("7点法");
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {
				rtv.initData(position);
				String itemContent = (String) adapterView
						.getItemAtPosition(position);
				schemeIndex = position;
				if (isSetting) {
					settingScheme(true);
				}
			}

			public void onNothingSelected(AdapterView<?> view) {
			}
		});
		if (User.schemeIndex == -1) {
			spinner.setSelection(0);
		} else {
			spinner.setSelection(User.schemeIndex);
		}
		findViewById(R.id.toggleButton1);

	}

	@Override
	public void getDataFromServer() {

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.back) {
			finish();
		}
	}

	protected void onResume() {
		super.onResume();
	};
}
