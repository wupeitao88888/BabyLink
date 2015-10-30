package com.shiliuke.fragment;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.adapter.ExerciseAdapter;
import com.shiliuke.bean.Exercise;
import com.shiliuke.bean.UserInfo;
import com.shiliuke.BabyLink.R;
import com.shiliuke.utils.AdvertisementList;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.utils.ViewUtil;
import com.shiliuke.view.LCSlideShowView;
import com.shiliuke.view.LCViewPageSelectListener;
import com.shiliuke.view.NoScrollListView;
import com.shiliuke.view.PullScrollView;
import com.shiliuke.view.PullToRefresh.PullableListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 活动
 */
public class FragmentExercise extends Fragment implements LCViewPageSelectListener {
    private View rootView;//缓存Fragment view
    private NoScrollListView exrcise_listview;
    private ExerciseAdapter eAdater;
    private ViewStub exrcise_nodate;
    private List<Exercise> list;
    private Activity mActivity = null;
    private LCSlideShowView advertisement;
    private LinearLayout lc_slideshowview_select;
    private List<AdvertisementList> listurl;
    private ImageView[] dots;
    private PullScrollView pullScrollView;
    private View inflate;

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
        pullScrollView = (PullScrollView) rootView.findViewById(R.id.pullScrollView);
        inflate = LayoutInflater.from(mActivity).inflate(R.layout.layout_activity, null);
        pullScrollView.addBodyView(inflate);
        exrcise_listview = (NoScrollListView) inflate.findViewById(R.id.exrcise_listview);
        exrcise_nodate = (ViewStub) inflate.findViewById(R.id.exrcise_nodate);

        list = new ArrayList<>();
        List<AdvertisementList> lista = new ArrayList<>();
        for (int i = 0; i <5; i++) {
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
        listurl = new ArrayList<AdvertisementList>();

//        for (int i = 0; i < 5; i++) {
//            AdvertisementList al = new AdvertisementList();
//            if ((i & 1) != 0)
//                al.setMedia_url("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic14.nipic.com%2F20110522%2F7411759_164157418126_2.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1183223528%2C3058066243%26fm%3D21%26gp%3D0.jpg");
//            else
//                al.setMedia_url("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fwww.xxjxsj.cn%2Farticle%2FUploadPic%2F2009-10%2F2009101018545196251.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D2199763593%2C4070991891%26fm%3D21%26gp%3D0.jpg");
//            list.add(al);
//        }
        initCarousel(lista);
    }


    private void initCarousel(final List<AdvertisementList> list) {
        advertisement = (LCSlideShowView) inflate.findViewById(R.id.lc_slideshowview_carousel);
        lc_slideshowview_select = (LinearLayout) inflate.findViewById(R.id.lc_slideshowview_select);
        advertisement.setIsAutoPlay(true);
        advertisement.setCirculation(true);
        List<View> imageUris = new ArrayList<View>();
        listurl = list;
        advertisement.clear();
        lc_slideshowview_select.removeAllViews();
        dots = new ImageView[listurl.size()];
        for (int i = 0; i < listurl.size(); i++) {
            ImageView imageView = new ImageView(mActivity);
            final AdvertisementList advertisementList = listurl.get(i);
            setImage(imageView, advertisementList.getMedia_url(), mActivity);
            RelativeLayout re = new RelativeLayout(mActivity);
            RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(imageParams);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ViewUtil.dip2px(mActivity, 201));
            re.setLayoutParams(params);
            re.addView(imageView);
            re.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(advertisementList.getAds_url())) {
//                        Intent intent = new Intent(mActivity, LCAdWebViewActiviy.class);
//                        intent.putExtra("url", advertisementList.getAds_url());
//                        startActivity(intent);
                        ToastUtil.showShort(mActivity, "没什么好看的！");
                    } else {
                        ToastUtil.showShort(mActivity, "没什么好看的！");
                    }
                }
            });
            imageUris.add(re);
            ImageView imageSelct = new ImageView(mActivity);
            imageSelct.setImageDrawable(this.getResources().getDrawable(R.drawable.ad_selector));
            dots[i] = imageSelct;
            int padding = (int) ViewUtil.dip2px(mActivity, 3);
            int h = (int) ViewUtil.dip2px(mActivity, 10);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(h, h);
            layoutParams.setMargins(padding, padding, padding, padding);
            imageSelct.setLayoutParams(layoutParams);
            lc_slideshowview_select.addView(imageSelct);
        }
        advertisement.setImageUris(imageUris);
        advertisement.setJXBViewPageSelectListener(this);
        if (listurl.size() > 0) {
            dots[0].setSelected(true);
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
    public void succeedCallBack(int p) {
        changeDots(p);
    }

    @Override
    public void succeedEndCallBack(int p) {

    }

    public void changeDots(int position) {

        for (int i = 0; i < dots.length; i++) {
            dots[i].setSelected(position == i);
        }
    }
}
