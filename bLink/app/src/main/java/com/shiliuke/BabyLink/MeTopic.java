package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.shiliuke.adapter.MeInitateAdapter;
import com.shiliuke.adapter.MeTopicAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.MeInitateActivity;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的话题
 * Created by wupeitao on 15/11/5.
 */
public class MeTopic extends ActivitySupport {


    private PullableListView meTopic_listView;
    private PullToRefreshLayout meTopic_PullToRefreshLayout;
   private MeTopicAdapter meTopicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_metopic);
        initView();

    }

    private void initView() {
        meTopic_listView = (PullableListView)findViewById(R.id.meTopic_listView);
        meTopic_PullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.meTopic_PullToRefreshLayout);


        meTopic_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        meTopic_PullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        meTopic_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });
        List<MeInitateActivity> mList = new ArrayList<>();
        for(int i=0;i<9;i++){
            MeInitateActivity ma=new MeInitateActivity("","去国土局","2015/11/11 11:11","1","10");
            mList.add(ma);
        }

        meTopicAdapter = new MeTopicAdapter(context, mList);
        meTopic_listView.setAdapter(meTopicAdapter);

    }
}

