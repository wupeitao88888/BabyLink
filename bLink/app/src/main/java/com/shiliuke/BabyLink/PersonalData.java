package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.shiliuke.base.ActivitySupport;

/**
 * 个人资料
 * Created by wupeitao on 15/11/10.
 */
public class PersonalData extends ActivitySupport implements View.OnClickListener {

    private RelativeLayout handpic_re,//用户头像
            nickname_re,//用户昵称
            updatePnumber_re,//修改手机号
            updatePW_re;//修改密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_userinfo);
        initView();
    }

    private void initView() {
        handpic_re = (RelativeLayout) findViewById(R.id.handpic_re);
        nickname_re = (RelativeLayout) findViewById(R.id.nickname_re);
        updatePnumber_re = (RelativeLayout) findViewById(R.id.updatePnumber_re);
        updatePW_re = (RelativeLayout) findViewById(R.id.updatePW_re);
        handpic_re.setOnClickListener(this);
        nickname_re.setOnClickListener(this);
        updatePnumber_re.setOnClickListener(this);
        updatePW_re.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.handpic_re:
                break;
            case R.id.nickname_re:
                break;
            case R.id.updatePnumber_re:
                mIntent(context, UpdatePnumberActivity.class);
                break;
            case R.id.updatePW_re:
                mIntent(context, UpdatePWActivity.class);
                break;
        }
    }
}
