package com.hunter.chenxi.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.chenxi.R;
import com.hunter.chenxi.ui.activity.UserInfoActivity;
import com.hunter.chenxi.ui.custom.pulltozoomview.PullToZoomScrollViewEx;
import com.hunter.chenxi.ui.activity.LoginActivity;
import com.hunter.chenxi.ui.activity.RegisterActivity;
import com.hunter.chenxi.ui.activity.StepCounterActivity;
import com.hunter.chenxi.utils.Utils;

/**
 * Created by Ming on 2016/2/18.
 */
public class HomeFragment extends Fragment {

    private Activity context;

    private View root;

    private PullToZoomScrollViewEx scrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity();
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        scrollView = (PullToZoomScrollViewEx) root.findViewById(R.id.scrollView);
        View headView = LayoutInflater.from(context).inflate(R.layout.member_head_view, null, false);
        View zoomView = LayoutInflater.from(context).inflate(R.layout.member_zoom_view, null, false);
        View contentView = LayoutInflater.from(context).inflate(R.layout.member_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);

        headView.findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterActivity.class);
                context.startActivity(intent);
            }
        });

        headView.findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toast("" + Utils.getBooleanData("loginde", false));
                if (!Utils.getBooleanData("loginde", false)) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    Utils.toast("登录成功");
                    startActivity(new Intent(Utils.getContext(), UserInfoActivity.class));
                }
            }
        });

        scrollView.getPullRootView().findViewById(R.id.tv_test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toast("待开发...");
            }
        });

        scrollView.getPullRootView().findViewById(R.id.tv_test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Utils.getContext(), StepCounterActivity.class));
            }
        });
    }


/*
    @OnClick(R.id.temp_btnSingin)
    public void btnSingin_onClick() {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
        //Toast.makeText(getActivity(), "You win!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.temp_btnLogin)
    public void btnLogin_onClick() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        //Toast.makeText(getActivity(), "You win!", Toast.LENGTH_SHORT).show();
    }*/
//
//    @OnClick(R.id.tv_test1)
//    public void tv_test1_onClick() {
//        Utils.toast("aa");
//    }
//
//    @OnClick(R.id.tv_test2)
//    public void tv_test2_onClick() {
//        Utils.toast("bb");
//    }
}
