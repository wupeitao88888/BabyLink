package com.shiliuke.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.Topic;
import com.shiliuke.view.NoScrollGridView;

public class TopicAdapter extends BaseAdapter {

	private List<Topic> mList;
	private Context mContext;

	public TopicAdapter(Context _context) {
		this.mContext = _context;
	}

	public void setData(List<Topic> _list) {
		this.mList = _list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Topic getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.friends_circle_item, parent, false);
			holder.gridView = (NoScrollGridView) convertView
					.findViewById(R.id.gridView);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		Topic mUserInfo = getItem(position);
		if (mList != null && mList.size() > 0) {
			holder.gridView.setVisibility(View.VISIBLE);
			holder.gridView.setAdapter(new MyGridAdapter(mUserInfo.getUi(),
					mContext));
			holder.gridView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// imageBrower(position,bean.urls);
						}
					});
		}
		return convertView;
	}

	public class ViewHolder {
		LinearLayout mContentimg;
		NoScrollGridView gridView;
	}

}
