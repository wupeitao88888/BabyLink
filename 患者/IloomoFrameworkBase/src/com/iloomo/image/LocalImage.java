package com.iloomo.image;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.os.Environment;

public class LocalImage implements SmartImage{
	private static WebImageCache webImageCache; // 图片缓存
	private String url; // 下载地址

	public LocalImage(String url) {
		this.url = url;
	}

	public LocalImage(String url, boolean autoRotate) {
		this.url = url;
	}

	/**
	 * 获取图片
	 */
	public Bitmap getBitmap(Context context) {
		// Don't leak context
		if (webImageCache == null) {
			webImageCache = new WebImageCache(context);
		}

		// 首先从缓存中获得图片
		Bitmap bitmap = null;
		if (url != null) {
			bitmap = webImageCache.get(url);
			if (bitmap == null) {
				bitmap = getBitmapFromUrl(url);
				if (bitmap != null) {
					webImageCache.put(url, bitmap,null);
				}
			}
		}

		return bitmap;
	}

	/**
	 * 获取本地图片
	 * @param url 图片地址
	 * @return
	 */
	private Bitmap getBitmapFromUrl(String url) {
		 Bitmap bitmap = null;
		  if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
	            File file = new File(url);
	            if(file.exists()) {
	                try {
						bitmap = BitmapFactory.decodeFile(url);
						bitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
					} catch (Exception e) {
						e.printStackTrace();
					}
	            }
	        }
		return bitmap;
	}

	/**
	 * 清除缓存
	 * @param url 图片地址
	 */
	public static void removeFromCache(String url) {
		if (webImageCache != null) {
			webImageCache.remove(url);
		}
	}

}
