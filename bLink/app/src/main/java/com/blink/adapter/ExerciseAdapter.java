package com.blink.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.blink.bean.Exercise;
import java.util.List;

/***
 *
 * 活动
 */
public class ExerciseAdapter extends BaseAdapter {
	private Context context;
	private List<Exercise> list;

	public ExerciseAdapter(Context context, List<Exercise> list) {
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
		Exercise classList = list.get(position);



		return convertView;
	}


}
