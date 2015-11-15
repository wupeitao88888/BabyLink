package com.iloomo.doctor;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iloomo.glucometer.modle.TestData;
import com.iloomo.glucometer.modle.User;
import com.iloomo.net.AsyncHttpClient;
import com.iloomo.net.JsonHttpResponseHandler;
import com.iloomo.net.RequestParams;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TabMe extends GameBaseActivity implements OnClickListener {
	LinearLayout item0, item1, item2, item3, item4, item5;
	LinearLayout mLogin;
	TextView dateSelect;

	@Override
	public void setContentLayout() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.tab_me);
		dateSelect = (TextView) findViewById(R.id.dateSelect1);
		mLogin = (LinearLayout) findViewById(R.id.login);
		mLogin.setOnClickListener(this);

	}

	@Override
	public void findViewById() {
		item0 = (LinearLayout) findViewById(R.id.item0);
		item0.setOnClickListener(this);

		item1 = (LinearLayout) findViewById(R.id.item1);
		item1.setOnClickListener(this);

		item2 = (LinearLayout) findViewById(R.id.item2);
		item2.setOnClickListener(this);

		item3 = (LinearLayout) findViewById(R.id.item3);
		item3.setOnClickListener(this);

		item4 = (LinearLayout) findViewById(R.id.item4);
		item4.setOnClickListener(this);

		item5 = (LinearLayout) findViewById(R.id.item5);
		item5.setOnClickListener(this);
		findViewById(R.id.item7).setOnClickListener(this);
		findViewById(R.id.item6).setOnClickListener(this);
		findViewById(R.id.item8).setOnClickListener(this);
		findViewById(R.id.item9).setOnClickListener(this);
		getDataFromServer();
	}

	@Override
	public void getDataFromServer() {
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.login) {
			Intent _intent = new Intent(this, LoginActivity.class);
			startActivity(_intent);
		} else if (v.getId() == R.id.item0) {

			final Calendar objTime = Calendar.getInstance();
			int iYear = objTime.get(Calendar.YEAR);
			int iMonth = objTime.get(Calendar.MONTH);
			int iDay = objTime.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog mDialog = new DatePickerDialog(TabMe.this,
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
							dateSelect.setText("预产期:" + arg1 + str2 + str3);
						}
					}, iYear, iMonth, iDay);
			mDialog.show();

		} else if (v.getId() == R.id.item1) {
			// 我的资料
			if (!User.showLoginButton()) {
				Intent intent = new Intent(this, InfoActivity.class);
				startActivity(intent);
			} else {
				showDintLogin();
			}

		} else if (v.getId() == R.id.item2) {
			// 我的血糖仪
			if (!User.showLoginButton()) {
				Intent intent = new Intent(this, InfoActivity.class);
				startActivity(intent);
			} else {
				showDintLogin();
			}
		} else if (v.getId() == R.id.item3) {
			// 我的医生
			if (!User.showLoginButton()) {
				Intent _intent = new Intent(TabMe.this,
						SearchDoctorActivity.class);
				startActivity(_intent);
			} else {
				showDintLogin();
			}
		} else if (v.getId() == R.id.item4) {
			// 使用教程
			Intent _intent = new Intent(this, TestAnmiActivity.class);
			startActivity(_intent);
		} else if (v.getId() == R.id.item5) {

			if (!User.showLoginButton()) {
				showYesOrNo("退出提醒", "退出当前账号？",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								User.logout(TabMe.this);
								DBTools dbt = new DBTools();
								dbt.init(TabMe.this);

								dbt.deleteTestData();
								Intent intent = new Intent(TabMe.this,
										LoginActivity.class);
								startActivity(intent);
								HostMainActivity.hma.setCurrentTab(0);
							}
						});
			} else {
				showDintLogin();
			}

		} else if (v.getId() == R.id.item7) {
			Intent _intent = new Intent(this, AlertActivity.class);
			startActivity(_intent);
		} else if (v.getId() == R.id.item6) {
			// 关于
			Intent _intent = new Intent(this, AboutActivity.class);
			startActivity(_intent);
		} else if (v.getId() == R.id.item8) {
			// 检查更新
			UmengUpdateAgent.setUpdateOnlyWifi(false);
			UmengUpdateAgent.setUpdateAutoPopup(false);
			UmengUpdateAgent.setUpdateCheckConfig(true);
			UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
				@Override
				public void onUpdateReturned(int updateStatus,
						UpdateResponse updateInfo) {
					cancleProcessDialog();
					if (updateStatus == 0 && updateInfo != null) {
						UmengUpdateAgent.setUpdateAutoPopup(true);
						UmengUpdateAgent.setUpdateCheckConfig(true);
						UmengUpdateAgent.update(TabMe.this);
						UmengUpdateAgent.setUpdateListener(null);
					} else {
						showYes("检查更新", "已经是最新版本");
					}
				}
			});
			showProcessDialog("检查更新");
			// UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			//
			// @Override
			// public void onUpdateReturned(int updateStatus,
			// UpdateResponse updateInfo) {
			// if (updateStatus == 0 && updateInfo != null) {
			// showUpdateDialog(updateInfo.path, updateInfo.updateLog);
			// }
			// // case 0: // has update
			// // case 1: // has no update
			// // case 2: // none wifi
			// // case 3: // time out
			// }
			// });

			UmengUpdateAgent.update(this);
		} else if (v.getId() == R.id.item9) {// 数据同步
			if (!User.showLoginButton()) {
				DBTools dbt = new DBTools();
				dbt.init(HostMainActivity.hma);
				atd = dbt.getNeedSync();
				showProcessDialog("正在同步数据请稍后...");
				if (atd.size() > 0) {// 如果有没有同步的数据则同步
					
					TestData _TestData = atd.get(0);
					sendData(_TestData);
				} else {// 如果没有需要同步的数据，则直接更新数据
					DBTools __dbt = new DBTools();
					__dbt.init(HostMainActivity.hma);
					__dbt.deleteTestData();
					updateTestData(User.uid);
				}
			} else {
				showDintLogin();
			}
		}
	}

	ArrayList<TestData> atd;

	public void sendData(final TestData _TestData) {
		if (User.uid == null) {
			return;
		}
		RequestParams requestParams = new RequestParams();
		requestParams.put("userId", User.uid);
		requestParams.put("point", "" + _TestData.timeId);
		requestParams.put("glyx", "" + _TestData.bloodGlucoseValues);
		requestParams.put("recordTime", "" + _TestData.timestamp);
		requestParams.put("recordDate", "" + _TestData.testDate);

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(getString(R.string.url_Monitor_saveMonitor), requestParams,
				new JsonHttpResponseHandler() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
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

								} else {
								}
							} else {
								_TestData.flag = 1;
								DBTools dbt = new DBTools();
								dbt.init(HostMainActivity.hma);
								dbt.insertTestData(_TestData);
								atd.remove(0);
								if (atd.size() > 0) {
									TestData _TestData = atd.get(0);
									sendData(_TestData);
								} else {
									DBTools __dbt = new DBTools();
									__dbt.init(HostMainActivity.hma);
									__dbt.deleteTestData();
									updateTestData(User.uid);
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						super.onFailure(error);
					}
				});
	}

	public void updateTestData(String userid) {
		DBTools dbt = new DBTools();
		dbt.init(HostMainActivity.hma);
		if (!dbt.isEmpty()) {
			finish();
			return;
		}
		if (userid == null) {
			finish();
			return;
		}
		RequestParams requestParams = new RequestParams();
		requestParams.put("userId", userid);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(getString(R.string.url_Monitor_searchMonitor),
				requestParams, new JsonHttpResponseHandler() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
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

								} else {
								}
							} else {
								JSONArray datas = response.getJSONArray("list");
								for (int i = 0; i < datas.length(); i++) {
									JSONObject _data = datas.getJSONObject(i);
									TestData _TestData = new TestData();
									_TestData.timeId = _data.getInt("point");
									_TestData.remark = _data
											.getString("remark");
									_TestData.bloodGlucoseValues = (float) _data
											.getDouble("glyx");
									_TestData.timestamp = _data
											.getString("recordTime");
									_TestData.testDate = _data
											.getString("recordDate");
									_TestData.flag = 1;
									DBTools dbt = new DBTools();
									dbt.insertTestData(_TestData);
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Toast.makeText(TabMe.this, "数据同步成功", Toast.LENGTH_SHORT)
								.show();
						cancleProcessDialog();
						
					}

					@Override
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						super.onFailure(error);
						Toast.makeText(TabMe.this, "数据同步失败", Toast.LENGTH_SHORT)
						.show();
						cancleProcessDialog();
					}
				});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!User.showLoginButton()) {
			mLogin.setVisibility(View.INVISIBLE);
		} else {
			mLogin.setVisibility(View.VISIBLE);
		}
		if (User.myDoctor != null && !User.myDoctor.equals("")
				&& !User.myDoctor.equals("null")) {
			((TextView) findViewById(R.id.tvMyDoctor)).setText("我的医生："
					+ User.myDoctor);
		} else {
			((TextView) findViewById(R.id.tvMyDoctor)).setText("我的医生");
		}
		if (User.name != null && !User.name.equals("")
				&& !User.name.equals("null")) {
			((TextView) findViewById(R.id.tvName)).setText(User.name);
		} else {
			((TextView) findViewById(R.id.tvName)).setText("");
		}
	}

	public void showDintLogin() {
		Toast.makeText(this, "亲，没登陆呢。点击左上角登陆！", Toast.LENGTH_SHORT).show();
	}
}
