package com.shiliuke.fragment;

import android.view.View.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.shiliuke.BabyLink.R;


public class FragmentMy extends Fragment implements OnClickListener{

	private RelativeLayout user_home_rl;
	private RelativeLayout user_order_rl;
	private RelativeLayout user_information_rl;
	private RelativeLayout user_help_rl;
	private RelativeLayout version_update_rl;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_my, null);
		user_home_rl = (RelativeLayout) view.findViewById(R.id.user_home_rl);
		user_order_rl = (RelativeLayout) view.findViewById(R.id.user_order_rl);
		user_information_rl = (RelativeLayout) view.findViewById(R.id.user_information_rl);
		user_help_rl = (RelativeLayout) view.findViewById(R.id.user_help_rl);
		version_update_rl = (RelativeLayout) view.findViewById(R.id.version_update_rl);
		user_home_rl.setOnClickListener(this);
		user_order_rl.setOnClickListener(this);
		user_information_rl.setOnClickListener(this);
		user_help_rl.setOnClickListener(this);
		version_update_rl.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.user_home_rl :
				// 我的主页
				break;
			case R.id.user_order_rl :
				// 我的订单
				break;
			case R.id.user_information_rl :
				// 个人资料
				break;
			case R.id.user_help_rl :
				// 帮助中心
				break;
			case R.id.version_update_rl :
				// 版本升级
				break;

		}
	}
}
