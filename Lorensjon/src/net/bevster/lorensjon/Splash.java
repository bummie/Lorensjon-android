//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------


package net.bevster.lorensjon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

public class Splash extends Activity {

	int _splashTime = 1500;
	boolean _active = true;
	Intent myIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		setContentView(R.layout.splash);
		myIntent = new Intent(Splash.this, HovedAktivitet.class);
		/*
		 * New Handler to start the Menu-Activity and close this Splash-Screen
		 * after some seconds.
		 */
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				/* Create an Intent that will start the Menu-Activity. */
				if (_active) {
					Splash.this.startActivity(myIntent);
					Splash.this.finish();
				}

			}
		}, _splashTime);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			_active = false;
			Splash.this.startActivity(myIntent);
			Splash.this.finish();
		}
		return true;
	}
}
