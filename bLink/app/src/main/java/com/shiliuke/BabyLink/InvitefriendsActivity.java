package com.shiliuke.BabyLink;

import android.os.Bundle;
import com.shiliuke.adapter.InvitefriendsAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.UserInfo;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;

import java.util.ArrayList;

public class InvitefriendsActivity extends ActivitySupport {

    private PullToRefreshLayout invitefriends_pulltorefreshlayout;
    private NOViewPagerPullableListView list_invitefriends;//页面list
    private ArrayList<UserInfo> infoList;
    private InvitefriendsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitefriends);
        setCtenterTitle("小区好友");
        list_invitefriends = (NOViewPagerPullableListView) findViewById(R.id.list_invitefriends);
        invitefriends_pulltorefreshlayout = (PullToRefreshLayout) findViewById(R.id.invitefriends_pulltorefreshlayout);
        initData();
        mAdapter = new InvitefriendsAdapter(infoList, this);
        list_invitefriends.setAdapter(mAdapter);
        invitefriends_pulltorefreshlayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                invitefriends_pulltorefreshlayout.refreshFinish(invitefriends_pulltorefreshlayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                invitefriends_pulltorefreshlayout.loadmoreFinish(invitefriends_pulltorefreshlayout.SUCCEED);
            }
        });
    }

    private void initData() {
        infoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            infoList.add(new UserInfo("王志" + i));
        }
    }
}
