package com.shiliuke.view.stickerview;

import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.shiliuke.bean.BeanShowModel;

/**
 * 图片贴纸View
 * Created by wangzhi on 15/10/30.
 */
public class StickerImageView extends View {

    private enum TYPE {
        COMPILE,//编辑模式
        BROWSE
    }

    private Bitmap bgBitmap;
    //    private ArrayList<StickerImageModel> mdata;
    private StickerImageModel compileModel;
    private BeanShowModel beanShowModel;
    private TYPE style = TYPE.COMPILE;

    public void setStyle(TYPE style) {
        this.style = style;
    }


    public StickerImageView(Context context) {
        this(context, null);
    }

    public StickerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置背景图片以及弹幕列表
     */
    public void setBgBitmap(Bitmap bitmap, BeanShowModel model) {
        this.bgBitmap = bitmap;
        this.beanShowModel = model;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bgBitmap == null || beanShowModel.getStickerlist().isEmpty()) {
            Bitmap bit = BitmapFactory.decodeResource(getResources(), StickerImageContans.DEFAULTBITMAP);
            canvas.drawBitmap(bit, 0, 0, null);
            return;
        }
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        if (beanShowModel.getStickerlist().isEmpty()) {
            return;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1.0f);
        paint.setTextSize(StickerImageContans.DEFAULTTEXTSIZE);
        paint.setColor(Color.WHITE);
        for (int i = 0; i < beanShowModel.getStickerlist().size(); i++) {
            StickerImageModel model = beanShowModel.getStickerlist().get(i);
            paint.setAlpha(model.getAlpha());
            canvas.drawText(model.getText(), model.getX(), model.getY(), paint);
        }
        if (compileModel != null) {
            paint.setAlpha(compileModel.getAlpha());
            canvas.drawText(compileModel.getText(), compileModel.getX(), compileModel.getY(), paint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (style == TYPE.COMPILE && compileModel != null) {
                    compileModel.setXy(event.getX(), event.getY());
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (style == TYPE.COMPILE && compileModel != null) {
                    compileModel.setXy(event.getX(), event.getY());
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    /**
     * 更新正在创建贴纸的文字
     */
    public void updateModelText(String text) {
        if (style == TYPE.COMPILE) {
            if (compileModel == null) {
                compileModel = new StickerImageModel(text);
            } else {
                compileModel.setText(text);
            }
            invalidate();
        }
    }

    /**
     * 增加贴纸
     */
    public boolean addModel() {
        if (style == TYPE.COMPILE) {
            if (compileModel == null) {
                return false;
            }
            beanShowModel.getStickerlist().add(compileModel);
            if (beanShowModel.isCanAnim()) {
                compileModel.startLooper(beanShowModel, handler);
            }
            compileModel = null;
            return true;
        }
        return false;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case StickerImageContans.DEFAULTHANDLER:
                    invalidate();
                    break;
                case StickerImageContans.DEFAULTHANDLERSTOP:
                    isAniming = false;
                    break;
            }
            super.handleMessage(msg);
        }
    };
//    public static boolean canAnim;

    private boolean isAniming = false;

    /**
     * 开始”动画"
     */
    public void startAnim() {
        if (isAniming) {
            return;
        }
        beanShowModel.setCanAnim(true);
        isAniming = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < beanShowModel.getStickerlist().size(); i++) {
                    beanShowModel.getStickerlist().get(i).startLooper(beanShowModel, handler);
                    if (!beanShowModel.isCanAnim()) {
                        break;
                    }
                    try {
                        Thread.sleep(StickerImageContans.DEFAULTSECONDTIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(StickerImageContans.DEFAULTHANDLER);
                }
            }
        }).start();
    }

    /**
     * 停止”动画"
     */
    public void stopAnim() {
        beanShowModel.setCanAnim(false);
    }

}
