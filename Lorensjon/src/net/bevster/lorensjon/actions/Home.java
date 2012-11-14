package net.bevster.lorensjon.actions;

import net.bevster.lorensjon.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.markupartist.android.widget.ActionBar.Action;

public class Home extends Activity implements Action {

	Intent myIntent;
	Action action;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public int getDrawable() {
		return R.drawable.akershus_logo_96;
	}

	@Override
	public void performAction(View view) {

	}
}