package com.shiliuke.BabyLink;

import android.os.Bundle;
import com.shiliuke.base.ActivitySupport;

/**
 * Created by wangzhi on 15/11/8.
 * 发现list详情页面
 */
public class FindDesActivity extends ActivitySupport {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_des);
        initView();
    }

    private void initView() {
        setCtenterTitle("发现详情");

    }


}
