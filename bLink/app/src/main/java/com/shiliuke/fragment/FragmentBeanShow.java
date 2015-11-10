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
import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.BeanShowAdapter;
import com.shiliuke.bean.BeanShowModel;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;
import com.shiliuke.view.TitleBar;
import com.shiliuke.view.stickerview.StickerExecutor;
import com.shiliuke.view.stickerview.StickerImageContans;
import com.shiliuke.view.stickerview.StickerImageModel;

import java.util.ArrayList;

/**
 * 秀逗页面
 */
public class FragmentBeanShow extends Fragment implements View.OnClickListener, AbsListView.OnScrollListener {

    private Button btn_beanshow_link;//link按钮
    private Button btn_beanshow_public;//广场按钮
    private PullableListView listview_beanshow;//列表
    private BeanShowAdapter beanShowAdapter;
    private PullToRefreshLayout beanshow_PullToRefreshLayout;
    private ArrayList<BeanShowModel> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beanshow, null);
        ((TitleBar) view.findViewById(R.id.meCommunity_title)).setCenterTitle("秀逗");
        btn_beanshow_link = (Button) view.findViewById(R.id.btn_beanshow_link);
        btn_beanshow_public = (Button) view.findViewById(R.id.btn_beanshow_public);
        btn_beanshow_public.setOnClickListener(this);
        btn_beanshow_public.setOnClickListener(this);
        listview_beanshow = (PullableListView) view.findViewById(R.id.listview_beanshow);
        beanshow_PullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.beanshow_PullToRefreshLayout);
        initData();
        beanShowAdapter = new BeanShowAdapter(listview_beanshow, this, data);
        listview_beanshow.setAdapter(beanShowAdapter);
        listview_beanshow.setOnScrollListener(this);
        beanshow_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                beanshow_PullToRefreshLayout.refreshFinish(beanshow_PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                beanshow_PullToRefreshLayout.loadmoreFinish(beanshow_PullToRefreshLayout.SUCCEED);

            }
        });
        return view;
    }

    private void initData() {
        data = new ArrayList<>();
        ////////
        BeanShowModel model;
        ArrayList<StickerImageModel> list;
        StickerImageModel s1, s2, s3, s4, s5, s6, s7;
        for (int i = 0; i < 17; i++) {
            list = new ArrayList<>();
            s1 = new StickerImageModel("第一个文字");
            s2 = new StickerImageModel("第二个文字");
            s3 = new StickerImageModel("第三个文字");
            s4 = new StickerImageModel("第四个文字");
            s5 = new StickerImageModel("第五个文字");
            s6 = new StickerImageModel("第六个文字");
            s7 = new StickerImageModel("第七个文字");
            s1.setXy(100, 100);
            s2.setXy(300, 200);
            s3.setXy(200, 300);
            s4.setXy(400, 400);
            s5.setXy(100, 500);
            s6.setXy(300, 600);
            s7.setXy(200, 700);
            list.add(s1);
            list.add(s2);
            list.add(s3);
            list.add(s4);
            list.add(s5);
            list.add(s6);
            list.add(s7);
            model = new BeanShowModel("headurl", "王志" + i, i + "分钟前", "contenturl", i + "1000逗", i + "逗逗逗，大家都来逗", list);
            data.add(model);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_beanshow_link:
//                ToastUtil.show(getContext(), "click link", 1);
//                break;
//            case R.id.btn_beanshow_public:
//                ToastUtil.show(getContext(), "click 广场", 1);
//                break;
        }
    }

    private int firstVisibleItem = 0, visibleItemCount = 0;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                beanShowAdapter.updateAnimItem(firstVisibleItem, visibleItemCount);
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                beanShowAdapter.stopAnim();
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;

    }

    @Override
    public void onStop() {
        beanShowAdapter.stopAnim();
        StickerExecutor.stopExecutor();
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
                    this.data.get(position).getStickerlist().add(model);
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
