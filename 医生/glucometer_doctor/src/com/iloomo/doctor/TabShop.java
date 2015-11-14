package com.iloomo.doctor;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TabShop extends GameBaseActivity {

	@Override
	public void setContentLayout() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.tab_shop);
	}

	@Override
	public void findViewById() {
		// TODO Auto-generated method stub
		WebView myWebView = (WebView) findViewById(R.id.webView);
	
		myWebView.loadUrl("http://weidian.com/s/204695635?wfr=c");
		WebSettings settings = myWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		WebChromeClient a  = new WebChromeClient();
		
		myWebView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});
	}

	@Override
	public void getDataFromServer() {
		// TODO Auto-generated method stub

	}

}
