package com.shiliuke.BabyLink;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.ActivityInfo;
import com.shiliuke.bean.Comment;
import com.shiliuke.bean.Image;
import com.shiliuke.bean.UserInfo;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.GlideCircleTransform;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.utils.ViewUtil;
import com.shiliuke.view.NoScrollGridView;
import com.shiliuke.view.NoScrollListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shiliuke.bean.ActivityInfo.*;

/**
 * 活动详情
 * Created by wpt on 2015/10/28.
 */
public class ExerciseActivity extends ActivitySupport implements View.OnClickListener, VolleyListerner {
    private Button exercise_apply_bt,//报名
            comment_on;//评论

    private ImageView exercise_pic,//活动图片
            exercise_authorPic;//发起人头像


    private TextView exercise_title,//活动标题
            activity_address,//活动地址
            begin_time,//begin_time
            max_man,//上限人数
            count,//报名人数
            price,//报名费用
            pay_way,//支付方式
            jihe_address,//集合地点
            utils,//交通工具
            link_name,//联系人
            link_mobile,//联系电话
            exercise_authorName,//发起人
            exercise_content,//活动介绍
            exercise_offer_content,//BabyLink以提供支持
            exercise_offer;//人数报名  报名人数（10人）


    private NoScrollGridView exercise_gridView;
    private NoScrollListView exercise_noscrolllistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_exercise);
        initView();
    }

    private void initView() {
        exercise_apply_bt = (Button) findViewById(R.id.exercise_apply_bt);
        comment_on = (Button) findViewById(R.id.comment_on);

        exercise_pic = (ImageView) findViewById(R.id.exercise_pic);
        exercise_authorPic = (ImageView) findViewById(R.id.exercise_authorPic);

        exercise_title = (TextView) findViewById(R.id.exercise_title);
        activity_address = (TextView) findViewById(R.id.activity_address);
        begin_time = (TextView) findViewById(R.id.begin_time);
        max_man = (TextView) findViewById(R.id.max_man);
        count = (TextView) findViewById(R.id.count);
        price = (TextView) findViewById(R.id.price);
        pay_way = (TextView) findViewById(R.id.pay_way);
        jihe_address = (TextView) findViewById(R.id.jihe_address);
        utils = (TextView) findViewById(R.id.utils);
        link_name = (TextView) findViewById(R.id.link_name);
        link_mobile = (TextView) findViewById(R.id.link_mobile);
        exercise_authorName = (TextView) findViewById(R.id.exercise_authorName);
        exercise_content = (TextView) findViewById(R.id.exercise_content);
        exercise_offer_content = (TextView) findViewById(R.id.exercise_offer_content);
        exercise_offer = (TextView) findViewById(R.id.exercise_offer);

        exercise_gridView = (NoScrollGridView) findViewById(R.id.exercise_gridView);
        exercise_noscrolllistview = (NoScrollListView) findViewById(R.id.exercise_noscrolllistview);

        exercise_apply_bt.setOnClickListener(this);
        comment_on.setOnClickListener(this);
        initNet();
    }

    private void initNet() {
        Map<String, String> params = new HashMap<>();
        params.put("member_id", "1");
        params.put("activity_id", 2 + "");
        BasicRequest.getInstance().requestPOST(ExerciseActivity.this, TaskID.ACTION_ACTIVITY_INFO, params, AppConfig.ACTIVITY_INFO, ActivityInfo.class);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exercise_apply_bt:
                mIntent(this, ApplyActivity.class);
                break;
            case R.id.comment_on:
                break;
        }
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        switch (taskid) {
            case TaskID.ACTION_ACTIVITY_INFO:
                ActivityInfo activityInfoCode = (ActivityInfo) obj;
                List<String> images = activityInfoCode.getDates().getImages();
                ActivityInfo.Dates  activityInfo= activityInfoCode.getDates();
                if (images.size() > 0)
                    ViewUtil.setImage(exercise_pic, images.get(0), context);
                ViewUtil.setImage(exercise_authorPic, activityInfo.getMember_avar(), context);
                ViewUtil.setText(context, exercise_title, activityInfo.getTitle());
                if (TextUtils.isEmpty(activityInfo.getActivity_address()))
                    ViewUtil.setText(context, activity_address, "活动地点：" + activityInfo.getActivity_address());
                else
                    activity_address.setVisibility(View.GONE);
                if (TextUtils.isEmpty(activityInfo.getBegin_time()))
                    ViewUtil.setText(context, begin_time, "活动时间：" + activityInfo.getBegin_time());
                else
                    begin_time.setVisibility(View.GONE);
                if (TextUtils.isEmpty(activityInfo.getMax_man()))
                    ViewUtil.setText(context, max_man, "上限人数：" + activityInfo.getMax_man());
                else
                    max_man.setVisibility(View.GONE);

                if (TextUtils.isEmpty(activityInfo.getCount()))
                    ViewUtil.setText(context, count, activityInfo.getCount());
                else
                    count.setVisibility(View.GONE);

                if (TextUtils.isEmpty(activityInfo.getPrice()))
                    ViewUtil.setText(context, price, "￥" + activityInfo.getPrice());
                else
                    price.setVisibility(View.GONE);

                if (TextUtils.isEmpty(activityInfo.getPay_way()))
                    ViewUtil.setText(context, pay_way, "支付方式：" + activityInfo.getPay_way());
                else
                    pay_way.setVisibility(View.GONE);

                if (TextUtils.isEmpty(activityInfo.getJihe_address()))
                    ViewUtil.setText(context, jihe_address, "集合地方：" + activityInfo.getJihe_address());
                else
                    jihe_address.setVisibility(View.GONE);

                if (TextUtils.isEmpty(activityInfo.getUtils()))
                    ViewUtil.setText(context, utils, "交通工具：" + activityInfo.getUtils());
                else
                    utils.setVisibility(View.GONE);

                if (TextUtils.isEmpty(activityInfo.getLink_name()))
                    ViewUtil.setText(context, link_name, "联  系人：" + activityInfo.getLink_name());
                else
                    link_name.setVisibility(View.GONE);

                if (TextUtils.isEmpty(activityInfo.getLink_mobile()))
                    ViewUtil.setText(context, link_mobile, "联  系人：" + activityInfo.getLink_mobile());
                else
                    link_mobile.setVisibility(View.GONE);
                ViewUtil.setText(context, exercise_authorName, activityInfo.getMember_name());
                ViewUtil.setText(context, exercise_content, activityInfo.getInfo());
                ViewUtil.setText(context, exercise_offer_content, activityInfo.getHelp());
                ViewUtil.setText(context, exercise_offer, "参与人数：" + activityInfo.getCount());
                break;
        }

    }


    @Override
    public void onResponseError(String error, int taskid) {
        switch (taskid) {
            case TaskID.ACTION_ACTIVITY_INFO:
                break;
        }
    }
}
