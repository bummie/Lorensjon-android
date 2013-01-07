//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon;

import net.bevster.lorensjon.io.EasyIO;
import net.bevster.lorensjon.url.PHPRequest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;

public class HovedAktivitet extends Activity {

	ActionBar actionBar;

	Button btn_nyheter, btn_ukeplan, btn_instillinger, btn_om;
	TextView txt_nyhet;

	String[] SETTINGS_UKEPLAN;

	EasyIO eIO;
	PHPRequest PReq;
	
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
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		eIO = new EasyIO();
		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);

		PReq = new PHPRequest();
		
		btn_nyheter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			/*	 btn_nyheter.setBackgroundColor(Color.argb(120, 46, 136, 158));

				 setContentView(R.layout.loading);
				 Intent myIntent = new Intent(HovedAktivitet.this, Nyheter.class);
				 HovedAktivitet.this.startActivity(myIntent);
				 HovedAktivitet.this.finish();
			*/
				Toast.makeText(getApplicationContext(), "Ikke tilgjengelig!", Toast.LENGTH_SHORT).show();

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

				setContentView(R.layout.loading);
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

		new Task_MOTD().execute(Integer.parseInt(SETTINGS_UKEPLAN[2].toString()) == 1, isOnline());

	}

	public boolean isOnline() {

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	private class Task_MOTD extends AsyncTask<Boolean, Integer, String> {

		protected String doInBackground(Boolean... params) {

			String mld = "Ingen melding";

			Log.w("Task_MOTD", Boolean.toString(params[0]));
			
			if (params[0]) {
				if (params[1]) {

					mld = PReq.request("loren_motd", "printfull", "", "")[0][0].toString();

				} else {
					mld = "Intet nettverk!";
				}
			} else {

				mld = "Vil du ikke ha spennende meldinger?";
			}

			return mld;
		}

		protected void onPostExecute(String result) {

			txt_nyhet.setText("");
			txt_nyhet.append(result);

		}

	}

}