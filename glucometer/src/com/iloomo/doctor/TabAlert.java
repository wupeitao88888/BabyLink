package com.iloomo.doctor;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.iloomo.glucometer.modle.TestData;
import com.iloomo.glucometer.modle.User;
import com.iloomo.glucometer.view.MyChartView;
import com.iloomo.glucometer.view.MyChartView.Mstyle;

public class TabAlert extends GameBaseActivity implements OnClickListener {
	LinearLayout mLogin;
	int[] valuesId = { R.id.showValue1, R.id.showValue2, R.id.showValue3,
			R.id.showValue4, R.id.showValue5, R.id.showValue6, R.id.showValue7 };
	MyChartView tu;
	public static String title[] = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };

	@Override
	public void setContentLayout() {
		this.setContentView(R.layout.tab_home);
	}

	@Override
	public void findViewById() {

		// TODO Auto-generated method stub
		tu = (MyChartView) findViewById(R.id.report);
		mLogin = (LinearLayout) findViewById(R.id.login);
		findViewById(R.id.showReport).setOnClickListener(this);
		mLogin.setOnClickListener(this);
		// findViewById(R.id.btntest).setOnClickListener(this);
		if (!User.showLoginButton()) {
			mLogin.setVisibility(View.INVISIBLE);
		}
		tu.setMstyle(Mstyle.Line);
		tu.setMargint(0);
		tu.setMarginb(15);
		horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
		linearLayout = (LinearLayout) findViewById(R.id.ll_main);
		InItTitle();

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try {
							a();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
			}
		}, 500);// delay=2000毫秒 后执行该任务
	}

	@Override
	public void getDataFromServer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.login) {
			Intent _intent = new Intent(this, LoginActivity.class);
			startActivity(_intent);
		} else if (v.getId() == R.id.btntest) {
			HostMainActivity.hma.setCurrentTab(0);
		} else if (v.getId() == R.id.showReport) {
			Intent _intent = new Intent(this, ReportActivity.class);
			startActivity(_intent);
		}
		// else if (v.getId() == R.id.gestation) {
		// if (User.uid == null || "".equals(User.uid)) {
		// Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();
		// } else {
		// // ((TextView)findViewById(R.id.gestation)).setOnClickListener(null);
		// Intent _intent = new Intent(this, InfoActivity.class);
		// startActivity(_intent);
		// }
		// }
	}

	ArrayList<ArrayList> weekList;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		try {
			if (!User.showLoginButton()) {
				mLogin.setVisibility(View.INVISIBLE);
			} else {
				mLogin.setVisibility(View.VISIBLE);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
			((TextView) findViewById(R.id.date))
					.setText(sdf.format(new Date()));
			if (User.data > 0) {
				((TextView) findViewById(R.id.showLastValue)).setText(""
						+ User.data);
			}
			DBTools dbt = new DBTools();
			dbt.init(this);
			weekList = dbt.getTestDataWeek();
			tu.setData(weekList);
			((TextView) findViewById(R.id.normalTimes)).setText("正常"
					+ (User.afterNormalValueTimes + User.beforNormalValueTimes)
					+ "次");
			((TextView) findViewById(R.id.heighTimes)).setText("偏高"
					+ (User.afterHeighValueTimes + User.beforHeighValueTimes)
					+ "次");
			((TextView) findViewById(R.id.lowTimes))
					.setText("偏低"
							+ (User.afterLowValueTimes + User.beforLowValueTimes)
							+ "次");
			if (User.maxValue > 0) {
				((TextView) findViewById(R.id.max)).setText("" + User.maxValue);
			} else {
				((TextView) findViewById(R.id.max)).setText("");
			}
			if(User.mixValue>0){
			((TextView) findViewById(R.id.mix)).setText("" + User.mixValue);
			}else{
				((TextView) findViewById(R.id.mix)).setText("");
			}
			DecimalFormat decimalFormat = new DecimalFormat(".0");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
			if (User.beforAverage > 0) {
				((TextView) findViewById(R.id.beforAverage))
						.setText(decimalFormat.format(User.beforAverage));
			} else {
				((TextView) findViewById(R.id.beforAverage)).setText("");
			}
			if (User.afterAverage > 0) {
				((TextView) findViewById(R.id.afterAverage))
						.setText(decimalFormat.format(User.afterAverage));
			} else {
				((TextView) findViewById(R.id.afterAverage)).setText("");
			}
			a();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ArrayList<TextView> textViews;
	private ArrayList<View> views;
	private int H_width;
	private HorizontalScrollView horizontalScrollView;
	private LinearLayout linearLayout;
	int pointIndex;

	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/***
	 * init title
	 */
	void InItTitle() {
		textViews = new ArrayList<TextView>();
		views = new ArrayList<View>();
		H_width = getWindowManager().getDefaultDisplay().getWidth() / 5;
		int height = dip2px(this, 20);
		for (int i = 0; i < title.length; i++) {
			TextView textView = new TextView(this);
			textView.setText(title[i]);
			textView.setTextSize(15);
			textView.setTextColor(Color.parseColor("#ff2bc8db"));
			textView.setWidth(H_width);
			textView.setHeight(height);
			// Log.e("aa", "text_width=" + textView.getWidth());
			textView.setGravity(Gravity.CENTER);
			textView.setId(i);
			textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setSelector(v.getId());
					a();
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
			// Log.e("aa", "linearLayout_width=" + linearLayout.getWidth());

		}
		Calendar c = Calendar.getInstance();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 2;
		if (dayOfWeek < 0) {
			dayOfWeek = 6;
		}
		setSelector(dayOfWeek);
	}

	public void a() {

		int id = pointIndex;
		for (int i = 0; i < title.length; i++) {
			if (views.size() > i) {
				views.get(i).setVisibility(View.VISIBLE);
			}
			if (id == i) {
				// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				// R.drawable.select_bg);
				// textViews.get(id).setBackgroundDrawable(
				// new BitmapDrawable(bitmap));
				textViews.get(id).setBackgroundColor(Color.WHITE);
				textViews.get(id).setTextColor(Color.parseColor("#ff2bc8db"));
				if (i > 2) {
					int scrollto = H_width * i - 180;
					horizontalScrollView.smoothScrollTo(scrollto, 0);
				} else {
					horizontalScrollView.smoothScrollTo(0, 0);
				}
			} else {
				textViews.get(i).setBackgroundDrawable(new BitmapDrawable());
				textViews.get(i).setTextColor(Color.GRAY);
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
		for (int i = 0; i < 7; i++) {
			((TextView) findViewById(valuesId[i])).setText("");
		}
		for (int i = 0; i < weekList.get(pointIndex).size(); i++) {
			TestData _td = (TestData) weekList.get(pointIndex).get(i);
			if (_td != null) {
				((TextView) findViewById(valuesId[_td.timeId])).setText(""
						+ _td.bloodGlucoseValues);
			}
		}
	}

	/***
	 * 选中效果
	 */
	public void setSelector(int id) {
		pointIndex = id;
	}
}
