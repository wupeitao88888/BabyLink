package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.BeanShowModelResult;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.view.stickerview.StickerImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhi on 15/11/4.
 */
public class StickerModifyActivity extends ActivitySupport implements View.OnClickListener, VolleyListerner {
    private EditText edit_modify_sticker;//弹幕输入框
    private Button btn_modify_sticker;//发布按钮
    private ImageView image_beanshow_item_head;//头像
    private TextView tv_beanshow_item_name;//用户名
    private TextView tv_beanshow_item_time;//发布时间
    private Button btn_beanshow_item_addfocus;//加关注按钮
    private StickerImageView mSticker_View;//主View
    private ImageView mSticker_image_View;//主View
    private TextView tv_beanshow_item_dou;//多少逗
    private TextView tv_beanshow_item_msg;//msg
    private Button btn_beanshow_item_dou;//逗一逗
    private Button btn_beanshow_item_share;//分享
    private String xiu_id;

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
        btn_beanshow_item_share.setVisibility(View.GONE);
        btn_beanshow_item_dou.setVisibility(View.GONE);
        btn_beanshow_item_addfocus.setVisibility(View.GONE);
        btn_modify_sticker.setOnClickListener(this);

        image_beanshow_item_head = (ImageView) findViewById(R.id.image_beanshow_item_head);
        mSticker_image_View = (ImageView) findViewById(R.id.sticker_image_beanshow_item);
        tv_beanshow_item_name = (TextView) findViewById(R.id.tv_beanshow_item_name);
        tv_beanshow_item_time = (TextView) findViewById(R.id.tv_beanshow_item_time);
        tv_beanshow_item_dou = (TextView) findViewById(R.id.tv_beanshow_item_dou);
        tv_beanshow_item_msg = (TextView) findViewById(R.id.tv_beanshow_item_msg);

        mSticker_View = (StickerImageView) findViewById(R.id.sticker_beanshow_item);

    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        BeanShowModelResult old = (BeanShowModelResult) intent.getSerializableExtra("model");
        if (old == null) {
            finish();
            return;
        }
        xiu_id = old.getXiu_id();
        final BeanShowModelResult model = new BeanShowModelResult();
        model.setInfo(old.getInfo());
        model.setCommend_list(old.getCommend_list());
        model.revertAlpha();
        Glide.with(context).load(old.getMember_avar()).into(image_beanshow_item_head);
        Glide.with(context).load(old.getImage_url()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                mSticker_View.setBeanShowModel(model);
                mSticker_View.stopAnim();
                mSticker_View.startAnim();
                return false;
            }
        }).into(mSticker_image_View);
        tv_beanshow_item_name.setText(old.getMember_name());
        tv_beanshow_item_time.setText(old.getTime());
        tv_beanshow_item_dou.setText(old.getCommend_list().size() + "");
        tv_beanshow_item_msg.setText(old.getInfo());
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
                String info = edit_modify_sticker.getText().toString();
                if (TextUtils.isEmpty(info)) {
                    showToast("请输入内容");
                    return;
                }


                if (mSticker_View.getCompileModel() != null) {
                    Map<String, String> params = new HashMap<>();
                    params.put("info", info);
                    params.put("member_id", "1");
                    params.put("xiu_id", xiu_id);
                    params.put("position_x", mSticker_View.getCompileModel().getX() + "");
                    params.put("position_y", mSticker_View.getCompileModel().getY() + "");
                    BasicRequest.getInstance().requestPost(this, TaskID.ACTION_XIUDOU_ADD_COMMEND, params, AppConfig.XIUDOU_ADDCOMMEND);
                } else {
                    showToast("挑逗失败");
                    setResult(RESULT_CANCELED);
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mSticker_View.stopAnim();
        super.onDestroy();
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        Intent intent = new Intent();
        intent.putExtra("model", mSticker_View.getCompileModel());
        intent.putExtra("position", getIntent().getIntExtra("position", 0));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onResponseError(String error, int taskid) {
        showToast(error);
    }
}
