package com.iloomo.image;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;

import com.iloomo.base.BaseApplication;

public class WebImageCache {
	private static final String DISK_CACHE_PATH = "/" + BaseApplication.mAppName + "/image/"; // 图片缓存地址
	private static final String DATA_CACHE_PATH = "/webimage_cache/"; // 
	private ConcurrentHashMap<String, SoftReference<Bitmap>> memoryCache; // 内容图片集合
	private String diskCachePath; // 缓存目录
	private boolean diskCacheEnabled = false; // 缓存目录是否存在
	private ExecutorService writeThread; //

	public WebImageCache(Context context) {
		// Set up in-memory cache store
		memoryCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>();

		// Set up disk cache store
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			diskCachePath = Environment.getExternalStorageDirectory().getPath() + DISK_CACHE_PATH;
		} else { 
			Context appContext = context.getApplicationContext();
			diskCachePath = appContext.getCacheDir().getAbsolutePath() + DATA_CACHE_PATH;
		}

		File outFile = new File(diskCachePath);
		outFile.mkdirs();
		diskCacheEnabled = outFile.exists();
		// Set up threadpool for image fetching tasks
		writeThread = Executors.newSingleThreadExecutor();
	}

	/**
	 * 获取图片
	 * @param url 图片地址
	 * @return bitmap 对象
	 */
	public Bitmap get(final String url) {
		Bitmap bitmap = null;
		// Check for image in memory
		if (bitmap == null) {
			bitmap = getBitmapFromMemory(url);
		}
		// Check for image on disk cache
		if (bitmap == null) {
			bitmap = getBitmapFromDisk(url);
			// Write bitmap back into memory cache
			if (bitmap != null) {
				cacheBitmapToMemory(url, bitmap);
			}
		}

		return bitmap;
	}
	
	/**
	 * 获取图片
	 * @param url 图片地址
	 * @return bitmap 对象
	 */
	public Bitmap get(final String url,boolean isRound) {
		Bitmap bitmap = null;
		// Check for image in memory
		if (bitmap == null) {
			bitmap = getBitmapFromMemory(url);
		}
		// Check for image on disk cache
		if (bitmap == null) {
			bitmap = getBitmapFromDisk(url);
			// Write bitmap back into memory cache
			if (bitmap != null) {
				cacheBitmapToMemory(url, bitmap);
			}
		}

		return bitmap;
	}

	
	/**
	 * 保存图片
	 * @param url 图片地址
	 * @param bitmap 图片
	 */
	public void put(String url, Bitmap bitmap,byte[] data) {
		if (bitmap!=null) {
			cacheBitmapToMemory(url, bitmap); // 内存
		}
		if (data!=null) {
			cacheBitmapToDisk(url, data); // SDCard
		}
		
	}

	/**
	 * 删除图片
	 * @param url 图片地址
	 */
	public void remove(String url) {
		if (url == null) {
			return;
		}

		// Remove from memory cache
		memoryCache.remove(getCacheKey(url));

		// Remove from file cache
		File f = new File(diskCachePath, url);
		if (f.exists() && f.isFile()) {
			f.delete();
		}
	}

	/**
	 * 清除缓存
	 */
	public void clear() {
		// Remove everything from memory cache
		memoryCache.clear();

		// Remove everything from file cache
		File cachedFileDir = new File(diskCachePath);
		if (cachedFileDir.exists() && cachedFileDir.isDirectory()) {
			File[] cachedFiles = cachedFileDir.listFiles();
			for (File f : cachedFiles) {
				if (f.exists() && f.isFile()) {
					f.delete();
				}
			}
		}
	}
	
	/**
	 * 清楚常驻内存
	 */
	public void clearHoldMoemory(){
	}

	/**
	 * 缓存图片到内存
	 * @param url
	 * @param bitmap
	 */
	public void cacheBitmapToMemory(final String url, final Bitmap bitmap) {
		memoryCache.put(getCacheKey(url), new SoftReference<Bitmap>(bitmap));
	}

	/**
	 * 缓存图片到SCDard
	 * @param url
	 * @param bitmap
	 */
	public void cacheBitmapToDisk(final String url, final Bitmap bitmap) {
		writeThread.execute(new Runnable() {
			@Override
			public void run() {
				if (diskCacheEnabled) {
					BufferedOutputStream ostream = null;
					try {
						ostream = new BufferedOutputStream(new FileOutputStream(new File(diskCachePath, getCacheKey(url))), 2 * 1024);
						bitmap.compress(CompressFormat.JPEG, 100, ostream);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} finally {
						try {
							if (ostream != null) {
								ostream.flush();
								ostream.close();
							}
						} catch (IOException e) {
						}
					}
				}
			}
		});
	}
	
	
	/**
	 * 
	 * 功能描述： 缓存图片到sd卡
	 *  @param url 图片下载地址
	 *  @param data  图片字节
	 * 创 建 人: DavikChen
	 * 日    期：  2012-11-29  下午9:49:40
	 * 修 改 人: 
	 * 日    期:
	 */
	private void cacheBitmapToDisk(final String url, final byte[] data){
		writeThread.execute(new Runnable() {
			@Override
			public void run() {
				if (diskCacheEnabled) {
					File file = new File(diskCachePath);
					if (!file.exists()) {
						file.mkdirs();
					}
					File picPath = new File(diskCachePath + getCacheKey(url));
					BufferedOutputStream bufferedOutputStream;
					try {
						bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(picPath));
						bufferedOutputStream.write(data);
						bufferedOutputStream.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * 从内存中获取图片
	 * @param url 图片地址
	 * @return bitmap 对象
	 */
	public Bitmap getBitmapFromMemory(String url) {
		Bitmap bitmap = null;
		SoftReference<Bitmap> softRef = memoryCache.get(getCacheKey(url));
		if (softRef != null) {
			bitmap = softRef.get();
		}

		return bitmap;
	}
	
	

	/**
	 * 从SDCard卡获得图片
	 * @param url 图片地址
	 * @return bitmap对象
	 */
	private Bitmap getBitmapFromDisk(String url) {
		Bitmap bitmap = null;
		if (diskCacheEnabled) {
			String filePath = getFilePath(url);
			File file = new File(filePath);
			if (file.exists()) {
				try {
					bitmap = BitmapFactory.decodeFile(filePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	/**
	 * 获得文件缓存到SDCard 
	 * @param url
	 * @return
	 */
	private String getFilePath(String url) {
		return diskCachePath + getCacheKey(url);
	}

	/**
	 *提出图片
	 * @param url 图片地址
	 * @return
	 */
	private String getCacheKey(String url) {
		if (url != null) {
			return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
		}
		return url; 
	}

	public ConcurrentHashMap<String, SoftReference<Bitmap>> getMemoryCache() {
		return memoryCache;
	}

	public void setMemoryCache(ConcurrentHashMap<String, SoftReference<Bitmap>> memoryCache) {
		this.memoryCache = memoryCache;
	}

	
	
	/**
	 * 去色同时加圆角
	 * @param bmpOriginal 原图
	 * @param pixels 圆角弧度
	 * @return 修改后的图片
	 */
	public Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
		return toRoundCorner(bmpOriginal, pixels);
	}

	/**
	 * 把图片变成圆角
	 * @param bitmap 需要修改的图片
	 * @param pixels 圆角的弧度
	 * @return 圆角图片
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = null;
		if (bitmap != null) {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = pixels;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		}

		return output;
	}

	public String getDiskCachePath() {
		return diskCachePath;
	}

	public void setDiskCachePath(String diskCachePath) {
		this.diskCachePath = diskCachePath;
	}

	public boolean isDiskCacheEnabled() {
		return diskCacheEnabled;
	}

	public void setDiskCacheEnabled(boolean diskCacheEnabled) {
		this.diskCacheEnabled = diskCacheEnabled;
	}
	
	
	
	
	
}