package com.shiliuke.BabyLink;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.shiliuke.adapter.MeStarChangeAdapter;
import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.MyShowBeanAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.MeStarChange;
import com.shiliuke.bean.MyShowBean;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;

import com.shiliuke.base.ActivitySupport;

/**
 * 我的秀逗
 * Created by wupeitao on 15/11/8.
 */
public class MeShowBeanActivity extends ActivitySupport {

    private NOViewPagerPullableListView showbean_listView;
    private MyShowBeanAdapter meStarChangeAdapter;
    private PullToRefreshLayout showbean_PullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_showbean);
        initView();
    }

    private void initView() {
        showbean_listView = (NOViewPagerPullableListView) findViewById(R.id.showbean_listView);
        showbean_PullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.showbean_PullToRefreshLayout);
        showbean_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        showbean_PullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉加载
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        showbean_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });
        List<MyShowBean> mList = new ArrayList<>();
        for(int i=0;i<9;i++){
            MyShowBean ma=new MyShowBean("","朝阳公园","1");
            mList.add(ma);
        }
        meStarChangeAdapter = new MyShowBeanAdapter(context, mList);
        showbean_listView.setAdapter(meStarChangeAdapter);

    }

}
