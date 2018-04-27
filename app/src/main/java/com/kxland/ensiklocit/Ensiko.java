package com.kxland.ensiklocit;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class Ensiko extends Activity implements MyCallback, TextToSpeech.OnInitListener {

	// Declare Variables
	ViewPager viewPager;
	PagerAdapter adapter;
	String[] kategori;
	String[] buah;
	String[] deskripsi;	
	Bitmap[] gambar;

	//db
	dbOperation db;
	
	private static final String TAG = "TextToSpeechDemo";
	
	private TextToSpeech mTts;
	
	//callback
	@Override
	public void callbackCall(int x) {
		// callback code goes here
		//Log.i("TAG", "kapetrug" + buah[x] );		
		bicara(deskripsi[x]);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//open db
        db = new dbOperation(this);
        
     
        List<Buah> buahs = db.getAllBuah();
        
               
        kategori = new String[buahs.size()];
        buah = new String[buahs.size()];
        deskripsi = new String[buahs.size()];
        gambar = new Bitmap[buahs.size()];
        
        //init
        int i = 0;
        for (Buah bh : buahs) {
        	kategori[i] = bh.getKategori();
        	buah[i] = bh.getBuah();
        	deskripsi[i] = bh.getDeskripsi();
        	gambar[i] = bh.getGambar();

        	
        	i ++;
        	//String log = "Id: "+bh.getKategori()+" ,Name: " + bh.getBuah() + " ,Phone: " + bh.getDeskripsi();        	
        	//Log.d("Name: ", log);
        }
       
        
		// Get the view from viewpager_main.xml
		setContentView(R.layout.viewpager_main);

		
		// Initialize text-to-speech. This is an asynchronous operation.
        // The OnInitListener (second argument) is called after initialization completes.
        // TextToSpeech.OnInitListener
        mTts = new TextToSpeech(this,this);
        
        
		// Locate the ViewPager in viewpager_main.xml
		viewPager = (ViewPager) findViewById(R.id.pager);
		// Pass results to ViewPagerAdapter Class
		adapter = new ViewPagerAdapter(Ensiko.this, kategori, buah, deskripsi, gambar);
				
		// Binds the Adapter to the ViewPager
		viewPager.setAdapter(adapter);
	}


	// Implements TextToSpeech.OnInitListener.
    public void onInit(int status) {
        int result=1;
        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
        if (status == TextToSpeech.SUCCESS) {
            if(isInternetAvailable()){
                result = mTts.setLanguage(new Locale("id","ID"));
            }else{
                result = mTts.setLanguage(Locale.US);
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

        super.onDestroy();
    }

    
    private void bicara(String kata) {
        
        mTts.speak(kata,
            TextToSpeech.QUEUE_FLUSH,  // Drop all pending entries in the playback queue.
            null);
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}