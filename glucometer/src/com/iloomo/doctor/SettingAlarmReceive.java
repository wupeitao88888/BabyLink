/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iloomo.doctor;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.iloomo.glucometer.modle.User;
import com.iloomo.glucometer.view.AlertScheme;
// Need the following import to get access to the app resources, since this
// class is in a sub-package.

/**
 * This is an example of implement an {@link BroadcastReceiver} for an alarm
 * that should occur once.
 * <p>
 * When the alarm goes off, we show a <i>Toast</i>, a quick message.
 */
public class SettingAlarmReceive extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent intent1 = new Intent(context, SettingNextAlarmReceive.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent1,
				0);
		Calendar calendar = getNextPointTime();
		if(calendar == null){
			Toast.makeText(context, "没有可以提醒的时间点", Toast.LENGTH_SHORT).show();
			return;
		}
		// Schedule the alarm!
		AlarmManager am = (AlarmManager) context
				.getSystemService(context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
	}

	/**
	 * This method creates an array of Intent objects representing the activity
	 * stack for the incoming message details state that the application should
	 * be in when launching it from a notification.
	 */
	static Intent[] makeMessageIntentStack(Context context, CharSequence from,
			CharSequence msg) {
		Intent[] intents = new Intent[1];
		// "App/Notification"
		intents[0] = new Intent(context,
				com.iloomo.doctor.HostMainActivity.class);
		intents[0].putExtra("notification", 1);
		return intents;
	}

	public static Calendar  getCurrentPointTime(){
		return getNextPointTime(0);
	}
	
	public static Calendar getNextPointTime(){
		return getNextPointTime(1);
	}
	private static Calendar getNextPointTime(int step) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int index0 = dayOfWeek - 2;
		if (index0 == -1) {
			index0 = 6;
		}
		int index1 = TabDetection.getPointIndex(hour);
		float[][] values;
		if (User.schemeIndex == 0) {
			values = AlertScheme.point5;
		} else {
			values = AlertScheme.point7;
		}
		int nextPointDay = -1;
		int nextPointIndex = -1;
		index1+=step;
		for (int i = index0; i < values.length; i++) {
			boolean mustBreak = false;
			for (int j = index1; j < values[i].length; j++) {
				int _value = (int) values[i][j];
				if (_value == 1) {
					nextPointIndex = j;
					mustBreak = true;
					break;
				}
			}
			nextPointDay = i;
			if (mustBreak) {
				break;
			} else {
				index1 = 0;// 第二天了时间从新来过
			}
		}
		if(nextPointIndex == -1){
			for (int i = 0; i < values.length; i++) {
				boolean mustBreak = false;
				for (int j = 0; j < values[i].length; j++) {
					int _value = (int) values[i][j];
					if (_value == 1) {
						nextPointIndex = j;
						mustBreak = true;
						break;
					}
				}
				nextPointDay++;
				if (mustBreak) {
					break;
				}
			}
		}
		if (nextPointDay != -1 && nextPointIndex != -1) {
			int dd = (nextPointDay - index0);// 下一个测试点距离今天有几天。
			int nextPointTime = TabDetection.getPointTime(nextPointIndex);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			int days = calendar.get(Calendar.DATE);
			int __year = calendar.get(Calendar.YEAR);
			int __month = calendar.get(Calendar.MONTH);
			int __date = calendar.get(Calendar.DAY_OF_MONTH);
			int ___day =  __date+dd;
			if(___day>days){
				___day-=days;
				__month+=1;
				if(__month>11){
					__year+=1;
					__month=0;
				}
			}
			 calendar.set(__year, __month,___day, nextPointTime, 0,0);
			 return calendar;
		}
		
		return null;
	}


}
