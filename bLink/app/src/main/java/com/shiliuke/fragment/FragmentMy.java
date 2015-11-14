package com.shiliuke.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shiliuke.BabyLink.HelpActivity;
import com.shiliuke.BabyLink.MeConvertCodeActivity;
import com.shiliuke.BabyLink.MeHomePage;
<<<<<<< Updated upstream
import com.shiliuke.BabyLink.MeOrderActivity;
=======
import com.shiliuke.BabyLink.MeTopic;
import com.shiliuke.BabyLink.MyHomeActivity;
>>>>>>> Stashed changes
import com.shiliuke.BabyLink.PayEndActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.BabyLink.PersonalData;


public class FragmentMy extends Fragment implements OnClickListener {


    private View rootView;
    private Activity mActivity = null;

    private RelativeLayout user_home_rl,//我的主页
            user_order_rl,//我的订单
            user_information_rl,//个人资料
            user_help_rl,//帮助中心
            version_update_rl,//版本升级
            exchange_code_rl,//兑换码
            pay_rl;//支付尾款

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (rootView == null) {
            rootView = initView();

        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private View initView() {
        mActivity = getActivity();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_me, null);
        user_home_rl = (RelativeLayout) view.findViewById(R.id.user_home_rl);
        user_order_rl = (RelativeLayout) view.findViewById(R.id.user_order_rl);
        user_information_rl = (RelativeLayout) view.findViewById(R.id.user_information_rl);
        user_help_rl = (RelativeLayout) view.findViewById(R.id.user_help_rl);
        version_update_rl = (RelativeLayout) view.findViewById(R.id.version_update_rl);
        exchange_code_rl = (RelativeLayout) view.findViewById(R.id.exchange_code_rl);
        pay_rl = (RelativeLayout) view.findViewById(R.id.pay_rl);


        user_home_rl.setOnClickListener(this);
        user_order_rl.setOnClickListener(this);
        user_information_rl.setOnClickListener(this);
        user_help_rl.setOnClickListener(this);
        version_update_rl.setOnClickListener(this);
        exchange_code_rl.setOnClickListener(this);
        pay_rl.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_home_rl:
                // 我的主页
                startActivity(new Intent(mActivity, MeHomePage.class));
                break;
            case R.id.user_order_rl:
                //我的订单
                startActivity(new Intent(mActivity, MeOrderActivity.class));
                break;
            case R.id.user_information_rl:
                //个人资料
                startActivity(new Intent(mActivity, PersonalData.class));
                break;
            case R.id.user_help_rl:
                //帮助中心
                startActivity(new Intent(mActivity, HelpActivity.class));
                break;
            case R.id.version_update_rl:
                //版本升级
                break;
            case R.id.exchange_code_rl:
                //兑换码
                startActivity(new Intent(mActivity, MeConvertCodeActivity.class));
                break;
            case R.id.pay_rl:
<<<<<<< Updated upstream
                startActivity(new Intent(mActivity, PayEndActivity.class));
=======
                startActivity(new Intent(mActivity,PayEndActivity.class));
>>>>>>> Stashed changes
                //支付尾款
                break;

        }
    }
}
