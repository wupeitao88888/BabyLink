package com.shiliuke.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.ChangeAdapter;
import com.shiliuke.adapter.NeighbourAdapter;
import com.shiliuke.bean.Change;
import com.shiliuke.bean.Neighbour;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 我的邻居
 */
public class FragmentNeighbour extends Fragment {
    private View rootView;//缓存Fragment view
    private NOViewPagerPullableListView neighbour_listView;
    private NeighbourAdapter changeAdapter;
    private Activity mActivity;
    private PullToRefreshLayout neighbour_PullToRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_neighbour, null);
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
        neighbour_listView = (NOViewPagerPullableListView) rootView.findViewById(R.id.neighbour_listView);
        neighbour_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.neighbour_PullToRefreshLayout);


        neighbour_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        neighbour_PullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        neighbour_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });


        List<Neighbour> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Neighbour cg = new Neighbour();
            cg.setName("小狗");
            cg.setAddress("北京");
            cg.setPic("http://m1.img.srcdd.com/farm2/d/2011/0817/01/5A461954F44D8DC67A17838AA356FE4B_S64_64_64.JPEG");
            list.add(cg);
        }


        changeAdapter = new NeighbourAdapter(mActivity, list);
        neighbour_listView.setAdapter(changeAdapter);
        neighbour_listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {

            }
        });
    }


}
