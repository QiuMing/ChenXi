package com.hunter.chenxi.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by Ming on 2016/3/11.
 */
public class NewsActivity extends BaseActivity {

    @Bind(R.id.webView_news)
    WebView webView;

    @Override
    public void initContentView() {
        setContentView(R.layout.fragment_news);
    }

    @Override
    public void initView() {
        Bundle bundle = this.getIntent().getExtras();
        String url = bundle.getString("url");
        Log.i("获取到的name值为", url);
        webView.loadUrl(url);
    }

    @Override
    public void initPresenter() {
    }
}
