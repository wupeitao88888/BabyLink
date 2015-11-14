package com.iloomo.custom;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
/**
 * gallery轮播控件
 * @author libin
 * @version 1.6.0
 */
public class CustomHomeGallery extends FrameLayout {
	private final int MIN_MOVE = 30;// 最小滑动距离
	private int width = 0;// 控件宽度
	private int length = 0;// view长度
	private int direction = 0;// 滑动方向 1：向左、-1 向右
	public int directionForScrollView = 1; // 在图文界面为处理gallery与scrollview的冲突来判定gallery的方向
	public boolean isTouch = false;// 是否触摸
	private boolean isDragging = false;// 是否在拖拽中
	
	private float snapBorderRatio = 0.5f;// 校正偏移
	private int paddingWidth = 5;// VIEW之间间距
	private long scrollTimeamp = 0;// 时间戳
	private float curretnOffset = 0.0f;// 当前位移
	
	private GestureDetector galleryGestureDetector;// 手势事件
	private ScrollAnimation scrollAnimation;// 滚动动画
	private Interpolator interpolator;// 动画速率变化器
	private int animationDuration = 350;// 动画播放时间
	
	private int position = 0;// 当前位置
	private int viewNumber = 0;// 当前View
	
	private SingleView[] singleView;// 子View数组
	private Adapter adapter;// 适配器
	
	private Context context;
	private int mTime = 4000; // 滚动时间间隔
	
	private float xPre = 0;// 当前点击坐标
	
	public CustomHomeGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		singleView = new SingleView[3];
		singleView[0] = new SingleView(0, this);
		singleView[1] = new SingleView(1, this);
		singleView[2] = new SingleView(2, this);
		
		galleryGestureDetector = new GestureDetector(new GalleryGestureDetector());
		
		// 初始化动画
		scrollAnimation = new ScrollAnimation();
		interpolator = AnimationUtils.loadInterpolator(context, android.R.anim.decelerate_interpolator);
	
		
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		width = right - left;
		if (changed == true) {
	    	// 适配列表，使显示第一个位置
	    	singleView[0].setOffset(0, 0, viewNumber);
	    	singleView[1].setOffset(0, 0, viewNumber);
	    	singleView[2].setOffset(0, 0, viewNumber);
	    }
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		boolean consumed = galleryGestureDetector.onTouchEvent(event);
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			xPre = event.getX();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			float xNow = event.getX();
			if(Math.abs(xNow - xPre) <= MIN_MOVE){
				itemClick.click(position);
			}
			
			
			if (isTouch || isDragging)
			{
				processScrollSnap();
				processGesture();
			}
			
			isTouch = false;
			// 抬起后，运行自动滚动线程  
			startTimer();
		}
		return consumed;
	}

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
		// 适配长度
		if(adapter == null) {
			length = 0;
		} else {
			length = adapter.getCount() == 0?0:adapter.getCount()-1;
		}
		/*初始化*/
		position = 0;
		viewNumber = 0;
		singleView[0].recycleView(position);
		singleView[1].recycleView(getNextPosition(position));
		singleView[2].recycleView(getPrevPosition(position));
		singleView[0].setOffset(0, 0, viewNumber);
		singleView[1].setOffset(0, 0, viewNumber);
		singleView[2].setOffset(0, 0, viewNumber);
		
	}
	
	/**
	 * 获取前一位置
	 * @param position 当前位置
	 * @return int 前一个位置
	 */
	private int getPrevPosition(int position) {
		int prevPosition = position - 1;
		if (prevPosition < 0) {
			prevPosition = length;
		}
		return prevPosition;
	}

	/**
	 * 获取后一位置
	 * @param position 当前位置
	 * @return int 下一个位置
	 */
	private int getNextPosition(int position) {
		int nextPosition = position + 1;
		if (nextPosition > length) {
			nextPosition = length + 1;
			nextPosition = 0;
		}
		return nextPosition;
	}

	/**
	 * 获取前一个view ID
	 * @param relativeViewNumber currentView ID
	 * @return int 
	 */
	private int getPrevViewNumber(int relativeViewNumber) {
		return (relativeViewNumber == 0) ? 2 : relativeViewNumber - 1;
	}

	/**
	 * 获取后一个view ID
	 * @param relativeViewNumber currentView ID
	 * @return int 下一个view ID
	 */
	private int getNextViewNumber(int relativeViewNumber) {
		return (relativeViewNumber == 2) ? 0 : relativeViewNumber + 1;
	}
	
	/**
	 * 获取view偏移量
	 * @param viewNumber view ID
	 * @param relativeViewNumber 相对view ID
	 * @return int 正值时为向左划，负值时为向右划，零值为不划动
	 */
	public int getViewOffset(int viewNumber, int relativeViewNumber) {
		int offsetWidth = width + paddingWidth;
		// 向左
		if (viewNumber == getPrevViewNumber(relativeViewNumber)) {
			return offsetWidth;
		}
		// 向右
		if (viewNumber == getNextViewNumber(relativeViewNumber)) {
			return offsetWidth * -1;
		}
		return 0;
	}
	
	/**
	 * 向左移动
	 */
	public void movePrevious() {
		direction = 1;
		processGesture();
	}

	/**
	 * 向右移动
	 */
	public void moveNext() {
		direction = -1;
		processGesture();
	}

	/**
	 * 初始移动事件
	 */
	private void processGesture() {
		int newViewNumber = viewNumber;
		int reloadViewNumber = 0;// 加入动画的view ID
		int reloadPosition = 0;// 加入动画的position
		// 重置touch事件
		isTouch = false;
		isDragging = false;
		
		if(direction > 0) {
			
			// 向右滑动
			newViewNumber = getPrevViewNumber(viewNumber);
			position = getPrevPosition(position);
			reloadViewNumber = getNextViewNumber(viewNumber); 
			reloadPosition = getPrevPosition(position);
		}
		
		if(direction < 0) {
			// 向左滑动
			newViewNumber = getNextViewNumber(viewNumber);
			position = getNextPosition(position);
			reloadViewNumber = getPrevViewNumber(viewNumber);
			reloadPosition = getNextPosition(position);
		}
		ic.change(position);
		if(newViewNumber != viewNumber) {
			viewNumber = newViewNumber;
			singleView[reloadViewNumber].recycleView(reloadPosition);
		}
		
		singleView[viewNumber].requestFocus();
		scrollAnimation.initAnimation(viewNumber);
		this.startAnimation(scrollAnimation);
		direction = 0;
	}

	/**
	 * 校正偏移
	 */
	void processScrollSnap() {
		// Snap to next view if scrolled passed snap position
		float rollEdgeWidth = width * snapBorderRatio;
		int rollOffset = width - (int) rollEdgeWidth;
		int currentOffset = singleView[viewNumber].getCurrentOffset();

		if (currentOffset <= rollOffset * -1) {
			direction = 1;
		}
		if (currentOffset >= rollOffset) {
			direction = -1;
		}
	}
	
	/**
	 * 子View
	 */
	private class SingleView {
		// private FrameLayout parentView;// 父布局
		private int viewNumber;// 当前View ID
		private LinearLayout linView;// 滚动的View 
		private View view;
		
		/**
		 * 构造方法
		 * @param viewNumber 当前view ID
		 * @param parentView 父布局
		 */
		SingleView(int viewNumber, FrameLayout parentView) {
			this.viewNumber = viewNumber;
			// this.parentView = parentView;
			
			linView = new LinearLayout(context);
			linView.setLayoutParams(new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));
			parentView.addView(linView);
			
		}
		
		/**
		 * 重新获取
		 * @param recyclePosition position坐标
		 */
		public void recycleView(int recyclePosition) {
			if(view != null) {
				linView.removeView(view);
			}
			// 获取适配器中的view
			if(adapter != null) {
				if(recyclePosition >= 0 && recyclePosition <= length) {
					view = adapter.getView(recyclePosition, view, linView);
				}
			}
			if(view != null) {
				linView.addView(view, new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));
			}
		}
		
		/**
		 * 设置当前偏移量
		 * @param x 当前x坐标
		 * @param y 当前y坐标
		 * @param relativeViewNumber 当前view ID
		 */
		public void setOffset(int x, int y, int relativeViewNumber) {
			linView.scrollTo(getViewOffset(viewNumber, relativeViewNumber) + x, y);
		}
		
		/**
		 * 获取当前偏移量
		 * @return int 当前偏移量
		 */
		public int getCurrentOffset() {
			return linView.getScrollX();
		}
		
		/**
		 * 申请焦点
		 */
		public void requestFocus() {
			linView.requestFocus();
		}
	}

	/**
	 * 自定义滚动动画效果
	 * 
	 */
	private class ScrollAnimation extends Animation {
		private boolean isRunning = false;// 动画是否正在进行
		private int currentView = 0;// 当前view ID
		private int initialOffset = 0;// 初始位置
		private int targetOffset = 0;// 目标位置
		private int distance = 0;// 距离
		
		/**
		 * 准备动画
		 * @param relativeViewNumber 相对变动view ID
		 */
		public void initAnimation(int relativeViewNumber) {
			// 如果动画画面不是正在进行动画的画面
			if(currentView != relativeViewNumber) {
				if(isRunning) {
					// 当动画进行时，又去触发新的动画
					int direction = relativeViewNumber == getPrevViewNumber(currentView)?1:-1;// 确认移动方向 
					int animDirection = distance<0?1:-1;// 当前动画方向
					if(direction == animDirection) {
						// 同一方向时，直接切换到下一个
						singleView[0].setOffset(targetOffset, 0, currentView);
						singleView[1].setOffset(targetOffset, 0, currentView);
						singleView[2].setOffset(targetOffset, 0, currentView);	
					}
				}
				currentView = relativeViewNumber;
			}
			
			initialOffset = singleView[currentView].getCurrentOffset();// 当前滚动坐标
			targetOffset = getViewOffset(currentView, currentView);// 目标坐标
			distance = targetOffset - initialOffset;// 距离
			
			this.setDuration(animationDuration);// 设置时间间隔
			this.setInterpolator(interpolator);// 使减速运动
			isRunning = true;
		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			interpolatedTime = (interpolatedTime > 1.0f) ? 1.0f : interpolatedTime;
			int offset = initialOffset + (int)(distance * interpolatedTime);// 设置动画路径
			
			for (int viewNumber = 0; viewNumber < 3; viewNumber++) {
				// 当距离存在，并且
				if (distance != 0) {
					singleView[viewNumber].setOffset(offset, 0, currentView);
				}
			}
		}
		@Override
		public boolean getTransformation(long currentTime,
				Transformation outTransformation) {
			if(!super.getTransformation(currentTime, outTransformation)) {
				// 当动画结束时进行操作，重置view 和 运行标识
				singleView[0].setOffset(targetOffset, 0, currentView);
				singleView[1].setOffset(targetOffset, 0, currentView);
				singleView[2].setOffset(targetOffset, 0, currentView);
				isRunning = false;
				return false;
			}
			
			// 触摸后动画结束
			if(isTouch || isDragging) {
				return false;
			}
			return true; 
		}
	}
	
	private class GalleryGestureDetector extends GestureDetector.SimpleOnGestureListener {
		@Override
		public void onLongPress(MotionEvent e) {
			direction = 0;
			processGesture();
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if(e2.getAction() == MotionEvent.ACTION_MOVE ) {
				// added by libin at 2011-08-13 begin
				if(e1.getX() - e2.getX() > MIN_MOVE) {
					// 向右移动
					directionForScrollView = -1;
				}
				if(e2.getX() - e1.getX() > MIN_MOVE) {
					// 向左移动
					directionForScrollView = 1;
				}
				// added by libin at 2011-08-13 end
				if(!isDragging) {
					isTouch = true;
					isDragging = true;
					direction = 0;
					scrollTimeamp = System.currentTimeMillis();// 获取当前时间
					curretnOffset = singleView[viewNumber].getCurrentOffset();
				}
				float maxVelocity = width / (animationDuration / 1000.0f);// 依据动画事件计算每一份距离
        		long timestampDelta = System.currentTimeMillis() - scrollTimeamp;
        		float maxScrollDelta = maxVelocity * (timestampDelta / 1000.0f);// 根据时间计算移动距离
        		float currentScrollDelta = e1.getX() - e2.getX();// 移动的坐标
				
        		// 如果距离超出进行操作
        		if (currentScrollDelta < maxScrollDelta * -1) currentScrollDelta = maxScrollDelta * -1;
        		if (currentScrollDelta > maxScrollDelta) currentScrollDelta = maxScrollDelta;
        		// 距离偏移量
	        	int scrollOffset = Math.round(curretnOffset + currentScrollDelta);
	        	// 如果超出控件大小进行设置
        		if (scrollOffset >= width) scrollOffset = width;
        		if (scrollOffset <= width * -1) scrollOffset = width * -1;
        		
        		singleView[0].setOffset(scrollOffset, 0, viewNumber);
        		singleView[1].setOffset(scrollOffset, 0, viewNumber);
        		singleView[2].setOffset(scrollOffset, 0, viewNumber);
			}
			
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if(e1.getX() - e2.getX() > MIN_MOVE) {
				// 向右移动
				// added by libin at 2011-08-13 begin
				directionForScrollView = -1;
				// added by libin at 2011-08-13 end
				moveNext();
			}
			if(e2.getX() - e1.getX() > MIN_MOVE) {
				// 向左移动
				// added by libin at 2011-08-13 begin
				directionForScrollView = 1;
				// added by libin at 2011-08-13 end
				movePrevious();
			}
			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			// 触摸时停止自动滚动线程
		    cancelTimer();
			
			isTouch = true;
			direction = 0;// 初始化方向
			return true;
		}
		
		@Override
		public void onShowPress(MotionEvent e) {
			
		}
		
    	@Override
		public boolean onSingleTapUp(MotionEvent e){
    		direction = 0;
            return false;
    	}
	}
	   /**
     * 自动滚动线程
     */
	private Runnable runnable = new Runnable() {  
	    
	    @Override
		public void run() {  
	    	 moveNext();
	        handler.postDelayed(this, mTime);  
	    }  
	  
	};  
	/**
	 * 自动滚动Handler
	 */
	final Handler handler = new Handler();
	
	ItemChange ic;
	OnItemClick itemClick;
	/**
	 * item改变方法
	 */
	public void setOnItemChanged(ItemChange ic){
		this.ic = ic;
	}
	/**
	 * item点击方法
	 */
	public void setOnItemClick(OnItemClick itemClick){
		this.itemClick = itemClick;
	}
	/**
	 * gallery中的item改变接口
	 */
	public interface ItemChange{
		void change(int position);
	}
	/**
	 * gallery中的item点击接口
	 */
	public interface OnItemClick{
		void click(int position);
	}
	/**
	 * 停止自动轮播
	 */
	public void cancelTimer(){
	    handler.removeCallbacks(runnable);
	}

    /**
     * 重新开始自动轮播
     */
    public void startTimer() {
        cancelTimer();
        handler.postDelayed(runnable, mTime); 
        
    }

	public int getmTime() {
		return mTime;
	}

	public void setmTime(int mTime) {
		this.mTime = mTime;
	}

    
}
