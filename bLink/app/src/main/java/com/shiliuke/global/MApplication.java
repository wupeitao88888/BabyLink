package com.shiliuke.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.squareup.otto.Bus;

import java.util.LinkedList;
import java.util.List;


/**
 * @author wpt
 */
public class MApplication extends Application {
    private static List<Activity> activityList = new LinkedList<Activity>();
    //    public static  String VERSION;
    public static Context context;
    private static MApplication app;
    private Bus mBus;

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

        context = getApplicationContext();
    }

    public static synchronized MApplication getInstance() {
        return app;
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

    public Bus getBus() {
        if (mBus == null) {
            mBus = new Bus();
        }
        return mBus;
    }
}
