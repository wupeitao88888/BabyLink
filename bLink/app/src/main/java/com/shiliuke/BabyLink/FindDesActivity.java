package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.shiliuke.base.ActivitySupport;

/**
 * Created by wangzhi on 15/11/8.
 * 发现list详情页面
 */
public class FindDesActivity extends ActivitySupport {

    private TextView tv_find_des_invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_des);
        initView();
    }

    private void initView() {
        setCtenterTitle("发现详情");
    }

    public void click(View v){
        switch (v.getId()){
            case R.id.tv_find_des_invite:
                startActivity(new Intent(this,InvitefriendsActivity.class));
                break;
        }
    }

}
