package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiliuke.adapter.TopicCommentListAdapter;
import com.shiliuke.adapter.TopicPicGridAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.Comment;
import com.shiliuke.bean.UserImgs;
import com.shiliuke.view.NoScrollGridView;
import com.shiliuke.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的话题详情
 * Created by wupeitao on 15/11/5.
 */
public class MeTopicInfoActivity extends ActivitySupport implements View.OnClickListener {

    private ImageView user_pic;//用户头像

    private TextView user_name,//用户姓名
            user_content,//话题内容
            user_time,//发表时间
            user_supportnames,//点赞者
            user_edit;//编辑
    private NoScrollListView noscrolllistview;
    private NoScrollGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_me_topic_info);
        initView();

    }


    private void initView() {
        user_pic = (ImageView) findViewById(R.id.user_pic);
        user_name = (TextView) findViewById(R.id.user_name);
        user_content = (TextView) findViewById(R.id.user_content);
        user_time = (TextView) findViewById(R.id.user_time);
        user_supportnames = (TextView) findViewById(R.id.user_supportnames);
        user_edit = (TextView) findViewById(R.id.user_edit);
        noscrolllistview = (NoScrollListView) findViewById(R.id.noscrolllistview);
        gridView = (NoScrollGridView) findViewById(R.id.gridView);
        gridView.setVisibility(View.VISIBLE);
        List<UserImgs> ui = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            UserImgs uimage = new UserImgs(1, "", "");
            ui.add(uimage);
        }


        gridView.setAdapter(new TopicPicGridAdapter(ui,
                context));
        gridView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {

                    }
                });
        List<Comment> commentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Comment ct = new Comment();
            ct.setMember_name("小明");
            ct.setInfo("隔壁村的钱寡妇，孙寡妇，李寡妇，周寡妇，吴寡妇，郑寡妇，王寡妇，冯寡妇，陈寡妇，卫寡妇，蒋寡妇，沈寡妇，韩寡妇，杨寡妇，朱寡妇，秦寡妇，尤寡妇，许寡妇，何寡妇，吕寡妇，施寡妇，张寡妇，魏寡妇，陶寡妇，戚寡妇，谢寡妇，邹寡妇，喻寡妇，章寡妇，云寡妇，苏寡妇，潘寡妇，范寡妇，彭寡妇，鲁寡妇，韦寡妇，昌寡妇，马寡妇，苗寡妇，凤寡妇，花寡妇，方寡妇，俞寡妇，任寡妇，袁寡妇，等360个寡妇都觉得很赞");
            if (isOdd(i)) {
                ct.setCommentReplyName("小李");
            }
            commentList.add(ct);
        }


        noscrolllistview.setVisibility(View.VISIBLE);
        noscrolllistview.setAdapter(new TopicCommentListAdapter(commentList,
                context));

        noscrolllistview
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {


                    }
                });

        user_edit.setOnClickListener(this);

    }
    public boolean isOdd(int i) {
        return i % 2 != 0;//如果一个数是偶数，就算是负数整除2余数也为0
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.user_edit:
                startActivity(new Intent(MeTopicInfoActivity.this,SendTopicActivity.class));
                break;
        }
    }
}
