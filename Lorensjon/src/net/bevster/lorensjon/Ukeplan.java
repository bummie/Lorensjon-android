//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon;

import net.bevster.lorensjon.gfx.ImageController;
import net.bevster.lorensjon.gfx.TouchImageView;
import net.bevster.lorensjon.io.EasyIO;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class Ukeplan extends Activity {

	ActionBar actionBar;
	Intent intent_menu;
	EasyIO eIO;
	ImageController imgCon;
	// Data
	String[] SETTINGS_UKEPLAN;
	Bitmap[] ukePlanListe;

	// View
	TouchImageView ukeplanBilde;
	Button btn_man, btn_tir, btn_ons, btn_tor, btn_fre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		setContentView(R.layout.ukeplan);

		imgCon = new ImageController();
		eIO = new EasyIO();
		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);

		intent_menu = new Intent(Ukeplan.this, HovedAktivitet.class);
		intent_menu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent_menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// View
		actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setTitle("Uke: " + SETTINGS_UKEPLAN[0].toString());
		actionBar.setHomeAction(new IntentAction(Ukeplan.this.getBaseContext(), intent_menu, R.drawable.akershus_logo_96));
		actionBar.addAction(new net.bevster.lorensjon.actions.Lagreplan());

		ukeplanBilde = (TouchImageView) findViewById(R.id.loren_ukeplanbilde);

		// Fiks dette rotet en vakker dag!
		btn_man = (Button) findViewById(R.id.knapp_man);
		btn_man.setBackgroundResource(R.drawable.button_blue);

		btn_tir = (Button) findViewById(R.id.knapp_tir);
		btn_tir.setBackgroundResource(R.drawable.button_blue);

		btn_ons = (Button) findViewById(R.id.knapp_ons);
		btn_ons.setBackgroundResource(R.drawable.button_blue);

		btn_tor = (Button) findViewById(R.id.knapp_tor);
		btn_tor.setBackgroundResource(R.drawable.button_blue);

		btn_fre = (Button) findViewById(R.id.knapp_fre);
		btn_fre.setBackgroundResource(R.drawable.button_blue);

	}

	@Override
	protected void onStart() {
		super.onStart();

		ukePlanListe = null;

		btn_man.setOnTouchListener((new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					if (ukePlanListe != null) {
						ukeplanBilde.setImageBitmap(ukePlanListe[0]);
					}

				} else if (event.getAction() == MotionEvent.ACTION_UP) {

					btn_man.setEnabled(false);
					btn_tir.setEnabled(true);
					btn_ons.setEnabled(true);
					btn_tor.setEnabled(true);
					btn_fre.setEnabled(true);

				}

				return false;
			}

		}));
		btn_tir.setOnTouchListener((new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (ukePlanListe != null) {
						ukeplanBilde.setImageBitmap(ukePlanListe[1]);
					}
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					btn_man.setEnabled(true);
					btn_tir.setEnabled(false);
					btn_ons.setEnabled(true);
					btn_tor.setEnabled(true);
					btn_fre.setEnabled(true);
				}

				return false;
			}

		}));
		btn_ons.setOnTouchListener((new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (ukePlanListe != null) {
						ukeplanBilde.setImageBitmap(ukePlanListe[2]);
					}
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					btn_man.setEnabled(true);
					btn_tir.setEnabled(true);
					btn_ons.setEnabled(false);
					btn_tor.setEnabled(true);
					btn_fre.setEnabled(true);
				}

				return false;
			}

		}));
		btn_tor.setOnTouchListener((new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (ukePlanListe != null) {
						ukeplanBilde.setImageBitmap(ukePlanListe[3]);
					}
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					btn_man.setEnabled(true);
					btn_tir.setEnabled(true);
					btn_ons.setEnabled(true);
					btn_tor.setEnabled(false);
					btn_fre.setEnabled(true);

				}

				return false;
			}

		}));
		btn_fre.setOnTouchListener((new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (ukePlanListe != null) {
						ukeplanBilde.setImageBitmap(ukePlanListe[4]);
					}
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					btn_man.setEnabled(true);
					btn_tir.setEnabled(true);
					btn_ons.setEnabled(true);
					btn_tor.setEnabled(true);
					btn_fre.setEnabled(false);
				}

				return false;
			}

		}));

		new Task_Hentbilde().execute();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		setContentView(R.layout.loading);
		Intent myIntent = new Intent(Ukeplan.this, HovedAktivitet.class);
		Ukeplan.this.startActivity(myIntent);
		Ukeplan.this.finish();

	}

	public boolean isOnline() {

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	private class Task_Hentbilde extends AsyncTask<Boolean, Integer, Integer> {

		protected Integer doInBackground(Boolean... params) {

			SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);

			int i = 0;
			if (imgCon.getLagretPlan() != null && SETTINGS_UKEPLAN[9].equalsIgnoreCase("0")) {
				imgCon.lagreBitmapCache("timeplan_cache", imgCon.getLagretPlan());
				ukePlanListe = imgCon.delDager(imgCon.getCachedPlan());
				i = 0;
			} else if (imgCon.getUkeplan() != null && isOnline()) {
				imgCon.lagreBitmapCache("timeplan_cache", imgCon.getUkeplan());
				ukePlanListe = imgCon.delDager(imgCon.getCachedPlan());
				i = 1;
			} else if (imgCon.getCachedPlan() != null) {
				i = 2;
				ukePlanListe = imgCon.delDager(imgCon.getCachedPlan());
			} else {
				i = 3;

			}

			return i;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Toast.makeText(getApplicationContext(), "Henter plan!", Toast.LENGTH_SHORT).show();

			btn_man.setEnabled(false);
			btn_tir.setEnabled(false);
			btn_ons.setEnabled(false);
			btn_tor.setEnabled(false);
			btn_fre.setEnabled(false);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);

			SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);

			int dag = 0;
			dag = Integer.parseInt(SETTINGS_UKEPLAN[8].trim());

			// Fiks for dagene 1 = S�ndag, 2 = Mandag ++++
			while (dag > 6) {
				dag--;
			}
			if (dag <= 1) {
				dag = 0;
			} else {
				dag -= 2;
			}

			Log.e("Ukeplan_dag", Integer.toString(dag));

			if (result == 0) {
				Toast.makeText(getApplicationContext(), "Lastet lagret plan!", Toast.LENGTH_SHORT).show();
				if (ukePlanListe != null)
					ukeplanBilde.setImageBitmap(ukePlanListe[dag]);

			}
			if (result == 1) {
				Toast.makeText(getApplicationContext(), "Lastet plan fra internettet!", Toast.LENGTH_SHORT).show();
				if (ukePlanListe != null)
					ukeplanBilde.setImageBitmap(ukePlanListe[dag]);

			}
			if (result == 2) {
				Toast.makeText(getApplicationContext(), "Eeh!", Toast.LENGTH_SHORT).show();
				if (ukePlanListe != null)
					ukeplanBilde.setImageBitmap(ukePlanListe[dag]);

			}
			if (result == 3) {
				Toast.makeText(getApplicationContext(), "Artig, noe gikk visstnok galt!", Toast.LENGTH_SHORT).show();

			}

			switch (dag) {
			case 0:
				btn_man.setEnabled(false);
				btn_tir.setEnabled(true);
				btn_ons.setEnabled(true);
				btn_tor.setEnabled(true);
				btn_fre.setEnabled(true);
				break;
			case 1:
				btn_man.setEnabled(true);
				btn_tir.setEnabled(false);
				btn_ons.setEnabled(true);
				btn_tor.setEnabled(true);
				btn_fre.setEnabled(true);
				break;
			case 2:
				btn_man.setEnabled(true);
				btn_tir.setEnabled(true);
				btn_ons.setEnabled(false);
				btn_tor.setEnabled(true);
				btn_fre.setEnabled(true);
				break;
			case 3:
				btn_man.setEnabled(true);
				btn_tir.setEnabled(true);
				btn_ons.setEnabled(true);
				btn_tor.setEnabled(false);
				btn_fre.setEnabled(true);
				break;
			case 4:
				btn_man.setEnabled(true);
				btn_tir.setEnabled(true);
				btn_ons.setEnabled(true);
				btn_tor.setEnabled(true);
				btn_fre.setEnabled(false);
				break;
			default:
				btn_man.setEnabled(false);
				btn_tir.setEnabled(true);
				btn_ons.setEnabled(true);
				btn_tor.setEnabled(true);
				btn_fre.setEnabled(true);
				break;
			}

		}
	}

}
