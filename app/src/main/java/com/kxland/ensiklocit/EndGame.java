package com.kxland.ensiklocit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

public class EndGame extends Activity {

	private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame);

        Bundle extras = getIntent().getExtras();
        int nilai = extras.getInt("nilai");
        
        nilai = nilai / 2;
        
        Log.i("INFONILAI", "skore=" + nilai);        
        

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(nilai);

        
    }
    
    //trigger dari xml
    public void keluar(View v){       
        this.finish();
    }
}
