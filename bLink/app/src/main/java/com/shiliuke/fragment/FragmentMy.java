package com.shiliuke.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.shiliuke.BabyLink.MeActivity;
import com.shiliuke.BabyLink.MeTopic;
import com.shiliuke.BabyLink.R;


public class FragmentMy extends Fragment implements OnClickListener {

    private RelativeLayout user_icon_rl,//用户信息
            user_activity_re,//我的活动
            user_topic_re,//我的话题
            user_change_re,//我的置换
            user_showbean_re,//我的秀逗
            user_friends_re//我的朋友
                    ;
    private ImageView user_icon_image;//用户头像


    private TextView user_name;//用户姓名
    private View rootView;
    private Activity mActivity = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (rootView == null) {
            rootView = initView();

        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private View initView() {
        mActivity = getActivity();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_my, null);
        user_icon_rl = (RelativeLayout) view.findViewById(R.id.user_activity_re);
        user_friends_re = (RelativeLayout) view.findViewById(R.id.user_friends_re);
        user_activity_re = (RelativeLayout) view.findViewById(R.id.user_activity_re);
        user_topic_re = (RelativeLayout) view.findViewById(R.id.user_topic_re);
        user_change_re = (RelativeLayout) view.findViewById(R.id.user_change_re);
        user_showbean_re = (RelativeLayout) view.findViewById(R.id.user_showbean_re);

        user_icon_rl.setOnClickListener(this);
        user_friends_re.setOnClickListener(this);
        user_activity_re.setOnClickListener(this);
        user_topic_re.setOnClickListener(this);
        user_change_re.setOnClickListener(this);
        user_showbean_re.setOnClickListener(this);




        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon_rl:
                // 我的信息
                break;
            case R.id.user_activity_re:
                // 我的活动
                Intent intent=new Intent(mActivity, MeActivity.class);
                startActivity(intent);
                break;
            case R.id.user_topic_re:
                // 我的话题
                startActivity(new Intent(mActivity, MeTopic.class));
                break;
            case R.id.user_change_re:
                // 我的置换
                break;
            case R.id.user_showbean_re:
                // 我的秀逗
                break;
            case R.id.user_friends:
                // 我的朋友
                break;

        }
    }
}
