package com.shiliuke.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.shiliuke.BabyLink.FindDesActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.FindAdapter;
import com.shiliuke.bean.FindModel;
import com.shiliuke.view.PopWnd;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.TitleBar;

import java.util.ArrayList;


public class FragmentFind extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private TextView tv_fragment_find_category;//发现页面分类选项
    private TextView tv_fragment_find_sort;//发现页面排序方式选项
    private View popBg;
    private PopWnd categoryPop;//分类pop
    private PopWnd sortPop;//排序pop
    private NOViewPagerPullableListView list_fragment_find;//页面list
    private PullToRefreshLayout find_PullToRefreshLayout;
    private FindAdapter mAdapter;
    private ArrayList<FindModel> mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, null);

        ((TitleBar) view.findViewById(R.id.meCommunity_title)).setCenterTitle("发现");
        list_fragment_find = (NOViewPagerPullableListView) view.findViewById(R.id.list_fragment_find);
        find_PullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.find_PullToRefreshLayout);
        tv_fragment_find_category = (TextView) view.findViewById(R.id.tv_fragment_find_category);
        tv_fragment_find_sort = (TextView) view.findViewById(R.id.tv_fragment_find_sort);
        popBg = view.findViewById(R.id.pop_bg);
        tv_fragment_find_category.setOnClickListener(this);
        tv_fragment_find_sort.setOnClickListener(this);
        initData();
        mAdapter = new FindAdapter(list_fragment_find, this, mData);
        list_fragment_find.setAdapter(mAdapter);
        list_fragment_find.setOnItemClickListener(this);
        find_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                find_PullToRefreshLayout.refreshFinish(find_PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                find_PullToRefreshLayout.loadmoreFinish(find_PullToRefreshLayout.SUCCEED);
            }
        });
        return view;
    }

    private void initData() {
        mData = new ArrayList<>();
        FindModel model;
        for (int i = 0; i < 7; i++) {
            model = new FindModel("topurl", "红黄蓝亲子园" + i, "$1000.00-$" + i + "000.00", "大兴枣园路" + i + "号", i + "00米");
            mData.add(model);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fragment_find_category://分类
                if (categoryPop == null) {
                    int[] cateIds = new int[]{R.id.tv_pop_find_category_server};
                    categoryPop = new PopWnd(getActivity(), R.layout.layout_pop_find_category, cateIds, this,popBg);
                }
                categoryPop.showPopWindow(v);
                break;
            case R.id.tv_fragment_find_sort://排序方式
                if (sortPop == null) {
                    int[] cateIds = new int[]{R.id.tv_pop_find_sort_person, R.id.tv_pop_find_sort_price, R.id.tv_pop_find_sort_range};
                    sortPop = new PopWnd(getActivity(), R.layout.layout_pop_find_sort, cateIds, this,popBg);
                }
                sortPop.showPopWindow(v);
                break;
            case R.id.tv_pop_find_category_server://分类-后台新建

                break;
            case R.id.tv_pop_find_sort_person://排序-参与人数

                break;
            case R.id.tv_pop_find_sort_range://排序-距离最近

                break;
            case R.id.tv_pop_find_sort_price://排序-价格最低

                break;
            case R.id.btn_find_des_reserve://我要预定

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        startActivityForResult(new Intent(getActivity(), FindDesActivity.class), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
