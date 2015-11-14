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
import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.shiliuke.BabyLink.LoginActivity;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.shiliuke.internet.NormalPostRequest;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.internet.VolleyTask;
import com.shiliuke.utils.L;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author yuemx 创建时间：2015年4月17日 下午12:08:45
 *         <p>
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

    public void Login(final VolleyListerner listerner, final int action, final Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.BASE_URL);
        sb.append(AppConfig.LOGIN);
        String requesturl = sb.toString();

        L.e("URL=" + requesturl);
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    listerner.onResponse(response.toString(), action, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    listerner.onResponseError(error.getMessage(),
                            action);
                }
            }
        }, params));
    }

    /**
     * 通过 “ID” 请求列表的时候调用的方法
     */
    public void requestPost(final VolleyListerner listerner, final int action, final Map<String, String> params, String strs) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.BASE_URL);
        sb.append(strs);
        String requesturl = sb.toString();

        L.e("发起URL=" + requesturl + addParams(params));
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    L.e("action=" + action + " 网络成功：" + response.toString());
                    try {
                        Map map = JSON.parseObject(response.toString(), Map.class);
                        if (!"0".equalsIgnoreCase(map.get("code").toString())) {
                            listerner.onResponseError(map.get("datas").toString(),
                                    action);
                            return;
                        }
                    } catch (Exception e) {
                        listerner.onResponseError("服务器异常",
                                action);
                        return;
                    }
                    listerner.onResponse(response.toString(), action, null);
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
            if (result.toString().substring(1, result.toString().length()).length() > 0) {
                return "?" + result.toString().substring(1, result.toString().length());
            } else {
                return result.toString().substring(1, result.toString().length()) + "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param listerner
     * @param action
     * @param params    参数
     *                  获取验证码 lvfl
     */
    public void getCodePost(final VolleyListerner listerner, final int action, final Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.BASE_URL);
        sb.append(AppConfig.GET_CODE);
        String requesturl = sb.toString();

        L.e("URL=" + requesturl);
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    listerner.onResponse(response.toString(), action, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    listerner.onResponseError(error.getMessage(),
                            action);
                }
            }
        }, params));
    }

    /**
     * @param listerner
     * @param action
     * @param params    注册
     */
    public void sendRegisterPost(final VolleyListerner listerner, final int action, final Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.BASE_URL);
        sb.append(AppConfig.SEND_REGISTER);
        String requesturl = sb.toString();

        L.e("URL=" + requesturl);
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    listerner.onResponse(response.toString(), action, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    listerner.onResponseError(error.getMessage(),
                            action);
                }
            }
        }, params));
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
