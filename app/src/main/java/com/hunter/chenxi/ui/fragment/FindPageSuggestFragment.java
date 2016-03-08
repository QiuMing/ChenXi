package com.hunter.chenxi.ui.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hunter.chenxi.R;
import com.hunter.chenxi.bean.SearchShop;
import com.hunter.chenxi.lib.loadmore.LoadMoreListView;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Ming on 2016/3/5.
 * 技术点：
 *
 * 使用到ButterKnife 为fragment 绑定 view
 * 使用了下拉刷新加载数据
 * 使用了通用的适配器
 * 使用了Picasso 来管理图片加载
 * 继承了AsyncTask 来实现数据在非主线程 中加载
 */
public class FindPageSuggestFragment  extends Fragment {

    private Activity context;
    private SearchShop param;   //查询参数，用于控制下拉 或者 上啦 时候数据请求加载
    private int pno = 1;
    private boolean isLoadAll;

    @Bind(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.listView)
    LoadMoreListView listView;

    QuickAdapter<SearchShop> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find_page_list,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initData();
        initView();
        loadData();

    }

    void initView() {
        adapter = new QuickAdapter<SearchShop>(context, R.layout.find_page_list_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, SearchShop shop) {
                helper.setText(R.id.name, shop.getName())
                        .setText(R.id.address, shop.getAddr())
                        .setImageUrl(R.id.logo, "http://sye.img.zhongsou.com"+shop.getLogo()); // 自动异步加载图片
            }
        };

        listView.setDrawingCacheEnabled(true);
        listView.setAdapter(adapter);

        // header custom begin
        final StoreHouseHeader header = new StoreHouseHeader(context);
        //header.setPadding(0, DeviceUtil.dp2px(context, 15), 0, 0);
        header.initWithString("ChenXi");
        header.setTextColor(getResources().getColor(R.color.gray));
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        // header custom end


        // 下拉刷新
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData();
                loadData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        // 加载更多
        listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadData();
            }
        });

        // 点击事件
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // UIHelper.showHouseDetailActivity(context);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    Picasso.with(context).pauseTag(context);
                } else {
                    Picasso.with(context).resumeTag(context);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // getLastVisibleItemBitmap(firstVisibleItem+visibleItemCount);
//                takeScreenShot(context);
            }
        });

    }

    private void initData() {
        param = new SearchShop();
        pno = 1;
        isLoadAll = false;
    }

    private void loadData() {
        if (isLoadAll) {
            return;
        }
        //数据加载，重新渲染listView
        new GetDataTask().execute();
    }


    private class GetDataTask extends AsyncTask<Void, Void, String[]> {
        List<SearchShop> list = new ArrayList<SearchShop>();

        @Override
        protected String[] doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://sye.zhongsou.com/ent/rest?m=dpSearch.recommendShop&p=eyJjaXR5IjoiYmVpamluZyIsImxhdCI6MzkuOTgyMzE0LCJsbmciOjExNi40MDk2NzEsInBubyI6%0AMSwicHNpemUiOjMwLCJzaWQiOjB9%0A?m=dpSearch.recommendShop&p=eyJjaXR5IjoiYmVpamluZyIsImxhdCI6MzkuOTgyMzE0LCJsbmciOjExNi40MDk2NzEsInBubyI6%0AMSwicHNpemUiOjMwLCJzaWQiOjB9%0A")
                    .build();

            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            client.setCookieHandler(cookieManager);

            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                JSONObject object = JSON.parseObject(response.body().string());

                 list = JSONArray.parseArray(object.getString("body"), SearchShop.class);
                Log.e("test", JSON.toJSONString(list));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {

            Log.e("___________","执行UI 线程"+JSON.toJSONString(list));
            mPtrFrame.refreshComplete();
            listView.updateLoadMoreViewText(list);

            isLoadAll = list.size() < 30;
            if(pno == 1) {
                adapter.clear();
            }

            adapter.addAll(list);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Picasso.with(context).resumeTag(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        Picasso.with(context).pauseTag(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Picasso.with(context).cancelTag(context);
    }
}
