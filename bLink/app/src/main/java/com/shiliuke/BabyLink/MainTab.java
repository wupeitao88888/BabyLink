package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.shiliuke.base.ActivitySupport;
import com.shiliuke.fragment.FragmentFind;
import com.shiliuke.fragment.FragmentHome;
import com.shiliuke.fragment.FragmentBeanShow;
import com.shiliuke.fragment.FragmentMy;
import com.shiliuke.fragment.FragmentSend;
import com.shiliuke.utils.L;
import com.shiliuke.view.LCDialog;
import com.shiliuke.view.TabFragmentHost;


public class MainTab extends ActivitySupport {

    private long exitTime = 0;
    public TabFragmentHost mTabHost;
    // 标签
    private String[] TabTag = {"tab1", "tab2", "tab3", "tab4", "tab5"};
    // 自定义tab布局显示文本和顶部的图片
    private Integer[] ImgTab = {R.layout.tab_main_home,
            R.layout.tab_main_beanshow, R.layout.tab_main_send, R.layout.tab_main_find, R.layout.tab_main_my};

    // tab 选中的activity
    private Class[] ClassTab = {FragmentHome.class, FragmentBeanShow.class, FragmentSend.class,
            FragmentFind.class, FragmentMy.class};

    // tab选中背景 drawable 样式图片 背景都是同一个,背景颜色都是 白色。。。
    private Integer[] StyleTab = {R.color.white, R.color.white, R.color.white,
            R.color.white, R.color.white};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintabs);
        setRemoveTitle();
        setupView();
        initValue();
        setLinstener();
        fillData();
    }

    private void setupView() {

        // 实例化framentTabHost
        mTabHost = (TabFragmentHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(),
                android.R.id.tabcontent);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (s.equals("tab3")) {
                    mTabHost.setCurrentTab(0);
                    showDialog();
                }
            }
        });
    }

    private void initValue() {

        // 初始化tab选项卡
        InitTabView();
    }

    private void setLinstener() {
        // imv_back.setOnClickListener(this);

    }

    private void fillData() {
        // TODO Auto-generated method stub

    }


    // 初始化 tab 自定义的选项卡 ///////////////
    private void InitTabView() {

        // 可以传递参数 b;传递公共的userid,version,sid
        Bundle b = new Bundle();
        // 循环加入自定义的tab
        for (int i = 0; i < TabTag.length; i++) {
            // 封装的自定义的tab的样式
            View indicator = getIndicatorView(i);
            mTabHost.addTab(
                    mTabHost.newTabSpec(TabTag[i]).setIndicator(indicator),
                    ClassTab[i], b);// 传递公共参数

        }
        mTabHost.getTabWidget().setDividerDrawable(R.color.white);
    }

    // 设置tab自定义样式:注意 每一个tab xml子布局的linearlayout 的id必须一样
    private View getIndicatorView(int i) {
        // 找到tabhost的子tab的布局视图
        View v = getLayoutInflater().inflate(ImgTab[i], null);
        LinearLayout tv_lay = (LinearLayout) v.findViewById(R.id.layout_back);
        tv_lay.setBackgroundResource(StyleTab[i]);

        return v;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return false;
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast(getResources().getString(R.string.exit));
            exitTime = System.currentTimeMillis();
        } else {
            isExit();
        }
    }

    public LCDialog showDialog() {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_addmenu,
                null);
        final LCDialog dialog = new LCDialog(MainTab.this, R.style.MyDialog, dialogView);
        ImageView publish_activity = (ImageView) dialogView.findViewById(R.id.publish_activity);
        ImageView publish_topic = (ImageView) dialogView.findViewById(R.id.publish_topic);
        ImageView publish_show = (ImageView) dialogView.findViewById(R.id.publish_show);
        ImageView publish_swap = (ImageView) dialogView.findViewById(R.id.publish_swap);
        dialog.show();

        publish_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mIntent(MainTab.this, IssueActivity.class);
                dialog.cancel();
            }
        });
        publish_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent(MainTab.this, SendTopic.class);
                dialog.cancel();
            }
        });
        publish_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent(MainTab.this, SendShowActivity.class);
                dialog.cancel();
            }
        });
        publish_swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent(MainTab.this, SendChange.class);
                dialog.cancel();
            }
        });
        return dialog;
    }
}
