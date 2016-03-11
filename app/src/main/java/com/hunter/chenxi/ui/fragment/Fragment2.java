package com.hunter.chenxi.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hunter.chenxi.R;
import com.hunter.chenxi.bean.New;
import com.hunter.chenxi.lib.loadmore.LoadMoreListView;
import com.hunter.chenxi.ui.activity.NewsActivity;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

public class Fragment2 extends Fragment{

	private Activity context;
	private New news;


	@Bind(R.id.rotate_header_list_view_frame)
	PtrClassicFrameLayout mPtrFrame;

	@Bind(R.id.listView)
	LoadMoreListView listView;

	QuickAdapter<New> adapter;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.find_page_list,container,false);
		ButterKnife.bind(this, view);
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

	void initData(){
		adapter = new QuickAdapter<New>(context,R.layout.find_page_list_item) {
			@Override
			protected void convert(BaseAdapterHelper helper, New item) {
				helper.setText(R.id.name,item.getTitle())
				.setText(R.id.address,item.getDate())
				.setText(R.id.newsUrl, item.getUrl())
				.setImageUrl(R.id.logo, "http://i3.go2yd.com/image.php?type=webp_180x120&url=" + item.getImage())
						;
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
				TextView tvUrl = (TextView) view.findViewById(R.id.newsUrl);
				Toast.makeText(getActivity(), "You win!" + tvUrl.getText(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(), NewsActivity.class);
				intent.putExtra("url", tvUrl.getText());
				startActivity(intent);
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

	void initView(){

	}


	public void  loadData(){

		AsyncHttpClient myClient = new AsyncHttpClient();
		com.loopj.android.http.PersistentCookieStore myCookieStore = new com.loopj.android.http.PersistentCookieStore(getActivity());
		myClient.setCookieStore(myCookieStore);

		BasicClientCookie newCookie = new BasicClientCookie("JSESSIONID", "lbZ-vIeSC3y-sA0-JUMxyw");
		//newCookie.setVersion(1);
		newCookie.setDomain("a1.go2yd.com");
		newCookie.setPath("/");

		myCookieStore.addCookie(newCookie);

		String someData = "{\"clientInfo\":{\"userInfo\":{\"mac\":\"00:66:65:2d:e4:4e\",\"language\":\"zh\",\"serviceProvider\":\"WIFI\",\"country\":\"CN\",\"imei\":\"d7082441784eb8271792f02dd4362ad4\"},\"deviceInfo\":{\"screenHeight\":1280,\"device\":\"S8\",\"androidVersion\":\"4.2.2\",\"model\":\"JY-G4S\",\"screenWidth\":720}}}";
		StringEntity entity = null;
		try {
			entity = new StringEntity(someData);
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		} catch(Exception e) {
			Log.e("error","error");
		}

		String  url = "http://a1.go2yd.com/Website/channel/best-news?platform=1&infinite=true&cstart=0&push_refresh=0&cend=6&appid=health&cv=3.2.2&" +
				"refresh=0&fields=docid&fields=date&fields=image&fields=image_urls&fields=like&fields=source" +
				"&fields=title&fields=url&fields=comment_count&fields=up&fields=down&version=010911&net=wifi" ;

		myClient.post(null,url,entity,"application/json",new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

				JSONObject object = JSON.parseObject(new String(responseBody));

				List<New> list = JSONArray.parseArray(object.getString("result"), New.class);
				mPtrFrame.refreshComplete();
				listView.updateLoadMoreViewText(list);

				/*isLoadAll = list.size() < 30;
				if(pno == 1) {
					adapter.clear();
				}*/

				adapter.addAll(list);

				Log.e("TAG", "onSuccess json = " + JSON.toJSONString(list));
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  byte[] errorResponse, Throwable e) {
				Log.e("TAG", "获取数据异常 ", e);
			}
		});

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
