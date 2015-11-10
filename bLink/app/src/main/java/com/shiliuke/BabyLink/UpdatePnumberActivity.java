package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shiliuke.base.ActivitySupport;

/**
 * 修改电话
 * Created by wupeitao on 15/11/10.
 */
public class UpdatePnumberActivity extends ActivitySupport implements View.OnClickListener {
    private EditText newPhoneNumber,//新号码
            identifying_code;//请输入验证码
    private Button send_identifying_code,//发送验证码
            submit;//提交


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_pnumber);
        initView();
    }

    private void initView() {
        newPhoneNumber = (EditText) findViewById(R.id.newPhoneNumber);
        identifying_code = (EditText) findViewById(R.id.identifying_code);
        send_identifying_code = (Button) findViewById(R.id.send_identifying_code);
        submit = (Button) findViewById(R.id.submit);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_identifying_code:
                break;
            case R.id.submit:
                break;
        }
    }
}
