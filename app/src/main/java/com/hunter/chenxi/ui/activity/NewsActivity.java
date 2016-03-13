package com.hunter.chenxi.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hunter.chenxi.R;
import com.hunter.chenxi.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by Ming on 2016/3/11.
 */
public class NewsActivity extends BaseActivity {

    @Bind(R.id.webView_news)
    WebView webView;

    @Bind(R.id.toolbar_with_title_view)
    Toolbar toolbar;
    @Override
    public void initContentView() {
        setContentView(R.layout.activity_news);
    }

    @Override
    public void initView() {
        Bundle bundle = this.getIntent().getExtras();
        String url = bundle.getString("url");
        Log.i("获取到的name值为", url);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
        toolbar.setTitle("atat");
    }

    @Override
    public void initPresenter() {
    }
}
