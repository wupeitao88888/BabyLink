package com.blink.global;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;



/**
 * 
 *
 * 
 * @author wpt
 */
public class MApplication extends Application {
	private static List<Activity> activityList = new LinkedList<Activity>();
//    public static  String VERSION;
	public static Context context;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// �쳣����
//        if(!L.isDebug){
////            CrashHandler crashHandler = CrashHandler.getInstance();
//            crashHandler.init(getApplicationContext());
//        }
//		VERSION=getResources().getString(R.string.vistion);

		context=getApplicationContext();
	}


	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public static void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.gc();
		System.exit(0);
	}
}
