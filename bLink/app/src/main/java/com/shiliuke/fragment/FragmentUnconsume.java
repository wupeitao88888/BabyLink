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
<<<<<<< Updated upstream
import com.shiliuke.BabyLink.UnconsumeCodeActivity;
=======
import com.shiliuke.BabyLink.UnconsumeCode;
import com.shiliuke.adapter.MeInitateAdapter;
>>>>>>> Stashed changes
import com.shiliuke.adapter.UnconsumeAdapter;
import com.shiliuke.bean.Unconsume;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 未消费
 */
public class FragmentUnconsume extends Fragment {
    private View rootView;//缓存Fragment view
    private PullableListView unconsume_listView;
    private UnconsumeAdapter unconsumeAdapter;
    private Activity mActivity;
    private PullToRefreshLayout unconsume_PullToRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_unconsume, null);
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
        unconsume_listView = (PullableListView) rootView.findViewById(R.id.unconsume_listView);
        unconsume_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.unconsume_PullToRefreshLayout);


        unconsume_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        unconsume_PullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        unconsume_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });
        List<Unconsume> mList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Unconsume ma = new Unconsume("去国土局", "2015/11/11 11:11", "1");
            mList.add(ma);
        }

        unconsumeAdapter = new UnconsumeAdapter(mActivity, mList);
        unconsume_listView.setAdapter(unconsumeAdapter);
        unconsume_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
<<<<<<< Updated upstream
                Intent intent = new Intent(mActivity, UnconsumeCodeActivity.class);
=======
                Intent intent = new Intent(mActivity, UnconsumeCode.class);
>>>>>>> Stashed changes
                startActivity(intent);
            }
        });
    }


}
