package com.shiliuke.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.Topic;
import com.shiliuke.utils.FaceConversionUtil;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.view.NoScrollGridView;
import com.shiliuke.view.NoScrollListView;

public class TopicAdapter extends BaseAdapter {

    private List<Topic> mList;
    private Context mContext;
    private PopupWindow popWindow;


    public TopicAdapter(final Context _context) {

        this.mContext = _context;

    }

    public void setData(List<Topic> _list) {
        this.mList = _list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Topic getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.friends_circle_item, parent, false);
        }

        NoScrollGridView gridView = com.shiliuke.utils.ViewHolder.get(convertView, R.id.gridView);
        final NoScrollListView noscrolllistview = com.shiliuke.utils.ViewHolder.get(convertView, R.id.noscrolllistview);
        ;
        ImageView comment_image = com.shiliuke.utils.ViewHolder.get(convertView, R.id.comment_image);

        final Topic mUserInfo = getItem(position);
        if (mList != null && mList.size() > 0) {
            gridView.setVisibility(View.VISIBLE);
            gridView.setAdapter(new TopicPicGridAdapter(mUserInfo.getUi(),
                    mContext));
            gridView
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            // imageBrower(position,bean.urls);
//                            ToastUtil.showShort(mContext, commentName);
                        }
                    });
        }

        if (mUserInfo.getCt() != null & mUserInfo.getCt().size() > 0) {
            noscrolllistview.setVisibility(View.VISIBLE);
            noscrolllistview.setAdapter(new TopicCommentListAdapter(mUserInfo.getCt(),
                    mContext));
            final String commentName = mUserInfo.getCt().get(position).getMember_name();
            noscrolllistview
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            // imageBrower(position,bean.urls);
                            ToastUtil.showShort(mContext, commentName);

                        }
                    });
        }
        comment_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPopup(view);

            }
        });


        return convertView;
    }

    private void showPopup(View parent) {
        if (popWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.comment_popu, null);
            // 创建一个PopuWidow对象
            popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        }
        //popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(true);
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(new LevelListDrawable());

        //软键盘不会挡着popupwindow
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置菜单显示的位置
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        //监听菜单的关闭事件
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        //监听触屏事件
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                return false;
            }
        });


    }



}
