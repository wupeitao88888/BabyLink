package com.iloomo.glucometer;

import android.view.View;
import android.view.View.OnClickListener;

public class TestAnmiActivity extends GameBaseActivity{

	@Override
	public void setContentLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.test_animi);
	}

	@Override
	public void findViewById() {
		// TODO Auto-generated method stub
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TestAnmiActivity.this.finish();
			}
		});
	}

	@Override
	public void getDataFromServer() {
		// TODO Auto-generated method stub
		
	}

}
