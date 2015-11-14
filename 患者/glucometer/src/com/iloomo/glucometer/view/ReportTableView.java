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
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.iloomo.glucometer.DBTools;
import com.iloomo.glucometer.modle.TestData;

/**
 * 
 * @author zhangyayun1@gmail.com
 * 
 */
@SuppressLint({ "ViewConstructor", "NewApi" })
public class ReportTableView extends LinearLayout {
	final int gridSize = 8;
	private Point mSelectedPoint;
	int gridHeight;
	int gridWidth;
	int textSize;
	int bgColor = Color.parseColor("#c9c9c9");
	int gridColor1 = Color.parseColor("#f8f8f8");
	int gridColor2 = Color.parseColor("#ececec");
	int gridColor3 = Color.parseColor("#ffffff");

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
	int totalvalue = 20;
	int pjvalue = 5;
	String xstr, ystr = "";// 横纵坐标的属性
	int margint = 15;
	int marginb = 40;
	int c = 0;
	int resid = 0;
	Boolean isylineshow;
	int days;
	static float[][] values = null;

	public void initData(int year, int month) {
		DBTools dbt = new DBTools();
		dbt.init(context);
		ArrayList<ArrayList> monthData = dbt.getMonthTestData(year, month);
		if (monthData != null && monthData.size() > 0) {
			days = monthData.size();
			values = new float[days][gridSize - 1];

			for (int i = 0; i < monthData.size(); i++) {
				ArrayList<TestData> dataList = monthData.get(i);

				if (dataList != null && dataList.size() > 0) {
					int dataIndex = 0;
					for (int j = 0; j < values[i].length; j++) {
						if (dataIndex < dataList.size()) {
							TestData td = dataList.get(dataIndex);
							if (j == td.timeId) {
								dataIndex++;
								values[i][j] = td.bloodGlucoseValues;
							} else {
								continue;
							}
						}
					}

				}
			}
		}
		invalidate();
	}

	public ReportTableView(Context ct) {
		super(ct);
		this.context = ct;
	}

	public ReportTableView(Context ct, AttributeSet attrs) {
		super(ct, attrs);
		this.context = ct;
	}

	public ReportTableView(Context ct, AttributeSet attrs, int defStyle) {
		super(ct, attrs, defStyle);
		this.context = ct;
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (gridHeight == 0) {
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this
					.getLayoutParams();
			int w = this.getWidth();
			gridHeight = w / 10;
			layoutParams.height = gridHeight * days;
			gridWidth = w / gridSize;
			textSize = dip2px(this.context, 15);
			this.getParent().requestLayout();
		}

		if (c != 0)
			this.setbg(c);
		if (resid != 0)
			this.setBackgroundResource(resid);

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

		// 竖线
		for (int i = 0; i < gridSize; i++) {
			if (i == 0 || i == 3 || i == 4 || i == 7) {
				paint.setColor(gridColor3);
			} else {
				paint.setColor(gridColor1);
			}

			paint.setStyle(Style.FILL);
			canvas.drawRect(gridWidth * i, 0, gridWidth * (i + 1), getHeight(),
					paint);
			if (i > 0) {
				paint.setColor(bgColor);
				paint.setStrokeWidth(1);
				if (i == 5 || i == 6) {
					canvas.drawLine(gridWidth * i + 1, 0, gridWidth * i + 1,
							getHeight(), paint);
				} else {
					canvas.drawLine(gridWidth * i, 0, gridWidth * i,
							getHeight(), paint);
				}
			}

		}
		// 划横线
		for (int i = 1; i <= days; i++) {
			paint.setColor(bgColor);
			canvas.drawLine(0, gridHeight * i, getWidth(), gridHeight * i,
					paint);

			drawString("" + i, 0 + gridWidth / 2, gridHeight * (i - 1)
					+ gridHeight / 2, canvas);
			// canvas.drawRect(gridWidth*i, 0, gridWidth*i+blwidh, getHeight(),
			// paint);
		}
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				for (int j = 0; j < values[i].length; j++) {
					if (values[i][j] > 0) {
						drawString("" + values[i][j], gridWidth + gridWidth * j
								+ gridWidth / 2, gridHeight * i + gridHeight
								/ 2, canvas);
					}
				}
			}
		}
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
		int _hspace = h / 20;
		int k = 0;
		for (int i = 0; i < target.length; i++) {
			if (target[i] >= 0) {
				points[k++] = new Point(left + _wspace * i,
						(int) (top - target[i] * _hspace));
			}

		}
		return points;
	}

	private void drawString(String text, int x, int y, Canvas canvas) {

		Paint p = new Paint();
		p.setAlpha(0x0000ff);
		p.setColor(Color.BLACK);
		p.setTextAlign(Paint.Align.CENTER);
		p.setTextSize(textSize);
		String familyName = "sans_serif";
		// Typeface font = Typeface.create(familyName, Typeface.BOLD);
		// p.setTypeface(font);
		p.setAntiAlias(true);
		canvas.drawText(text, x, y + textSize / 2, p);

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

	public int getTotalvalue() {
		return totalvalue;
	}

	public void setTotalvalue(int totalvalue) {
		this.totalvalue = totalvalue;
	}

	public int getPjvalue() {
		return pjvalue;
	}

	public void setPjvalue(int pjvalue) {
		this.pjvalue = pjvalue;
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
		this.marginb = marginb;
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
		this.bheight = bheight;
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
}
