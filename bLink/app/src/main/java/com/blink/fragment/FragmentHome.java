package com.blink.fragment;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.blink.main.R;
import com.blink.view.PullScrollView;

public class FragmentHome extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private View rootView;//缓存Fragment view
    private Activity mActivity = null;
    private PullScrollView meCommunity_PullScrollView;
    private FragmentTabHost mTabHost = null;
    private ViewPager id_viewpager;
    private FragmentExercise fe = new FragmentExercise();
    private FragmentTopic ft = new FragmentTopic();
    private FragmentChange fc = new FragmentChange();
    private Fragment[] fragments = {fe, ft, fc};
    private boolean[] fragmentsUpdateFlag = {false, false, false};
    private FragmentPagerAdapter adapter;
    private RelativeLayout tab_exrcise;
    private RelativeLayout tab_topic;
    private RelativeLayout tab_change;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            initView(rootView);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView(View rootView) {
        mActivity = this.getActivity();
        meCommunity_PullScrollView = (PullScrollView) rootView.findViewById(R.id.meCommunity_PullScrollView);
        View bodyview = LayoutInflater.from(mActivity).inflate(R.layout.layout_community, null);
        meCommunity_PullScrollView.addBodyView(bodyview);
      tab_exrcise = (RelativeLayout) bodyview.findViewById(R.id.tab_exrcise);
        tab_topic = (RelativeLayout) bodyview.findViewById(R.id.tab_topic);
        tab_change = (RelativeLayout) bodyview.findViewById(R.id.tab_change);
        tab_exrcise.setOnClickListener(this);
        tab_topic.setOnClickListener(this);
        tab_change.setOnClickListener(this);
        id_viewpager = (ViewPager) bodyview.findViewById(R.id.id_viewpager);
        adapter = new MyFragmentPagerAdapter(getFragmentManager());
        id_viewpager.setAdapter(adapter);
        id_viewpager.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_exrcise:
                id_viewpager.setCurrentItem(0);
                tab_exrcise.setSelected(true);
                tab_topic.setSelected(false);
                tab_change.setSelected(false);
                break;
            case R.id.tab_topic:
                id_viewpager.setCurrentItem(1);
                tab_exrcise.setSelected(false);
                tab_topic.setSelected(true);
                tab_change.setSelected(false);
                break;
            case R.id.tab_change:
                id_viewpager.setCurrentItem(2);
                tab_exrcise.setSelected(false);
                tab_topic.setSelected(false);
                tab_change.setSelected(true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        FragmentManager fm;

        MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragments[position % fragments.length];
            return fragments[position % fragments.length];
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //得到缓存的fragment
            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);
            //得到tag，这点很重要
            String fragmentTag = fragment.getTag();

            if (fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]) {
                //如果这个fragment需要更新

                FragmentTransaction ft = fm.beginTransaction();
                //移除旧的fragment
                ft.remove(fragment);
                //换成新的fragment
                fragment = fragments[position % fragments.length];
                //添加新fragment时必须用前面获得的tag，这点很重要
                ft.add(container.getId(), fragment, fragmentTag);
                ft.attach(fragment);
                ft.commit();

                //复位更新标志
                fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
            }

            return fragment;
        }
    }
}
