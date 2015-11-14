package com.iloomo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

public class MD5Utils {
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 获取签名字符串
	 * @return String 返回签名字符串
	 */
	public static String getSign(Map<String, String> data) {
		// 获取map长度
		int length = data.size();
		String[] array = new String[length];
		StringBuffer stringBuffer = new StringBuffer();

		int x = 0;// 数组下标标识
		for (Map.Entry<String, String> m : data.entrySet()) {
			array[x] = m.getKey() + m.getValue();
			x++;
		}
		// 升序排序
		Arrays.sort(array);

		for (int i = 0; i < length; i++) {
			stringBuffer.append(array[i]);
		}
		return md5(stringBuffer.toString());
	}

	/**
	 * MD5
	 * @param b byte数组
	 * @return String byte数组处理后字符串
	 */
	public static String toHexString(byte[] b) {// String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * MD5运算
	 * @param s 传入明文
	 * @return String 返回密文
	 */
	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return s;
	}
}
