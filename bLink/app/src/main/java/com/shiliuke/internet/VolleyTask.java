/*
 * Copyright (C) 2015 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package com.shiliuke.internet;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * @author lvfl
 * 创建时间：2015年11月5日 上午10:31:13
 *
 * 轻型网络请求框架
 */
public class VolleyTask {
	
	private Context context;
	private RequestQueue mQueue;
	private static VolleyTask instance;
	

	private VolleyTask(Context context) {
		this.context = context;
		mQueue = Volley.newRequestQueue(context);  
	}
	
	public static VolleyTask getInstance(Context context){
		if(instance==null){
			synchronized (VolleyTask.class) {
				if(instance==null){
					instance = new VolleyTask(context);
				}
			}
		}
		return instance;
	}
	
	public void addRequest(Request request){

		mQueue.add(request);
		mQueue.start();
	}
	
	private void initParam(Map<String,String> params) {
		//参数初始化(预留方法)
		

	}

	public void cancleAll(){
		mQueue.cancelAll(context);
	}
	
	
}
