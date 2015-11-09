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
import com.shiliuke.adapter.MeInitateAdapter;
import com.shiliuke.bean.MeInitateActivity;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 我我参与的
 */
public class FragmentMeTakePartIn extends Fragment {
    private View rootView;//缓存Fragment view
    private PullableListView me_take_part_in_listView;
    private Activity mActivity;
    private PullToRefreshLayout me_take_part_in_PullToRefreshLayout;
    private MeInitateAdapter meInitateAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_me_takepartin, null);
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
        me_take_part_in_listView = (PullableListView) rootView.findViewById(R.id.me_take_part_in_listView);
        me_take_part_in_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.me_take_part_in_PullToRefreshLayout);
        me_take_part_in_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        me_take_part_in_PullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        me_take_part_in_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });

        List<MeInitateActivity> mList = new ArrayList<>();
        for(int i=0;i<9;i++){
            MeInitateActivity ma=new MeInitateActivity("","传媒大学看妹子","2015/11/11 11:11","1","10");
            mList.add(ma);
        }

        meInitateAdapter = new MeInitateAdapter(mActivity, mList);
        me_take_part_in_listView.setAdapter(meInitateAdapter);
    }


}
