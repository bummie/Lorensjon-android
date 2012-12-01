package net.bevster.lorensjon;

import net.bevster.lorensjon.io.EasyIO;
import net.bevster.lorensjon.url.Nyhet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.markupartist.android.widget.ActionBar;

public class HovedAktivitet extends Activity {

	ActionBar actionBar;

	Button btn_nyheter, btn_ukeplan, btn_instillinger, btn_om;
	TextView txt_nyhet;

	String[] SETTINGS_UKEPLAN;

	EasyIO eIO;

	public static Nyhet prs_nyhet;

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

		eIO = new EasyIO();
		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);

		btn_nyheter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				btn_nyheter.setBackgroundColor(Color.argb(120, 46, 136, 158));

				new Thread(new Runnable() {
					public void run() {
						btn_nyheter.post(new Runnable() {
							public void run() {
								Intent myIntent = new Intent(HovedAktivitet.this, Nyheter.class);
								HovedAktivitet.this.startActivity(myIntent);
								HovedAktivitet.this.finish();
							}
						});
					}
				}).start();

			}
		});
		btn_ukeplan.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				btn_ukeplan.setBackgroundColor(Color.argb(120, 46, 136, 158));

				new Thread(new Runnable() {
					public void run() {
						btn_ukeplan.post(new Runnable() {
							public void run() {
								setContentView(R.layout.loading);
								Intent myIntent = new Intent(HovedAktivitet.this, Ukeplan.class);
								HovedAktivitet.this.startActivity(myIntent);
								HovedAktivitet.this.finish();
							}
						});
					}
				}).start();

			}
		});
		btn_om.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				btn_om.setBackgroundColor(Color.argb(120, 46, 136, 158));

				new Thread(new Runnable() {
					public void run() {
						btn_om.post(new Runnable() {
							public void run() {
								// setContentView(R.layout.loading);
								Intent myIntent = new Intent(HovedAktivitet.this, Om.class);
								HovedAktivitet.this.startActivity(myIntent);
								HovedAktivitet.this.finish();
							}
						});
					}
				}).start();

			}
		});
		btn_instillinger.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				btn_instillinger.setBackgroundColor(Color.argb(120, 46, 136, 158));

				new Thread(new Runnable() {
					public void run() {
						btn_instillinger.post(new Runnable() {
							public void run() {
								setContentView(R.layout.loading);
								Intent myIntent = new Intent(HovedAktivitet.this, Instillinger.class);
								HovedAktivitet.this.startActivity(myIntent);
								HovedAktivitet.this.finish();
							}
						});
					}
				}).start();

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

			if (params[0]) {
				if (params[1]) {

					prs_nyhet = new Nyhet(Nyhet.ADRESSE_NETTSIDE);

					mld = prs_nyhet.returnMelding();

				} else {
					mld = "Intet nettverk!";
				}
			} else {

				mld = "Vil du ikke ha spennende meldinger?";
			}

			return mld;
		}

		protected void onProgressUpdate(Integer... progress) {

		}

		protected void onPostExecute(String result) {

			txt_nyhet.setText("");
			txt_nyhet.append(result);

		}

	}

}