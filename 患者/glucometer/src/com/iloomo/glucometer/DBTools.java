package com.iloomo.glucometer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;

import com.iloomo.db.SQLHandle;
import com.iloomo.glucometer.modle.TestData;
import com.iloomo.util.ILog;

/**
 * @author yanghailong 管理所有的数据库操作的工具类
 */
public class DBTools {
	private static String DBNAME = "iloomo_glucometer.db";
	private static int VERSION = 6;
	private static SQLHandle sqlHandle;

	public void init(Context context) {
		sqlHandle = new SQLHandle(context, DBNAME, VERSION,
				TestData.TABLE_NAME, TestData.TABLE_BODY);
	}

	public int insertTestData(TestData pTestData) {
		Object[] params = { null, pTestData.bloodGlucoseValues,
				pTestData.testDate, pTestData.timeId, pTestData.fullData,
				pTestData.remark, pTestData.timestamp ,pTestData.flag};

		String sql = "select id from  " + TestData.TABLE_NAME
				+ " where testDate='" + pTestData.testDate + "' and"
				+ " timeId='" + pTestData.timeId + "' order by timestamp";
		sqlHandle.open();
		Cursor cursor = sqlHandle.findQuery(sql);
		int id = -1;
		while (cursor.moveToNext()) {
			id = cursor.getInt(cursor.getColumnIndex("id"));
		}
		int count = 0;
		if (id > 0) {
			Object[] params1 = { pTestData.bloodGlucoseValues,
					pTestData.testDate, pTestData.fullData, pTestData.remark,
					pTestData.timestamp,pTestData.flag, id };
			String update = "update  t_test_data set bloodGlucoseValues=?,testDate=?,fullData=?,remark=?,timestamp=?,flag=? where id=?";
			count = sqlHandle.update(update, params1);
		} else {
			count = sqlHandle.insert(TestData.INSERT_BODY, params);
		}

		if (count == 1) {
			ILog.d("iloomo", "insertTestData 成功 ");
		} else {
			ILog.d("iloomo", "insertTestData 失败 ");
		}
		sqlHandle.close();
		cursor.close();
		return count;
	}

	List<TestData> getTodayTestData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		return getOneDayTestData(date);
		// String sql = "select * from  " + TestData.TABLE_NAME
		// + " where testDate='" + date + "' order by timestamp";
		// String sql1 = "select * from  " + TestData.TABLE_NAME;
		// Cursor cursor = sqlHandle.findQuery(sql);
		// List<TestData> list = new ArrayList<TestData>();
		// TestData _testData;
		// while (cursor.moveToNext()) {
		// int a1 = cursor.getColumnIndex("id");
		// int a2 = cursor.getColumnIndex("bloodGlucoseValues");
		// int a3 = cursor.getColumnIndex("timeId");
		// int a4 = cursor.getColumnIndex("fullData");
		// int a5 = cursor.getColumnIndex("testDate");// datetime(,'localtime')
		// _testData = new TestData(cursor.getInt(a1), cursor.getFloat(a2),
		// cursor.getInt(a3), cursor.getBlob(a4), cursor.getString(a5));
		// list.add(_testData);
		// }
		// return list;
	}

	// List<TestData> getWeekTestData() {
	//
	// SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
	// Calendar ca = Calendar.getInstance(Locale.CHINA);
	// // 当前时间，貌似多余，其实是为了所有可能的系统一致
	// ca.setTimeInMillis(System.currentTimeMillis());
	// System.out.println("当前时间:" + sdf1.format(ca.getTime()));
	// ca.setFirstDayOfWeek(Calendar.MONDAY);
	// ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	// String date = sdf1.format(ca.getTime());
	//
	// ca.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	// // 增加一个星期，才是我们中国人理解的本周日的日期
	//
	// String date2 = sdf1.format(ca.getTime());
	// ;
	//
	// String sql = "select * from  " + TestData.TABLE_NAME
	// + " where testDate>='" + date + "' and" + " testDate<='"
	// + date2 + "' order by timestamp";
	// String sql1 = "select * from  " + TestData.TABLE_NAME;
	// Cursor cursor = sqlHandle.findQuery(sql);
	// ArrayList<TestData> list = new ArrayList<TestData>();
	// ArrayList<ArrayList> week = new ArrayList<ArrayList>();
	// TestData _testData;
	// while (cursor.moveToNext()) {
	// int a1 = cursor.getColumnIndex("id");
	// int a2 = cursor.getColumnIndex("bloodGlucoseValues");
	// int a3 = cursor.getColumnIndex("timeId");
	// int a4 = cursor.getColumnIndex("fullData");
	// int a5 = cursor.getColumnIndex("testDate");// datetime(,'localtime')
	// _testData = new TestData(cursor.getInt(a1), cursor.getFloat(a2),
	// cursor.getInt(a3), cursor.getBlob(a4), cursor.getString(a5));
	// list.add(_testData);
	// }
	// return list;
	// }

	public ArrayList<ArrayList> getTestDataWeek() {
		ArrayList<ArrayList> week = new ArrayList<ArrayList>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

		Calendar mondayDate = getCurrentMonday();
		mondayDate.setFirstDayOfWeek(Calendar.MONDAY);
		for (int i = 0; i < 7; i++) {
			mondayDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY + i);
			String date = sdf1.format(mondayDate.getTime());
			week.add(getOneDayTestData(date));

		}
		//
		// Calendar ca = Calendar.getInstance(Locale.CHINA);
		// ca.setFirstDayOfWeek(Calendar.MONDAY);
		// int day = ca.get(ca.DAY_OF_WEEK);
		//
		// if (day == Calendar.SUNDAY) {
		// ca.add(Calendar.WEEK_OF_MONTH, 0);
		// ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// for (int i = 0; i < 7; i++) {
		// String date = sdf1.format(ca.getTime());
		// week.add(getOneDayTestData(date));
		// ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY + i);
		// }
		// } else {
		// ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// for (int i = 0; i < 7; i++) {
		// String date = sdf1.format(ca.getTime());
		// week.add(getOneDayTestData(date));
		// ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY + i);
		// }
		// }
		return week;
	}

	public Calendar getCurrentMonday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(monday);
		return c;
	}

	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	public ArrayList<ArrayList> getMonthTestData(int year, int month) {
		ArrayList<ArrayList> monthData = new ArrayList<ArrayList>();
		Date first = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		try {
			first = sdf.parse("" + year + month);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(year, month - 1, 1);
		calendar.roll(Calendar.DATE, false);
		int days = calendar.get(Calendar.DATE);
		for (int i = 1; i <= days; i++) {
			String date = sdf1.format(first.getTime());
			first.setDate(first.getDate() + 1);
			monthData.add(getOneDayTestData(date));
		}
		return monthData;
	}

	public ArrayList<TestData> getOneDayTestData(String date) {
		String sql = "select * from  " + TestData.TABLE_NAME
				+ " where testDate='" + date + "' order by timeId";
		String sql1 = "select * from  " + TestData.TABLE_NAME;
		sqlHandle.open();
		Cursor cursor = sqlHandle.findQuery(sql);
		ArrayList<TestData> list = new ArrayList<TestData>();
		TestData _testData;
		while (cursor.moveToNext()) {
			int a1 = cursor.getColumnIndex("id");
			int a2 = cursor.getColumnIndex("bloodGlucoseValues");
			int a3 = cursor.getColumnIndex("timeId");
			int a4 = cursor.getColumnIndex("fullData");
			int a5 = cursor.getColumnIndex("testDate");// datetime(,'localtime')
			int a6 =cursor.getColumnIndex("flag");
			_testData = new TestData(cursor.getInt(a1), cursor.getFloat(a2),
					cursor.getInt(a3), cursor.getBlob(a4), cursor.getString(a5));
			_testData.flag = cursor.getInt(a6);
			list.add(_testData);
		}
		cursor.close();
		sqlHandle.close();
		return list;
	}
	public ArrayList<TestData> getNeedSync() {
		String sql = "select * from  " + TestData.TABLE_NAME
				+ " where flag='0'";
		sqlHandle.open();
		Cursor cursor = sqlHandle.findQuery(sql);
		ArrayList<TestData> list = new ArrayList<TestData>();
		TestData _testData;
		while (cursor.moveToNext()) {
			int a1 = cursor.getColumnIndex("id");
			int a2 = cursor.getColumnIndex("bloodGlucoseValues");
			int a3 = cursor.getColumnIndex("timeId");
			int a4 = cursor.getColumnIndex("fullData");
			int a5 = cursor.getColumnIndex("testDate");// datetime(,'localtime')
			int a6 = cursor.getColumnIndex("timestamp");
			_testData = new TestData(cursor.getInt(a1), cursor.getFloat(a2),
					cursor.getInt(a3), cursor.getBlob(a4), cursor.getString(a5));
			_testData.timestamp = cursor.getString(a6);
			list.add(_testData);
		}
		sqlHandle.close();
		cursor.close();
		return list;
	}
	public boolean isEmpty() {
		String sql = "select * from  " + TestData.TABLE_NAME;
		Cursor cursor = sqlHandle.findQuery(sql);
		if(cursor.getCount()>0){
			return false;
		}else{
			return true;
		}
	}
	public void deleteTestData(){
		sqlHandle.open();
		sqlHandle.deleteAll("delete  from  " + TestData.TABLE_NAME );
		sqlHandle.close();
	}
}
