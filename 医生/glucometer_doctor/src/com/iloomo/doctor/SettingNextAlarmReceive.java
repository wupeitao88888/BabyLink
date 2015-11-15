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

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
// Need the following import to get access to the app resources, since this
// class is in a sub-package.

/**
 * This is an example of implement an {@link BroadcastReceiver} for an alarm
 * that should occur once.
 * <p>
 * When the alarm goes off, we show a <i>Toast</i>, a quick message.
 */
@SuppressLint("NewApi")
public class SettingNextAlarmReceive extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "血糖检测提醒", Toast.LENGTH_SHORT).show();
		showAppNotification(context);
		Intent intent1 = new Intent(context, SettingNextAlarmReceive.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent1,
				0);
		Calendar calendar = SettingAlarmReceive.getNextPointTime();
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

	@SuppressLint("NewApi")
	void showAppNotification(Context context) {
		// look up the notification manager service
		NotificationManager nm = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);

		// The details of our fake message
		CharSequence from = "血糖检测提醒";
		CharSequence message = "";
		PendingIntent contentIntent = PendingIntent.getActivities(context, 0,
				makeMessageIntentStack(context, from, message),
				PendingIntent.FLAG_CANCEL_CURRENT);
		String tickerText = context.getString(R.string.about_update_text1,
				message);
		Notification notif = new Notification(R.drawable.logo1, tickerText,
				System.currentTimeMillis());
		notif.setLatestEventInfo(context, from, message, contentIntent);

		notif.defaults = Notification.DEFAULT_ALL;
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		try {
			nm.notify(R.string.alertMessage, notif);
		} catch (Exception e) {

			e.printStackTrace();
		}
		;
	}

}
