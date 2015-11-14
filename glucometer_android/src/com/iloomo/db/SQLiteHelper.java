package com.iloomo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author suzhigang
 *
 */
public class SQLiteHelper extends SQLiteOpenHelper{
	/**
	 * 
	 * @param TABENAME 数据表的名称       
	 * @param TABLEBODY 创建数据表的sql语句
	 */
	public static String TABLENAME ;
	public static String TABLEBODY ;
	
	
	/**
	 * 
	 * @param context 上下文环境
	 * @param DBNAME   要创建的数据库名称
	 * @param VERSION  要创建的数据库版本
	 * @param table_name  要创建的表的名称
	 * @param table_body  要创建的表的sql语句
	 */
	public SQLiteHelper(Context context,String DBNAME ,int VERSION,String table_name,String table_body) {
		super(context, DBNAME, null, VERSION);
		TABLENAME = table_name; 
		TABLEBODY = table_body;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println(TABLEBODY+"-----------------------------");
		db.execSQL(TABLEBODY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP table if exists " + TABLENAME );
		onCreate(db);
	}
	
}
