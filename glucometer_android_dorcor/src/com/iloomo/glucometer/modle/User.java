package com.iloomo.glucometer.modle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.iloomo.base.BaseApplication;
import com.iloomo.glucometer.service.DataSync;

public class User {
	public static float[] afterValue = { 3.3f, 6.7f };
	public static float[] beforValue = { 3.3f, 5.3f };
	public static  float maxValue=0.0f,mixValue=0.0f,afterAverage=0.0f,beforAverage=0.0f;
	public static int beforNormalValueTimes,afterNormalValueTimes,beforHeighValueTimes,afterHeighValueTimes,beforLowValueTimes,afterLowValueTimes;
	public static String uid;
	public static String gestation;
	public static String myDoctor;
	public static String password;
	public static String phone;
	public static float data;
	public static String name = "";
	public static int schemeIndex = -1;

	public static void saveUserIno(Context context, String puid,
			String ppassword, String pphone) {
		uid = puid;
		password = ppassword;
		phone = pphone;
		saveCurrentUser(context);
		// 实例化SharedPreferences对象（第一步）
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				BaseApplication.mAppName + uid, Activity.MODE_PRIVATE);
		// 实例化SharedPreferences.Editor对象（第二步）
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		// 用putString的方法保存数据
		editor.putString("uid", puid);
		editor.putString("password", ppassword);
		editor.putString("phone", pphone);
		editor.commit();
		loadData(context, true);
	}

	public static void saveScheme(Context context, int index) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				BaseApplication.mAppName, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		// 用putString的方法保存数据
		schemeIndex = index;
		editor.putInt("schemeIndex", schemeIndex);
		editor.commit();
	}

	public static void loadScheme(Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				BaseApplication.mAppName, Activity.MODE_PRIVATE);
		schemeIndex = mySharedPreferences.getInt("schemeIndex", -1);
	}

	private static void saveCurrentUser(Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				BaseApplication.mAppName, Activity.MODE_PRIVATE);
		// 实例化SharedPreferences.Editor对象（第二步）
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		// 用putString的方法保存数据
		editor.putString("uid", uid);
		editor.commit();
	}

	/**
	 * 保存预产期
	 * @param context
	 * @param pGestation
	 */
	public static void saveGestation(Context context, String pGestation) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				BaseApplication.mAppName + uid, Activity.MODE_PRIVATE);
		// 实例化SharedPreferences.Editor对象（第二步）
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		// 用putString的方法保存数据
		editor.putString("gestation", pGestation);
		editor.commit();
	}

	public static void saveName(Context context, String pName) {
		name = pName;
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				BaseApplication.mAppName + uid, Activity.MODE_PRIVATE);
		// 实例化SharedPreferences.Editor对象（第二步）
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		// 用putString的方法保存数据
		editor.putString("name", pName);
		editor.commit();
	}

	public static void saveMyDoctor(Context context, String pmyDoctor) {
		myDoctor = pmyDoctor;
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				BaseApplication.mAppName + uid, Activity.MODE_PRIVATE);
		// 实例化SharedPreferences.Editor对象（第二步）
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		// 用putString的方法保存数据
		editor.putString("myDoctor", pmyDoctor);
		editor.commit();
	}

	public static void loadData(Context context, boolean login) {
		if (!login) {
			loadUser(context);
		}
		loadScheme(context);
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				BaseApplication.mAppName + uid, Activity.MODE_PRIVATE);
		// 实例化SharedPreferences.Editor对象（第二步）
		phone = mySharedPreferences.getString("phone", null);
		password = mySharedPreferences.getString("password", null);
		gestation = mySharedPreferences.getString("gestation", null);
		myDoctor = mySharedPreferences.getString("myDoctor", null);
		name = mySharedPreferences.getString("name", null);
	}

	public static boolean guid(Context context){
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				BaseApplication.mAppName, Activity.MODE_PRIVATE);
		return mySharedPreferences.getBoolean("guid", false);
	}
	public static void  saveGuid(Context context){
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				BaseApplication.mAppName, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		// 用putString的方法保存数据
		editor.putBoolean("guid", true);
		editor.commit();
	}
	
	public static void loadUser(Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				BaseApplication.mAppName, Activity.MODE_PRIVATE);
		DataSync.userid = uid = mySharedPreferences.getString("uid", null);
	}

	public static boolean showLoginButton() {
		return uid == null || "".equals(uid);
	}

	public static void logout(Context context) {
		maxValue=0.0f;mixValue=0.0f;afterAverage=0.0f;beforAverage=0.0f;
		 beforNormalValueTimes=0;
		 afterNormalValueTimes=0;
		 beforHeighValueTimes=0;
		 afterHeighValueTimes=0;
		 beforLowValueTimes=0;
		 afterLowValueTimes=0;
		uid = "";
		// 实例化SharedPreferences对象（第一步）
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				BaseApplication.mAppName, Activity.MODE_PRIVATE);
		// 实例化SharedPreferences.Editor对象（第二步）
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		// 用putString的方法保存数据
		editor.putString("uid", uid);
		editor.commit();
		loadData(context, false);
	}
}
