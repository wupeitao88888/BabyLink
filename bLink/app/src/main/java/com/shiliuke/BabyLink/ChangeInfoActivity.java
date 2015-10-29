package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shiliuke.base.ActivitySupport;

/**
 * Created by wpt on 2015/10/29.
 */
public class ChangeInfoActivity extends ActivitySupport implements View.OnClickListener {
    private Button changeinfo_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_changeinfo);
        changeinfo_submit = (Button) findViewById(R.id.changeinfo_submit);
        changeinfo_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changeinfo_submit:
                break;
        }
    }
}
