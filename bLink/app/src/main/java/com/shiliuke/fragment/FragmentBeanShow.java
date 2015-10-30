package com.shiliuke.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.BeanShowAdapter;
import com.shiliuke.bean.BeanShowModel;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.view.stickerview.StickerImageModel;

import java.util.ArrayList;

/**
 * 秀逗页面
 */
public class FragmentBeanShow extends Fragment implements View.OnClickListener, AbsListView.OnScrollListener {

    private Button btn_beanshow_link;//link按钮
    private Button btn_beanshow_public;//广场按钮
    private ListView listview_beanshow;//列表
    private BeanShowAdapter beanShowAdapter;
    private ArrayList<BeanShowModel> data;

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
        listview_beanshow = (ListView) view.findViewById(R.id.listview_beanshow);
        btn_beanshow_public.setOnClickListener(this);
        btn_beanshow_public.setOnClickListener(this);
        initData();
        beanShowAdapter = new BeanShowAdapter(listview_beanshow,getActivity(), data);
        listview_beanshow.setAdapter(beanShowAdapter);
        listview_beanshow.setOnScrollListener(this);
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
            case R.id.btn_beanshow_link:
                ToastUtil.show(getContext(), "click link", 1);
                break;
            case R.id.btn_beanshow_public:
                ToastUtil.show(getContext(), "click 广场", 1);
                break;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        beanShowAdapter.updateAnimItem(firstVisibleItem,visibleItemCount);
    }

    @Override
    public void onDestroy() {
        beanShowAdapter.stopAnim();
        super.onDestroy();
    }
}
