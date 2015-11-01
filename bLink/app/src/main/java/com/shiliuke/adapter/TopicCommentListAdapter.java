package com.shiliuke.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.Comment;
import com.shiliuke.bean.UserImgs;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

public class TopicCommentListAdapter extends BaseAdapter {
    private List<Comment> mUI;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public TopicCommentListAdapter(List<Comment> ui, Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mUI = ui;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mUI == null ? 0 : mUI.size();
    }

    @Override
    public String getItem(int position) {
        return mUI.get(position).getCommentName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = mUI.get(position);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_comment_item,
                    parent, false);
        }
        TextView commentContent = ViewHolder.get(convertView, R.id.commentContent);
        TextView commentName = ViewHolder.get(convertView, R.id.commentName);
        TextView commentReply_text = ViewHolder.get(convertView, R.id.commentReply_text);
        TextView commentReplyName = ViewHolder.get(convertView, R.id.commentReplyName);
        ViewUtil.setText(context, commentName, comment.getCommentName());
        ViewUtil.setText(context, commentContent, comment.getCommentContent());
        ViewUtil.setText(context, commentReplyName, comment.getCommentReplyName());
        if (TextUtils.isEmpty(comment.getCommentReplyName())) {
            commentReply_text.setVisibility(View.GONE);
        }else{
            commentReply_text.setVisibility(View.VISIBLE);
        }

        return convertView;
    }


}
