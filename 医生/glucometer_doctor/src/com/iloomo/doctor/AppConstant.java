package com.iloomo.doctor;

public class AppConstant {
	/** 跳转到应用列表页面  传递classid 的键名 **/
	public static final String  CLASS_APP_LIST_CLASS_ID_KEY = "classid";
	/** 跳转到应用列表页面  传递subclassid 的键名 **/
	public static final String  CLASS_APP_LIST_SUBCLASS_ID_KEY = "subclassid";
	/** 跳转到应用列表页面  传递分类名称 的键名 **/
	public static final String  CLASS_APP_LIST_CLASS_NAME_KEY = "classname";
	/** 跳转到专题详情页面 传递专题id 的键名 **/
	public static final String CLASS_TOPIC_DEITAL_TOPIC_ID_KEY = "topicid";
	/** 跳转到应用详情页面 传递专题应用id 的键名 **/
	public static final String APP_DETAIL_APP_ID_KEY = "app_id";
	/** 应用存储 sharedpreference 的名字 **/
	public static final String sharedPreferenceName = "game_center"; // app SharedPreferences存储名称
//	/** 登陆保存用户id的键名 **/
//	public static final String USER_ID = "uid";
	/** 登陆保存用户授权的键名 **/
	public static final String USER_KEY = "key";
	/** 登陆保存用户获得的任务成就名称的键名 **/
	public static final String USER_INSTALL_APP_TASK= "install_app_task";
	/** 登陆保存用户获得的金币名称的键名 **/
	public static final String USER_INSTALL_APP_COIN= "install_app_coin";
	/** 用户id **/
	public static final String USER_NAME_UI_KEY = "uid";
	/** 用户名email **/
	public static final String USER_EMAIL_NAME_KEY = "user_email_name";
	/** 昵称  **/
	public static final String NICK_USID_KEY = "nick_name";
	/** 登陆时的usid **/
	public static final String USER_USID_LOGIN_KEY = "usid_login";

	
	/** 往用户评论页面传递的应用id **/
	public static final String USER_COMMONT_UID= "user_commont_appid";
	public static final String USER_COMMONT_APPNAME = "user_commont_appname";
	public static final String USER_COMMONT_APPPIC_URL ="user_commont_apppic";
	public static final String USER_COMMONT_VERSION = "user_commont_version";
	public static final String USER_COMMONT_FILESIZE = "user_commont_file_size";
	
	/** 进入引导页面来源存储 key **/
	public static final String GO_GUIDE_FALG = "go_guide_falg";
	
	/** 流量设置  **/
	/** 非wifi下载提示　**/
	public static final String SET_NETWORK_WIFI_KEY = "wifi_warm";
	/** 不加载列表图片 **/
	public static final String SET_NETWORK_NO_DOWNLOAD_IMAGE = "no_download_image";
	
	/** 非WIFI下提醒设置 key **/
	public static final String WIFI_SET_KEY = "wifi_set_key";
	/** 3G节省流量设置 key **/
	public static final String FLOW_SET_KEY = "flow_set_key";
	/** 自动更新提醒设置 key **/
	public static final String REMIND_SET_KEY = "remind_set_key";
	
	
	/** 通知栏提醒 **/
	/** 通知栏更新提醒 **/
	public static final String SET_NOTIFICATION_UPDATE = "notification_update";
	/** 通知栏安装提醒　**/
	public static final String SET_NOTIFICATION_INSTALL = "notification_install";
	/** 开启页面左右滑动  **/
	public static final String SET_NOTIFICATION_VIEWPAGER = "viewpager_scoller";
	
	/** 下载管理 **/
	/** 开启批量下载 **/
	public static final String SET_DOWNLOAD_MULTI_TASK = "multi_task";
	/** 自动下载为完成的任务 **/
	public static final String SET_DOWNLOAD_AUTO_NO_OVER = "auto_download_old";
	/** 下载到SD卡　**/
	public static final String SET_DOWNLOAD_PATH = "download_path";
	
	/** 详情大图页面传递的key **/
	public static final String DETAIL_IAMGE_LIST = "detail_image_list";
	public static final String DETAIL_IMAGE_POSITION = "detail_image_position";
	
	/** 没有登陆时跳转到登录页面时  上级页面 **/
	public static final String LONG_TARGET_CLASS = "target_class";
	
	public static final String ABOUT_RESOURCE_ID = "about_resource_id";
	/** 传递到详情页面的应用下载状态 **/
	public static final String DETAIL_DOWNLAOD_STATE = "detail_downlaod_state";
	/** 传递到详情页面的应用下载进度 **/
	public static final String DETAIL_DOWNLOAD_PERCENT ="detail_download_percent";
	/** 传递到详情页面的应用已经下载大小 **/
	public static final String DETAIL_DOWNLOADED_APP_SIZE = "detail_downloaded_app_size";
	/** 传递到详情页面的应用已总大小大小 **/
	public static final String DETAIl_DOWNLOAD_APP_TOTAL_SIZE = "detail_downlaod_app_total_size";
	/** 通知栏跳转到首页标识 **/
	public static final String NOTIFICATION_MAIN_FLAG = "notification_main_flag";
	/** 欢迎页面图片地址 **/
	public static final String WELCOME_PIC_URL = "welcome_picurl";
	/** 欢迎页面图片md5值 **/
	public static final String WELCOME_PICMD5 = "welcome_picmd5";
	
	public final static String QQ_APP_ID = "100453878";
	/** 删除下载任务的广播 **/
	public static final String DETIAL_DOWNLAOD_ACTION = "android.intent.action.DownloadDelete";
	
	/** 首页开关 广播**/
	public static final String Home_Close_Open = "android.intent.action.Home_Close_Open";
	
	/** 进入详情传递的游戏id key值 **/
	public static final String APP_DETAIL_GAME_ID_KEY = "app_id";
	
	/**　标签切换广播 **/
	public static final String BR_MAIN_TAB_CHANGE_MESSAGE = "BroadcastReceiverMain_Tab_Change";
	public static final String BR_MAIN_TAB_CHANGE_MESSAGE_KEY1 = "tab_name";
	
	
	// 四个标签栏页面
		public static  String[] TOP_ACTIVITY_NAME = new String[] {"TabAlert","TabDetection", "TabMe","TabShop" };

		public static String[] getTopActivityName() {
			return TOP_ACTIVITY_NAME;
		}

		public static String[] getTOP_ACTIVITY_NAME() {
			return TOP_ACTIVITY_NAME;
		}

		public static void setTOP_ACTIVITY_NAME(String[] tOP_ACTIVITY_NAME) {
			TOP_ACTIVITY_NAME = tOP_ACTIVITY_NAME;
		}
	
	

}
