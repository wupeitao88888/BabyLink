package com.iloomo.glucometer.view;

import com.iloomo.doctor.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;


public class LoadingDialog extends Dialog {

	private TextView tv;

	public LoadingDialog(Context context) {
		super(context, R.style.loadingDialogStyle);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_loading);
		tv = (TextView) findViewById(R.id.dialog_message);
		tv.setText("正在上传.....");
		LinearLayout linearLayout = (LinearLayout) this
				.findViewById(R.id.LinearLayout);
		linearLayout.getBackground().setAlpha(210);
	}

}
