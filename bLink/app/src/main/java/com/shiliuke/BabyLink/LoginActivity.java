package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shiliuke.base.ActivitySupport;

/**
 * Created by lc-php1 on 2015/10/26.
 */
public class LoginActivity extends ActivitySupport implements OnClickListener{

    private EditText user_name;
    private EditText password;
    private Button login_Btn;
    private TextView forget_password;
    private TextView qq_login;
    private TextView wx_login;
    private TextView sina_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        initView();

    }

    private void initView() {
        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        login_Btn = (Button) findViewById(R.id.login_Btn);
        forget_password = (TextView) findViewById(R.id.forget_password);
        qq_login = (TextView) findViewById(R.id.qq_login);
        wx_login = (TextView) findViewById(R.id.wx_login);
        sina_login = (TextView) findViewById(R.id.sina_login);
        setCtenterTitle(getResources().getString(R.string.login));
        setRightTitle(getResources().getString(R.string.registe));
        login_Btn.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        wx_login.setOnClickListener(this);
        sina_login.setOnClickListener(this);
        setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent(LoginActivity.this, RegisterActivity.class);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_Btn:
                mIntent(LoginActivity.this, MainTab.class);
            break;
            case R.id.forget_password:
                mIntent(LoginActivity.this, ForgetPassActivity.class);
            break;
            case R.id.qq_login:

            break;
            case R.id.wx_login:

            break;
            case R.id.sina_login:

            break;
        }
    }
}
