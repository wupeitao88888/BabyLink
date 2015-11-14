package com.iloomo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.os.Environment;

import com.iloomo.base.BaseApplication;

/**
 * 创 建 人: DavikChen 日 期： 2012-11-30 上午9:12:34 修 改 人： 日 期： 描 述： 页面缓存工具类 版 本 号：
 */
public class CacheUtils {
	private static final String DISK_CACHE_PATH = "/" + BaseApplication.mAppName + "/pageCache/"; // 图片缓存地址
	private static final String DATA_CACHE_PATH = "/pageCache/"; //
	private static String diskCachePath; // 缓存目录

	/**
	 * 
	 * 功能描述：     初始化路径
	 * 创 建 人: DavikChen
	 * 日    期：  2012-11-30  上午9:59:49
	 * 修 改 人: 
	 * 日    期:
	 */
	private static void initDiskCache(Context mContext) {
		// Set up disk cache store
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			diskCachePath = Environment.getExternalStorageDirectory().getPath() + DISK_CACHE_PATH;
		} else {
			Context appContext = mContext.getApplicationContext();
			diskCachePath = appContext.getCacheDir().getAbsolutePath() + DATA_CACHE_PATH;
		}

		File outFile = new File(diskCachePath);
		outFile.mkdirs();
	}

	/**
	 * 功能描述： 缓存数据到本地
	 * @param mContext 上下文
	 * @param mObject 数据 创 建 人: DavikChen 日 期： 2012-11-30 上午9:16:33 修 改 人: 日 期:
	 */
	public static void cacheDataToDisk(Context mContext, Object mObject) {
		
		initDiskCache(mContext);
		FileOutputStream mFileOutputStream = null;
		try {
			String className = mContext.getClass().getSimpleName();
			mFileOutputStream = new FileOutputStream(new File(diskCachePath + className));
			mFileOutputStream.write(mObject.toString().getBytes());
			mFileOutputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (mFileOutputStream != null) {
				try {
					mFileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * 功能描述： 读取数据
	 *  @param mContext   上下文
	 * 创 建 人: DavikChen
	 * 日    期：  2012-11-30  上午9:35:49
	 * 修 改 人: 
	 * 日    期:
	 */
	public static Object readCacheDataFromDisk(Context mContext){
		initDiskCache(mContext);
		String data = null;
		try {
			FileInputStream mFileInputStream = null;
			data = null;
			String className = mContext.getClass().getSimpleName();
			File file = new File(diskCachePath + className);
			if (file.exists()) {
				mFileInputStream = new FileInputStream(file);
				data = FileUtils.readInStream(mFileInputStream);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return parseResponse(data);
	}
	
	
	/**
	 * 
	 * 功能描述： 缓存数据到本地
	 *  @param mContext 上下文
	 *  @param mObject 数据
	 *  @param unique  唯一id
	 * 创 建 人: DavikChen
	 * 日    期：  2012-11-30  上午9:33:50
	 * 修 改 人: 
	 * 日    期:
	 */
	public static void cacheDataToDisk(Context mContext, Object mObject,String unique){
		initDiskCache(mContext);
		FileOutputStream mFileOutputStream = null;
		try {
			String className = mContext.getClass().getSimpleName();
			mFileOutputStream = new FileOutputStream(new File(diskCachePath + className+unique));
			mFileOutputStream.write(mObject.toString().getBytes());
			mFileOutputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (mFileOutputStream != null) {
				try {
					mFileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * 功能描述： 读取数据
	 *  @param mContext   上下文
	 * 创 建 人: DavikChen
	 * 日    期：  2012-11-30  上午9:35:49
	 * 修 改 人: 
	 * 日    期:
	 */
	public static Object readCacheDataFromDisk(Context mContext,String uniqu){
		initDiskCache(mContext);
		String data = null;
		try {
			FileInputStream mFileInputStream = null;
			data = null;
			String className = mContext.getClass().getSimpleName();
			File file = new File(diskCachePath + className+uniqu);
			if (file.exists()) {
				mFileInputStream = new FileInputStream(file);
				data = FileUtils.readInStream(mFileInputStream);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return parseResponse(data);
	}
	
	/**
	 * 
	 * 功能描述： json 对象转换
	 *  @param responseBody 字符内容
	 *  @return    
	 * 创 建 人: DavikChen
	 * 日    期：  2012-11-30  上午10:58:20
	 * 修 改 人: 
	 * 日    期:
	 */
	 private static  Object parseResponse(String responseBody) {
	    	if (responseBody !=null) {
	    		try {
					return new JSONTokener(responseBody).nextValue();
				} catch (JSONException e) {
					return "";
				}
			}else {
				return "";
			}
				
	        
	    }
}
