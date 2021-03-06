package com.shiliuke.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.MeStarChangeAdapter;
import com.shiliuke.bean.MeStarChange;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 我参与的置换
 */
public class FragmentMeTakeChange extends Fragment {
    private View rootView;//缓存Fragment view
    private PullableListView metakechange_listView;
    private MeStarChangeAdapter meStarChangeAdapter;
    private Activity mActivity;
    private PullToRefreshLayout metakechange_PullToRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_metakechange, null);
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
        mActivity = getActivity();
        metakechange_listView = (PullableListView) rootView.findViewById(R.id.metakechange_listView);
        metakechange_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.metakechange_PullToRefreshLayout);


        metakechange_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        metakechange_PullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉刷新操作

                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        metakechange_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });
        List<MeStarChange> mList = new ArrayList<>();
        for(int i=0;i<9;i++){
            MeStarChange ma=new MeStarChange("","去国土局","2015/11/11 11:11","1");
            mList.add(ma);
        }
        meStarChangeAdapter = new MeStarChangeAdapter(mActivity, mList);
        metakechange_listView.setAdapter(meStarChangeAdapter);
    }


}
