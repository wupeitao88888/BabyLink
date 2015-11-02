package com.shiliuke.fragment;

import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.TopicAdapter;
import com.shiliuke.bean.Comment;
import com.shiliuke.bean.Topic;
import com.shiliuke.bean.UserImgs;
import com.shiliuke.utils.FaceConversionUtil;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
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
	private PullableListView toaic_listView;
	private Activity mActivity = null;
	private PullToRefreshLayout toaic_PullToRefreshLayout;
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
		new Thread(new Runnable() {
			@Override
			public void run() {
				FaceConversionUtil
						.getInstace().getFileText(
						mActivity);
			}
		}).start();
		mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		View view = inflater.inflate(R.layout.fragment_topic, null);
		toaic_listView=(PullableListView)view.findViewById(R.id.toaic_listView);
		toaic_PullToRefreshLayout=(PullToRefreshLayout)view.findViewById(R.id.toaic_PullToRefreshLayout);



		toaic_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
				// 下拉刷新操作
				new Handler() {
					@Override
					public void handleMessage(Message msg) {
						toaic_PullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
					}
				}.sendEmptyMessageDelayed(0, 2000);

			}

			@Override
			public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
				// 下拉刷新操作
				new Handler() {
					@Override
					public void handleMessage(Message msg) {
						toaic_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
					}
				}.sendEmptyMessageDelayed(0, 2000);

			}
		});

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
		List<Comment> commentList=new ArrayList<>();
		for(int i=0;i<5;i++){
			Comment ct= new Comment();
			ct.setCommentName("小明");
			ct.setCommentContent("隔壁村的钱寡妇，孙寡妇，李寡妇，周寡妇，吴寡妇，郑寡妇，王寡妇，冯寡妇，陈寡妇，卫寡妇，蒋寡妇，沈寡妇，韩寡妇，杨寡妇，朱寡妇，秦寡妇，尤寡妇，许寡妇，何寡妇，吕寡妇，施寡妇，张寡妇，魏寡妇，陶寡妇，戚寡妇，谢寡妇，邹寡妇，喻寡妇，章寡妇，云寡妇，苏寡妇，潘寡妇，范寡妇，彭寡妇，鲁寡妇，韦寡妇，昌寡妇，马寡妇，苗寡妇，凤寡妇，花寡妇，方寡妇，俞寡妇，任寡妇，袁寡妇，等360个寡妇都觉得很赞");
			if(isOdd(i)){
				ct.setCommentReplyName("小李");
			}
			commentList.add(ct);
		}
		mUserInfo2.setCt(commentList);
		mList.add(mUserInfo2);
		TopicAdapter mWeChatAdapter = new TopicAdapter(mActivity);
		mWeChatAdapter.setData(mList);
		toaic_listView.setAdapter(mWeChatAdapter);
		toaic_listView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
								 int totalItemCount) {

			}
		});
		return view;
	}
	public  boolean isOdd(int i){
		return i % 2!=0;//如果一个数是偶数，就算是负数整除2余数也为0
	}
}
