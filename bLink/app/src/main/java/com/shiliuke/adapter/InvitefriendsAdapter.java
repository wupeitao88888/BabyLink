package com.shiliuke.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.UserInfo;
import com.shiliuke.utils.ViewHolder;

import java.util.ArrayList;

/**
 * Created by wangzhi on 15/11/11.
 */
public class InvitefriendsAdapter extends BaseAdapter {
    private ArrayList<UserInfo> infoList;
    private Context context;

    public InvitefriendsAdapter(ArrayList<UserInfo> infoList, Context context) {
        this.infoList = infoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_inviterfriends, null);
        }
        UserInfo info = infoList.get(position);
        ImageView image_item_inviterfriends_head = ViewHolder.get(convertView, R.id.image_item_inviterfriends_head);
        TextView tv_item_inviterfriends_name = ViewHolder.get(convertView, R.id.tv_item_inviterfriends_name);
        Button btn_item_inviterfiends_inviter = ViewHolder.get(convertView, R.id.btn_item_inviterfiends_inviter);
        tv_item_inviterfriends_name.setText(info.getMember_avar());
        return convertView;
    }
}
