package com.shiliuke.view.stickerview;

import android.os.Handler;
import com.shiliuke.bean.BeanShowModelResult;

import java.io.Serializable;

/**
 * 图片贴纸Model
 * Created by wangzhi on 15/10/30.
 */
public class StickerImageModel implements Serializable {
    private String commend_id;//
    private String xiu_id;//
    private String member_id;//
    private String member_name;//
    private String member_avar;//
    private String info;//贴纸文字
    private int alpha;//透明度
    private int f;
    private String position_x;
    private String position_y;
    private String add_time;

    /**
     * 开始改变透明度
     *
     * @param handler
     */
    public void startLooper(final BeanShowModelResult model, final Handler handler) {
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
        this.info = text;
        this.position_x = StickerImageContans.DEFAULTX + "";
        this.position_y = StickerImageContans.DEFAULTY + "";
        this.alpha = StickerImageContans.MAXALPHA;
    }

    public void setXy(float x, float y) {
        this.position_x = x + "";
        this.position_y = y + "";
    }

    public float getY() {
        return Float.parseFloat(position_y);
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public float getX() {
        return Float.parseFloat(position_x);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
