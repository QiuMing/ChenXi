package com.hunter.chenxi.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by Ming on 2016/3/12.
 */
public class AboutUsActivity extends BaseActivity{


    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_about_us);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setTitle("ChenXi");// 标题的文字需在setSupportActionBar之前，不然会无效
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }
}


