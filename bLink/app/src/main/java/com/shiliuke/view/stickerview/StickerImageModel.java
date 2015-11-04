package com.shiliuke.view.stickerview;

import android.os.Handler;
import com.shiliuke.bean.BeanShowModel;

import java.io.Serializable;

/**
 * 图片贴纸Model
 * Created by wangzhi on 15/10/30.
 */
public class StickerImageModel implements Serializable{
    private String text;//贴纸文字
    private int alpha;//透明度
    private int f;//透明度
    private float x;
    private float y;

    /**
     * 开始改变透明度
     *
     * @param handler
     */
    public void startLooper(final BeanShowModel model, final Handler handler) {
        StickerExecutor.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (model.isCanAnim()) {
                    if (alpha == StickerImageContans.MAXALPHA) {
                        try {
                            Thread.sleep(StickerImageContans.DEFAULTMAXALTIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        f = -1;
                        updateAlpha();
                        handler.sendEmptyMessage(StickerImageContans.DEFAULTHANDLER);
                        continue;
                    }
                    if (alpha == StickerImageContans.MINALPHA) {
                        try {
                            Thread.sleep(StickerImageContans.DEFAULTMINALTIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        f = 1;
                        updateAlpha();
                        handler.sendEmptyMessage(StickerImageContans.DEFAULTHANDLER);
                        continue;
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
        });
    }

    private void updateAlpha() {
        alpha = StickerImageContans.COMPILEALPHA * f + alpha;
        if (alpha >= StickerImageContans.MAXALPHA) {
            alpha = StickerImageContans.MAXALPHA;
        }
        if (alpha <= StickerImageContans.MINALPHA) {
            alpha = StickerImageContans.MINALPHA;
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

    public void setAlpha(int alpha) {
        this.alpha = alpha;
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
