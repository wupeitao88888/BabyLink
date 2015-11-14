package com.shiliuke.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.shiliuke.BabyLink.MainTab;
import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.BeanShowAdapter;
import com.shiliuke.bean.BeanShowModel;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.stickerview.StickerExecutor;
import com.shiliuke.view.stickerview.StickerImageContans;
import com.shiliuke.view.stickerview.StickerImageModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 秀逗页面
 */
public class FragmentBeanShow extends Fragment implements View.OnClickListener, AbsListView.OnScrollListener, VolleyListerner {

    private Button btn_beanshow_link;//link按钮
    private Button btn_beanshow_public;//广场按钮
    private NOViewPagerPullableListView listview_beanshow;//列表
    private BeanShowAdapter beanShowAdapter;
    private PullToRefreshLayout beanshow_PullToRefreshLayout;
    private ArrayList<BeanShowModel.BeanShowModelResult> data;
    private Boolean isLink = true;
    private int page = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beanshow, null);
        btn_beanshow_link = (Button) view.findViewById(R.id.btn_beanshow_link);
        btn_beanshow_public = (Button) view.findViewById(R.id.btn_beanshow_public);
        btn_beanshow_link.setOnClickListener(this);
        btn_beanshow_public.setOnClickListener(this);
        listview_beanshow = (NOViewPagerPullableListView) view.findViewById(R.id.listview_beanshow);
        beanshow_PullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.beanshow_PullToRefreshLayout);
        requestData();
        listview_beanshow.setOnScrollListener(this);
        beanshow_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                page = 1;
                requestData();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                page++;
                lodeData();
            }
        });
        return view;
    }

    /**
     * 刷新列表
     */

    private void requestData() {
        Map<String, String> params = new HashMap<>();
        params.put("member_id", "1");
        params.put("page", page + "");
        BasicRequest.getInstance().requestPost(this, TaskID.ACTION_XIUDOU_LINK, params, isLink ? AppConfig.XIUDOU_LINK : AppConfig.XIUDOU_GUANGCHANG);
    }

    /**
     * 加载列表
     */

    private void lodeData() {
        Map<String, String> params = new HashMap<>();
        params.put("member_id", "1");
        params.put("page", page + "");
        BasicRequest.getInstance().requestPost(this, TaskID.ACTION_XIUDOU_LINK, params, AppConfig.XIUDOU_LINK);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_beanshow_link:
                isLink = true;
                page = 1;
                ToastUtil.show(getContext(), "click link", 1);
                break;
            case R.id.btn_beanshow_public:
                page = 1;
                isLink = false;
                ToastUtil.show(getContext(), "click 广场", 1);
                break;
        }
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        switch (taskid) {
            case TaskID.ACTION_XIUDOU_LINK:
                Gson g = new Gson();
//                BeanShowModel model = JSON.parseObject(str, BeanShowModel.class);
                BeanShowModel model = g.fromJson(str,BeanShowModel.class);
                if (!"0".equalsIgnoreCase(model.getCode())) {
                    onResponseError("error", taskid);
                    return;
                }
                data = model.getDatas();
                if (beanShowAdapter == null) {
                    beanShowAdapter = new BeanShowAdapter(listview_beanshow, this, data, isLink);
                }
                listview_beanshow.setAdapter(beanShowAdapter);
                beanshow_PullToRefreshLayout.refreshFinish(beanshow_PullToRefreshLayout.SUCCEED);
                beanshow_PullToRefreshLayout.loadmoreFinish(beanshow_PullToRefreshLayout.SUCCEED);
                break;
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        ((MainTab) getActivity()).showToast(error);
        beanshow_PullToRefreshLayout.loadmoreFinish(beanshow_PullToRefreshLayout.FAIL);
        beanshow_PullToRefreshLayout.refreshFinish(beanshow_PullToRefreshLayout.FAIL);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//                beanShowAdapter.updateAnimItem(firstVisibleItem, visibleItemCount);
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                beanShowAdapter.stopAnim();
                break;
        }
    }

    private int firstVisibleItem = 0, visibleItemCount = 0;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;

    }

    @Override
    public void onResume() {
        if (beanShowAdapter != null) {
            beanShowAdapter.updateAnimItem(firstVisibleItem, visibleItemCount);
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        if (beanShowAdapter != null) {
            beanShowAdapter.stopAnim();
            StickerExecutor.stopExecutor();
        }
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case StickerImageContans.REQUESTADDMODEL:
                if (resultCode == Activity.RESULT_OK) {
                    int position = data.getIntExtra("position", 0);
                    StickerImageModel model = (StickerImageModel) data.getSerializableExtra("model");
//                    this.data.get(position).getStickerlist().add(model);
                    this.beanShowAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
