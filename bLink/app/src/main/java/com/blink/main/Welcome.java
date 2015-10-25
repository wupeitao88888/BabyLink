package com.blink.main;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.blink.base.ActivitySupport;

public class Welcome extends ActivitySupport implements View.OnClickListener {
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_welcome);
        setRemoveTitle();
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login:
                mIntent(Welcome.this,MainTab.class);
                break;

        }
    }
}
