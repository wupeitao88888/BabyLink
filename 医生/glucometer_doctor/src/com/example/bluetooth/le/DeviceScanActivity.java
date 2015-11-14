package com.example.bluetooth.le;

import com.iloomo.doctor.R;
import com.iloomo.doctor.TabDetection;
import com.iloomo.util.ILog;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
@SuppressLint("NewApi")
public class DeviceScanActivity {

	private BluetoothAdapter mBluetoothAdapter;
	private boolean mScanning;
	private Handler mHandler;
	public TabDetection mActivity;

	public DeviceScanActivity(TabDetection pActivity) {
		mActivity = pActivity;
		mHandler = new Handler();
		// 初始化 Bluetooth adapter, 通过蓝牙管理器得到一个参考蓝牙适配器(API必须在以上android4.3或以上和版本)
		final BluetoothManager bluetoothManager = (BluetoothManager) mActivity
				.getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		// 检查设备上是否支持蓝牙
		if (mBluetoothAdapter == null) {
			Toast.makeText(mActivity, R.string.error_bluetooth_not_supported,
					Toast.LENGTH_SHORT).show();
			return;
		}

	}

	private static final int REQUEST_ENABLE_BT = 1;
	// 10秒后停止查找搜索.
	private static final long SCAN_PERIOD = 6000000;

	public void initBluetooth() {
		// 为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用
		ILog.d("ILOOMO", "scanLeDevice");
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			mActivity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} else {
			scanLeDevice(true);
		}

	}

	public void destroy() {
		// mActivity.unregisterReceiver(mBluetoothStateListener);
	}

	public void scanLeDevice(final boolean enable) {
		if (enable && mBluetoothAdapter.isEnabled()) {
			// Stops scanning after a pre-defined scan period.
			// mHandler.postDelayed(new Runnable() {
			// @Override
			// public void run() {
			// mScanning = false;
			// mBluetoothAdapter.stopLeScan(mLeScanCallback);
			// }
			// }, SCAN_PERIOD);
			Intent gattServiceIntent = new Intent(mActivity,
					BluetoothLeService.class);
			mActivity.stopService(gattServiceIntent);
			if (mActivity.mBluetoothLeService != null) {
				mActivity.mBluetoothLeService.close();
				
				mActivity.mBluetoothLeService = null;
			}
			if (!mScanning) {
				mScanning = true;
				ILog.d("ILOOMO", "startLeScan");
				if (!mBluetoothAdapter.startLeScan(mLeScanCallback)) {
					mLeScanCallback = null;
					mLeScanCallback = new MyLeScanCallback(); 
					mBluetoothAdapter.startLeScan(mLeScanCallback);
					ILog.d("ILOOMO", "startLeScan2");
				}
			}
		} else {
			if (mScanning) {
				mScanning = false;
				ILog.d("ILOOMO", "stopLeScan");
				mBluetoothAdapter.stopLeScan(mLeScanCallback);
			}
		}
	}
	BluetoothAdapter.LeScanCallback mLeScanCallback = new MyLeScanCallback();
	public class MyLeScanCallback implements BluetoothAdapter.LeScanCallback {

		@Override
		public void onLeScan(BluetoothDevice device, int arg1, byte[] arg2) {

			String dn = device.getName();
			if (dn.startsWith("Tv221") && mScanning) {
				if (mActivity.mBluetoothLeService == null) {
					Intent gattServiceIntent = new Intent(mActivity,
							BluetoothLeService.class);
					gattServiceIntent.putExtra(TabDetection.EXTRAS_DEVICE_NAME,
							device.getName());
					gattServiceIntent.putExtra(
							TabDetection.EXTRAS_DEVICE_ADDRESS,
							device.getAddress());
					mActivity.startService(gattServiceIntent);
				} else {
					mActivity.mBluetoothLeService.connect(device.getAddress());
				}
				scanLeDevice(false);
			}

		}

	}

	// Device scan callback.
//	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
//
//		@Override
//		public void onLeScan(BluetoothDevice device, int arg1, byte[] arg2) {
//			String dn = device.getName();
//			if (dn.startsWith("Tv221") && mScanning) {
//				if (mActivity.mBluetoothLeService == null) {
//					Intent gattServiceIntent = new Intent(mActivity,
//							BluetoothLeService.class);
//					gattServiceIntent.putExtra(TabDetection.EXTRAS_DEVICE_NAME,
//							device.getName());
//					gattServiceIntent.putExtra(
//							TabDetection.EXTRAS_DEVICE_ADDRESS,
//							device.getAddress());
//					mActivity.startService(gattServiceIntent);
//				} else {
//					mActivity.mBluetoothLeService.connect(device.getAddress());
//				}
//				scanLeDevice(false);
//			}
//
//		}
//
//	};

	static class ViewHolder {
		TextView deviceName;
		TextView deviceAddress;
	}
}