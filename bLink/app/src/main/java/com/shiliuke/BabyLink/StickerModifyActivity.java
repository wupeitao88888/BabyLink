package com.shiliuke.BabyLink;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.BeanShowModel;
import com.shiliuke.view.stickerview.StickerImageContans;
import com.shiliuke.view.stickerview.StickerImageView;

/**
 * Created by wangzhi on 15/11/4.
 */
public class StickerModifyActivity extends ActivitySupport implements View.OnClickListener {
    private EditText edit_modify_sticker;//弹幕输入框
    private Button btn_modify_sticker;//发布按钮
    private ImageView image_beanshow_item_head;//头像
    private TextView tv_beanshow_item_name;//用户名
    private TextView tv_beanshow_item_time;//发布时间
    private Button btn_beanshow_item_addfocus;//加关注按钮
    private StickerImageView mSticker_View;//主View
    private TextView tv_beanshow_item_dou;//多少逗
    private TextView tv_beanshow_item_msg;//msg
    private Button btn_beanshow_item_dou;//逗一逗
    private Button btn_beanshow_item_share;//分享
    private BeanShowModel oldModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_sticker);
        initView();
        initData();
    }

    private void initView() {
        setCtenterTitle("弹幕详情");
        edit_modify_sticker = (EditText) findViewById(R.id.edit_modify_sticker);
        edit_modify_sticker.addTextChangedListener(mEditWatcher);

        btn_modify_sticker = (Button) findViewById(R.id.btn_modify_sticker);
        btn_beanshow_item_addfocus = (Button) findViewById(R.id.btn_beanshow_item_addfocus);
        btn_beanshow_item_dou = (Button) findViewById(R.id.btn_beanshow_item_dou);
        btn_beanshow_item_share = (Button) findViewById(R.id.btn_beanshow_item_share);
        btn_modify_sticker.setOnClickListener(this);

        image_beanshow_item_head = (ImageView) findViewById(R.id.image_beanshow_item_head);
        tv_beanshow_item_name = (TextView) findViewById(R.id.tv_beanshow_item_name);
        tv_beanshow_item_time = (TextView) findViewById(R.id.tv_beanshow_item_time);
        tv_beanshow_item_dou = (TextView) findViewById(R.id.tv_beanshow_item_dou);
        tv_beanshow_item_msg = (TextView) findViewById(R.id.tv_beanshow_item_msg);

        mSticker_View = (StickerImageView) findViewById(R.id.sticker_beanshow_item);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        BeanShowModel model = (BeanShowModel) intent.getSerializableExtra("model");
        if (model == null) {
            return;
        }
        oldModel = new BeanShowModel(model.getHeadurl(), model.getName(), model.getTime(), model.getContenturl(), model.getTotaldou(), model.getMsg(), model.getStickerlist());
        for (int i = 0; i < oldModel.getStickerlist().size(); i++) {
            oldModel.getStickerlist().get(i).setAlpha(StickerImageContans.MAXALPHA);
        }
        Bitmap bit = BitmapFactory.decodeResource(getResources(), StickerImageContans.DEFAULTBITMAP);
        mSticker_View.setBgBitmap(bit, oldModel);
        mSticker_View.startAnim();
    }

    TextWatcher mEditWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mSticker_View.updateModelText(s.toString());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_modify_sticker:
                if (mSticker_View.getCompileModel() != null) {
                    Intent intent = new Intent();
                    intent.putExtra("model", mSticker_View.getCompileModel());
                    intent.putExtra("position", getIntent().getIntExtra("position", 0));
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    setResult(RESULT_CANCELED);
                    finish();
                }
                break;
        }
    }
}
