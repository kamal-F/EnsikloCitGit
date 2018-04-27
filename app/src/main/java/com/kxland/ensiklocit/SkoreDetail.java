package com.kxland.ensiklocit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class SkoreDetail extends Activity {
	
	dbOperation db;
	
	SkoreStamp ssdetail;
	TextView txtTgl;
	TextView txtBenar;
	TextView txtListSalah;
	TextView txtLama;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skore_detail);

		//dari activity sebelumnya
		Bundle extras = getIntent().getExtras();
		String tanggal = extras.getString("tanggal");
		
		
		//open db
		db = new dbOperation(this);
		
		//query berdasarkan tanggal
		ssdetail = db.getSStamp(tanggal);
		
		txtTgl = (TextView) this.findViewById(R.id.txtTgl);
		txtBenar = (TextView) this.findViewById(R.id.txtBenar);
		txtListSalah = (TextView) this.findViewById(R.id.txtListSalah);
		txtLama = (TextView) this.findViewById(R.id.txtLama);
		

		txtTgl.setText(ssdetail.getTanggal());
		txtBenar.setText(ssdetail.getBenar() + " soal");
		txtListSalah.setText(ssdetail.getListSalah());
		txtLama.setText(Integer.toString(ssdetail.getlama()) + " detik");
		
	}
}
