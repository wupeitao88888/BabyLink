package com.iloomo.glucometer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.iloomo.glucometer.R;


public class DefaultDialogView extends RelativeLayout {

	
	private Button bt_cancel, bt_yes, bt_no;

	public DefaultDialogView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DefaultDialogView(Context context) {
		super(context);
	}

	@Override
	protected void onFinishInflate() {
		

		bt_yes = (Button) findViewById(R.id.dialog_yes);
		bt_no = (Button) findViewById(R.id.dialog_no);
		bt_cancel = (Button) findViewById(R.id.dialog_cancel);
		super.onFinishInflate();
	}

	public void HiddenButton(int ButtonId) {
		if (ButtonId == R.id.dialog_yes) {
			this.bt_yes.setVisibility(GONE);
		} else if (ButtonId == R.id.dialog_no) {
			this.bt_no.setVisibility(GONE);
		} else if (ButtonId == R.id.dialog_cancel) {
			this.bt_cancel.setVisibility(GONE);
		}
	}
}
