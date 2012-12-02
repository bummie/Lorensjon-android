//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon;

import net.bevster.lorensjon.adapters.Om_adapter;

import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class Om extends Activity {

	ActionBar actionBar;
	ViewFlow viewFlow;

	Intent intent_menu;

	TextView txt_kred;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		setContentView(R.layout.om);

		intent_menu = new Intent(Om.this, HovedAktivitet.class);
		intent_menu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent_menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		actionBar = (ActionBar) findViewById(R.id.actionbar);

		actionBar.setTitle(R.string.app_name);
		actionBar.setHomeAction(new IntentAction(Om.this.getBaseContext(), intent_menu, R.drawable.akershus_logo_96));

		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		Om_adapter adapter = new Om_adapter(this);
		viewFlow.setAdapter(adapter, 0);
		TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
		indicator.setTitleProvider(adapter);
		viewFlow.setFlowIndicator(indicator);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		String takk = "Takk til følgene! \n\n\n Lithium Hosting - Hosting av database \n\n jSoup - Jonathan Hedley \n\n ViewFlow -  Patrik 'pakerfeldt' Åkerfeldt \n\n Actionbar - Johan 'johannilsson' Nilsson  \n\n Webviewer -  Nova Software \n\n Ikoner - 'brsev' \n\n 3rik -  Link til Rælingen VGS \n\n MCane -  Link til Strømmen VGS \n\n Google ;) \n\n\n Skål! \n\n ~Sebs";

		txt_kred = (TextView) findViewById(R.id.txt_kreditt);
		txt_kred.setText(takk);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		setContentView(R.layout.loading);
		Intent myIntent = new Intent(Om.this, HovedAktivitet.class);
		Om.this.startActivity(myIntent);
		Om.this.finish();

	}

}
