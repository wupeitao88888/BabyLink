package com.iloomo.bean;

import java.io.Serializable;

/**
 * 
 * 创 建 人: DavikChen
 * 日    期：  2012-12-5  下午11:23:08
 * 修 改 人： 
 * 日   期： 
 * 描   述： 客户端错误bean
 * 版 本 号：1.0
 */
public class ErrorBean implements Serializable {
	private static final long serialVersionUID = 1L;
	// 错误代码号
	public int error_code = -1;
	// 错误信息
	public String error_message = "解析错误";
	@Override
	public String toString() {
		return "ErrorBean [error_code=" + error_code + ", error_message=" + error_message + "]";
	}

}
