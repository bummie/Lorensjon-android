package net.bevster.lorensjon;

import net.bevster.lorensjon.url.Nyhet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.markupartist.android.widget.ActionBar;

public class HovedAktivitet extends Activity {

	ActionBar actionBar;

	Button btn_nyheter, btn_ukeplan, btn_instillinger, btn_om;
	TextView txt_nyhet;

	Nyhet prs_nyhet;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.menu);

		actionBar = (ActionBar) findViewById(R.id.actionbar);

		actionBar.setTitle(R.string.app_name);
		// actionBar.setHomeAction(new Home());

		btn_nyheter = (Button) findViewById(R.id.btn_nyheter);
		btn_ukeplan = (Button) findViewById(R.id.btn_ukeplan);
		btn_om = (Button) findViewById(R.id.btn_om);
		btn_instillinger = (Button) findViewById(R.id.btn_instillinger);
		txt_nyhet = (TextView) findViewById(R.id.txt_melding);

	}

	public HovedAktivitet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		btn_nyheter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				btn_nyheter.setBackgroundColor(Color.argb(120, 46, 136, 158));
				Intent myIntent = new Intent(HovedAktivitet.this, Nyheter.class);
				HovedAktivitet.this.startActivity(myIntent);
				HovedAktivitet.this.finish();

			}
		});
		btn_ukeplan.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				btn_ukeplan.setBackgroundColor(Color.argb(120, 46, 136, 158));
				setContentView(R.layout.loading);
				Intent myIntent = new Intent(HovedAktivitet.this, Ukeplan.class);
				HovedAktivitet.this.startActivity(myIntent);
				HovedAktivitet.this.finish();

			}
		});
		btn_om.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				btn_om.setBackgroundColor(Color.argb(120, 46, 136, 158));
				// setContentView(R.layout.loading);
				Intent myIntent = new Intent(HovedAktivitet.this, Om.class);
				HovedAktivitet.this.startActivity(myIntent);
				HovedAktivitet.this.finish();

			}
		});
		btn_instillinger.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				btn_instillinger.setBackgroundColor(Color.argb(120, 46, 136, 158));
				setContentView(R.layout.loading);
				Intent myIntent = new Intent(HovedAktivitet.this, Instillinger.class);
				HovedAktivitet.this.startActivity(myIntent);
				HovedAktivitet.this.finish();

			}
		});

		visMOTD();

	}

	void visMOTD() {
		if (isOnline()) {

			prs_nyhet = new Nyhet(Nyhet.ADRESSE_NETTSIDE);

			txt_nyhet.setText("");
			txt_nyhet.append(prs_nyhet.returnMelding());

		} else {
			txt_nyhet.setText("");
			txt_nyhet.append("Intet nettverk!");
		}
	}

	public boolean isOnline() {

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

}