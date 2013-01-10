//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon.actions;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.markupartist.android.widget.ActionBar.Action;

public class Zoom extends Activity implements Action {

	Intent myIntent;
	Action action;

	// String SETTINGS_UKEPLAN[];
	// EasyIO eIO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// eIO = new EasyIO();
		// SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);

	}

	@Override
	public int getDrawable() {
		return R.drawable.ic_menu_zoom;
	}

	@Override
	public void performAction(View view) {

		// Toast.makeText(getApplicationContext(), "Zoomer!", Toast.LENGTH_SHORT).show();

	}
}