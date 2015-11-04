package com.shiliuke.view.stickerview;

import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.shiliuke.bean.BeanShowModel;
import com.shiliuke.utils.L;

/**
 * 图片贴纸View
 * Created by wangzhi on 15/10/30.
 */
public class StickerImageView extends View {

    private enum TYPE {
        COMPILE,//编辑模式
        BROWSE;
    }

    private Bitmap bgBitmap;
    //    private ArrayList<StickerImageModel> mdata;
    private StickerImageModel compileModel;
    private BeanShowModel beanShowModel;
    private TYPE style = TYPE.COMPILE;
    private Paint mTextPaint;
    private Paint mBgPaint;

    private float x1, x2, y1, y2;
    private boolean isCurrentClick = false;


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
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFilterBitmap(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeWidth(1.0f);
        mTextPaint.setTextSize(StickerImageContans.DEFAULTTEXTSIZE);
        mTextPaint.setColor(StickerImageContans.DEFAULTTEXTCOLOR);
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setFilterBitmap(true);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setStrokeWidth(1.0f);
        mBgPaint.setTextSize(StickerImageContans.DEFAULTTEXTSIZE);
        mBgPaint.setColor(StickerImageContans.DEFAULTCIRCLECOLOR);
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
        for (int i = 0; i < beanShowModel.getStickerlist().size(); i++) {
            StickerImageModel model = beanShowModel.getStickerlist().get(i);
            mTextPaint.setAlpha(model.getAlpha());
            mBgPaint.setAlpha(model.getAlpha());

//            float y = model.getY() - StickerImageContans.DEFAULTTEXTSIZE / 4;
//            canvas.drawCircle(model.getX(), y, StickerImageContans.DEFAULTCIRCLERADIUS / 2, mBgPaint);
//            canvas.drawCircle(model.getX() + getTextWidth(mTextPaint, model.getText()), y, StickerImageContans.DEFAULTCIRCLERADIUS / 2, mBgPaint);
            RectF oval3 = new RectF(model.getX() - StickerImageContans.DEFAULTBGLEFT, model.getY() - StickerImageContans.DEFAULTBGHEIGHT, model.getX() + getTextWidth(mTextPaint, model.getText()) + StickerImageContans.DEFAULTBGLEFT, model.getY() + StickerImageContans.DEFAULTBGHEIGHT / 2);// 设置个新的长方形
            canvas.drawRoundRect(oval3, StickerImageContans.DEFAULTBGX, StickerImageContans.DEFAULTBGY, mBgPaint);

            canvas.drawText(model.getText(), model.getX(), model.getY(), mTextPaint);
        }
        if (compileModel != null) {
            mTextPaint.setAlpha(compileModel.getAlpha());
            mBgPaint.setAlpha(compileModel.getAlpha());
            RectF oval3 = new RectF(compileModel.getX() - StickerImageContans.DEFAULTBGLEFT, compileModel.getY() - StickerImageContans.DEFAULTBGHEIGHT, compileModel.getX() + getTextWidth(mTextPaint, compileModel.getText()) + StickerImageContans.DEFAULTBGLEFT, compileModel.getY() + StickerImageContans.DEFAULTBGHEIGHT / 2);// 设置个新的长方形
            canvas.drawRoundRect(oval3, StickerImageContans.DEFAULTBGX, StickerImageContans.DEFAULTBGY, mBgPaint);
            canvas.drawText(compileModel.getText(), compileModel.getX(), compileModel.getY(), mTextPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (style == TYPE.COMPILE && compileModel != null) {
                    if (event.getX() > x1 && event.getX() < x2 && event.getY() > y1 && event.getY() < y2) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        compileModel.setXy(event.getX(), event.getY());
                        invalidate();
                        isCurrentClick = true;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (style == TYPE.COMPILE && compileModel != null && isCurrentClick) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    compileModel.setXy(event.getX(), event.getY());
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                setCurrentXy();
                isCurrentClick = false;
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
            setCurrentXy();
            invalidate();
        }
    }

    /**
     * 更新新建贴纸的点击区域
     */
    private void setCurrentXy() {
        x1 = compileModel.getX() - StickerImageContans.DEFAULTBGLEFT;
        y1 = compileModel.getY() - StickerImageContans.DEFAULTBGHEIGHT;
        x2 = compileModel.getX() + getTextWidth(mTextPaint, compileModel.getText()) + StickerImageContans.DEFAULTBGLEFT;
        y2 = compileModel.getY() + StickerImageContans.DEFAULTBGHEIGHT / 2;
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
                    beanShowModel.setIsAniming(false);
                    break;
            }
            super.handleMessage(msg);
        }
    };
//    public static boolean canAnim;


    /**
     * 开始”动画"
     */
    public void startAnim() {
        if (beanShowModel.isAniming()) {
            return;
        }
        beanShowModel.setIsAniming(true);
        StickerExecutor.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < beanShowModel.getStickerlist().size(); i++) {
                    StickerImageModel model = beanShowModel.getStickerlist().get(i);
                    model.startLooper(beanShowModel, handler);
                    if (!beanShowModel.isCanAnim()) {
                        break;
                    }
                    if (model.getAlpha() == StickerImageContans.MAXALPHA) {
                        try {
                            Thread.sleep(StickerImageContans.DEFAULTSECONDTIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * 得到文字的长度
     *
     * @param paint
     * @param str
     * @return
     */
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    public StickerImageModel getCompileModel() {
        return compileModel;
    }
}
