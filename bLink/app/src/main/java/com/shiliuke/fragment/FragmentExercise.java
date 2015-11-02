package com.shiliuke.fragment;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.adapter.ExerciseAdapter;
import com.shiliuke.bean.Exercise;
import com.shiliuke.bean.UserInfo;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.AdvertisementList;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.utils.ViewUtil;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;
import com.shiliuke.view.imgscroll.MyImgScroll;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 活动
 */
public class FragmentExercise extends Fragment {
    private View rootView;//缓存Fragment view
    private PullableListView exrcise_listview;
    private ExerciseAdapter eAdater;
    private ViewStub exrcise_nodate;
    private List<Exercise> list;
    private Activity mActivity = null;
    private MyImgScroll lc_slideshowview_carousel;

    private LinearLayout ovalLayout; // 圆点容器
    private List<View> listViews; // 图片组
    private List<AdvertisementList> lista;
    private PullToRefreshLayout exrcise_PullToRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_exercise, null);
            initView(rootView);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    boolean canscoll = false;

    private void initView(View rootView) {

        mActivity = this.getActivity();


        exrcise_listview = (PullableListView) rootView.findViewById(R.id.exrcise_listview);
        exrcise_nodate = (ViewStub) rootView.findViewById(R.id.exrcise_nodate);
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.layout_activity, null);
        exrcise_listview.addHeaderView(inflate);

        lc_slideshowview_carousel = (MyImgScroll) inflate.findViewById(R.id.lc_slideshowview_carousel);




        ovalLayout = (LinearLayout) inflate.findViewById(R.id.vb);
        exrcise_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.exrcise_PullToRefreshLayout);
        exrcise_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        exrcise_PullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        exrcise_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });


        list = new ArrayList<>();
        lista = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Exercise ec = new Exercise();

            ec.setExercise_address("北京朝阳");
            ec.setExercise_author("小小明");
            ec.setExercise_authorName("小小明");
            ec.setExercise_authorPic("http://m1.img.srcdd.com/farm2/d/2011/0817/01/5A461954F44D8DC67A17838AA356FE4B_S64_64_64.JPEG");
            ec.setExercise_pic("http://m1.img.srcdd.com/farm2/d/2011/0817/01/5A461954F44D8DC67A17838AA356FE4B_S64_64_64.JPEG");
            ec.setExercise_time("2015/12/9");
            ec.setExercise_usercount("60");
            ec.setExercise_title("举报成龙");
            List<UserInfo> uinfoList = new ArrayList<>();
            for (int p = 0; p < 5; p++) {
                UserInfo ui = new UserInfo();
                ui.setUserpic("http://m1.img.srcdd.com/farm2/d/2011/0817/01/5A461954F44D8DC67A17838AA356FE4B_S64_64_64.JPEG");
                uinfoList.add(ui);
            }
            ec.setExercise_signlist(uinfoList);
            list.add(ec);
            AdvertisementList al = new AdvertisementList();
            if ((i & 1) != 0)
                al.setMedia_url("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic14.nipic.com%2F20110522%2F7411759_164157418126_2.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1183223528%2C3058066243%26fm%3D21%26gp%3D0.jpg");
            else
                al.setMedia_url("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fwww.xxjxsj.cn%2Farticle%2FUploadPic%2F2009-10%2F2009101018545196251.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D2199763593%2C4070991891%26fm%3D21%26gp%3D0.jpg");
            lista.add(al);
        }
        eAdater = new ExerciseAdapter(mActivity, list);
        exrcise_listview.setAdapter(eAdater);
        InitViewPager();//初始化图片
        //开始滚动
        lc_slideshowview_carousel.start(mActivity, listViews, 4000, ovalLayout,
                R.layout.ad_bottom_item, R.id.ad_item_v,
                R.mipmap.ad_select, R.mipmap.ad_normal);

    }

    /**
     * 初始化图片
     */
    private void InitViewPager() {
        listViews = new ArrayList<View>();

        for (int i = 0; i < lista.size(); i++) {
            ImageView imageView = new ImageView(mActivity);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {// 设置图片点击事件

                    ToastUtil.showLong(mActivity, "点击了:" + lc_slideshowview_carousel.getCurIndex());
                }
            });
            setImage(imageView, lista.get(i).getMedia_url(), mActivity);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            listViews.add(imageView);
        }
    }

    private void setImage(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.gray)
                .error(R.drawable.gray)
                .crossFade()
                .into(iview);

    }


}
