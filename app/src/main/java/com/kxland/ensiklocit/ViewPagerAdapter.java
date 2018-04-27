package com.kxland.ensiklocit;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//The callback interface

interface MyCallback {
 void callbackCall(int pilih);
}

//Ensiko.this, kategori, buah, deskripsi, gambar
public class ViewPagerAdapter extends PagerAdapter {
	// Declare Variables
	Context context;
	String[] kategori;
	String[] buah;
	String[] deskripsi;	
	Bitmap[] gambar;
	
	
	LayoutInflater inflater;

	MyCallback callback;	
	
	
	public ViewPagerAdapter(Context context, String[] kategori, String[] buah,
			String[] deskripsi, Bitmap[] gambar) {
		this.context = context;
		this.kategori = kategori;
		this.buah = buah;
		this.deskripsi = deskripsi;
		this.gambar = gambar;
	}

	@Override
	public int getCount() {
		return kategori.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		// Declare Variables
		TextView txtkategori;
		TextView txtbuah;
		
		ImageView imggambar;
		

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.viewpager_item, container,
				false);

		
		// Locate the TextViews in viewpager_item.xml
		txtkategori = (TextView) itemView.findViewById(R.id.kategori);
		txtbuah = (TextView) itemView.findViewById(R.id.buah);
		

		// Capture position and set to the TextViews
		txtkategori.setText(kategori[position]);
		txtbuah.setText(buah[position]);
		

		// Locate the ImageView in viewpager_item.xml
		imggambar = (ImageView) itemView.findViewById(R.id.gambar);
		// Capture position and set to the ImageView		
		imggambar.setImageBitmap(gambar[position]);
		
		final int pos;
		pos = position;
		
		
		
		itemView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
	        	
	            callback = (MyCallback)context;
	            callback.callbackCall(pos);
	            
	        }
	    });
		
		
		
		// Add viewpager_item.xml to ViewPager
		((ViewPager) container).addView(itemView);
		
		
		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// Remove viewpager_item.xml from ViewPager
		((ViewPager) container).removeView((RelativeLayout) object);

	}
	
	
}
