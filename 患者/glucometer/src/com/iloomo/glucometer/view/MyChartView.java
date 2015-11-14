package com.iloomo.glucometer.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.iloomo.glucometer.modle.TestData;
import com.iloomo.glucometer.modle.User;

/**
 * 
 * @author zhangyayun1@gmail.com
 * 
 */
@SuppressLint("ViewConstructor")
public class MyChartView extends View {
	public static int RECT_SIZE = 2;
	private Point mSelectedPoint;
	String[] xTable = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
	static float[] before = new float[28];
	static float[] after = new float[28];
	
	// static float[] degrees = { 0.0f, 5.0f, 10.0f, 15.0f, 20.0f };
//	static float[] degrees = { 0, 1, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8, 9,
//			10, 11, 12, 13, 14, 15, 16 };
	static float[] degrees = { 2f, 2.5f, 3.0f, 3.5f, 4.0f, 4.5f, 5.0f, 5.5f, 6.0f, 6.5f,
		7f, 7.5f, 8f, 8.5f, 9f, 9.5f, 10.0f };
	static float unitValue = degrees[1]-degrees[0];
	int c1 = Color.parseColor("#2bc8db");
	int c2 = Color.parseColor("#00b189");

	static {
		for (int i = 0; i < before.length; i++) {
			before[i] = 9;
		}
	}

	static {
		before[0] = -1;
		before[10] = -1;
		before[18] = -1;
	}

	// 枚举实现坐标桌面的样式风格
	public static enum Mstyle {
		Line, Curve
	}

	private Mstyle mstyle = Mstyle.Line;
	private Point[] mPoints = new Point[100];

	Context context;
	int bheight = 0;
	// HashMap<Double, Double> map;
	// ArrayList<Double> dlk;
	String xstr, ystr = "";// 横纵坐标的属性
	int margint = 15;
	int marginb = 40;
	int c = 0;
	int resid = 0;
	Boolean isylineshow;

	/**
	 * @param map
	 *            需要的数据，虽然key是double，但是只用于排序和显示，与横向距离无关
	 * @param totalvalue
	 *            Y轴的最大值
	 * @param pjvalue
	 *            Y平均值
	 * @param xstr
	 *            X轴的单位
	 * @param ystr
	 *            Y轴的单位
	 * @param isylineshow
	 *            是否显示纵向网格
	 * @return
	 */
	public void SetTuView(HashMap<Double, Double> map, int totalvalue,
			int pjvalue, String xstr, String ystr, Boolean isylineshow) {
		// this.map = map;
		this.xstr = xstr;
		this.ystr = ystr;
		this.isylineshow = isylineshow;
		// 屏幕横向
		// act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	public MyChartView(Context ct) {
		super(ct);
		this.context = ct;
	}

	public MyChartView(Context ct, AttributeSet attrs) {
		super(ct, attrs);
		this.context = ct;
	}

	public MyChartView(Context ct, AttributeSet attrs, int defStyle) {
		super(ct, attrs, defStyle);
		this.context = ct;
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		if (c != 0)
			this.setbg(c);
		if (resid != 0)
			this.setBackgroundResource(resid);
		int height = getHeight();
		if (bheight == 0)
			bheight = height - marginb - margint;

		int width = getWidth();
		int blwidh = dip2px(context, 30);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.GRAY);
		paint.setStrokeWidth(1);
		paint.setStyle(Style.STROKE);
		int spaceY = bheight / degrees.length;
		float _hspace =( bheight / degrees.length)/unitValue;
		paint.setStyle(Style.FILL);
		paint.setColor(Color.parseColor("#eeeeee"));
		canvas.drawRect(blwidh, bheight - _hspace * (User.afterValue[1]-degrees[0])
				+ margint, width, bheight - _hspace * (User.afterValue[0]-degrees[0])
				+ margint, paint);
		paint.setColor(Color.parseColor("#dddddd"));
		canvas.drawRect(blwidh, bheight - _hspace * (User.beforValue[1]-degrees[0])
				+ margint, width, bheight - _hspace * (User.beforValue[0]-degrees[0])
				+ margint, paint);
		// yyyyyyyyyyy坐标
		for (int i = 0; i < degrees.length; i++) {
			paint.setColor(Color.GRAY);
			canvas.drawLine(blwidh, bheight - (spaceY) * i + margint, width,
					bheight - (spaceY) * i + margint, paint);// Y坐标
			if (i % 2 == 0) {
				drawString("" + degrees[i], blwidh / 2, bheight - (spaceY) * i
						+ margint, canvas);
			}
		}

		ArrayList<Integer> xlist = new ArrayList<Integer>();// 记录每个x的值
		// 画直线（纵向）
		paint.setColor(Color.GRAY);
		for (int i = 0; i < xTable.length; i++) {
			drawString(xTable[i],
					blwidh + (width - blwidh) / xTable.length * i, bheight
							+ marginb + margint - 10, canvas);// X坐标
			canvas.drawLine(blwidh + (width - blwidh) / xTable.length * i, 0,
					blwidh + (width - blwidh) / xTable.length * i, bheight
							+ margint, paint);
		}

		drawPoints(before, width, blwidh, paint, canvas, c1, c1);
		drawPoints(after, width, blwidh, paint, canvas, c2, c2);
	}

	public void drawPoints(float[] target, int width, int blwidh, Paint paint,
			Canvas canvas, int color, int color2) {
		mPoints = getNewPoints(target, width - blwidh, bheight, blwidh, bheight
				+ margint);
		paint.setColor(color);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(0);

		if (mstyle == Mstyle.Curve)
			drawscrollline(mPoints, canvas, paint);
		else
			drawline(mPoints, canvas, paint);

		paint.setColor(color2);
		paint.setStyle(Style.FILL);
		for (int i = 0; i < mPoints.length; i++) {
			if (mPoints[i] == null) {
				return;
			}
			// canvas.drawRect(pointToRect(mPoints[i]), paint);
			int r = dip2px(context, RECT_SIZE);
			canvas.drawCircle(mPoints[i].x, mPoints[i].y, r, paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			for (int i = 0; i < mPoints.length; i++) {
				// if (pointToRect(mPoints[i])
				// .contains(event.getX(), event.getY())) {
				// System.out.println("-yes-" + i);
				// mSelectedPoint = mPoints[i];
				// }
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (null != mSelectedPoint) {
				// mSelectedPoint.x = (int) event.getX();
				mSelectedPoint.y = (int) event.getY();
				// invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			mSelectedPoint = null;
			break;
		default:
			break;
		}
		return true;

	}

	private RectF pointToRect(Point p) {
		return new RectF(p.x - RECT_SIZE / 2, p.y - RECT_SIZE / 2, p.x
				+ RECT_SIZE / 2, p.y + RECT_SIZE / 2);
	}

	private void drawscrollline(Point[] ps, Canvas canvas, Paint paint) {
		Point startp = new Point();
		Point endp = new Point();
		for (int i = 0; i < ps.length - 1; i++) {
			startp = ps[i];
			endp = ps[i + 1];
			int wt = (startp.x + endp.x) / 2;
			Point p3 = new Point();
			Point p4 = new Point();
			p3.y = startp.y;
			p3.x = wt;
			p4.y = endp.y;
			p4.x = wt;

			Path path = new Path();
			path.moveTo(startp.x, startp.y);
			path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
			canvas.drawPath(path, paint);

		}
	}

	private void drawline(Point[] ps, Canvas canvas, Paint paint) {
		Point startp = new Point();
		Point endp = new Point();
		for (int i = 0; i < ps.length - 1; i++) {
			startp = ps[i];
			endp = ps[i + 1];
			if (endp != null) {
				canvas.drawLine(startp.x, startp.y, endp.x, endp.y, paint);
			} else {
				break;
			}
		}
	}

	private Point[] getpoints(ArrayList<Double> dlk,
			HashMap<Double, Double> map, ArrayList<Integer> xlist, int max,
			int h) {
		Point[] points = new Point[dlk.size()];
		for (int i = 0; i < dlk.size(); i++) {
			int ph = h - (int) (h * (map.get(dlk.get(i)) / max));

			points[i] = new Point(xlist.get(i), ph + margint);
		}
		return points;
	}

	private Point[] getNewPoints(float[] target, int w, int h, int left, int top) {
		Point[] points = new Point[target.length];
		int _wspace = w / target.length;
		float _hspace = (h / degrees.length)/unitValue;
		int k = 0;
		int r = dip2px(context, RECT_SIZE);
		for (int i = 0; i < target.length; i++) {
			if (target[i] >= 0) {
				points[k++] = new Point(left + _wspace * i + 2 * r,
						(int) (top - (target[i]-degrees[0]) * _hspace));
			}
		}
		return points;
	}

	private void drawString(String text, int x, int y, Canvas canvas) {
		Paint p = new Paint();
		p.setAlpha(0x0000ff);
		p.setTextSize(dip2px(context, 10));
		String familyName = "sans_serif";
		Typeface font = Typeface.create(familyName, Typeface.BOLD);
		p.setTypeface(font);
		p.setTextAlign(Paint.Align.CENTER);
		p.setAntiAlias(true);
		canvas.drawText(text, x, y, p);

	}

	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<Double> getintfrommap(HashMap<Double, Double> map) {
		ArrayList<Double> dlk = new ArrayList<Double>();
		int position = 0;
		if (map == null)
			return null;
		Set set = map.entrySet();
		Iterator iterator = set.iterator();

		while (iterator.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry mapentry = (Map.Entry) iterator.next();
			dlk.add((Double) mapentry.getKey());
		}
		for (int i = 0; i < dlk.size(); i++) {
			int j = i + 1;
			position = i;
			Double temp = dlk.get(i);
			for (; j < dlk.size(); j++) {
				if (dlk.get(j) < temp) {
					temp = dlk.get(j);
					position = j;
				}
			}

			dlk.set(position, dlk.get(i));
			dlk.set(i, temp);
		}
		return dlk;

	}

	public void setbg(int c) {
		this.setBackgroundColor(c);
	}

	public String getXstr() {
		return xstr;
	}

	public void setXstr(String xstr) {
		this.xstr = xstr;
	}

	public String getYstr() {
		return ystr;
	}

	public void setYstr(String ystr) {
		this.ystr = ystr;
	}

	public int getMargint() {
		return margint;
	}

	public void setMargint(int margint) {
		this.margint = margint;
	}

	public Boolean getIsylineshow() {
		return isylineshow;
	}

	public void setIsylineshow(Boolean isylineshow) {
		this.isylineshow = isylineshow;
	}

	public int getMarginb() {
		return marginb;
	}

	public void setMarginb(int marginb) {
		this.marginb =dip2px(context, marginb);
	}

	public Mstyle getMstyle() {
		return mstyle;
	}

	public void setMstyle(Mstyle mstyle) {
		this.mstyle = mstyle;
	}

	public int getBheight() {
		return bheight;
	}

	public void setBheight(int bheight) {
		this.bheight = dip2px(context, bheight);
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getResid() {
		return resid;
	}

	public void setResid(int resid) {
		this.resid = resid;
	}

	public void setData(ArrayList<ArrayList> weekList) {
		for (int i = 0; i < before.length; i++) {
			before[i] = -1;
			after[i] = -1;
		}
		User.beforNormalValueTimes = 0;
		User.afterNormalValueTimes = 0;
		User.afterNormalValueTimes = User.beforHeighValueTimes = User.afterHeighValueTimes = User.beforLowValueTimes = User.afterLowValueTimes = 0;
		float afterTotalValue = 0, beforTotalValue = 0;
		if (weekList != null && weekList.size() > 0) {
			for (int i = 0; i < weekList.size(); i++) {
				ArrayList<TestData> dataList = weekList.get(i);
				if (dataList != null && dataList.size() > 0) {
					for (int j = 0; j < dataList.size(); j++) {
						TestData td = dataList.get(j);
						if (td != null) {
							if (td.bloodGlucoseValues > User.maxValue) {
								User.maxValue = td.bloodGlucoseValues;
							}
							if (User.mixValue == 0) {
								User.mixValue = td.bloodGlucoseValues;
							}
							if (td.bloodGlucoseValues < User.mixValue) {
								User.mixValue = td.bloodGlucoseValues;
							}

							if (td.timeId % 2 == 0) {
								before[4 * i + td.timeId / 2] = td.bloodGlucoseValues;
								beforTotalValue += td.bloodGlucoseValues;
								if (td.bloodGlucoseValues >= User.beforValue[0]
										&& td.bloodGlucoseValues <= User.beforValue[1]) {
									User.beforNormalValueTimes++;
								} else if (td.bloodGlucoseValues > User.beforValue[1]) {
									User.beforHeighValueTimes++;
								} else if (td.bloodGlucoseValues < User.beforValue[0]) {
									User.beforLowValueTimes++;
								}

							} else {
								after[4 * i + td.timeId / 2] = td.bloodGlucoseValues;
								afterTotalValue += td.bloodGlucoseValues;
								if (td.bloodGlucoseValues >= User.afterValue[0]
										&& td.bloodGlucoseValues <= User.afterValue[1]) {
									User.afterNormalValueTimes++;
								} else if (td.bloodGlucoseValues > User.afterValue[1]) {
									User.afterHeighValueTimes++;
								} else if (td.bloodGlucoseValues < User.afterValue[0]) {
									User.afterLowValueTimes++;
								}

							}
						}
					}

				}
			}
		}
		User.afterAverage = afterTotalValue/(float)(User.afterNormalValueTimes+User.afterHeighValueTimes+User.afterLowValueTimes);
		User.beforAverage = beforTotalValue/(float)(User.beforNormalValueTimes+User.beforHeighValueTimes+User.beforLowValueTimes);
		invalidate();
	}
}
