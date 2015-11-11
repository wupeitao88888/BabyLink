package com.shiliuke.fragment;


import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.adapter.ExerciseAdapter;
import com.shiliuke.bean.Code;
import com.shiliuke.bean.Exercise;
import com.shiliuke.bean.UserInfo;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.AdvertisementList;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;
import com.shiliuke.view.imgscroll.MyImgScroll;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 活动
 */
public class FragmentExercise extends Fragment implements VolleyListerner {
    private View rootView;//缓存Fragment view
    private PullableListView exrcise_listview;
    private ExerciseAdapter eAdater;
    private View exrcise_nodate;
    private List<Exercise> list;//活动列表
    private List<AdvertisementList> lista;//广告位图片
    private Activity mActivity = null;
    private MyImgScroll lc_slideshowview_carousel;
    private LinearLayout ovalLayout; // 圆点容器
    private List<View> listViews; // 图片组
    private PullToRefreshLayout exrcise_PullToRefreshLayout;
    private int page = 1;

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


    private void initView(View rootView) {

        mActivity = this.getActivity();
        exrcise_listview = (PullableListView) rootView.findViewById(R.id.exrcise_listview);
        exrcise_nodate = (View) rootView.findViewById(R.id.exrcise_nodate);
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.layout_activity, null);
        exrcise_listview.addHeaderView(inflate);
        lc_slideshowview_carousel = (MyImgScroll) inflate.findViewById(R.id.lc_slideshowview_carousel);
        ovalLayout = (LinearLayout) inflate.findViewById(R.id.vb);
        exrcise_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.exrcise_PullToRefreshLayout);


        exrcise_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                page = 1;
                Map<String, String> params = new HashMap<>();
                params.put("member_id", "1");
                params.put("page", page + "");
                BasicRequest.getInstance().requestPost(FragmentExercise.this, TaskID.ACTION_ACTIVITY, params, AppConfig.ACTIVITY);

            }
            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉刷新操作
                page++;
                Map<String, String> params = new HashMap<>();
                params.put("member_id", "1");
                params.put("page", page + "");
                BasicRequest.getInstance().requestPost(FragmentExercise.this, TaskID.ACTION_ACTIVITY, params, AppConfig.ACTIVITY);
            }
        });
        //自动加载
        exrcise_PullToRefreshLayout.autoRefresh();
        list = new ArrayList<>();
        lista = new ArrayList<>();


    }

    /**
     * 初始化广告位图片
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
            setImage(imageView, lista.get(i).getUrl(), mActivity);
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


    @Override
    public void onResponse(String str, int taskid) {
        analysisJson(str, taskid);
    }

    private void analysisJson(String str, int taskid) {
        if (TextUtils.isEmpty(str)) {
            if (page == 1) {
                exrcise_PullToRefreshLayout.refreshFinish(exrcise_PullToRefreshLayout.SUCCEED);
            } else {
                exrcise_PullToRefreshLayout.loadmoreFinish(exrcise_PullToRefreshLayout.SUCCEED);
            }
            return;
        }
        switch (taskid) {
            case TaskID.ACTION_ACTIVITY:
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(str);
                    Code code = JSON.parseObject(str, Code.class);
                    org.json.JSONObject datas = jsonObject.getJSONObject("datas");

                    if ("0".equals(code.getCode())) {
                        JSONArray advs = datas.getJSONArray("advs");
                        List<AdvertisementList> advertisementList = JSON.parseArray(advs.toString(), AdvertisementList.class);
                        JSONArray activity_list = datas.getJSONArray("activity_list");
                        List<Exercise> exerciseList = JSON.parseArray(activity_list.toString(), Exercise.class);
                        code.setAdvertisementList(advertisementList);

                        for (int i = 0; i < activity_list.length(); i++) {
                            Exercise exercise = exerciseList.get(i);
                            org.json.JSONObject json = activity_list.getJSONObject(i);
                            JSONArray log_list = json.getJSONArray("log_list");
                            List<UserInfo> userInfo = JSON.parseArray(log_list.toString(), UserInfo.class);
                            exercise.setUserInfos(userInfo);
                        }
                        code.setExercise(exerciseList);
                        if (page == 1) {
                            list.clear();
                            list = exerciseList;
                            exrcise_PullToRefreshLayout.refreshFinish(exrcise_PullToRefreshLayout.SUCCEED);
                        } else {
                            list.addAll(exerciseList);
                            exrcise_PullToRefreshLayout.loadmoreFinish(exrcise_PullToRefreshLayout.SUCCEED);
                        }
                        lista.clear();
                        lista = advertisementList;
                        eAdater = new ExerciseAdapter(mActivity, list);
                        exrcise_listview.setAdapter(eAdater);

                        InitViewPager();
                        //开始滚动
                        lc_slideshowview_carousel.start(mActivity, listViews, 4000, ovalLayout,
                                R.layout.ad_bottom_item, R.id.ad_item_v,
                                R.mipmap.ad_select, R.mipmap.ad_normal);

                    } else {
                        String error = datas.optString("error", "");
                        ToastUtil.showShort(mActivity, error);
                        if (page == 1) {
                            exrcise_PullToRefreshLayout.refreshFinish(exrcise_PullToRefreshLayout.SUCCEED);
                        } else {
                            exrcise_PullToRefreshLayout.loadmoreFinish(exrcise_PullToRefreshLayout.SUCCEED);
                        }
                    }


                } catch (JSONException e) {
                    if (page == 1) {
                        exrcise_PullToRefreshLayout.refreshFinish(exrcise_PullToRefreshLayout.SUCCEED);
                    } else {
                        exrcise_PullToRefreshLayout.loadmoreFinish(exrcise_PullToRefreshLayout.SUCCEED);
                    }
                }
                break;
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        switch (taskid) {
            case TaskID.ACTION_ACTIVITY:
                if (page == 1) {
                    exrcise_PullToRefreshLayout.refreshFinish(exrcise_PullToRefreshLayout.SUCCEED);
                } else {
                    exrcise_PullToRefreshLayout.loadmoreFinish(exrcise_PullToRefreshLayout.SUCCEED);
                }
                break;
        }

    }
}
