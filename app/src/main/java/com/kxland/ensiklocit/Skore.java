package com.kxland.ensiklocit;


import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class Skore extends Activity {


	private ListView listView1;

	//private List<hasilke> results=new ArrayList<hasilke>();
	int idDetail=1;

	dbOperation db;

	List<String> tanggal;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skore);
		
		int max_list_skore = 0;
		
		//open db
		db = new dbOperation(this);
		
		//initialisasi paket Adapter ambil count skore dari skore db
		max_list_skore = db.getSkoreCount();
		

		try{
			//getAllTimeStampSkore
			tanggal = db.getAllTimeStampSkore();

			if(max_list_skore > 0){
				pilihan dataskore[] = new pilihan[max_list_skore];

				for (int k = 0; k < max_list_skore ; k++)		 {
					dataskore[k] = new pilihan(R.drawable.v, tanggal.get(k));    // initialize each object in the array
				}


				View header = (View)getLayoutInflater().inflate(R.layout.list_view_header_row_nilai, null);

				pilihanAdapter adapter = new pilihanAdapter(this,
						R.layout.listview_item_row, dataskore);

				listView1 = (ListView)findViewById(R.id.listView1);
				listView1.addHeaderView(header);
				listView1.setAdapter(adapter);

				
				//listen to listview events
				listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {

						//idDetail=results.get((int) id).nomer;
						
						if(position > 0){
							//Log.e("OY_SKORE", "yg terpencet " + tanggal.get(position - 1));	
							
							Intent intent;
	
							//kirim tanggal utk lihat detail							
							intent = new Intent(getApplicationContext(), SkoreDetail.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intent.putExtra("tanggal", tanggal.get(position - 1));
							getApplicationContext().startActivity(intent);
	
							//finish();
							
						}
					}
				});


			}
			else{
				//belum ada nilai, silakan bermain dahulu
				finish();
			}
		}
		catch (Exception e) {
			Log.e(getClass().getSimpleName(), e.toString());
		}
	}
}
