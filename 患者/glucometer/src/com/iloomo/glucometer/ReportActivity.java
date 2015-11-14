package com.iloomo.glucometer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.iloomo.glucometer.view.ReportTableView;

public class ReportActivity extends GameBaseActivity implements OnClickListener {
	TextView dateSelect;
	ReportTableView rtv;
	@Override
	public void setContentLayout() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.report);
	}
	int mYear,mMonth;
	@Override
	public void findViewById() {
		// TODO Auto-generated method stub
		rtv = ((ReportTableView) findViewById(R.id.reportTable));
		dateSelect = (TextView) findViewById(R.id.dateSelect);
		findViewById(R.id.back).setOnClickListener(this);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String dateNowStr = sdf.format(d);
		dateSelect.setText(dateNowStr);
		dateSelect.setOnClickListener(dateListener);
		final Calendar objTime = Calendar.getInstance();
		 mYear = objTime.get(Calendar.YEAR);
		 mMonth = objTime.get(Calendar.MONTH)+1;
	}

	@Override
	public void getDataFromServer() {
		// TODO Auto-generated method stub

	}
	OnClickListener dateListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			final Calendar objTime = Calendar.getInstance();
			mYear = objTime.get(Calendar.YEAR);
			mMonth = objTime.get(Calendar.MONTH);
			int iDay = objTime.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog mDialog = new DatePickerDialog(ReportActivity.this,
					AlertDialog.THEME_HOLO_LIGHT, DatePickerListener, mYear,
					mMonth, iDay);
			mDialog.show();
			DatePicker dp = findDatePicker((ViewGroup) mDialog.getWindow()
					.getDecorView());

			if (dp != null) {
				int SDKVersion;
				try {
					SDKVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
				} catch (NumberFormatException e) {
					SDKVersion = 0;
				}
				if (SDKVersion < 11) {
					// ((ViewGroup) dp.getChildAt(0)).getChildAt(1).setVisibility(
					// View.GONE);
					((ViewGroup) dp.getChildAt(0)).getChildAt(2).setVisibility(
							View.GONE);
				} else if (SDKVersion > 14) {
					// View view1 = ((ViewGroup) ((ViewGroup) dp.getChildAt(0))
					// .getChildAt(0)).getChildAt(1);
					// view1.setVisibility(View.GONE);

					View view2 = ((ViewGroup) ((ViewGroup) dp.getChildAt(0))
							.getChildAt(0)).getChildAt(2);
					view2.setVisibility(View.GONE);
				}
			}

					
		}
	};
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.back){
			finish();
		}
		
	}

	private DatePicker findDatePicker(ViewGroup group) {
		if (group != null) {
			for (int i = 0, j = group.getChildCount(); i < j; i++) {
				View child = group.getChildAt(i);
				if (child instanceof DatePicker) {
					return (DatePicker) child;
				} else if (child instanceof ViewGroup) {
					DatePicker result = findDatePicker((ViewGroup) child);
					if (result != null)
						return result;
				}
			}
		}
		return null;
	}

	private OnDateSetListener DatePickerListener = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			if (arg2 + 1 < 10) {
				dateSelect.setText(arg1 + "-0" + (arg2 + 1));
			} else {
				dateSelect.setText(arg1 + "-" + (arg2 + 1));
			}
			if(rtv != null){
				rtv.initData(arg1, arg2+1);
			}
		}
	};
	protected void onResume() {
		super.onResume();
		if(rtv != null){
			rtv.initData(mYear, mMonth);
		}
	};
}
