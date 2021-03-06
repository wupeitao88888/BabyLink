/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shiliuke.global;


// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbAppConfig.java
 * 描述：初始设置类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2014-07-03 下午1:33:39
 */
public class AppConfig {

    /**
     * UI设计的基准宽度.
     */
    public static int UI_WIDTH = 720;

    /**
     * UI设计的基准高度.
     */
    public static int UI_HEIGHT = 1080;
    /**
     * Textview为空.
     */
    public static String TEXTVIEW_NULL = "TextView为空";
    /**
     * Text为空.
     */
    public static String TEXT_NULL = "Text为空";
    /**
     * 默认 SharePreferences文件名.
     */
    public static String SHARED_PATH = "app_share";
    //基础URL
    public static String BASE_URL = "http://101.200.174.65/";
    // 获取验证码
    public static String GET_CODE = "babyLink/mobile.php/Mobile/Login/sendCode";
    // 注册
    public static String SEND_REGISTER = "babyLink/mobile.php/Mobile/Login/register";
    // 登陆
    public static String LOGIN = "babyLink/mobile.php/Mobile/Login";
    // 秀逗Link
    public static String XIUDOU_LINK = "babyLink/mobile.php/Mobile/Xiu/link";
    // 秀逗广场
    public static String XIUDOU_GUANGCHANG = "babyLink/mobile.php/Mobile/Xiu";
    // 秀逗_添加评论
    public static String XIUDOU_ADDCOMMEND = "babyLink/mobile.php/Mobile/Xiu/addCommend";
    // 秀逗_加关注
    public static String XIUDOU_ADDFRIEND = "babyLink/mobile.php/Mobile/Xiu/addFriend";
    //基础URL
    public static String BASE_URL_V1 = "babyLink/mobile.php/Mobile";
    //活动列表
    public static String ACTIVITY = BASE_URL_V1 + "/Activity";
    //活动详情
    public static String ACTIVITY_INFO = ACTIVITY + "/activity_info";
    //微信appID
    public static String WX_APP_ID = "wx1e5e6a9c7ec380c1";
    public static String ACTION_WXSHARE_SUCCESS = "com.shiliuke.BabLink.ACTION_WXSHARESHARE";
    public static String ACTION_WXSHARE_FAILD = "com.shiliuke.BabLink.ACTION_WXSHAREFAILD";
    public static String ACTION_WXPAY_SUCCESS = "com.shiliuke.BabLink.ACTION_WXPAYSUCCESS";
    public static String ACTION_WXPAY_CANCLE = "com.shiliuke.BabLink.ACTION_WXPAYCANCLE";
    public static String ACTION_WXPAY_FAILD = "com.shiliuke.BabLink.ACTION_WXPAYFALID";
    public static String ACTION_WXPAY_UNSUPPORT = "com.shiliuke.BabLink.ACTION_WXPAYUNSUPPORT";

}
