package com.hunter.chenxi.ui.fragment;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hunter.chenxi.R;
import com.hunter.chenxi.bean.SearchShopBean;
import com.hunter.chenxi.net.PersistentCookieStore;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment3 extends Fragment {

	@Bind(R.id.fragment3_Button)
	Button btnTest;

	@Bind(R.id.fragment3_text)
	TextView txtView;

	@Bind(R.id.fragment3_Button2)
	Button btn2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment3, container, false);
		ButterKnife.bind(this, view);
		return view;
	}


	public  void  test_share(){

		SharedPreferences sp = getActivity().getSharedPreferences("CookiePrefsFile", 0);
		txtView.setText("修改 文字成功");

		SharedPreferences.Editor editor = sp.edit();
		editor.putString("STRING_KEY", "string");
		editor.putInt("INT_KEY", 0);
		editor.putBoolean("BOOLEAN_KEY", true);
		editor.putString("JSESSIONID", "lbZ-vIeSC3y-sA0-JUMxyw");

		editor.commit();

		Log.d("SP", sp.getString("STRING_KEY", "none"));
		Log.d("SP", sp.getString("NOT_EXIST", "none"));
		txtView.setText(sp.getString("JSESSIONID", "none"));
	}

	@OnClick(R.id.fragment3_Button)
	public void  test_asy(){

		AsyncHttpClient myClient = new AsyncHttpClient();
		com.loopj.android.http.PersistentCookieStore myCookieStore = new com.loopj.android.http.PersistentCookieStore(getActivity());
		myClient.setCookieStore(myCookieStore);

		BasicClientCookie newCookie = new BasicClientCookie("JSESSIONID", "lbZ-vIeSC3y-sA0-JUMxyw");
		//newCookie.setVersion(1);
		newCookie.setDomain("a1.go2yd.com");
		newCookie.setPath("/");

		myCookieStore.addCookie(newCookie);
		RequestParams params = new RequestParams();


		params.put("platform","1");
		params.put("appid","health");
		params.put("cv","3.2.2");
		params.put("version","010911");
		params.put("net","wifi");

 		myClient.post("http://a1.go2yd.com/Website/proxy/real-time-log",params ,new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

			String json = new String(responseBody);
			Log.e("TAG", "onSuccess json = " + json);
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
								  byte[] errorResponse, Throwable e) {
				Log.e("TAG", "获取数据异常 ", e);
			}
		});

		RequestParams params2 = new RequestParams();


		params2.put("platform","1");
		params2.put("infinite","true");
		params2.put("cstart","0");
		params2.put("cend","30");
		params2.put("appid","health");
		params2.put("cv","3.2.2");
		params2.put("refresh","0");
		params2.put("channel_id","2230739234");
		params2.put("fields","docid");
		params2.put("fields","date");
		params2.put("fields","image");
		params2.put("fields","image_urls");
		params2.put("fields","like");
		params2.put("fields","source");
		params2.put("fields","title");
		params2.put("fields","url");
		params2.put("fields","comment_count");
		params2.put("fields","up");
		params2.put("fields","down");
		params2.put("version","010911");
		params2.put("net","wifi");


		String someData = "{\"clientInfo\":{\"userInfo\":{\"mac\":\"00:66:65:2d:e4:4e\",\"language\":\"zh\",\"serviceProvider\":\"WIFI\",\"country\":\"CN\",\"imei\":\"d7082441784eb8271792f02dd4362ad4\"},\"deviceInfo\":{\"screenHeight\":1280,\"device\":\"S8\",\"androidVersion\":\"4.2.2\",\"model\":\"JY-G4S\",\"screenWidth\":720}}}";
		StringEntity entity = null;
		try {
			entity = new StringEntity(someData);
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		} catch(Exception e) {
			Log.e("error","error");
		}

		String  url = "http://a1.go2yd.com/Website/channel/best-news?platform=1&infinite=true&cstart=0&push_refresh=0&cend=30&appid=health&cv=3.2.2&refresh=0&fields=docid&fields=date&fields=image&fields=image_urls&fields=like&fields=source&fields=title&fields=url&fields=comment_count&fields=up&fields=down&version=010911&net=wifi" ;

		myClient.post(null,url,entity,"application/json",new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

				//String json = new String(responseBody);

				JSONObject object = JSON.parseObject(new String(responseBody));

				//list = JSONArray.parseArray(object.getString("body"), SearchShopBean.class);

				Log.e("TAG", "onSuccess json = " + JSON.toJSONString(object.get("result")));
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  byte[] errorResponse, Throwable e) {
				Log.e("TAG", "获取数据异常 ", e);
			}
		});
	}


	/**
	 * 获取标准 Cookie
	 */
	private String getCookieText() {
		com.loopj.android.http.PersistentCookieStore myCookieStore = new com.loopj.android.http.PersistentCookieStore(getActivity());
		List<Cookie> cookies = myCookieStore.getCookies();
		Log.d("TAG", "cookies.size() = " + cookies.size());
		//Util.setCookies(cookies);
		for (Cookie cookie : cookies) {
			Log.d("TAG", cookie.getName() + " = " + cookie.getValue());
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cookies.size(); i++) {
			Cookie cookie = cookies.get(i);
			String cookieName = cookie.getName();
			String cookieValue = cookie.getValue();
			if (!TextUtils.isEmpty(cookieName)
					&& !TextUtils.isEmpty(cookieValue)) {
				sb.append(cookieName + "=");
				sb.append(cookieValue + ";");
			}
		}
		Log.e("cookie", sb.toString());
		return sb.toString();
	}


	class PageTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url("http://a1.go2yd.com/Website/channel/news-list-for-channel")
					.build();

			client.setCookieHandler(new CookieManager(
					new PersistentCookieStore(getActivity()),
					CookiePolicy.ACCEPT_ALL));


 			try {
				Response  response = client.newCall(request).execute();
				if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

				JSONObject object = JSON.parseObject(response.body().string());

				List<SearchShopBean> list = JSONArray.parseArray(object.getString("body"), SearchShopBean.class);
				Log.e("++++++++++++",JSON.toJSONString(list));
				System.out.println(JSON.toJSONString(list));
				return  JSON.toJSONString(list);


			} catch (IOException e) {
				e.printStackTrace();
			}


			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			txtView.setText(JSON.toJSONString(result));
		}
	}

}
