package com.shiliuke.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.BeanShowModel;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.view.stickerview.StickerImageView;

import java.util.ArrayList;

/**
 * Created by wangzhi on 15/10/30.
 * 秀逗页面List Adapter
 */
public class BeanShowAdapter extends BaseAdapter {
    private View footView;
    private ArrayList<BeanShowModel> data;
    private Context context;
    private int animItem = -1;

    public BeanShowAdapter(View footView, Context context, ArrayList<BeanShowModel> data) {
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
        if (animItem == -1) {
            animItem = position;
        } else if (animItem != position) {
            data.get(animItem).setCanAnim(false);
            animItem = position;
        }
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_beanshow, null);
        }
        final BeanShowModel model = data.get(position);
        ImageView image_beanshow_item_head = ViewHolder.get(convertView, R.id.image_beanshow_item_head);
        TextView tv_beanshow_item_name = ViewHolder.get(convertView, R.id.tv_beanshow_item_name);
        TextView tv_beanshow_item_time = ViewHolder.get(convertView, R.id.tv_beanshow_item_time);
        TextView tv_beanshow_item_dou = ViewHolder.get(convertView, R.id.tv_beanshow_item_dou);
        TextView tv_beanshow_item_msg = ViewHolder.get(convertView, R.id.tv_beanshow_item_msg);
        StickerImageView sticker_beanshow_item = ViewHolder.get(convertView, R.id.sticker_beanshow_item);
        sticker_beanshow_item.setTag(position);
        ////////////
        tv_beanshow_item_name.setText(model.getName());
        tv_beanshow_item_time.setText(model.getTime());
        tv_beanshow_item_dou.setText(model.getTotaldou());
        tv_beanshow_item_msg.setText(model.getMsg());
        ////////////
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg_login_guide);
        sticker_beanshow_item.setBgBitmap(bitmap, model);
        sticker_beanshow_item.startAnim();
        ////////////
        Button btn_beanshow_item_dou = ViewHolder.get(convertView, R.id.btn_beanshow_item_dou);
        Button btn_beanshow_item_share = ViewHolder.get(convertView, R.id.btn_beanshow_item_share);
        Button btn_beanshow_item_addfocus = ViewHolder.get(convertView, R.id.btn_beanshow_item_addfocus);
        return convertView;
    }

    /**
     * 关闭在执行的“动画”
     */
    public void stopAnim() {
        data.get(animItem).setCanAnim(false);
        animItem = -1;
    }

    /**
     * 更新执行动画的View
     *
     * @param firstVisibleItem
     * @param visibleItemCount
     */
    public void updateAnimItem(int firstVisibleItem, int visibleItemCount) {
        if (visibleItemCount == 1) {
            if (animItem == -1) {
                animItem = firstVisibleItem;
            } else if (animItem != firstVisibleItem) {
                data.get(animItem).setCanAnim(false);
                animItem = firstVisibleItem;
            }
            ((StickerImageView) footView.findViewWithTag(animItem)).startAnim();
        }
    }
}
