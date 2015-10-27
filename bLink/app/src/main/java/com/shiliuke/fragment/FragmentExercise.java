package com.shiliuke.fragment;


import com.shiliuke.adapter.ExerciseAdapter;
import com.shiliuke.bean.Exercise;
import com.shiliuke.bean.UserInfo;
import com.shiliuke.BabyLink.R;
import com.shiliuke.utils.DataService;
import com.shiliuke.utils.L;
import com.shiliuke.view.PullToRefreshTopLayout;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;
import github.chenupt.multiplemodel.ModelListAdapter;

/**
 * 活动
 */
public class FragmentExercise extends Fragment {
    private View rootView;//缓存Fragment view
    private ListView exrcise_listview;
        private ExerciseAdapter eAdater;
    private ViewStub exrcise_nodate;
    private List<Exercise> list;
    private Activity mActivity = null;
    //    AndroidAdapter androidAdapter;
    private ModelListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_exercise, null);
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
        L.e("++++++++++++++++++++++++++++++++++++++++");
        exrcise_listview = (ListView) rootView.findViewById(R.id.exrcise_listview);
        exrcise_nodate = (ViewStub) rootView.findViewById(R.id.exrcise_nodate);

        list = new ArrayList<>();
        mActivity = this.getActivity();
        for (int i = 0; i < 10; i++) {
            Exercise ec = new Exercise();
            ec.setExercise_address("北京朝阳");
            ec.setExercise_author("小小明");
            ec.setExercise_authorName("小小明");
            ec.setExercise_authorPic("http://m1.img.srcdd.com/farm2/d/2011/0817/01/5A461954F44D8DC67A17838AA356FE4B_S64_64_64.JPEG");
            ec.setExercise_pic("http://m1.img.srcdd.com/farm2/d/2011/0817/01/5A461954F44D8DC67A17838AA356FE4B_S64_64_64.JPEG");
            ec.setExercise_time("2015/12/9");
            ec.setExercise_usercount("60");
            ec.setExercise_title("举报成龙");
            List<UserInfo> uinfoList = new ArrayList<>();
            for (int p = 0; p < 5; p++) {
                UserInfo ui = new UserInfo();
                ui.setUserpic("http://m1.img.srcdd.com/farm2/d/2011/0817/01/5A461954F44D8DC67A17838AA356FE4B_S64_64_64.JPEG");
                uinfoList.add(ui);
            }
            ec.setExercise_signlist(uinfoList);
            list.add(ec);
        }

        L.e("个数：" + list.size());
        eAdater = new ExerciseAdapter(mActivity, list);
        exrcise_listview.setAdapter(eAdater);
        exrcise_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                EventBus.getDefault().post(AttachUtil.isAdapterViewAttach(view));

            }
        });

    }


    public void exerciseRefresh(){

    }
}
