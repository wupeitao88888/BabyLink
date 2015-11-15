package com.iloomo.doctor;

import com.iloomo.base.BaseActivity;
import com.iloomo.base.BaseApplication;
import com.iloomo.glucometer.view.DefaultDialogBuilder;
import com.iloomo.utils.DavikActivityManager;
import com.iloomo.utils.Exit;
import com.iloomo.utils.LogMessage;
import com.umeng.analytics.MobclickAgent;

import android.content.DialogInterface.OnClickListener;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 创 建 人: iloomo 日 期： 2013-1-10 下午1:04:08 修 改 人： 日 期： 描 述： 版 本 号：
 */
public abstract class GameBaseActivity extends BaseActivity {
	private DefaultDialogBuilder ib; // 进度条
	private DefaultDialogBuilder msgib; // 进度条
	public final int IS_IN_GROUPACTIVTY = 1; // 父视图是activitygroup
	public final int IS_SINGLE_ACTIVITY = 2;
	private Exit exit = new Exit();

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			LogMessage.i("onKeyDown", "返回按键");
			BaseApplication application = (BaseApplication) getApplication();
			String name = application.getActivityManager()
					.getCurrentActivityName();
			boolean isExitFlag = isTopActivity(name);
			if (!isExitFlag) {
				application.getActivityManager().popActivity(this);
				return super.onKeyDown(keyCode, event);
			}
			return true;
		}
		return false;

	}

	/**
	 * 判断当前页面是否是标签栏页面
	 * 
	 * @param name
	 *            当前页面的类名
	 * @return
	 */
	private boolean isTopActivity(String name) {
		int length = AppConstant.TOP_ACTIVITY_NAME.length;
		for (int i = 0; i < length; i++) {
			if (name.equals(AppConstant.TOP_ACTIVITY_NAME[i])) {
				pressAgainExit();
				return true;
			}
		}
		return false;

	}

	/**
	 * 再按一次退出程序。
	 */
	private void pressAgainExit() {

		if (exit.isExit()) {
			DavikActivityManager.getScreenManager().exitApp(getClass());
			finish();
		} else {
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			exit.doExitInOneSecond();
		}
	}

	public void showYesOrNo(String title, String msg,OnClickListener yesListener) {
		if (msgib == null) {
			msgib = new DefaultDialogBuilder(this, 0);
		} else {
			msgib = new DefaultDialogBuilder(this, 0);
		}
		msgib.setYesOrNo();
		msgib.setTitle(title);
		msgib.setMessage(msg);
		msgib.setPositiveButton("是", yesListener);
		msgib.setNeutralButton("否", null);
		msgib.show();
	}
	public void showYes(String title, String msg) {
		if (msgib == null) {
			msgib = new DefaultDialogBuilder(this, 0);
		} else {
			msgib = new DefaultDialogBuilder(this, 0);
		}
		msgib.setOnlyYes();
		msgib.setNeutralButton("确定", null);
		msgib.setTitle(title);
		msgib.setMessage(msg);
		msgib.show();
	}

	/**
	 * show the processDialog
	 */
	public void showProcessDialog(String content, int flag) {

		if (ib == null) {
			if (flag == IS_IN_GROUPACTIVTY) {
				ib = new DefaultDialogBuilder(this.getParent(), 1);
			} else {
				ib = new DefaultDialogBuilder(this, 1);
			}

		}
		ib.setTitle(R.string.dialog_title);
		if (content.equals("")) {
			ib.setMessage(getString(R.string.progress_content_message) + "...");
		} else
			ib.setMessage(content);
		ib.show();
	}

	/**
	 * cancle the progressDialog
	 */
	public void cancleProcessDialog() {
		if (ib != null) {
			ib.cancle();
			ib = null;
		}
	}

	/**
	 * show the processDialog
	 */
	public void showProcessDialog(int flag) {
		if (ib == null) {
			if (flag == IS_IN_GROUPACTIVTY) {
				ib = new DefaultDialogBuilder(this.getParent(), 1);
			} else {
				ib = new DefaultDialogBuilder(this, 1);
			}
		}

		ib.setTitle(R.string.dialog_title);
		ib.setMessage(getString(R.string.progress_content_message) + "...");
		ib.show();
	}

	/**
	 * show the processDialog
	 */
	public void showProcessDialog(String content) {

		if (ib == null) {
			ib = new DefaultDialogBuilder(this, 1);
		} else {
			ib = new DefaultDialogBuilder(this, 1);
		}

		ib.setTitle(R.string.dialog_title);
		if (content.equals("")) {
			ib.setMessage(getString(R.string.progress_content_message) + "...");
		} else
			ib.setMessage(content);
		ib.show();
	}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	cancleProcessDialog();
}
}
