//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class Nyheter extends Activity {

	ActionBar actionBar;

	Intent intent_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		setContentView(R.layout.nyheter);

		intent_menu = new Intent(Nyheter.this, HovedAktivitet.class);
		intent_menu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent_menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		actionBar = (ActionBar) findViewById(R.id.actionbar);

		actionBar.setTitle(R.string.app_name);
		actionBar.setHomeAction(new IntentAction(Nyheter.this.getBaseContext(), intent_menu, R.drawable.akershus_logo_96));

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		setContentView(R.layout.loading);
		Intent myIntent = new Intent(Nyheter.this, HovedAktivitet.class);
		Nyheter.this.startActivity(myIntent);
		Nyheter.this.finish();

	}

}
