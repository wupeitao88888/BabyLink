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
import com.shiliuke.bean.PayEnd;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 * 已退款
 */
public class AlreadyRefundAdapter extends BaseAdapter {

    private List<PayEnd> mList;
    private Context mContext;


    public AlreadyRefundAdapter(final Context _context, List<PayEnd> mList) {

        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PayEnd getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_already_refund_item, parent, false);
        }

        PayEnd payEnd = mList.get(position);
        ImageView pay_url = ViewHolder.get(convertView, R.id.pay_url);
        TextView pay_title = ViewHolder.get(convertView, R.id.pay_title);
        TextView not_yet = ViewHolder.get(convertView, R.id.not_yet);//实付
        TextView pay_status = ViewHolder.get(convertView, R.id.pay_status);//支付状态
        Button pay_end = ViewHolder.get(convertView, R.id.pay_end);//退款

        setImage(pay_url, payEnd.getPay_url(), mContext);
        ViewUtil.setText(mContext, pay_title, payEnd.getPay_title());
        ViewUtil.setText(mContext, not_yet, payEnd.getNot_yet());

        pay_end.setOnClickListener(new View.OnClickListener() {
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
