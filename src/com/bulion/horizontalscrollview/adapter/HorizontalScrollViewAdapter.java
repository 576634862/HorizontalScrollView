package com.bulion.horizontalscrollview.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bulion.horizontalscrollview.R;
import com.bulion.horizontalscrollview.R.id;
import com.bulion.horizontalscrollview.R.layout;
import com.bulion.horizontalscrollview.domain.ImageData.Goods;
import com.lidroid.xutils.BitmapUtils;

public class HorizontalScrollViewAdapter
{

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Goods> mGoodsList;

	public HorizontalScrollViewAdapter(Context context, ArrayList<Goods> mGoodsList)
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mGoodsList = mGoodsList;
	}

	public int getCount()
	{
		return mGoodsList.size();
	}

	public Object getItem(int position)
	{
		return mGoodsList.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_index_gallery_item, parent, false);
			viewHolder.mImg = (ImageView) convertView
					.findViewById(R.id.id_index_gallery_item_image);
			viewHolder.mText = (TextView) convertView
					.findViewById(R.id.id_index_gallery_item_text);

			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		BitmapUtils mBitmapUtils = new BitmapUtils(mContext);
		mBitmapUtils.display(viewHolder.mImg, mGoodsList.get(position).goods_image);
		viewHolder.mText.setText(mGoodsList.get(position).goods_title);
		return convertView;
	}

	private class ViewHolder
	{
		ImageView mImg;
		TextView mText;
	}

}
