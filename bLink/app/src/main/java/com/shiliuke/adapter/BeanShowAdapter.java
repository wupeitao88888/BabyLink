package com.shiliuke.adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.BeanShowModel;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.view.stickerview.StickerImageContans;
import com.shiliuke.view.stickerview.StickerImageView;

import java.util.ArrayList;

/**
 * Created by wangzhi on 15/10/30.
 * 秀逗页面List Adapter
 */
public class BeanShowAdapter extends BaseAdapter {
    private View footView;
    private ArrayList<BeanShowModel.BeanShowModelResult> data;
    private Fragment context;
    private Boolean isLink = true;

    public BeanShowAdapter(View footView, Fragment context, ArrayList<BeanShowModel.BeanShowModelResult> data, Boolean isLink) {
        this.footView = footView;
        this.context = context;
        this.data = data;
        this.isLink = isLink;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.e("tag", "getview position=" + position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context.getActivity()).inflate(R.layout.item_beanshow, null);
        }
        final BeanShowModel.BeanShowModelResult model = data.get(position);
        ImageView image_beanshow_item_head = ViewHolder.get(convertView, R.id.image_beanshow_item_head);
        TextView tv_beanshow_item_name = ViewHolder.get(convertView, R.id.tv_beanshow_item_name);
        TextView tv_beanshow_item_time = ViewHolder.get(convertView, R.id.tv_beanshow_item_time);
        TextView tv_beanshow_item_dou = ViewHolder.get(convertView, R.id.tv_beanshow_item_dou);
        TextView tv_beanshow_item_msg = ViewHolder.get(convertView, R.id.tv_beanshow_item_msg);
        ImageView sticker_image_beanshow_item = ViewHolder.get(convertView, R.id.sticker_image_beanshow_item);
        final StickerImageView sticker_beanshow_item = ViewHolder.get(convertView, R.id.sticker_beanshow_item);
        sticker_beanshow_item.setTag(position);
        Glide.with(context).load(model.getMember_avar()).into(image_beanshow_item_head);
        Glide.with(context).load(model.getImage_url()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                sticker_beanshow_item.setBeanShowModel(model);
                if (position != 0) {
                    model.revertAlpha();
                }
                sticker_beanshow_item.startAnim();
                return false;
            }
        }).into(sticker_image_beanshow_item);
        tv_beanshow_item_name.setText(model.getMember_name());
        tv_beanshow_item_time.setText(model.getTime());
        tv_beanshow_item_dou.setText("1");
        tv_beanshow_item_msg.setText(model.getInfo());
        ////////////
        Button btn_beanshow_item_dou = ViewHolder.get(convertView, R.id.btn_beanshow_item_dou);
        btn_beanshow_item_dou.setTag(position);
        btn_beanshow_item_dou.setOnClickListener(douClick);
        Button btn_beanshow_item_share = ViewHolder.get(convertView, R.id.btn_beanshow_item_share);
        Button btn_beanshow_item_addfocus = ViewHolder.get(convertView, R.id.btn_beanshow_item_addfocus);
        if (isLink) {
            btn_beanshow_item_addfocus.setVisibility(View.VISIBLE);
        } else {
            btn_beanshow_item_addfocus.setVisibility(View.GONE);
        }
        return convertView;
    }

    /**
     * 关闭在执行的“动画”
     */
    public void stopAnim() {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setCanAnim(false);
        }
    }

    /**
     * 更新执行动画的View
     *
     * @param firstVisibleItem
     * @param visibleItemCount
     */
    public void updateAnimItem(int firstVisibleItem, int visibleItemCount) {
        for (; visibleItemCount > 0; visibleItemCount--, firstVisibleItem++) {
            data.get(firstVisibleItem).setCanAnim(true);
            ((StickerImageView) footView.findViewWithTag(firstVisibleItem)).startAnim();
        }
    }

    /**
     * button“逗一逗”
     */
    private View.OnClickListener douClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction("com.shiliuke.StickerModifyActivity");
            intent.putExtra("model", data.get((Integer) v.getTag()));
            intent.putExtra("position", (int) v.getTag());
            context.startActivityForResult(intent, StickerImageContans.REQUESTADDMODEL);
        }
    };
}
