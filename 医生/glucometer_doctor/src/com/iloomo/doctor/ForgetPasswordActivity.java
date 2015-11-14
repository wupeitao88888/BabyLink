package com.iloomo.doctor;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.iloomo.glucometer.modle.User;
import com.iloomo.net.AsyncHttpClient;
import com.iloomo.net.JsonHttpResponseHandler;
import com.iloomo.net.RequestParams;

public class ForgetPasswordActivity extends GameBaseActivity implements OnClickListener {
	TextView tvPhone;
	TextView tvPassword;

	@Override
	public void setContentLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.forget_password);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.next).setOnClickListener(this);
		tvPhone = (TextView) findViewById(R.id.phone);
		tvPassword = (TextView) findViewById(R.id.password);
	
		String _phone = this.getIntent().getStringExtra("phone");
		if(_phone != null){
			tvPhone.setText(_phone);
		}
	}

	@Override
	public void findViewById() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getDataFromServer() {
		// TODO Auto-generated method stub

	}

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.back) {
			finish();
		} else if (v.getId() == R.id.next) {

			RequestParams requestParams = new RequestParams();
			final String _mob = tvPhone.getText().toString();
			final String _password = tvPassword.getText().toString();
			String _accCode = tvPhone.getText().toString();

			requestParams.put("mob", _mob);
			requestParams.put("password", _password);
			requestParams.put("accCode", _accCode);

			AsyncHttpClient client = new AsyncHttpClient();
			client.get(getString(R.string.url_Acc_changPwd), requestParams,
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
										Toast.makeText(ForgetPasswordActivity.this,
												_msg, Toast.LENGTH_SHORT)
												.show();
									}
								} else {
									String _uid = response.getString("uid");
									User.saveUserIno(ForgetPasswordActivity.this, _uid,
											_password, _mob);
									Intent intent = new Intent(
											ForgetPasswordActivity.this,
											LoginActivity.class);
									startActivity(intent);
									finish();
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
	}
}
