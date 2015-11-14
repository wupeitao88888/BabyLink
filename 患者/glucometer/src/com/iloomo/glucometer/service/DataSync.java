package com.iloomo.glucometer.service;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.iloomo.glucometer.DBTools;
import com.iloomo.glucometer.HostMainActivity;
import com.iloomo.glucometer.R;
import com.iloomo.glucometer.modle.TestData;
import com.iloomo.net.AsyncHttpClient;
import com.iloomo.net.JsonHttpResponseHandler;
import com.iloomo.net.RequestParams;

public class DataSync extends Service {
	public static String userid = "";
	Intent mIntent;
	ArrayList<TestData> atd;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		mIntent = intent;

		isRunning = true;
		MyThread a = new MyThread();
		new Thread(a).start();
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	boolean isRunning = false;

	public class MyThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRunning) {
				try {
					DBTools dbt = new DBTools();
					dbt.init(HostMainActivity.hma);
					atd = dbt.getNeedSync();
					if (atd.size() > 0) {
						TestData _TestData = atd.get(0);
						sendData(_TestData);
					} 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Thread.sleep(10000 * 6 * 60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// 一个小时执行一次
			}
		}
	}

	

	public void sendData(final TestData _TestData) {
		if (userid == null) {
			return;
		}
		RequestParams requestParams = new RequestParams();
		requestParams.put("userId", userid);
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
