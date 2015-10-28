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
import com.shiliuke.bean.Change;
import com.shiliuke.bean.Exercise;
import com.shiliuke.utils.GlideCircleTransform;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/**
 * 活动
 */
public class ChangeAdapter extends BaseAdapter {
    private Context context;
    private List<Change> list;

    public ChangeAdapter(Context context, List<Change> list) {
        super();
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Change classList = list.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_change_item, null);
        }

        ImageView changepic = ViewHolder.get(convertView, R.id.changepic);
        TextView changeName = ViewHolder.get(convertView, R.id.changeName);
        TextView changAddress = ViewHolder.get(convertView, R.id.changAddress);
        ImageView changeUrl = ViewHolder.get(convertView, R.id.changeUrl);
        TextView mineGoods = ViewHolder.get(convertView, R.id.mineGoods);
        TextView exchangeGoods = ViewHolder.get(convertView, R.id.exchangeGoods);
        Button look_info = ViewHolder.get(convertView, R.id.look_info);

        setImage(changeUrl, classList.getChangeUrl(),context);
        setImageUserPic(changepic, classList.getChangePic(), context);
        ViewUtil.setText(context, changeName, classList.getChangeName());
        ViewUtil.setText(context,changAddress,classList.getChangAddress());
        ViewUtil.setText(context,mineGoods,classList.getMineGoods());
        ViewUtil.setText(context,exchangeGoods,classList.getExchangeGoods());
        look_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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

    private void setImageUserPic(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).transform(new GlideCircleTransform(context))
                .placeholder(R.drawable.title_bar)
                .error(R.drawable.title_bar)
                .crossFade()
                .into(iview);
    }


}
