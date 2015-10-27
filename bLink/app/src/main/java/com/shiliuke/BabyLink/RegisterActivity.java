package com.shiliuke.BabyLink;

import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shiliuke.base.ActivitySupport;

/**
 * Created by lc-php1 on 2015/10/26.
 */
public class RegisterActivity extends ActivitySupport implements OnClickListener{

    private TextView user_service ;
    private Button get_code ;
    private Button register_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);
        initView();
    }

    private void initView() {
        user_service = (TextView) findViewById(R.id.user_service);
        get_code = (Button) findViewById(R.id.get_code);
        register_Btn = (Button) findViewById(R.id.register_Btn);
        register_Btn.setText(getResources().getString(R.string.registe));
        setCtenterTitle(getResources().getString(R.string.registe));
        user_service.setText(Html.fromHtml("点击注册即同意《<font color= #FF0000 >用户服务协议</font>》"));
        get_code.setOnClickListener(this);
        register_Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_Btn:
                Toast.makeText(this,"注册",Toast.LENGTH_SHORT).show();
                break;
            case R.id.get_code:
                Toast.makeText(this,"获取验证码",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
