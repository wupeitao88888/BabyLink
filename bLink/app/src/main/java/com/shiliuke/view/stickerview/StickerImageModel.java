package com.shiliuke.view.stickerview;

import android.os.Handler;
import com.shiliuke.bean.BeanShowModel;
import com.shiliuke.utils.L;

/**
 * 图片贴纸Model
 * Created by wangzhi on 15/10/30.
 */
public class StickerImageModel {
    private String text;//贴纸文字
    private int alpha;//透明度
    private float x;
    private float y;

    /**
     * 开始改变透明度
     *
     * @param handler
     */
    public void startLooper(final BeanShowModel model, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (model.isCanAnim()) {
                    if (alpha == StickerImageContans.MAXALPHA) {
                        try {
                            Thread.sleep(StickerImageContans.DEFAULTNOMALTIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        alpha = 0;
                        handler.sendEmptyMessage(StickerImageContans.DEFAULTHANDLER);
                    }
                    try {
                        Thread.sleep(StickerImageContans.DEFAULTCOMPILETIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateAlpha();
                    handler.sendEmptyMessage(StickerImageContans.DEFAULTHANDLER);
//                    L.d("isCanAnim" + model.isCanAnim());
                }
                handler.sendEmptyMessage(StickerImageContans.DEFAULTHANDLERSTOP);
            }
        }).start();
    }

    private void updateAlpha() {
        alpha += StickerImageContans.COMPILEALPHA;
        if (alpha >= StickerImageContans.MAXALPHA) {
            alpha = StickerImageContans.MAXALPHA;
        }
    }

    public StickerImageModel(String text) {
        this.text = text;
        this.x = StickerImageContans.DEFAULTX;
        this.y = StickerImageContans.DEFAULTY;
        this.alpha = StickerImageContans.MAXALPHA;
    }

    public void setXy(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getY() {

        return y;
    }

    public int getAlpha() {
        return alpha;
    }

    public float getX() {
        return x;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
