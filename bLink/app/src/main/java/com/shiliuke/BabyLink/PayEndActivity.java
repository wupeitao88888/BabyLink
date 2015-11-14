package com.shiliuke.BabyLink;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.shiliuke.adapter.PayEndAdapter;
import com.shiliuke.adapter.UnconsumeAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.PayEnd;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * <!-- 支付尾款 -->
 * Created by wupeitao on 15/11/7.
 */
public class PayEndActivity extends ActivitySupport {

    private PullableListView payend_listView;
    private PayEndAdapter payEndAdapter;
    private PullToRefreshLayout payend_PullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payend);
        initView();
    }

    private void initView() {
        payend_listView = (PullableListView) findViewById(R.id.payend_listView);
        payend_PullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.payend_PullToRefreshLayout);


        payend_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        payend_PullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        payend_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });
        List<PayEnd> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PayEnd payEnd = new PayEnd("百度音乐", "定价：￥10000", "已付：￥10", "未付：￥1900", "");
            list.add(payEnd);
        }


        payEndAdapter = new PayEndAdapter(context, list);
        payend_listView.setAdapter(payEndAdapter);
    }
}
