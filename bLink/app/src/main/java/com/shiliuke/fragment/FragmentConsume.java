package com.shiliuke.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.shiliuke.BabyLink.R;
import com.shiliuke.BabyLink.UnconsumeCode;
import com.shiliuke.adapter.MeInitateAdapter;
import com.shiliuke.adapter.UnconsumeAdapter;
import com.shiliuke.bean.MeInitateActivity;
import com.shiliuke.bean.Unconsume;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 已消费
 */
public class FragmentConsume extends Fragment {
    private View rootView;//缓存Fragment view
    private PullableListView consume_listView;
    private UnconsumeAdapter unconsumeAdapter;
    private Activity mActivity;
    private PullToRefreshLayout consume_PullToRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_consume, null);
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
        consume_listView = (PullableListView) rootView.findViewById(R.id.consume_listView);
        consume_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.consume_PullToRefreshLayout);


        consume_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        consume_PullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        consume_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });
        List<Unconsume> mList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Unconsume ma = new Unconsume("去传媒大学", "2015/11/11 11:11", "1");
            mList.add(ma);
        }

        unconsumeAdapter = new UnconsumeAdapter(mActivity, mList);
        consume_listView.setAdapter(unconsumeAdapter);
        consume_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, UnconsumeCode.class);
                startActivity(intent);
            }
        });
    }


}
