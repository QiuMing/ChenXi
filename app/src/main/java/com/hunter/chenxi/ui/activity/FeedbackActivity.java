package com.hunter.chenxi.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by Ming on 2016/3/12.
 */
public class FeedbackActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_version)
    TextView mVersionTextView;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_about);
    }

    @Override
    public void initView() {
        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initPresenter() {

    }
}
