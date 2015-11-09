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
import com.shiliuke.bean.MyShowBean;
import com.shiliuke.bean.Neighbour;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 *
 * 我的邻居
 *
 *
 */
public class NeighbourAdapter extends BaseAdapter {

    private List<Neighbour> mList;
    private Context mContext;


    public NeighbourAdapter(final Context _context, List<Neighbour> mList) {
        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Neighbour getItem(int position) {
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
                    R.layout.layout_neighbour_item, parent, false);
        }
        Neighbour memyshowbeanActivity = mList.get(position);
        ImageView neighbour_pic = ViewHolder.get(convertView, R.id.neighbour_pic);
        TextView neighbour_name = ViewHolder.get(convertView, R.id.neighbour_name);
        TextView neighbour_address = ViewHolder.get(convertView, R.id.neighbour_address);



        setImage(neighbour_pic, memyshowbeanActivity.getPic(), mContext);
        ViewUtil.setText(mContext, neighbour_name, memyshowbeanActivity.getName());
        ViewUtil.setText(mContext, neighbour_address, memyshowbeanActivity.getAddress());


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
