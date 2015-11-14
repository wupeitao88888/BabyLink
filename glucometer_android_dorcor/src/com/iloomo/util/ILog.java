package com.iloomo.util;

import android.util.Log;

public class ILog {
	public static final boolean isDebug = true;
	public static void d(String tag,String msg){
		if(isDebug)
		Log.d(tag, msg+"\n");
	}

}
