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
import com.shiliuke.adapter.AlreadyDepositAdapter;
import com.shiliuke.adapter.NotYetDepositAdapter;
import com.shiliuke.bean.PayEnd;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 已付订金
 */
public class FragmentAlreadyDeposit extends Fragment {
    private View rootView;//缓存Fragment view
    private NOViewPagerPullableListView a_deposit_listView;
    private AlreadyDepositAdapter alreadyDepositAdapter;
    private Activity mActivity;
    private PullToRefreshLayout a_deposit_PullToRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_alreadydeposit, null);
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
        a_deposit_listView = (NOViewPagerPullableListView) rootView.findViewById(R.id.a_deposit_listView);
        a_deposit_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.a_deposit_PullToRefreshLayout);


        a_deposit_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        a_deposit_PullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        a_deposit_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });
        List<PayEnd> mList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            PayEnd ma = new PayEnd("去国土局", "", "", "1000", "");
            mList.add(ma);
        }

        alreadyDepositAdapter = new AlreadyDepositAdapter(mActivity, mList);
        a_deposit_listView.setAdapter(alreadyDepositAdapter);

    }


}
