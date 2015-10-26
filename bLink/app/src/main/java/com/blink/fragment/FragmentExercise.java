package com.blink.fragment;

import com.blink.adapter.ExerciseAdapter;
import com.blink.main.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;

/***
 *
 * 活动
 */
public class FragmentExercise extends Fragment {
	private View rootView;//缓存Fragment view
	private ListView exrcise_listview;
	private ExerciseAdapter eAdater;
	private ViewStub exrcise_nodate;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_exercise, null);
			initView(rootView);
		}
		//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	private void initView(View rootView) {
		exrcise_listview=(ListView)rootView.findViewById(R.id.exrcise_listview);
		exrcise_nodate=(ViewStub)rootView.findViewById(R.id.exrcise_nodate);


	}
}
