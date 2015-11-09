package com.shiliuke.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.Friends;
import com.shiliuke.bean.Neighbour;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 * 我的好友
 */
public class FriendsAdapter extends BaseAdapter {

    private List<Friends> mList;
    private Context mContext;


    public FriendsAdapter(final Context _context, List<Friends> mList) {
        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Friends getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_friends_item, parent, false);
        }
        Friends friends = mList.get(position);
        ImageView friends_pic = ViewHolder.get(convertView, R.id.friends_pic);
        TextView friends_name = ViewHolder.get(convertView, R.id.friends_name);
        Button friends_remove = ViewHolder.get(convertView, R.id.friends_remove);


        setImage(friends_pic, friends.getPic(), mContext);
        ViewUtil.setText(mContext, friends_name, friends.getName());

        friends_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private void setImage(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.gray)
                .error(R.drawable.gray)
                .crossFade()
                .into(iview);

    }

}
