package com.easemob.chatuidemo.ui;

import java.io.ByteArrayOutputStream;

import com.easemob.chat.EMChatManager;
import com.iloomo.doctor.R;
import com.iloomo.doctor.ReportActivity;
import com.iloomo.doctor.TabDetection;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 
 * 朋友个人详情
 * 
 * @author wupeitao
 *
 */
public class UserProfileActivity extends BaseActivity implements OnClickListener {

	private static final int REQUESTCODE_PICK = 1;
	private static final int REQUESTCODE_CUTTING = 2;
	// private ImageView headAvatar;
	// private ImageView headPhotoUpdate;
	// private ImageView iconRightArrow;
	// private TextView tvNickName;
	private TextView tvUsername;
	// private ProgressDialog dialog;
	// private RelativeLayout rlNickName;

	/***
	 * 
	 * 
	 * 我的方法
	 */
	private TextView age;// 年龄
	private TextView sex;
	private TextView due_date;// 预产期
	private TextView haemoglobin;// 血红蛋白
	private TextView sugar;// 糖化
	private Button blood_sugar_date;// 血糖数据
	private Button motion_date;// 运动记录
	private Button eat_record;// 饮食记录
	private Button send_msg;// 发消息

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.em_activity_user_profile);
		initView();
		initListener();
	}

	private void initView() {
		// headAvatar = (ImageView) findViewById(R.id.user_head_avatar);
		// headPhotoUpdate = (ImageView)
		// findViewById(R.id.user_head_headphoto_update);
		tvUsername = (TextView) findViewById(R.id.user_username);
		// tvNickName = (TextView) findViewById(R.id.user_nickname);
		// rlNickName = (RelativeLayout) findViewById(R.id.rl_nickname);
		// iconRightArrow = (ImageView) findViewById(R.id.ic_right_arrow);
		age = (TextView) findViewById(R.id.age);
		sex = (TextView) findViewById(R.id.sex);
		due_date = (TextView) findViewById(R.id.due_date);
		haemoglobin = (TextView) findViewById(R.id.haemoglobin);
		sugar = (TextView) findViewById(R.id.sugar);

		blood_sugar_date = (Button) findViewById(R.id.blood_sugar_date);
		motion_date = (Button) findViewById(R.id.motion_date);
		eat_record = (Button) findViewById(R.id.eat_record);
		send_msg = (Button) findViewById(R.id.send_msg);
		blood_sugar_date.setOnClickListener(this);
		motion_date.setOnClickListener(this);
		eat_record.setOnClickListener(this);
		send_msg.setOnClickListener(this);
	}

	private void initListener() {
		Intent intent = getIntent();
		String username = intent.getStringExtra("username");
		boolean enableUpdate = intent.getBooleanExtra("setting", false);
		if (enableUpdate) {
			// headPhotoUpdate.setVisibility(View.VISIBLE);
			// iconRightArrow.setVisibility(View.VISIBLE);
			// rlNickName.setOnClickListener(this);
			// headAvatar.setOnClickListener(this);
		} else {
			// headPhotoUpdate.setVisibility(View.GONE);
			// iconRightArrow.setVisibility(View.INVISIBLE);
		}
		if (username != null) {
			if (username.equals(EMChatManager.getInstance().getCurrentUser())) {
				tvUsername.setText(EMChatManager.getInstance().getCurrentUser());
				// EaseUserUtils.setUserNick(username, tvNickName);
				// EaseUserUtils.setUserAvatar(this, username, headAvatar);
			} else {
				tvUsername.setText(username);
				// EaseUserUtils.setUserNick(username, tvNickName);
				// EaseUserUtils.setUserAvatar(this, username, headAvatar);
				// asyncFetchUserInfo(username);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.user_head_avatar:
		// uploadHeadPhoto();
		// break;
		// case R.id.rl_nickname:
		// final EditText editText = new EditText(this);
		// new
		// AlertDialog.Builder(this).setTitle(R.string.setting_nickname).setIcon(android.R.drawable.ic_dialog_info).setView(editText)
		// .setPositiveButton(R.string.dl_ok, new
		// DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// String nickString = editText.getText().toString();
		// if (TextUtils.isEmpty(nickString)) {
		// Toast.makeText(UserProfileActivity.this,
		// getString(R.string.toast_nick_not_isnull),
		// Toast.LENGTH_SHORT).show();
		// return;
		// }
		// updateRemoteNick(nickString);
		// }
		// }).setNegativeButton(R.string.dl_cancel, null).show();
		// break;
		case R.id.blood_sugar_date:
			Intent intent=new Intent(this,ReportActivity.class);
			startActivity(intent);
			break;
		case R.id.motion_date:
			break;
		case R.id.eat_record:
			break;
		case R.id.send_msg:
			break;
		default:
			break;
		}

	}

	public void asyncFetchUserInfo(String username) {
		// HXHelper.getInstance().getUserProfileManager().asyncGetUserInfo(username,
		// new EMValueCallBack<EaseUser>() {
		//
		// @Override
		// public void onSuccess(EaseUser user) {
		// if (user != null) {
		// tvNickName.setText(user.getNick());
		// if(!TextUtils.isEmpty(user.getAvatar())){
		// Picasso.with(UserProfileActivity.this).load(user.getAvatar()).placeholder(R.drawable.em_default_avatar).into(headAvatar);
		// }else{
		// Picasso.with(UserProfileActivity.this).load(R.drawable.em_default_avatar).into(headAvatar);
		// }
		// HXHelper.getInstance().saveContact(user);
		// }
		// }
		//
		// @Override
		// public void onError(int error, String errorMsg) {
		// }
		// });
	}

	private void uploadHeadPhoto() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(R.string.dl_title_upload_photo);
		builder.setItems(
				new String[] { getString(R.string.dl_msg_take_photo), getString(R.string.dl_msg_local_upload) },
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0:
							Toast.makeText(UserProfileActivity.this, getString(R.string.toast_no_support),
									Toast.LENGTH_SHORT).show();
							break;
						case 1:
							Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
							pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
							startActivityForResult(pickIntent, REQUESTCODE_PICK);
							break;
						default:
							break;
						}
					}
				});
		builder.create().show();
	}

	private void updateRemoteNick(final String nickName) {
		// dialog = ProgressDialog.show(this,
		// getString(R.string.dl_update_nick), getString(R.string.dl_waiting));
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// boolean updatenick =
		// HXHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(nickName);
		// if (UserProfileActivity.this.isFinishing()) {
		// return;
		// }
		// if (!updatenick) {
		// runOnUiThread(new Runnable() {
		// public void run() {
		// Toast.makeText(UserProfileActivity.this,
		// getString(R.string.toast_updatenick_fail), Toast.LENGTH_SHORT)
		// .show();
		// dialog.dismiss();
		// }
		// });
		// } else {
		// runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		// dialog.dismiss();
		// Toast.makeText(UserProfileActivity.this,
		// getString(R.string.toast_updatenick_success), Toast.LENGTH_SHORT)
		// .show();
		// tvNickName.setText(nickName);
		// }
		// });
		// }
		// }
		// }).start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUESTCODE_PICK:
			if (data == null || data.getData() == null) {
				return;
			}
			startPhotoZoom(data.getData());
			break;
		case REQUESTCODE_CUTTING:
			if (data != null) {
				setPicToView(data);
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", true);
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, REQUESTCODE_CUTTING);
	}

	/**
	 * save the picture data
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		// Bundle extras = picdata.getExtras();
		// if (extras != null) {
		// Bitmap photo = extras.getParcelable("data");
		// Drawable drawable = new BitmapDrawable(getResources(), photo);
		// headAvatar.setImageDrawable(drawable);
		// uploadUserAvatar(Bitmap2Bytes(photo));
		// }

	}

	private void uploadUserAvatar(final byte[] data) {
		// dialog = ProgressDialog.show(this,
		// getString(R.string.dl_update_photo), getString(R.string.dl_waiting));
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// final String avatarUrl =
		// HXHelper.getInstance().getUserProfileManager().uploadUserAvatar(data);
		// runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		// dialog.dismiss();
		// if (avatarUrl != null) {
		// Toast.makeText(UserProfileActivity.this,
		// getString(R.string.toast_updatephoto_success),
		// Toast.LENGTH_SHORT).show();
		// } else {
		// Toast.makeText(UserProfileActivity.this,
		// getString(R.string.toast_updatephoto_fail),
		// Toast.LENGTH_SHORT).show();
		// }
		//
		// }
		// });
		//
		// }
		// }).start();
		//
		// dialog.show();
	}

	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
}
