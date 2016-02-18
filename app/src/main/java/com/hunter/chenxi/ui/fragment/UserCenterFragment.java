package com.hunter.chenxi.ui.fragment;

import android.app.Activity;
import android.view.View;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseFragment;

/**
 * Created by Ming on 2016/2/16.
 */
public class UserCenterFragment extends BaseFragment{

    private Activity ctx;
    private View layout;

    @Override
    public int getLayoutRes() {
        return 0;
    }

    @Override
    public void initView() {
        ctx = this.getBaseActivity();
        layout = ctx.getLayoutInflater().inflate(R.layout.fragment_feedback,null);



    }
}
