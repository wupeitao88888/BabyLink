package com.iloomo.doctor;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iloomo.glucometer.modle.Doctor;
import com.iloomo.glucometer.modle.User;
import com.iloomo.net.AsyncHttpClient;
import com.iloomo.net.JsonHttpResponseHandler;
import com.iloomo.net.RequestParams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchDoctorActivity extends GameBaseActivity implements
		OnClickListener {

	private TextView tvPhone;
	private TextView tvPassword;
	ListView lvDoctors;
	MyAdapter mya;

	@Override
	public void setContentLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.doctors);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.llSearch).setOnClickListener(this);
		lvDoctors = (ListView) findViewById(R.id.lvDoctors);
		mya = new MyAdapter(this);
		lvDoctors.setAdapter(mya);
	}

	@Override
	public void findViewById() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getDataFromServer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.back) {
			finish();
		} else if (v.getId() == R.id.llSearch) {
			String searchContent = ((EditText) findViewById(R.id.etSearch))
					.getText().toString();
			RequestParams requestParams = new RequestParams();
			requestParams.put("name", searchContent);

			AsyncHttpClient client = new AsyncHttpClient();
			client.get(getString(R.string.url_doctorSearch), requestParams,
					new JsonHttpResponseHandler() {
						@Override
						public void onStart() {
							// TODO Auto-generated method stub
							super.onStart();
							showProcessDialog("正在查询...");
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
										Toast.makeText(
												SearchDoctorActivity.this,
												_msg, Toast.LENGTH_SHORT)
												.show();
									}
									mya.doctors.clear();
									mya.notifyDataSetChanged();
								} else {
									JSONArray _doctors = response
											.getJSONArray("list");
									mya.doctors.clear();
									if (_doctors != null) {

										for (int i = 0; i < _doctors.length(); i++) {
											JSONObject _jdoctor = _doctors
													.getJSONObject(i);
											Doctor _Doctor = new Doctor();
											_Doctor.name = _jdoctor
													.getString("name");
											_Doctor.hospital = _jdoctor
													.getString("hospital");
											_Doctor.city = _jdoctor
													.getString("city");
											_Doctor.id = _jdoctor.getLong("id");
											mya.doctors.add(_Doctor);

										}
									}
									mya.notifyDataSetChanged();
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

	private class MyAdapter extends BaseAdapter implements OnClickListener {
		LayoutInflater mInflater;

		public MyAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		ArrayList<Doctor> doctors = new ArrayList<Doctor>();

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return doctors.size();
		}

		@Override
		public Object getItem(int index) {

			return doctors.get(index);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_doctor, null);
				holder = new ViewHolder();
				holder.city = (TextView) convertView
						.findViewById(R.id.tvHospital);
				holder.name = (TextView) convertView.findViewById(R.id.tvName);
				convertView.setTag(holder);
				convertView.setOnClickListener(this);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Doctor _doctor = doctors.get(position);
			holder.city.setText(_doctor.getDoctor());
			holder.name.setText(_doctor.name);
			holder.doctor = _doctor;

			return convertView;
		}

		@Override
		public void onClick(View v) {
			final Doctor __doctor = ((ViewHolder) v.getTag()).doctor;
			// dueDate 预产期
			// editText1 姓名
			// editText2 性别
			// editText3 年龄
			RequestParams requestParams = new RequestParams();
			requestParams.put("name", User.name);
			requestParams.put("id", User.uid);
			requestParams.put("doctorId", "" + __doctor.id);
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
										Toast.makeText(
												SearchDoctorActivity.this,
												_msg, Toast.LENGTH_SHORT)
												.show();
									} else {
										User.saveMyDoctor(
												SearchDoctorActivity.this,
												__doctor.name);
										finish();
									}
								} else {
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

	public final class ViewHolder {
		public TextView name;
		public TextView city;
		Doctor doctor;
	}
}
