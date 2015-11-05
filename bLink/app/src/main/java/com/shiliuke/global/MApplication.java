package com.shiliuke.global;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


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
	private static MApplication app;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		app = this;
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

	public static MApplication getApp() {
		return app;
	}
}
