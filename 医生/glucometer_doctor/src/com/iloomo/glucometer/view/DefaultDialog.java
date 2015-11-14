package com.iloomo.glucometer.view;

import com.iloomo.doctor.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DefaultDialog extends AlertDialog implements
		android.view.View.OnClickListener {

	private LinearLayout view; // 布局
	private LayoutInflater mInflater; // 布局管理器
	private Context context; // 上下文
	private Button button1; // 第一按钮
	private Button button2; // 第二个按钮
	private Button button3; // 第三个按钮
	private TextView itemMessage, itemTitle;

	protected DefaultDialog(Context context, int flag) {
		super(context);
		this.context = context;
		mInflater = LayoutInflater.from(this.context);
		if (flag == 0) {
			view = (LinearLayout) mInflater.inflate(R.layout.dialog_iphone,
					null);
			button1 = (Button) view.findViewById(R.id.dialog_yes);
			button2 = (Button) view.findViewById(R.id.dialog_no);
			button3 = (Button) view.findViewById(R.id.dialog_cancel);
			itemTitle = (TextView) view.findViewById(R.id.dialog_title);
		} else if (flag == 1) {
			view = (LinearLayout) mInflater.inflate(R.layout.dialog_loading,
					null);
		}

		itemMessage = (TextView) view.findViewById(R.id.dialog_message);
	}

	public void setYesOrNo() {
		button1.setVisibility(View.VISIBLE);
		button2.setVisibility(View.VISIBLE);
		button3.setVisibility(View.GONE);
	}
	public void setOnlyYes() {
		button1.setVisibility(View.GONE);
		button2.setVisibility(View.VISIBLE);
		button3.setVisibility(View.GONE);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(view);
	}

	@Override
	public void setMessage(CharSequence message) {
		itemMessage.setText(message);
	}

	@Override
	public void setTitle(CharSequence title) {
		if (itemTitle != null) {
			itemTitle.setText(title);
		}

	}

	@Override
	public void setButton(CharSequence text, final OnClickListener listener) {
		button1 = (Button) view.findViewById(R.id.dialog_yes);
		button1.setText(text);
		button1.setVisibility(View.VISIBLE);
		button1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				if (listener != null) {
					listener.onClick(DefaultDialog.this, 0);
				}
				dismiss();
			}
		});

		super.setButton(text, listener);
	}

	@Override
	public void setButton2(CharSequence text, final OnClickListener listener) {
		button2 = (Button) view.findViewById(R.id.dialog_no);
		button2.setText(text);
		button2.setVisibility(View.VISIBLE);
		button2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				if (listener != null) {
					listener.onClick(DefaultDialog.this, 0);
				}
				dismiss();
			}
		});
		super.setButton2(text, listener);
	}

	@Override
	public void setButton3(CharSequence text, final OnClickListener listener) {
		button3 = (Button) view.findViewById(R.id.dialog_cancel);
		button3.setText(text);
		button3.setVisibility(View.VISIBLE);
		button3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				if (listener != null) {
					listener.onClick(DefaultDialog.this, 0);
				}
				dismiss();
			}
		});
		super.setButton3(text, listener);
	}

	/**
	 * 设置第一个按钮点击效果或者背景图片
	 * 
	 * @param background
	 *            背景资源id
	 */
	public void setPositiveButtonBackground(int background) {
		button1.setBackgroundResource(background);
	}

	/**
	 * 设置第二个按钮点击效果或者背景图片
	 * 
	 * @param background
	 *            背景资源id
	 */
	public void setNeutralButtonBackground(int background) {
		button2.setBackgroundResource(background);
	}

	/**
	 * 设置第三个按钮点击效果或者背景图片
	 * 
	 * @param background
	 */
	public void setNegativeButtonBackground(int background) {
		button3.setBackgroundResource(background);
	}

	/**
	 * 设置第一个按钮文字颜色
	 * 
	 * @param color
	 *            颜色id
	 */
	public void setPositiveButton(int color) {
		button1.setTextColor(color);
	}

	/**
	 * 设置第二个按钮文字颜色
	 * 
	 * @param color
	 *            颜色id
	 */
	public void setNeutralButtonTextColor(int color) {
		button2.setTextColor(color);
	}

	/**
	 * 设置第三个按钮文字颜色
	 * 
	 * @param color
	 *            颜色id
	 */
	public void setNegativeButtonTextColor(int color) {
		button3.setTextColor(color);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
