package com.iloomo.doctor;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.iloomo.glucometer.modle.User;
import com.iloomo.net.AsyncHttpClient;
import com.iloomo.net.JsonHttpResponseHandler;
import com.iloomo.net.RequestParams;

public class InfoActivity extends GameBaseActivity implements OnClickListener {
	TextView dateSelect;

	@Override
	public void setContentLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.info);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.submit).setOnClickListener(this);
		dateSelect = (TextView) findViewById(R.id.dueDate);
		dateSelect.setOnClickListener(this);
		if(User.gestation != null){
			dateSelect.setText(User.gestation);
		}
//		Date d = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String dateNowStr = sdf.format(d);
//		dateSelect.setHint(dateNowStr);
	}

	@Override
	public void findViewById() {
		// TODO Auto-generated method stub
		getDataFromServer();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void getDataFromServer() {
		// dueDate 预产期
		// editText1 姓名
		// editText2 性别
		// editText3 年龄

		RequestParams requestParams = new RequestParams();
		final String _dueDate = ((TextView) findViewById(R.id.dueDate))
				.getText().toString();
		final String _name = ((EditText) findViewById(R.id.editText1))
				.getText().toString();
		final String _gender = ((EditText) findViewById(R.id.editText2))
				.getText().toString();
		final String _age = ((EditText) findViewById(R.id.editText3)).getText()
				.toString();
		requestParams.put("id", User.uid);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(getString(R.string.url_getUserInfo), requestParams,
				new JsonHttpResponseHandler() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						showProcessDialog("正在获取数据...");
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
						cancleProcessDialog();
					}

					@Override
					public void onSuccess(JSONObject response) {
						// TODO Auto-generated method stub
						super.onSuccess(response);
						try {
							int _ret = response.getInt("result");
							if (_ret == 0) {
								int _resultCode = response.getInt("resultCode");
								if (_resultCode != 99) {
									String _msg = response
											.getString("resultMsg");
									Toast.makeText(InfoActivity.this, _msg,
											Toast.LENGTH_SHORT).show();
								}
							} else {
								JSONObject data = response
										.getJSONObject("data");
								String __name = data.getString("name");
								if (__name != null && !"null".equals(__name))
									((EditText) findViewById(R.id.editText1))
											.setText(__name);

								String __dueDate = data.getString("gestation");
								if (__dueDate != null
										&& !"null".equals(__dueDate))
									((TextView) findViewById(R.id.dueDate))
											.setText(__dueDate);

								String __age = data.getString("birthday");
								if (__age != null && !"null".equals(__age))
									((EditText) findViewById(R.id.editText3))
											.setText(__age);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cancleProcessDialog();
					}

					@Override
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						super.onFailure(error);
						cancleProcessDialog();
					}
				});

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.back) {
			finish();
		} else if (v.getId() == R.id.submit) {
			// dueDate 预产期
			// editText1 姓名
			// editText2 性别
			// editText3 年龄

			RequestParams requestParams = new RequestParams();
			final String _dueDate = ((TextView) findViewById(R.id.dueDate))
					.getText().toString();
			final String _name = ((EditText) findViewById(R.id.editText1))
					.getText().toString();
			final String _gender = ((EditText) findViewById(R.id.editText2))
					.getText().toString();
			final String _age = ((EditText) findViewById(R.id.editText3))
					.getText().toString();
			requestParams.put("name", _name);
//			requestParams.put("gender", _gender);
			requestParams.put("gestation", _dueDate);
			User.gestation = _dueDate;
			requestParams.put("id", User.uid);
			requestParams.put("birthday", _age);
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(getString(R.string.url_updateUser), requestParams,
					new JsonHttpResponseHandler() {
						@Override
						public void onStart() {
							// TODO Auto-generated method stub
							super.onStart();
							showProcessDialog("正在提交数据...");
						}

						@Override
						public void onFinish() {
							// TODO Auto-generated method stub
							super.onFinish();
							cancleProcessDialog();
						}

						@Override
						public void onSuccess(JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(response);
							try {
								int _ret = response.getInt("result");
								if (_ret == 0) {
									int _resultCode = response
											.getInt("resultCode");
									if (_resultCode != 99) {
										String _msg = response
												.getString("resultMsg");
										Toast.makeText(InfoActivity.this, _msg,
												Toast.LENGTH_SHORT).show();
									}else{
										User.saveGestation(InfoActivity.this,User.gestation);
										User.saveName(InfoActivity.this, _name);
										finish();
									}
								} else {
									User.saveGestation(InfoActivity.this,User.gestation);
									// String _uid = response.getString("uid");
									// User.saveUserIno(InfoActivity.this, _uid,
									// _password, _mob);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							cancleProcessDialog();
						}

						@Override
						public void onFailure(Throwable error) {
							// TODO Auto-generated method stub
							super.onFailure(error);
							cancleProcessDialog();
						}
					});

		} else if (v.getId() == R.id.dueDate) {
			final Calendar objTime = Calendar.getInstance();
			int iYear = objTime.get(Calendar.YEAR);
			int iMonth = objTime.get(Calendar.MONTH);
			int iDay = objTime.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog mDialog = new DatePickerDialog(InfoActivity.this,
					AlertDialog.THEME_HOLO_LIGHT, new OnDateSetListener() {
						public void onDateSet(DatePicker arg0, int arg1,
								int arg2, int arg3) {
							String str2, str3;
							if (arg2 + 1 < 10) {
								str2 = "-0" + (arg2 + 1);
							} else {
								str2 = "-" + (arg2 + 1);
							}

							if (arg3 < 10) {
								str3 = "-0" + arg3;
							} else {
								str3 = "-" + arg3;
							}
							dateSelect.setText(arg1 + str2 + str3);
						}
					}, iYear, iMonth, iDay);
			mDialog.show();
		}
	}

}
