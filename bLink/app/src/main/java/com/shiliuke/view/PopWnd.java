package com.shiliuke.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import com.shiliuke.BabyLink.R;

public class PopWnd {
    protected OnClickListener mPlayMenuItemClick;// 监听事件
    protected PopupWindow popupWindow;
    protected Context context;
    protected View mPopBg;// 背景

    /**
     * @param ctx        上下文
     * @param resourceId 布局文件
     * @param idList     viewId
     * @param itemClick  监听器
     */
    public PopWnd(Context ctx, int resourceId, int[] idList,
                  OnClickListener itemClick) {
        this(ctx, resourceId, idList, itemClick, null, true);
    }
    /**
     * @param ctx        上下文
     * @param resourceId 布局文件
     * @param idList     viewId
     * @param itemClick  监听器
     */
    public PopWnd(Context ctx, int resourceId, int[] idList,
                  OnClickListener itemClick,View popBg) {
        this(ctx, resourceId, idList, itemClick, popBg, true);
    }
    /**
     * @param ctx           上下文
     * @param resourceId    布局文件
     * @param idList        viewId
     * @param itemClick     监听器
     * @param popBg         背景
     * @param isWrapContent 是否是包裹内容
     */
    @SuppressWarnings("deprecation")
    public PopWnd(Context ctx, int resourceId, int[] idList,
                  final OnClickListener itemClick, View popBg, boolean isWrapContent) {
        context = ctx;
        mPopBg = popBg;
        View view = LayoutInflater.from(ctx).inflate(resourceId, null);
        for (int i = 0; i < idList.length; i++) {
            view.findViewById(idList[i]).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dissmiss();
                            itemClick.onClick(v);
                        }
                    });
        }
        if (isWrapContent) {
            popupWindow = new PopupWindow(view,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        } else {
            popupWindow = new PopupWindow(view,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                if (mPopBg != null) {
                    mPopBg.setVisibility(View.GONE);
                }
            }
        });

    }

    public void dissmiss() {
        popupWindow.dismiss();
    }

    public void showPopWindow(View v) {
        if (mPopBg != null) {
            mPopBg.setVisibility(View.VISIBLE);
        }
        popupWindow.showAsDropDown(v);
    }

}
