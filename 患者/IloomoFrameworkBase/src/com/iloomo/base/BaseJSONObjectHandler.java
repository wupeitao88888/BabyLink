package com.iloomo.base;

import org.json.JSONObject;
/**
 * 
 * 创 建 人: DavikChen
 * 日    期：  2012-12-5  下午11:22:05
 * 修 改 人： 
 * 日   期： 
 * 描   述： 解析基类 -- JSONObject字符串
 * 版 本 号：1.0
 */
public abstract class BaseJSONObjectHandler {
	/**
	 * 
	 * 功能描述： 解析方法
	 *  @param buffer 需要解析的JSONObject
	 *  @return  解析后的对象
	 * 创 建 人: DavikChen
	 * 日    期：  2012-12-5  下午11:20:32
	 * 修 改 人: 
	 * 日    期:
	 */
	public abstract Object parseJSON(JSONObject buffer);
}
