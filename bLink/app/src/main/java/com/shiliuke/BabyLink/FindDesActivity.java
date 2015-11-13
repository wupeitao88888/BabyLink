package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.shiliuke.base.ActivitySupport;

/**
 * Created by wangzhi on 15/11/8.
 * 发现list详情页面
 */
public class FindDesActivity extends ActivitySupport {

    private TextView tv_find_des_invite;
    private Button btn_find_des_reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_des);
        initView();
    }

    private void initView() {
        setCtenterTitle("发现详情");
        btn_find_des_reserve = (Button) findViewById(R.id.btn_find_des_reserve);
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.tv_find_des_invite:
                startActivity(new Intent(this, InvitefriendsActivity.class));
                break;
            case R.id.btn_find_des_reserve:
                Intent intent = new Intent(this, PayMentActivity.class);
                intent.putExtra(PayMentActivity.PARAMS_TITLE, "保养费");
                intent.putExtra(PayMentActivity.PARAMS_PRICE, "1000");
                startActivity(intent);
                break;
        }
    }
}
