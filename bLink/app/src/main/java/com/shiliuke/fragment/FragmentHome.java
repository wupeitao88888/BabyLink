package com.shiliuke.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.shiliuke.BabyLink.R;
import com.shiliuke.utils.FragmentEvent;
import com.shiliuke.utils.L;
import com.shiliuke.utils.MSG;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.utils.ViewUtil;
import com.shiliuke.view.IndexViewPager;
import com.shiliuke.view.PullScrollView;


public class FragmentHome extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener,FragmentEvent.OnEventListener{
    private View rootView;
    private Activity mActivity = null;
    private RelativeLayout meCommunity_PullScrollView;
    private FragmentTabHost mTabHost = null;
    private IndexViewPager id_viewpager;
    private FragmentExercise fe = new FragmentExercise();
    private FragmentTopic ft = new FragmentTopic();
    private FragmentChange fc = new FragmentChange();
    private Fragment[] fragments = {fe, ft, fc};
    private boolean[] fragmentsUpdateFlag = {false, false, false};
    private FragmentPagerAdapter adapter;
    private RelativeLayout tab_exrcise;
    private RelativeLayout tab_topic;
    private RelativeLayout tab_change;

    Handler mainHandler = new Handler() {

        /*
         * （非 Javadoc）
         *
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg) {
            // TODO 自动生成的方法存根
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG.INTO_05:
                    fragments[3] = fc;
                    fragmentsUpdateFlag[3] = true;
                    adapter.notifyDataSetChanged();
                    break;
                default:
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            initView(rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView(View rootView) {
        mActivity = this.getActivity();
        meCommunity_PullScrollView = (RelativeLayout) rootView.findViewById(R.id.meCommunity_PullScrollView);
//        dragLayout = (DragTopLayout) rootView.findViewById(R.id.drag_layout);
        tab_exrcise = (RelativeLayout) rootView.findViewById(R.id.tab_exrcise);
        tab_topic = (RelativeLayout) rootView.findViewById(R.id.tab_topic);
        tab_change = (RelativeLayout) rootView.findViewById(R.id.tab_change);
        tab_exrcise.setOnClickListener(this);
        tab_topic.setOnClickListener(this);
        tab_change.setOnClickListener(this);
        id_viewpager = (IndexViewPager) rootView.findViewById(R.id.id_viewpager);
        adapter = new MyFragmentPagerAdapter(getFragmentManager());
        id_viewpager.setAdapter(adapter);
        id_viewpager.setOnPageChangeListener(this);
        id_viewpager.setCurrentItem(0);
        tab_exrcise.setSelected(true);
        tab_topic.setSelected(false);
        tab_change.setSelected(false);
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

    @Override
    public void onEvent(int what, Bundle data, Object object) {
        mainHandler.sendEmptyMessage(what);
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
        public void destroyItem(ViewGroup container, int position, Object object) {
        //重载该方法，防止其它视图被销毁，防止加载视图卡顿
        // super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);

            String fragmentTag = fragment.getTag();

            if (fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]) {


                FragmentTransaction ft = fm.beginTransaction();

                ft.remove(fragment);

                fragment = fragments[position % fragments.length];

                ft.add(container.getId(), fragment, fragmentTag);
                ft.attach(fragment);
                ft.commit();


                fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
            }

            return fragment;
        }
    }
}
