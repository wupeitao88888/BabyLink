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
import com.shiliuke.BabyLink.ChangeInfoActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.MeStarChange;
import com.shiliuke.bean.MyShowBean;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 *
 * 我的秀逗
 *
 *
 */
public class MyShowBeanAdapter extends BaseAdapter {

    private List<MyShowBean> mList;
    private Context mContext;


    public MyShowBeanAdapter(final Context _context, List<MyShowBean> mList) {
        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public MyShowBean getItem(int position) {
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
                    R.layout.layout_myshowbean_item, parent, false);
        }
        MyShowBean memyshowbeanActivity = mList.get(position);
        ImageView myshowbean_pic = ViewHolder.get(convertView, R.id.myshowbean_pic);
        TextView myshowbean_name = ViewHolder.get(convertView, R.id.myshowbean_name);
//        TextView myshowbean_time = ViewHolder.get(convertView, R.id.myshowbean_time);
        Button myshowbean_status = ViewHolder.get(convertView, R.id.myshowbean_status);


        setImage(myshowbean_pic, memyshowbeanActivity.getSbean_pic(), mContext);
        ViewUtil.setText(mContext, myshowbean_name, memyshowbeanActivity.getSbean_name());
//        ViewUtil.setText(mContext, myshowbean_time, memyshowbeanActivity.getchange_time());
//        myshowbean_status.setSelected(true);//true是灰色

        myshowbean_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, ChangeInfoActivity.class));
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
