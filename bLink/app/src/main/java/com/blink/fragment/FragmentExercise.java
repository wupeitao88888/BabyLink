package com.blink.fragment;

import com.blink.adapter.ExerciseAdapter;
import com.blink.bean.Exercise;
import com.blink.bean.UserInfo;
import com.blink.main.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/***
 *
 * 活动
 */
public class FragmentExercise extends Fragment {
	private View rootView;//缓存Fragment view
	private ListView exrcise_listview;
	private ExerciseAdapter eAdater;
	private ViewStub exrcise_nodate;
	private List<Exercise> list;
	private Activity mActivity = null;
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
		list=new ArrayList<>();
		mActivity = this.getActivity();
		for (int i=0;i<30;i++){
			Exercise ec=new Exercise();
			ec.setExercise_address("北京朝阳");
			ec.setExercise_author("小小明");
			ec.setExercise_authorName("小小明");
			ec.setExercise_authorPic("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fwww.qjis.com%2Fuploads%2Fallimg%2F130401%2F110439A91-5.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1519979105%2C1747027397%26fm%3D21%26gp%3D0.jpg");
			ec.setExercise_pic("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fwww.qjis.com%2Fuploads%2Fallimg%2F130401%2F110439A91-5.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1519979105%2C1747027397%26fm%3D21%26gp%3D0.jpg");
			ec.setExercise_time("2015/12/9");
			ec.setExercise_usercount("60");
			ec.setExercise_title("举报成龙");
			List<UserInfo> uinfoList=new ArrayList<>();
			for(int p=0;p<5;p++){
				UserInfo ui=new UserInfo();
				ui.setUserpic("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fwww.qjis.com%2Fuploads%2Fallimg%2F130401%2F110439A91-5.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1519979105%2C1747027397%26fm%3D21%26gp%3D0.jpg");
				uinfoList.add(ui);
			}
			ec.setExercise_signlist(uinfoList);



		}
		eAdater=new ExerciseAdapter(mActivity,list);
		exrcise_listview.setAdapter(eAdater);

	}
}
