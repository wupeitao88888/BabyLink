package com.shiliuke.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.MeTopicInfoActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.MeInitateActivity;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 *
 * 我参与的与我发起的共用一个adapter
 *
 *
 */
public class MeTopicAdapter extends BaseAdapter {

    private List<MeInitateActivity> mList;
    private Context mContext;


    public MeTopicAdapter(final Context _context, List<MeInitateActivity> mList) {

        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public MeInitateActivity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_metopic_item, parent, false);
        }
        MeInitateActivity meInitateActivity = mList.get(position);
        ImageView initate_pic = ViewHolder.get(convertView, R.id.initate_pic);
        TextView initate_name = ViewHolder.get(convertView, R.id.initate_name);
        TextView initate_time = ViewHolder.get(convertView, R.id.initate_time);
        TextView initate_count = ViewHolder.get(convertView, R.id.initate_count);
        Button initate_status = ViewHolder.get(convertView, R.id.initate_status);


        setImage(initate_pic, meInitateActivity.getInitate_pic(), mContext);
        ViewUtil.setText(mContext, initate_name, meInitateActivity.getInitate_name());
        ViewUtil.setText(mContext, initate_time, meInitateActivity.getInitate_time());
        ViewUtil.setText(mContext, initate_count, meInitateActivity.getInitate_count());
        initate_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,MeTopicInfoActivity.class);
                mContext.startActivity(intent);
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
