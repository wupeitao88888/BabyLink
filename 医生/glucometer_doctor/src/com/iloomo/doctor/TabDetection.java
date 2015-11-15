package com.iloomo.doctor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.bluetooth.le.BluetoothLeService;
import com.example.bluetooth.le.DeviceScanActivity;
import com.example.bluetooth.le.SampleGattAttributes;
import com.iloomo.glucometer.modle.TestData;
import com.iloomo.glucometer.modle.User;
import com.iloomo.util.DateUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * For a given BLE device, this Activity provides the user interface to connect,
 * display data, and display GATT services and characteristics supported by the
 * device. The Activity communicates with {@code BluetoothLeService}, which in
 * turn interacts with the Bluetooth LE API.
 */

@SuppressLint("NewApi")
public class TabDetection extends GameBaseActivity implements OnClickListener {
	private float[] value;
	public static String title[] = { "早餐前", "早餐后", "午餐前", "午餐后", "晚餐前", "晚餐后",
			"凌晨" };
	private final static String TAG = TabDetection.class.getSimpleName();
	public DeviceScanActivity dsa;
	public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
	public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

	private TextView mConnectionState;
	private EditText mDataField;
	private String mDeviceName;
	private String mDeviceAddress = null;

	public static BluetoothLeService mBluetoothLeService;
	private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
	private boolean mConnected = false;
	private BluetoothGattCharacteristic mNotifyCharacteristic;

	private final String LIST_NAME = "NAME";
	private final String LIST_UUID = "UUID";
	ImageView mShowDeviceAmini;
	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();

			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
				updateConnectionState(R.string.connected);
			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
					.equals(action)) {
				updateConnectionState(R.string.disconnected);
//				mBluetoothLeService.close();
				dsa.scanLeDevice(true);
			} else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED
					.equals(action)) {
				// Show all the supported services and characteristics on the
				// user interface.
				displayGattServices(mBluetoothLeService
						.getSupportedGattServices());
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
				displayData(intent
						.getStringExtra(BluetoothLeService.EXTRA_DATA));

			}

		}

	};
	private HorizontalScrollView horizontalScrollView;
	private LinearLayout linearLayout;
	private ArrayList<TextView> textViews;
	private ArrayList<View> views;
	private int H_width;
	private TextView dateSelect;
	private TextView timeSelect;

	BroadcastReceiver mBluetoothStateListener = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent pIntent) {
			int state = pIntent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
			switch (state) {
			case BluetoothAdapter.STATE_TURNING_ON:
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dsa.scanLeDevice(true);
				break;
			case BluetoothAdapter.STATE_ON:
				
				break;
			case BluetoothAdapter.STATE_TURNING_OFF:
				updateConnectionState(R.string.disconnected);
//				mBluetoothLeService.close();
				dsa.scanLeDevice(false);
				break;
			case BluetoothAdapter.STATE_OFF:

				break;
			}
		}
	};

	public void initBooth() {
		if (!this.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT)
					.show();
		} else {
			if (dsa == null) {
				dsa = new DeviceScanActivity(this);
				dsa.initBluetooth();
				IntentFilter stateChangeFilter = new IntentFilter(
						BluetoothAdapter.ACTION_STATE_CHANGED);
				registerReceiver(mBluetoothStateListener, stateChangeFilter);
				registerReceiver(mGattUpdateReceiver,
						makeGattUpdateIntentFilter());
			}
		}

	}

	public void destoryBooth() {
		if (this.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			unregisterReceiver(mGattUpdateReceiver);
			unregisterReceiver(mBluetoothStateListener);
			//
			if (dsa != null) {
				dsa.destroy();
				dsa.scanLeDevice(false);
			}
			Intent gattServiceIntent = new Intent(this,
					BluetoothLeService.class);
			stopService(gattServiceIntent);
			if (mBluetoothLeService != null) {
				mBluetoothLeService.close();
				
				mBluetoothLeService = null;
			}
		}
		

	}

	@Override
	protected void onResume() {
		super.onResume();
		initBooth();
		if (!User.showLoginButton()) {
			findViewById(R.id.login).setVisibility(View.INVISIBLE);
		} else {
			findViewById(R.id.login).setVisibility(View.VISIBLE);
			findViewById(R.id.login).setOnClickListener(this);
		}

		if (mBluetoothLeService != null) {
			final boolean result = mBluetoothLeService.connect(mDeviceAddress);
			Log.d(TAG, "Connect request result=" + result);
		}

		// /////////
		if (User.gestation != null) {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateNowStr = sdf.format(d);
			try {
				int weeks = 40 - (DateUtil.daysBetween(dateNowStr,
						User.gestation) / 7);
				((TextView) findViewById(R.id.dute)).setText("孕期:" + weeks
						+ "周");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			((TextView) findViewById(R.id.dute)).setText("设置预产期");
			((TextView) findViewById(R.id.dute)).setOnClickListener(this);
		}
		
		
		refreshTitle();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		destoryBooth();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_connect:
			mBluetoothLeService.connect(mDeviceAddress);
			return true;
		case R.id.menu_disconnect:
			mBluetoothLeService.disconnect();
			return true;
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateConnectionState(final int resourceId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (resourceId == R.string.connected) {
					mShowDeviceAmini
							.setImageResource(R.drawable.test_via_dnurse);
					mConnectionState.setTextColor(Color.WHITE);
				} else {
					mShowDeviceAmini
							.setImageResource(R.drawable.test_via_dnurse1);
					mConnectionState
							.setTextColor(Color.parseColor("#ffececec"));
				}
				mConnectionState.setText(resourceId);
			}
		});
	}

	private void displayData(String data) {
		if (data != null) {
			String tmpData = mDataField.getText().toString();
			mDataField.setText(data);
		}
	}

	// Demonstrates how to iterate through the supported GATT
	// Services/Characteristics.
	// In this sample, we populate the data structure that is bound to the
	// ExpandableListView
	// on the UI.
	private void displayGattServices(List<BluetoothGattService> gattServices) {
		if (gattServices == null)
			return;
		String uuid = null;
		String unknownServiceString = getResources().getString(
				R.string.unknown_service);
		String unknownCharaString = getResources().getString(
				R.string.unknown_characteristic);
		ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
		ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();
		mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

		// Loops through available GATT Services.
		for (BluetoothGattService gattService : gattServices) {
			HashMap<String, String> currentServiceData = new HashMap<String, String>();
			uuid = gattService.getUuid().toString();

			if (uuid.contains("ffe0")) {
				currentServiceData
						.put(LIST_NAME, SampleGattAttributes.lookup(uuid,
								unknownServiceString));
				currentServiceData.put(LIST_UUID, uuid);
				gattServiceData.add(currentServiceData);

				ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
				List<BluetoothGattCharacteristic> gattCharacteristics = gattService
						.getCharacteristics();
				ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();

				// Loops through available Characteristics.
				for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
					charas.add(gattCharacteristic);
					HashMap<String, String> currentCharaData = new HashMap<String, String>();
					uuid = gattCharacteristic.getUuid().toString();
					if (uuid.contains("ffe4")) {
						currentCharaData.put(LIST_NAME, SampleGattAttributes
								.lookup(uuid, unknownCharaString));
						currentCharaData.put(LIST_UUID, uuid);
						gattCharacteristicGroupData.add(currentCharaData);

						if (mGattCharacteristics != null) {
							final BluetoothGattCharacteristic characteristic = gattCharacteristic;

							final int charaProp = characteristic
									.getProperties();
							if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
								// If there is an active notification on a
								// characteristic, clear
								// it first so it doesn't update the data field
								// on the user interface.
								if (mNotifyCharacteristic != null) {
									mBluetoothLeService
											.setCharacteristicNotification(
													mNotifyCharacteristic,
													false);
									mNotifyCharacteristic = null;
								}
								mBluetoothLeService
										.readCharacteristic(characteristic);
							}
							if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
								mNotifyCharacteristic = characteristic;
								mBluetoothLeService
										.setCharacteristicNotification(
												characteristic, true);
							}

						}
					}
				}
				mGattCharacteristics.add(charas);
				gattCharacteristicData.add(gattCharacteristicGroupData);

			}
		}

	}

	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		return intentFilter;
	}

	@Override
	public void setContentLayout() {
		// TODO Auto-generated method stub
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.tab_detection);
	
	}

	@Override
	public void findViewById() {
		findViewById(R.id.btnsubmit).setOnClickListener(this);
		mShowDeviceAmini = (ImageView) findViewById(R.id.showDeviceAmini);
		mShowDeviceAmini.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent _intent = new Intent(TabDetection.this,
						TestAnmiActivity.class);
				startActivity(_intent);
			}
		});
		mConnectionState = (TextView) findViewById(R.id.deviceStatus1);
		mDataField = (EditText) findViewById(R.id.value);
		mDataField.setHintTextColor(Color.WHITE);
		horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
		linearLayout = (LinearLayout) findViewById(R.id.ll_main);
		dateSelect = (TextView) findViewById(R.id.dateSelect);
		timeSelect = (TextView) findViewById(R.id.timeSelect);
		dateSelect.setOnClickListener(this);
		timeSelect.setOnClickListener(this);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		dateSelect.setText(dateNowStr);
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
		String dateNowStr1 = sdf1.format(d);
		timeSelect.setText(dateNowStr1);
		InItTitle();
		((TextView) findViewById(R.id.value10)).setText("目标值：" + value[0] + "－"
				+ value[1]);
		
		 Timer timer = new Timer(); 
		 timer.schedule(new TimerTask() {  
		        public void run() {  
		        	runOnUiThread(new Runnable() {

		    			@Override
		    			public void run() {
		    				try {
		    					refreshTitle();
		    				} catch (Exception e) {
		    					// TODO Auto-generated catch block
		    					e.printStackTrace();
		    				}
		    				
		    			}});
		        }  
		    }, 500);// delay=2000毫秒 后执行该任务
		
	}

	@Override
	public void getDataFromServer() {
		// TODO Auto-generated method stub

	}

	/***
	 * init title
	 */
	void InItTitle() {
		textViews = new ArrayList<TextView>();
		views = new ArrayList<View>();
		H_width = getWindowManager().getDefaultDisplay().getWidth() / 4;
		int height = 70;
		for (int i = 0; i < title.length; i++) {
			TextView textView = new TextView(this);
			textView.setText(title[i]);
			textView.setTextSize(17);
			textView.setTextColor(Color.WHITE);
			textView.setWidth(H_width);
			textView.setHeight(height);
			Log.e("aa", "text_width=" + textView.getWidth());
			textView.setGravity(Gravity.CENTER);
			textView.setId(i);
			textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setSelector(v.getId());
					refreshTitle();
				}
			});
			textViews.add(textView);
			// 分割线
			View view = new View(this);
			LinearLayout.LayoutParams layoutParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.width = 1;
			layoutParams.height = height - 30;
			layoutParams.gravity = Gravity.CENTER;

			view.setLayoutParams(layoutParams);
			view.setBackgroundColor(Color.WHITE);
			linearLayout.addView(textView);
			if (i != title.length - 1) {
				linearLayout.addView(view);
				views.add(view);
			}
			Log.e("aa", "linearLayout_width=" + linearLayout.getWidth());

		}
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		selectPoint(hour);
	}

	public int pointIndex = 0;

	/***
	 * 选中效果
	 */
	public void setSelector(int id) {
		pointIndex = id;
	}

	public void refreshTitle() {
	
		int id = pointIndex;
		for (int i = 0; i < title.length; i++) {
			if (views.size() > i) {
				views.get(i).setVisibility(View.VISIBLE);
			}
			if (id == i) {
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.select_bg);
				textViews.get(id).setBackgroundDrawable(
						new BitmapDrawable(bitmap));
				textViews.get(id).setTextColor(Color.parseColor("#2bc8db"));
				if (i > 2) {
					int scrollto = H_width * i - 180;
					horizontalScrollView.smoothScrollTo(scrollto, 0);
				} else {
					horizontalScrollView.smoothScrollTo(0, 0);
				}
			} else {
				textViews.get(i).setBackgroundDrawable(new BitmapDrawable());
				textViews.get(i).setTextColor(Color.WHITE);
			}
		}
		if (views.size() - 1 >= id && id > 0) {
			views.get(id - 1).setVisibility(View.GONE);
			views.get(id).setVisibility(View.GONE);
		} else if (id == 0) {
			views.get(id).setVisibility(View.GONE);
		} else {
			views.get(id - 1).setVisibility(View.GONE);
		}
	
	}
	
	public void selectPoint(int hour) {
		if (6 <= hour && hour <= 8) {
			// 早餐前
			setSelector(0);
			value = User.beforValue;
		} else if (8 < hour && hour <= 10) {
			// 早餐后
			setSelector(1);
			value = User.afterValue;

		} else if (10 < hour && hour <= 12) {
			// 午餐前
			setSelector(2);
			value = User.beforValue;
		} else if (12 < hour && hour <= 14) {
			// 午餐后
			setSelector(3);
			value = User.afterValue;
		} else if (14 < hour && hour <= 18) {
			// 晚餐前
			setSelector(4);
			value = User.beforValue;
		} else if (18 < hour && hour <= 21) {
			// 晚餐后
			setSelector(5);
			value = User.afterValue;
		} else if (21 < hour && hour <= 23) {
			// 凌晨
			value = User.beforValue;
			setSelector(6);
		} else if (0 <= hour && hour <= 5) {
			// 凌晨
			value = User.beforValue;
			setSelector(6);
		}
	}

	public static int getPointIndex(int hour) {
		int _index = -1;
		if (6 <= hour && hour <= 8) {
			// 早餐前
			_index = 0;
		} else if (8 < hour && hour <= 10) {
			// 早餐后
			_index = 1;
		} else if (10 < hour && hour <= 12) {
			// 午餐前
			_index = 2;
		} else if (12 < hour && hour <= 14) {
			// 午餐后
			_index = 3;
		} else if (14 < hour && hour <= 18) {
			// 晚餐前
			_index = 4;
		} else if (18 < hour && hour <= 21) {
			// 晚餐后
			_index = 5;
		} else if (21 < hour && hour <= 23) {
			// 凌晨
			_index = 6;
		} else if (0 <= hour && hour <= 5) {
			_index = 6;
		}
		return _index;
	}

	public static int getPointTime(int index) {
		int hour = 0;
		if (index == 0) {
			hour = 8;
		} else if (index == 1) {
			hour = 10;
		}
		if (index == 2) {
			hour = 12;
		}
		if (index == 3) {
			hour = 14;
		}
		if (index == 4) {
			hour = 18;
		}
		if (index == 5) {
			hour = 21;
		}
		if (index == 6) {
			hour = 23;
		}
		return hour;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.login) {
			Intent _intent = new Intent(this, LoginActivity.class);
			startActivity(_intent);
		} else if (v.getId() == R.id.dateSelect) {
			final Calendar objTime = Calendar.getInstance();
			int iYear = objTime.get(Calendar.YEAR);
			int iMonth = objTime.get(Calendar.MONTH);
			int iDay = objTime.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog mDialog = new DatePickerDialog(TabDetection.this,
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
		} else if (v.getId() == R.id.timeSelect) {
			final Calendar objTime = Calendar.getInstance();
			int hourOfDay = objTime.get(Calendar.HOUR_OF_DAY);
			int minute = objTime.get(Calendar.MINUTE);
			new TimePickerDialog(TabDetection.this,
					AlertDialog.THEME_HOLO_LIGHT, new OnTimeSetListener() {
						public void onTimeSet(TimePicker arg0, int arg1,
								int arg2) {
							String str2, str3;
							if (arg1 < 10) {
								str2 = "0" + (arg1);
							} else {
								str2 = "" + (arg1);
							}

							if (arg2 < 10) {
								str3 = ":0" + arg2;
							} else {
								str3 = ":" + arg2;
							}
							timeSelect.setText(str2 + str3);
						}
					}, hourOfDay, minute, true).show();
		} else if (v.getId() == R.id.btnsubmit) {
			Number number = null;
			String s = mDataField.getText().toString();
			if ("".equals(s) || s == null) {
				Toast.makeText(TabDetection.this, "请输入血糖值", Toast.LENGTH_SHORT)
						.show();
			} else {
				// try {
				// number = NumberFormat.getNumberInstance(Locale.FRENCH)
				// .parse(s);
				// } catch (ParseException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				DBTools dbt = new DBTools();
				dbt.init(this);
				long time = (System.currentTimeMillis());
				TestData pTestData = new TestData();
				pTestData.timestamp = "" + time;
				// pTestData.remark = ((EditText) findViewById(R.id.remark))
				// .getText().toString();
				pTestData.fullData = BluetoothLeService.fullData;
				pTestData.timeId = pointIndex;
				pTestData.testDate = dateSelect.getText().toString();
				User.data = pTestData.bloodGlucoseValues = Float.parseFloat(s);
				int ret = dbt.insertTestData(pTestData);
				if (ret == 1) {
					BluetoothLeService.fullData = null;
					mDataField.setText("");
					// ((EditText) findViewById(R.id.remark)).setText("");
					HostMainActivity.hma.setCurrentTab(1);
					Toast.makeText(TabDetection.this, "保存成功",
							Toast.LENGTH_SHORT).show();

				}
			}
		}
	}

	public BluetoothManager getSystemService(String bluetoothService) {
		// TODO Auto-generated method stub
		return null;
	}

}
