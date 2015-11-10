package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.shiliuke.base.ActivitySupport;

/**
 * 修改密码
 * Created by wupeitao on 15/11/10.
 */
public class UpdatePWActivity extends ActivitySupport {
    private EditText old_pw,
            new_pw,
            re_new_pw;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_updatepw);
        initView();
    }

    private void initView() {
        old_pw = (EditText) findViewById(R.id.old_pw);
        new_pw = (EditText) findViewById(R.id.new_pw);
        re_new_pw = (EditText) findViewById(R.id.re_new_pw);
        submit = (Button) findViewById(R.id.submit);
    }
}
