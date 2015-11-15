package com.easemob.easeui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.easeui.R;

/**
 * 标题栏
 *
 */
public class EaseTitleBar extends RelativeLayout {

	protected RelativeLayout leftLayout;
	protected ImageView leftImage;
	protected RelativeLayout rightLayout;
	protected ImageView rightImage;
	protected TextView titleView;
	protected RelativeLayout titleLayout;
	/***
	 * 
	 * 后加的
	 */
	protected RelativeLayout left_layout_textre;
	protected RelativeLayout right_layout_textre;
	protected TextView left_image_textTitle;
	protected TextView right_layout_textTitle;

	public EaseTitleBar(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);
	}

	public EaseTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public EaseTitleBar(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {
		LayoutInflater.from(context).inflate(R.layout.ease_widget_title_bar, this);
		leftLayout = (RelativeLayout) findViewById(R.id.left_layout);
		leftImage = (ImageView) findViewById(R.id.left_image);
		rightLayout = (RelativeLayout) findViewById(R.id.right_layout);
		rightImage = (ImageView) findViewById(R.id.right_image);
		titleView = (TextView) findViewById(R.id.title);
		titleLayout = (RelativeLayout) findViewById(R.id.root);
		left_layout_textre = (RelativeLayout) findViewById(R.id.left_layout_textre);
		right_layout_textre = (RelativeLayout) findViewById(R.id.right_layout_textre);
		left_image_textTitle = (TextView) findViewById(R.id.left_image_textTitle);
		right_layout_textTitle = (TextView) findViewById(R.id.right_layout_textTitle);
		parseStyle(context, attrs);
	}

	public void setOnLeftTextViewClickListener(OnClickListener listener){
		left_layout_textre.setOnClickListener(listener);
	}
	public void setOnRightTextViewClickListener(OnClickListener listener){
		right_layout_textre.setOnClickListener(listener);
	}
	public void setLeftTitle(String text){
		left_image_textTitle.setText(text);
		left_layout_textre.setVisibility(View.VISIBLE);
	}
	public void setRightTitle(String text){
		right_layout_textTitle.setText(text);
		right_layout_textre.setVisibility(View.VISIBLE);
	}
	
	
	
	private void parseStyle(Context context, AttributeSet attrs) {
		if (attrs != null) {
			TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseTitleBar);
			String title = ta.getString(R.styleable.EaseTitleBar_titleBarTitle);
			titleView.setText(title);

			Drawable leftDrawable = ta.getDrawable(R.styleable.EaseTitleBar_titleBarLeftImage);
			if (null != leftDrawable) {
				leftImage.setImageDrawable(leftDrawable);
			}
			Drawable rightDrawable = ta.getDrawable(R.styleable.EaseTitleBar_titleBarRightImage);
			if (null != rightDrawable) {
				rightImage.setImageDrawable(rightDrawable);
			}

			Drawable background = ta.getDrawable(R.styleable.EaseTitleBar_titleBarBackground);
			if (null != background) {
				titleLayout.setBackgroundDrawable(background);
			}

			ta.recycle();
		}
	}

	public void setLeftImageResource(int resId) {
		leftImage.setImageResource(resId);
	}

	public void setRightImageResource(int resId) {
		rightImage.setImageResource(resId);
	}

	public void setLeftLayoutClickListener(OnClickListener listener) {
		leftLayout.setOnClickListener(listener);
	}

	public void setRightLayoutClickListener(OnClickListener listener) {
		rightLayout.setOnClickListener(listener);
	}

	public void setLeftLayoutVisibility(int visibility) {
		leftLayout.setVisibility(visibility);
	}

	public void setRightLayoutVisibility(int visibility) {
		rightLayout.setVisibility(visibility);
	}

	public void setTitle(String title) {
		titleView.setText(title);
	}

	public void setBackgroundColor(int color) {
		titleLayout.setBackgroundColor(color);
	}

	public RelativeLayout getLeftLayout() {
		return leftLayout;
	}

	public RelativeLayout getRightLayout() {
		return rightLayout;
	}
}
