/*
 * Copyright (C) 2015 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package com.shiliuke.model;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.shiliuke.BabyLink.LoginActivity;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.shiliuke.internet.NormalPostRequest;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.internet.VolleyTask;
import com.shiliuke.utils.L;
import com.shiliuke.utils.LCSharedPreferencesHelper;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuemx 创建时间：2015年4月17日 下午12:08:45
 *         <p/>
 *         类 描述
 */
public class BasicRequest {

    private static BasicRequest basic;
    private static String baseURL;

    /**
     * 获取唯一实例对象
     */
    public static BasicRequest getInstance() {
        if (basic == null) {
            synchronized (BasicRequest.class) {
                if (basic == null) {
                    basic = new BasicRequest();
                }
            }
        }
        baseURL = LoginActivity.BASE_URL;

        return basic;
    }

    /**
     * 通过 “ID” 请求列表的时候调用的方法
     */
    public void request(final VolleyListerner listerner, final int action,
                        String... strs) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseURL);
        sb.append(pubString(strs));
        String requesturl = sb.toString();
        L.e("发起URL=" + requesturl);
        VolleyTask.getInstance(MApplication.getApp()).addRequest(
                new StringRequest(requesturl, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (null != response) {
                            L.e("action=" + action + " 网络成功：" + response);
                            listerner.onResponse(response, action);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (!TextUtils.isEmpty(error.getMessage())) {
                            L.e("action=" + action + " 网络错误：" + error.getMessage());
                            listerner.onResponseError(error.getMessage(),
                                    action);
                        }
                    }
                }) {
                    @Override
                    public RetryPolicy getRetryPolicy() {
                        // TODO Auto-generated constructor stub
                        return new DefaultRetryPolicy(30 * 1000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    }
                });
    }

    /**
     * 通过 “ID” 请求列表的时候调用的方法
     */
    public void requestPost(final VolleyListerner listerner, final int action, final Map<String, String> params, String strs) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.BASE_URL);
        sb.append(strs);
        String requesturl = sb.toString();

        L.e("发起URL=" + requesturl+addParams(params));
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    L.e("action=" + action + " 网络成功：" + response.toString());
                    listerner.onResponse(response.toString(), action);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    L.e("action=" + action + " 网络错误：" + error.getMessage());
                    listerner.onResponseError(error.getMessage(),
                            action);
                }
            }
        }, params));
    }
    public String addParams(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        for (String key : params.keySet()) {
            if (params.size() > 0)
                result.append("&");
            result.append(key);
            result.append("=");
            result.append(params.get(key));
        }
        try {
            if(result.toString().substring(1, result.toString().length()).length()>0){
                return "?"+result.toString().substring(1, result.toString().length());
            }else{
                return result.toString().substring(1, result.toString().length())+"";
            }

        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 通过 “ID” 请求列表的时候的拼装方法
     */
    private String pubString(String... strs) {
        StringBuffer sb = new StringBuffer();
        sb.append("/data");
        for (int i = 0; i < strs.length; i++) {
            sb.append("/" + strs[i]);
        }
        return sb.toString();
    }

}
