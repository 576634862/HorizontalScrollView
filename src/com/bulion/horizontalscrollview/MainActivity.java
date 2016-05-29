package com.bulion.horizontalscrollview;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bulion.horizontalscrollview.R;
import com.bulion.horizontalscrollview.adapter.HorizontalScrollViewAdapter;
import com.bulion.horizontalscrollview.domain.ImageData;
import com.bulion.horizontalscrollview.domain.ImageData.Goods;
import com.bulion.horizontalscrollview.utlis.CacheUtils;
import com.bulion.horizontalscrollview.view.MyHorizontalScrollView;
import com.bulion.horizontalscrollview.view.MyHorizontalScrollView.CurrentImageChangeListener;
import com.bulion.horizontalscrollview.view.MyHorizontalScrollView.OnItemClickListener;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class MainActivity extends Activity
{
	private MyHorizontalScrollView mHorizontalScrollView;
	private HorizontalScrollViewAdapter mAdapter;
	private ImageView mImg;
	private ImageData mImageData;
	private String URL ="http://192.168.191.1:8080/test/anzmall/CarouselImage.json";
	private ArrayList<Goods> mGoodsList;
	private BitmapUtils mBitmapUtils;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mImg = (ImageView) findViewById(R.id.id_content);
		mHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.id_horizontalScrollView);
		inintTwoData();
	}
	public void inintTwoData() {
		String cache =CacheUtils.getCache(URL,MainActivity.this);
		if(!TextUtils.isEmpty(cache)){
			System.out.println("发现缓存");
			processResult(cache);
		}else{
			System.out.println("网络获取");
			getDataFromServer();
		}
	}
	
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, URL, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// 请求成功
				String result = responseInfo.result;// 获取json字符串
				System.out.println("请求的结果:" + result);
				processResult(result);
				// // 写缓存
				CacheUtils.setCache(URL, result, getApplicationContext());
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// 请求失败
				error.printStackTrace();
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
				System.out.println("请求失败");
			}
		});
	}
		
		/**
		 * 解析json数据
		 * 
		 * @param result
		 */
		protected void processResult(String result) {
			mBitmapUtils= new BitmapUtils(getApplicationContext());
			// gson->json
			Gson gson = new Gson();
			mImageData = gson.fromJson(result, ImageData.class);
			System.out.println("解析结果:" + mImageData);
			mGoodsList = mImageData.goods;
			mAdapter = new HorizontalScrollViewAdapter(getApplicationContext(), mGoodsList);
			//添加滚动回调
			mHorizontalScrollView
					.setCurrentImageChangeListener(new CurrentImageChangeListener()
					{
						@Override
						public void onCurrentImgChanged(int position,
								View viewIndicator)
						{
							mBitmapUtils.display(mImg, mGoodsList.get(position).goods_image);
							viewIndicator.setBackgroundColor(Color
									.parseColor("#AA024DA4"));
						}
					});
			//添加点击回调
			mHorizontalScrollView.setOnItemClickListener(new OnItemClickListener()
			{

				@Override
				public void onClick(View view, int position)
				{
					mBitmapUtils.display(mImg, mGoodsList.get(position).goods_image);
					view.setBackgroundColor(Color.parseColor("#AA024DA4"));
				}
			});
			//设置适配器
			mHorizontalScrollView.initDatas(mAdapter);
		}

}
