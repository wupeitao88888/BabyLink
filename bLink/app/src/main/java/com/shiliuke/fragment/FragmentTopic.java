package com.shiliuke.fragment;

import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.TopicAdapter;
import com.shiliuke.bean.Topic;
import com.shiliuke.bean.UserImgs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/***
 *
 * 话体
 */
public class FragmentTopic extends Fragment {
	private View rootView;
	private  LayoutInflater inflater;
	private ListView toaic_listView;
	private Activity mActivity = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			this.inflater=inflater;
			rootView = initView();
//			i;
		}
		//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	private View initView() {
		mActivity = this.getActivity();
		View view = inflater.inflate(R.layout.fragment_topic, null);
		toaic_listView=(ListView)view.findViewById(R.id.toaic_listView);

		List<Topic> mList = new ArrayList<Topic>();
		Topic mUserInfo = new Topic();
		UserImgs m = new UserImgs();
		m.setUrls("http://m1.img.srcdd.com/farm2/d/2011/0817/01/5A461954F44D8DC67A17838AA356FE4B_S64_64_64.JPEG");
		mUserInfo.getUi().add(m);
		mList.add(mUserInfo);
		//---------------------------------------------
		Topic mUserInfo2 = new Topic();
		for(int i=0;i<9;i++){
			UserImgs m2 = new UserImgs();
			m2.setUrls("http://m1.img.srcdd.com/farm2/d/2011/0817/01/5A461954F44D8DC67A17838AA356FE4B_S64_64_64.JPEG");
			mUserInfo2.getUi().add(m2);
		}


		mList.add(mUserInfo2);

		TopicAdapter mWeChatAdapter = new TopicAdapter(mActivity);
		mWeChatAdapter.setData(mList);
		toaic_listView.setAdapter(mWeChatAdapter);


		return view;
	}
}
