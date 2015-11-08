package com.shiliuke.adapter;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.FindModel;
import com.shiliuke.utils.ViewHolder;

import java.util.ArrayList;

/**
 * Created by wangzhi on 15/11/8.
 * 发现页面List Adapter
 */
public class FindAdapter extends BaseAdapter {
    private View footView;
    private ArrayList<FindModel> data;
    private Fragment context;

    public FindAdapter(View footView, Fragment context, ArrayList<FindModel> data) {
        this.footView = footView;
        this.context = context;
        this.data = data;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context.getActivity()).inflate(R.layout.item_find, null);
        }
        FindModel model = data.get(position);
        ImageView image_item_find_topbg = ViewHolder.get(convertView, R.id.image_item_find_topbg);
        ImageView image_item_find_top = ViewHolder.get(convertView, R.id.image_item_find_top);
        TextView tv_item_find_title = ViewHolder.get(convertView, R.id.tv_item_find_title);
        TextView tv_item_find_price = ViewHolder.get(convertView, R.id.tv_item_find_price);
        TextView tv_item_find_address = ViewHolder.get(convertView, R.id.tv_item_find_address);
        TextView tv_item_find_distance = ViewHolder.get(convertView, R.id.tv_item_find_distance);
        ///////
        tv_item_find_title.setText(model.getTitle());
        tv_item_find_price.setText(model.getPrice());
        tv_item_find_address.setText(model.getAddress());
        tv_item_find_distance.setText(model.getDistance());
        return convertView;
    }
}
