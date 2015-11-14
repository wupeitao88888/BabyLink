package com.iloomo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * author:suzhigang
 * 
 */

public class SQLHandle {
	
	private SQLiteHelper helper;
	private SQLiteDatabase sda;
	
	
	//得到写数据库对象
	public void open(){
		try {
			sda = helper.getWritableDatabase();
		} catch (SQLiteException e) {
			sda = helper.getReadableDatabase();
		}
	}
	
	//关闭写数据库对象
	public void close(){
		if(sda != null){
			sda.close();
			sda = null ;
		}
	}
	
	/**
	 * @param SQLHandle 初始化
	 * @param context 上下文环境
	 * @param DBNAME   要创建的数据库名称
	 * @param VERSION  要创建的数据库版本
	 * @param table_name  要创建的表的名称
	 * @param table_body  要创建的表的sql语句
	 */
	public SQLHandle(Context context,String DBNAME,int VERSION,String table_name,String table_body){
		helper = new SQLiteHelper(context,DBNAME,VERSION,table_name,table_body);
	}
	
	/**
	 * 
	 * @param sql 执行插入操作的sql语句
	 * @param params  将插入的字段封装到一个Object【】中
	 * @return  成功返回      1
	 */
	public int insert(String sql,Object[] params) {
		open();
		try {
			sda.execSQL(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return e.hashCode();
		}finally{
			close();
		}
		return 1;
	}
	
	/**
	 * 
	 * @param sql 执行更新操作的sql语句
	 * @param params  将插入的字段封装到一个Object【】中
	 * @return  成功返回      1
	 */
	public int update(String sql,Object[] params) {
		open();
		try {
			sda.execSQL(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return e.hashCode();
		}finally{
			close();
		}
		return 1;
	}
	
	/**
	 * 
	 * @param sql 执行按条件查询操作的sql语句
	 * @param types  将插入的字段封装到一个String【】中
	 * @return  返回一个游标对象
	 */
	public Cursor findQuery(String sql ,String[] types) {
		open();
		Cursor cursor = sda.rawQuery(sql, types);
		return cursor ;
	}
	/**
	 * 
	 * @param sql 执行查询操作的sql语句
	 * @return  返回一个游标对象
	 */
	public Cursor findQuery(String sql) {
		open();
		Cursor cursor = sda.rawQuery(sql, null);
		return cursor ;
	}
	
	/**
	 * 
	 * @param sql 执行删除操作的sql语句
	 */
	public void deleteAll(String sql) {
		open();
		sda.execSQL(sql);
		close();
	}
	
	/**
	 * 
	 * @param sql 执行按条件删除操作的sql语句
	 * @param id  传入要删除的id
	 * @return  成功返回1
	 */
	public int deleteOne(String sql,int id) {
		open();
		Object[] params = {id};
		try {
			sda.execSQL(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return e.hashCode();
		}finally{
			close();
		}
		return 1;
	}
	
	/**
	 * 
	 * @param sql 查询语句sql
	 * @return  返回查询记录条数
	 */
	public int rowCount(String sql) {
		open();
		Cursor cursor = findQuery(sql);
		return cursor.getCount();
	}
}
