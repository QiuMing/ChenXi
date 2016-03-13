package com.hunter.chenxi.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hunter.chenxi.R;
import com.hunter.chenxi.app.Constants;
import com.hunter.chenxi.bean.NewsBean;
import com.hunter.chenxi.net.AsyncHttpClientUtil;
import com.hunter.chenxi.ui.activity.NewsActivity;
import com.hunter.chenxi.ui.custom.LoadMoreListView;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
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

public class FindPageSecondTabFragment extends Fragment{

	private Activity context;
 	private int pageNumbers ;
 	private String requestUrl;

	@Bind(R.id.rotate_header_list_view_frame)
	PtrClassicFrameLayout mPtrFrame;

	@Bind(R.id.listView)
	LoadMoreListView listView;

	QuickAdapter<NewsBean> adapter;

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
		pageNumbers = 1;
		initRequestData();
		initView();
		loadData();
	}


	//这里链接对应心里健康栏目
	void initRequestData(){
		//Toast.makeText(getActivity(), "The pageNumber is " + pageNumbers, Toast.LENGTH_SHORT).show();
		int cend = pageNumbers * Constants.LIST_ITEM_NUMBERS;
		int cstart = cend - Constants.LIST_ITEM_NUMBERS;
		Log.e("Tag","cend "+cend + "  cstart"+cstart);
		requestUrl = "http://a1.go2yd.com/Website/channel/news-list-for-channel?platform=1&infinite=true&cstart="+cstart +
				 "&push_refresh=0&cend="+cend+"&appid=health&cv=3.2.2&" +
				 "refresh=0&channel_id=2230739278&fields=docid&fields=date&"+
				 "fields=image&fields=image_urls&fields=like&fields=source&fields=title&fields=url&fields=comment_count&fields=up&fields=down&version=010911";

	}

	void initView(){
		adapter = new QuickAdapter<NewsBean>(context,R.layout.find_page_list_item) {
			@Override
			protected void convert(BaseAdapterHelper helper, NewsBean item) {
				if(item.getImage()!=null && item.getTitle()!=null) {
					helper.setText(R.id.news_item_title, item.getTitle())
							.setText(R.id.news_itme_time, item.getDate())
							.setText(R.id.news_content_url, item.getUrl())
							.setText(R.id.news_item_source,item.getSource())
							.setImageUrl(R.id.news_image_url, "http://i3.go2yd.com/image.php?type=webp_180x120&url=" + item.getImage())
					;
				}
			}
		};

		listView.setDrawingCacheEnabled(true);
		listView.setAdapter(adapter);

		listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				loadData();
			}
		});

		listView.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				TextView tvUrl = (TextView) view.findViewById(R.id.news_content_url);
				Intent intent = new Intent(getActivity(), NewsActivity.class);
				intent.putExtra("url", tvUrl.getText());
				startActivity(intent);
			}
		});


		mPtrFrame.setLastUpdateTimeRelateObject(this);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				pageNumbers = 1;
				initRequestData();
				loadData();
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
			}
		});
	}


	public void  loadData(){
		AsyncHttpClientUtil myClient = AsyncHttpClientUtil.getInstance();
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

		myClient.post(null,requestUrl,entity,"application/json",new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

				JSONObject object = JSON.parseObject(new String(responseBody));

				List<NewsBean> list = JSONArray.parseArray(object.getString("result"), NewsBean.class);
				listView.updateLoadMoreViewText(list);
				pageNumbers++;
				adapter.addAll(list);

				mPtrFrame.refreshComplete();
				Log.i("TAG", "The pageNumbers is  " +pageNumbers);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  byte[] errorResponse, Throwable e) {
				Log.i("TAG", "获取数据异常 ", e);
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
