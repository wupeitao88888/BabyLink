package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.shiliuke.base.ActivitySupport;

/**
 * Created by lc-php1 on 2015/10/26.
 */
public class RegisterActivity extends ActivitySupport {

    private TextView user_service ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);
        initView();
    }

    private void initView() {
        user_service = (TextView) findViewById(R.id.user_service);
        user_service.setText(Html.fromHtml(getResources().getString(R.string.user_service)).toString());
    }
}
