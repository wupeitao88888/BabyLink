package com.blink.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class CourtemListView extends ListView
{

	public CourtemListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CourtemListView(Context context) {
		super(context);
	}

	public CourtemListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
