package com.shiliuke.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.UserImgs;

public class MyGridAdapter extends BaseAdapter {
    private List<UserImgs> mUI;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public MyGridAdapter(List<UserImgs> ui, Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mUI = ui;
        this.context=context;
    }

    @Override
    public int getCount() {
        return mUI == null ? 0 : mUI.size();
    }

    @Override
    public String getItem(int position) {
        return mUI.get(position).urls;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyGridViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyGridViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.user_img_item,
                    parent, false);
            viewHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.iv_user_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }
        String url = getItem(position);
        setImage(viewHolder.imageView, url, context);
        return convertView;
    }

    private void setImage(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.title_bar)
                .error(R.drawable.title_bar)
                .crossFade()
                .into(iview);

    }

    private static class MyGridViewHolder {
        ImageView imageView;
    }
}
