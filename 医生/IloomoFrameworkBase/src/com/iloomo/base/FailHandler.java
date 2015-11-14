package com.iloomo.base;

import org.json.JSONObject;

import com.iloomo.bean.ErrorBean;

/**
 * 
 * 创 建 人: DavikChen
 * 日    期：  2012-12-5  下午11:22:36
 * 修 改 人： 
 * 日   期： 
 * 描   述：错误解析类 
 * 版 本 号：1.0
 */
public class FailHandler {

	public Object parseJSON(String buffer) {
		ErrorBean errorBean = null;
		try {
			JSONObject buJsonObject = new JSONObject(buffer);
			errorBean = new ErrorBean();
			if (!buJsonObject.isNull("error_response")) {
				JSONObject errorObject = buJsonObject.getJSONObject("error_response");
				String codeString = errorObject.getString("code");
				int code = Integer.parseInt(codeString);
				errorBean.error_code = code;
				errorBean.error_message = errorObject.getString("msg");
			}
		} catch (Exception e) {
			return new ErrorBean();
		}

		return errorBean;
	}

}
