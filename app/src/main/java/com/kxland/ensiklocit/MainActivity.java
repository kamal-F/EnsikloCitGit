package com.kxland.ensiklocit;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private ListView listView1;
    int item_pilihan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialisasi paket Adapter
        pilihan pilihan_data[] = new pilihan[]
                {
                        new pilihan(R.drawable.ensiko, "Ensiklopedia Buah"),
                        new pilihan(R.drawable.whatisit, "Gambar apa?"),
                        new pilihan(R.drawable.ensikogame, "Permainan"),
                        new pilihan(R.drawable.nilai, "Nilai"),
                        new pilihan(R.drawable.help, "Pertolongan"),
                        new pilihan(R.drawable.exit,  "Keluar")
                };

        pilihanAdapter adapter = new pilihanAdapter(this,
                R.layout.listview_item_row, pilihan_data);

        listView1 = (ListView)findViewById(R.id.listView1);

        View header = getLayoutInflater().inflate(R.layout.list_view_header_row, null);
        listView1.addHeaderView(header);

        listView1.setAdapter(adapter);

        //listen to listview events
        listView1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                item_pilihan = position;
                //item_pilihan= Integer.toString(position);
                //Toast.makeText(getBaseContext(),  "<>" +item_pilihan, Toast.LENGTH_LONG).show();
                
                Intent intent;
                
                switch (position) {

                case 1:
                	//define a new Intent for the second Activity
                	//intent = new Intent(getApplicationContext(), MenuTanaman.class);
                    intent = new Intent(getApplicationContext(), Ensiko.class);
                	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                	getApplicationContext().startActivity(intent);
                	break;
                    case 2:
                        intent= new Intent(getApplicationContext(), CloudVision.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        break;
                case 3:
                	intent= new Intent(getApplicationContext(), Game.class);
                	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                	getApplicationContext().startActivity(intent);
                	break;
                case 4:
                	intent= new Intent(getApplicationContext(), Skore.class);
                	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                	getApplicationContext().startActivity(intent);
                	break;
                case 5:
                	intent = new Intent(getApplicationContext(), Pertolongan.class);
                	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                	getApplicationContext().startActivity(intent);
                	break;
                case 6:
                	// Create out AlterDialog
                	Builder builder = new Builder(MainActivity.this);
                	builder.setMessage("Keluar dari aplikasi");
                	builder.setCancelable(true);
                	builder.setPositiveButton("Ya", new OkOnClickListener());
                	builder.setNegativeButton("Tidak", new CancelOnClickListener());
                	AlertDialog dialog = builder.create();
                	dialog.show();
                	break;
                }
            }
        });

    }

    

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            MainActivity.this.finish();

            //finish();

        }
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
