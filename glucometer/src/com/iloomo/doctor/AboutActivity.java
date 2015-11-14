package com.iloomo.doctor;



import android.view.View;
import android.view.View.OnClickListener;

public class AboutActivity extends GameBaseActivity{

	@Override
	public void setContentLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.about);
	}

	@Override
	public void findViewById() {
		// TODO Auto-generated method stub
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AboutActivity.this.finish();
			}
		});
	}

	@Override
	public void getDataFromServer() {
		// TODO Auto-generated method stub
		
	}

}
