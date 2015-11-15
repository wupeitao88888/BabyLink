package com.iloomo.doctor;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iloomo.glucometer.modle.TestData;
import com.iloomo.glucometer.modle.User;
import com.iloomo.net.AsyncHttpClient;
import com.iloomo.net.JsonHttpResponseHandler;
import com.iloomo.net.RequestParams;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends GameBaseActivity implements OnClickListener {

	private TextView tvPhone;
	private TextView tvPassword;

	@Override
	public void setContentLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.login);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.signin).setOnClickListener(this);
		findViewById(R.id.submit).setOnClickListener(this);
		tvPhone = (TextView) findViewById(R.id.phone);
		tvPassword = (TextView) findViewById(R.id.password);
//		tvPassword.setText(User.password);
		tvPhone.setText(User.phone);
		findViewById(R.id.forgetPassword).setOnClickListener(this);
	}

	@Override
	public void findViewById() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getDataFromServer() {

		// TODO Auto-generated method stub
		if (User.uid == null) {
			return;
		}
		RequestParams requestParams = new RequestParams();
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
									Toast.makeText(LoginActivity.this, _msg,
											Toast.LENGTH_SHORT).show();
								}
							} else {
								JSONObject data = response
										.getJSONObject("data");
								String __name = data.getString("name");
								if (__name != null && !"null".equals(__name)){
//									((TextView) findViewById(R.id.tvName))
//											.setText(__name);
								User.saveName(LoginActivity.this, __name);
								}
								String __dueDate = data.getString("gestation");
								if (__dueDate != null
										&& !"null".equals(__dueDate)){
									User.saveGestation(LoginActivity.this, __dueDate);
								}
							}

						} catch (Exception e) {
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
	ArrayList<TestData> atd;
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.forgetPassword) {
			
			// 填写从短信SDK应用后台注册得到的APPKEY
			String APPKEY = "6c017209ad3c";

			// 填写从短信SDK应用后台注册得到的APPSECRET
			String APPSECRET = "225c598e4d0839d83a1a572605accfbe";
			SMSSDK.initSDK(this, APPKEY, APPSECRET);
			// 打开注册页面
			RegisterPage registerPage = new RegisterPage();
			registerPage.setRegisterCallback(new EventHandler() {
				public void afterEvent(int event, int result, Object data) {
					// 解析注册结果
					if (result == SMSSDK.RESULT_COMPLETE) {
						@SuppressWarnings("unchecked")
						HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
						String country = (String) phoneMap.get("country");
						String phone = (String) phoneMap.get("phone");
//						tvPhone.setText(phone);
						Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
						intent.putExtra("phone", phone);
						startActivity(intent);
						finish();
					}else{
					}
				}
			});
			registerPage.show(this);
			
		} else if (v.getId() == R.id.back) {
			finish();
		} else if (v.getId() == R.id.signin) {
			// 填写从短信SDK应用后台注册得到的APPKEY
			String APPKEY = "6c017209ad3c";

			// 填写从短信SDK应用后台注册得到的APPSECRET
			String APPSECRET = "225c598e4d0839d83a1a572605accfbe";
			SMSSDK.initSDK(this, APPKEY, APPSECRET);
			// 打开注册页面
			RegisterPage registerPage = new RegisterPage();
			registerPage.setRegisterCallback(new EventHandler() {
				public void afterEvent(int event, int result, Object data) {
					// 解析注册结果
					if (result == SMSSDK.RESULT_COMPLETE) {
						@SuppressWarnings("unchecked")
						HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
						String country = (String) phoneMap.get("country");
						String phone = (String) phoneMap.get("phone");
//						tvPhone.setText(phone);
						Intent intent = new Intent(LoginActivity.this,SignInActivity.class);
						intent.putExtra("phone", phone);
						startActivity(intent);
						finish();
					}else{
					}
				}
			});
			registerPage.show(this);
		} else if (v.getId() == R.id.submit) {
			RequestParams requestParams = new RequestParams();
			final String _mob = tvPhone.getText().toString();
			final String _password = tvPassword.getText().toString();
			requestParams.put("mob", _mob);
			requestParams.put("password", _password);
			requestParams.put("accCode", _mob);

			AsyncHttpClient client = new AsyncHttpClient();
			client.get(getString(R.string.url_login), requestParams,
					new JsonHttpResponseHandler() {
						@Override
						public void onStart() {
							// TODO Auto-generated method stub
							super.onStart();
							showProcessDialog("正在登录...");
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
									int _resultCode = response
											.getInt("resultCode");
									if (_resultCode != 99) {
										String _msg = response
												.getString("resultMsg");
										Toast.makeText(LoginActivity.this,
												_msg, Toast.LENGTH_SHORT)
												.show();
									}
									cancleProcessDialog();
								} else {
									String _uid = response.getString("uid");
									User.saveUserIno(LoginActivity.this, _uid,
											_password, _mob);
									cancleProcessDialog();
									DBTools dbt = new DBTools();
									dbt.init(HostMainActivity.hma);
									atd = dbt.getNeedSync();
									if (atd.size() > 0) {//如果有没有同步的数据则同步
										showProcessDialog("正在同步数据...");
										TestData _TestData = atd.get(0);
										sendData(_TestData);
									} else{//如果没有需要同步的数据，则直接更新数据
										DBTools __dbt = new DBTools();
										__dbt.init(HostMainActivity.hma);
										__dbt.deleteTestData();
										updateTestData(_uid);
									}
									
								}
								getDataFromServer();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(Throwable error) {
							// TODO Auto-generated method stub
							super.onFailure(error);
							cancleProcessDialog();
						}
					});
		}
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
//						
//						finish();
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
						cancleProcessDialog();
						finish();
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
						Toast.makeText(LoginActivity.this, "数据同步成功", Toast.LENGTH_SHORT).show();
						cancleProcessDialog();
						finish();
					}

					@Override
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						super.onFailure(error);
						cancleProcessDialog();
					}
				});

	}
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
								}else{
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

}
