package com.iloomo.glucometer.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class DefaultDialogBuilder extends AlertDialog.Builder {
	private DefaultDialog md; // 默认对话框
	private Context context; // 上下文

	/**
	 * 构造函数
	 * @param context 上下文
	 */
	public DefaultDialogBuilder(Context context,int flag) {
		super(context);
		md = new DefaultDialog(context,flag);
		this.context = context;
	}
	public void setYesOrNo(){
		md.setYesOrNo();
	}
	public void setOnlyYes(){
		md.setOnlyYes();
	}
	/**
	 * 通过string id 设置提示信息
	 *  @param messageId 提示字符String id
	 */
	public DefaultDialogBuilder setMessage(int messageId) {
		md.setMessage(context.getResources().getString(messageId));
		return this;
	}

	/**
	 * 直接设置提示信息
	 *@param message 提示字符
	 */
	public DefaultDialogBuilder setMessage(CharSequence message) {
		md.setMessage(message);
		return this;
	}

	/**
	 * 通过string id 设置头信息
	 * @param titleId 头字符Stirng id
	 */
	public DefaultDialogBuilder setTitle(int titleId) {
		md.setTitle(context.getResources().getString(titleId));
		return this;
	}

	/**
	 * 直接设置头信息
	 * @param title 头信息字符
	 */
	public DefaultDialogBuilder setTitle(CharSequence title) {
		md.setTitle(title);
		return this;
	}
	
	/**
	 * 第一个按钮
	 * 通过String id 设置按钮文字
	 * @param textId 字符id
	 * @param listener 监听器
	 */
	public DefaultDialogBuilder setPositiveButton(int textId, OnClickListener listener) {
		md.setButton(context.getResources().getString(textId), listener);
		return this;
	}

	/**
	 * 第一个按钮
	 * 直接这是按钮文字
	 * @param textId 按钮问题
	 * @param listener 监听器
	 */
	public DefaultDialogBuilder setPositiveButton(CharSequence text, OnClickListener listener) {
		md.setButton(text, listener);
		return this;
	}

	/**
	 * 第二个按钮
	 * 通过String id 设置按钮文字
	 * @param textId 字符id
	 * @param listener 监听器
	 */
	public DefaultDialogBuilder setNeutralButton(int textId, OnClickListener listener) {
		md.setButton2(context.getResources().getString(textId), listener);
		return this;
	}

	/**
	 * 第二个按钮
	 * 直接这是按钮文字
	 * @param textId 字符
	 * @param listener 监听器
	 */
	public DefaultDialogBuilder setNeutralButton(CharSequence text, OnClickListener listener) {
		md.setButton2(text, listener);
		return this;
	}

	/**
	 * 第三个按钮
	 * 通过String id 设置按钮文字
	 * @param textId 字符id
	 * @param listener 监听器
	 */
	public DefaultDialogBuilder setNegativeButton(int textId, OnClickListener listener) {
		md.setButton3(context.getResources().getString(textId), listener);
		return this;
	}

	/**
	 * 第三个按钮
	 * 直接这是按钮文字
	 * @param textId 字符
	 * @param listener 监听器
	 */
	public DefaultDialogBuilder setNegativeButton(CharSequence text, OnClickListener listener) {
		md.setButton3(text, listener);
		return this;
	}
	
	@Override
	public DefaultDialog create() {
		return md;
	}
	
	/**
	 * 获得对话框引用
	 * @return 对话框引用
	 */
	public DefaultDialog getDefaultDialog(){
		return md;
	}
	
	public void cancle(){
		if (md!=null) {
			md.dismiss();
		}
		
	}
}