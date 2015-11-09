package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiliuke.base.ActivitySupport;

/**
 * Created by wupeitao on 15/11/5.
 */
public class MeHomePage extends ActivitySupport implements View.OnClickListener {
    private RelativeLayout user_icon_rl,//用户信息
            user_activity_re,//我的活动
            user_topic_re,//我的话题
            user_change_re,//我的置换
            user_showbean_re,//我的秀逗
            user_friends_re//我的朋友
                    ;
    private ImageView user_icon_image;//用户头像


    private TextView user_name;//用户姓名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_homepage);
        initView();
    }
    private void initView() {


        user_icon_rl = (RelativeLayout) findViewById(R.id.user_activity_re);
        user_friends_re = (RelativeLayout)findViewById(R.id.user_friends_re);
        user_activity_re = (RelativeLayout)findViewById(R.id.user_activity_re);
        user_topic_re = (RelativeLayout)findViewById(R.id.user_topic_re);
        user_change_re = (RelativeLayout)findViewById(R.id.user_change_re);
        user_showbean_re = (RelativeLayout)findViewById(R.id.user_showbean_re);

        user_icon_rl.setOnClickListener(this);
        user_friends_re.setOnClickListener(this);
        user_activity_re.setOnClickListener(this);
        user_topic_re.setOnClickListener(this);
        user_change_re.setOnClickListener(this);
        user_showbean_re.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon_rl:
                // 我的信息
                break;
            case R.id.user_activity_re:
                // 我的活动
                Intent intent=new Intent(context, MeActivity.class);
                startActivity(intent);
                break;
            case R.id.user_topic_re:
                // 我的话题
                startActivity(new Intent(context, MeTopic.class));
                break;
            case R.id.user_change_re:
                // 我的置换
                mIntent(context,MeChange.class);
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
