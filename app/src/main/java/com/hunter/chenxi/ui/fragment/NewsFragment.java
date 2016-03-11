package com.hunter.chenxi.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.hunter.chenxi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/3/10.
 */
public class NewsFragment extends Fragment{

    @Bind(R.id.webView_news)
      WebView webView;

     String url;

    public NewsFragment(String url){
        this.url = url;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,container,false);
        ButterKnife.bind(this, view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e("Tab","加载");
        webView.loadUrl(url);
        super.onActivityCreated(savedInstanceState);
    }
}
