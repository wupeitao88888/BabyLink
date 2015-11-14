package com.iloomo.utils;

import com.iloomo.base.BaseApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * 
 * 创 建 人: DavikChen
 * 日    期：  2013-1-5  下午4:41:45
 * 修 改 人： 
 * 日   期： 
 * 描   述： 异常广播器
 * 版 本 号：
 */
public class AppExceptionReceiver extends BroadcastReceiver {
	/** 异常 **/
	static final String ACTION = "android.intent.action.anjoyo_exception";
	/** 异常文件传递的key值 **/
	public static final String EXCEPTION_FILE_PATH_KEY ="exception_file_path_key";

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		if (arg1.getAction().equals(ACTION)) {
			String filePathString = arg1.getStringExtra(EXCEPTION_FILE_PATH_KEY);
			// 创建Intent
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			// 设置内容类型
			emailIntent.setType("plain/text");
			// 设置额外信息
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { BaseApplication.developEmail});
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "错误报告");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "非常感谢你提交错误报告,帮助我们改进应用 非常感谢！");
			emailIntent.putExtra(Intent.EXTRA_STREAM, filePathString);
			// 启动Activity
			arg0.startActivity(Intent.createChooser(emailIntent, "发送邮件..."));

		}
		
	}
}
