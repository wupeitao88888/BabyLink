package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shiliuke.base.ActivitySupport;

/**
 * 发布活动
 * Created by wpt on 2015/10/29.
 */
public class IssueActivity extends ActivitySupport implements View.OnClickListener {
    private Button activity_issue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_issue);
        initView();
    }

    private void initView() {
        activity_issue = (Button) findViewById(R.id.activity_issue);
        activity_issue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_issue:
                mIntent(this, SendIssueActivity.class);
                break;
        }
    }


}
