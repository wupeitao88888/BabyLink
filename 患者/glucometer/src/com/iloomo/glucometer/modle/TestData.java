package com.iloomo.glucometer.modle;


public class TestData {
	public static final float lowValue = 3.9f;// 低血糖标准值
	public static final float highValue = 5.8f;// 高血糖标准值
	public float bloodGlucoseValues;// 显示的血糖值
	public int timeId;// 0早餐前/空腹,1早餐后,2午餐前,3午餐后,4晚餐前,5晚餐后,6凌晨
	public byte[] fullData;// 血糖仪传过来的浓度值
	public String testDate;// 测试时候的日期
	public int id;
	public String timestamp;
	public String remark;
	public int flag=0;//是否已经同步 1表示已经和服务器同步 2 表示不同步
	public final static String TABLE_NAME = "t_test_data";
	public final static String TABLE_BODY = "create table t_test_data ( id integer primary key,bloodGlucoseValues float,testDate date,timeId integer,fullData BLOB,remark varchar(255),timestamp varchar(32),flag integer)";
	public final static String INSERT_BODY = "insert into t_test_data(id,bloodGlucoseValues,testDate,timeId,fullData,remark,timestamp,flag) values (?,?,?,?,?,?,?,?)";

	public TestData(int id, float bloodGlucoseValues, int timeId,
			byte[] fullData, String testDate) {
		this.id = id;
		this.bloodGlucoseValues = bloodGlucoseValues;
		this.timeId = timeId;
		this.fullData = fullData;
		this.testDate = testDate;
	}

	public TestData() {
		
	}
}
