package com.kxland.ensiklocit;


import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class Splash extends Activity {

	private long splashDelay =1500;//2seconds


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);


		//create timer delay
		TimerTask task =new TimerTask() {

			@Override
			public void run() {
				// do nothing
				finish();
				Intent mainIntent=new Intent().setClass(Splash.this,  MainActivity.class);
				startActivity(mainIntent);				
			}
		};

		Timer timer=new Timer();
		timer.schedule(task, splashDelay);
	}
}
