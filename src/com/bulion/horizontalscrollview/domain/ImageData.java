package com.bulion.horizontalscrollview.domain;

import java.util.ArrayList;

public class ImageData {
	public ArrayList<Images> image;
	public ArrayList<Goods> goods;
	
	public class Images{
		public String image_url;
		public String image_title;
		
		@Override
		public String toString() {
			return "Images [image_url=" + image_url + ", image_title=" + image_title + "]";
		}
	}
	public class Goods{
		public String goods_url;
		public String goods_image;
		public String goods_title;
		public String goods_now;
		public String goods_original;
		@Override
		public String toString() {
			return "Goods [goods_url=" + goods_url + ", goods_image="
					+ goods_image + ", goods_title=" + goods_title
					+ ", goods_now=" + goods_now + ", goods_original="
					+ goods_original + "]";
		}
	}
	@Override
	public String toString() {
		return "ImageData [image=" + image + ", goods=" + goods + "]";
	}
}
