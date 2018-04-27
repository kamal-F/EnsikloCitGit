package com.kxland.ensiklocit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class Game extends Activity implements TextToSpeech.OnInitListener{

	String[] kategori;
	String[] buah;
	String[] deskripsi;	
	Bitmap[] gambar;

	private static final String TAG = "permainan";

	dbOperation db;
	private TextToSpeech mTts;
	
	
	ImageView imgBuah;
	Button button1;
	Button button2;
    
	String jawabanbenar = "";
	String soalBuah = ""; //utk keperluan list salah
	String acakan = "";
	String listsalah = "";
	
	private int nomerke = 0;
	private int lama;
	private int nilai = 0;
	
	private int jmlsoal = 0;
	private int maxsoal = 0;
	private int soalke = 0;
	
	final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final int N = alphabet.length();
	
	private boolean timerJalan=true;

	public void onInit(int status) {
		int result=1;
		// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
		if (status == TextToSpeech.SUCCESS) {
			if(isInternetAvailable()){
				result = mTts.setLanguage(new Locale("id","ID"));
			}else{
				result = mTts.setLanguage(Locale.US);
			}
			if (result == TextToSpeech.LANG_MISSING_DATA ||
					result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Lanuage data is missing or the language is not supported.
				Log.e(TAG, "Language is not available.");
			} else {
				// Check the documentation for other possible result codes.
				// For example, the language may be available for the locale,
				// but not for the specified country and variant.

				// The TTS engine has been successfully initialized.
				// Allow the user to press the button for the app to speak again.
				startTimerThread();
				//bicara(buah[1]);
			}
		} else {
			// Initialization failed.
			Log.e(TAG, "Could not initialize TextToSpeech.");
		}
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown!
		if (mTts != null) {
			mTts.stop();
			mTts.shutdown();
		}
		
		timerJalan = false;
		
		super.onDestroy();
	}
	
	private void bicara(String kata) {

		mTts.speak(kata,
				TextToSpeech.QUEUE_FLUSH,  // Drop all pending entries in the playback queue.
				null);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//open db
		db = new dbOperation(this);


		List<Buah> buahs = db.getAllBuah();

		//maxsoal anggap 10
		maxsoal = 10;
		
		jmlsoal = buahs.size();
				
		kategori = new String[jmlsoal];
		buah = new String[jmlsoal];
		deskripsi = new String[jmlsoal];
		gambar = new Bitmap[jmlsoal];
		
		
		int i = 0;
		for (Buah bh : buahs) {
			kategori[i] = bh.getKategori();
			buah[i] = bh.getBuah();
			deskripsi[i] = bh.getDeskripsi();
			gambar[i] = bh.getGambar();
			
			i ++;
		}


		//game layout Get the view from game_main.xml
		setContentView(R.layout.game_main);
		
		imgBuah=(ImageView) this.findViewById(R.id.imgBuah);
        button1 = (Button) this.findViewById(R.id.button1);
        button2 = (Button) this.findViewById(R.id.button2);

        button1.setOnClickListener(myhandler1);
		button2.setOnClickListener(myhandler2);
		
       
        
		// Initialize text-to-speech. This is an asynchronous operation.
		// The OnInitListener (second argument) is called after initialization completes.
		mTts = new TextToSpeech(this,
				this  // TextToSpeech.OnInitListener
				);
		
		//init
		cekJawab("awalun");
		
		//tunggu tts selesai init startTimerThread();
		
	}
	
	
	View.OnClickListener myhandler1 = new View.OnClickListener() {
		public void onClick(View v) {
			//Log.i("TAG", "kapetrug" +  button1.getText());			
			//cekjawab
			cekJawab( button1.getText().toString() );			
		}
	};
	
	
	View.OnClickListener myhandler2 = new View.OnClickListener() {
		public void onClick(View v) {
			//cekjawab
			cekJawab( button2.getText().toString() );
		}
	};

	//timer increase
	private void startTimerThread() {
		final Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			
			public void run() {

				while (timerJalan) {
					try {
						Thread.sleep(1000);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.post(new Runnable(){
						public void run() {
							if(timerJalan){
								lama ++;

								// diucapkan setiap 4s
								if(mod(lama, 4) == 0){
									
									// sound
									bicara(buah[nomerke]);
								}
							}
						}
					});
				}
			}
		};
		new Thread(runnable).start();
	}


	//fungsi modulo
	private int mod(int x, int y)
	{
		int result = x % y;
		if (result < 0)
			result += y;
		return result;
	}

	
	//cek jawaban
	private void cekJawab(String jawab){
		Random r = new Random();
		int ri; //random int 0 to 1
		
		//soal tambah terus
		//simpan yg benar
		//list yg salah
		
		if(soalke > 0){
			if(jawabanbenar.equals(jawab)){
				nilai ++;				
			} else {
				//catat soal yg dijawab salah
				listsalah = listsalah + "[ " + soalBuah + "]";
			}
		}
		
		//maxsoal, soal selesai?
		if(soalke < maxsoal){
			soalke ++;
			
			//random nomerke dari jmlsoal
			nomerke = r.nextInt(jmlsoal);
			
		} else {			
			//game berhenti
			//simpan record nilai ke db
			//waktu sekarang, nilai/maxsoal, lama, list salah
			timerJalan = false;
			button1.setEnabled(false);
			button2.setEnabled(false);
			
			
			//get time now
			//waktu
			SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
			String newtime =  sdfDateTime.format(new Date(System.currentTimeMillis()));
            
			//Log.i("TAG","waktu: " +  newtime + ", nilai: " +  nilai + ", lama main: " + lama + "s" + ", list salah: " + listsalah);
			//Log.i("TAG","soal ke=" + soalke);
			db.addSkore(newtime, nilai, listsalah, lama);
			
			
			//new intent show endgame, max 10 bintang
			
			Intent intent;

            intent = new Intent(this, EndGame.class);           
            intent.putExtra("nilai", nilai);
            this.startActivity(intent);
            
            this.finish();
		}
		
		
		
		imgBuah.setImageBitmap(gambar[nomerke]);		
		
		//simpan jawaban utk respon berikutnya
		jawabanbenar  = String.valueOf(kategori[nomerke]);
		soalBuah = String.valueOf(buah[nomerke]);
		
		acakan = String.valueOf( alphabet.charAt(r.nextInt(N)));
		
		//memastikan acak
		while (acakan.equals(jawabanbenar)){
			acakan = String.valueOf( alphabet.charAt(r.nextInt(N)));
		};
		
		ri = r.nextInt(2);
				
		//random button text
		if(ri == 1){
			button1.setText( String.valueOf(kategori[nomerke]) );
			button2.setText( acakan );
		} else 
		{
			button1.setText( acakan );
			button2.setText( String.valueOf(kategori[nomerke]) );
		}
		
	}
	public boolean isInternetAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null;
	}
}
