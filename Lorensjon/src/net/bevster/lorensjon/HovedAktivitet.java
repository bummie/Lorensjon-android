//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.markupartist.android.widget.ActionBar;

public class HovedAktivitet extends Activity {

	ActionBar actionBar;

	Button btn_nyheter, btn_ukeplan, btn_instillinger, btn_om;
	TextView txt_nyhet;

	String[] SETTINGS_UKEPLAN;
	String[] SETTINGS_STUDENT;

	EasyIO eIO;
	PHPRequest PReq;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.menu);
		eIO = new EasyIO();

		actionBar = (ActionBar) findViewById(R.id.actionbar);
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

		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);
		SETTINGS_STUDENT = eIO.getTable(EasyIO.SETTINGS_STUDENTER);

		if (eIO.fileExist(EasyIO.SETTINGS_UKEPLAN)) {

			if (SETTINGS_STUDENT[0].equalsIgnoreCase("0")) {
				actionBar.setTitle(R.string.app_name);
			} else {
				actionBar.setTitle(getSetning());

			}
		}

		PReq = new PHPRequest();

		btn_nyheter.setOnTouchListener((new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					btn_nyheter.setBackgroundColor(Color.argb(120, 46, 136, 158));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					btn_nyheter.setBackgroundColor(Color.argb(0, 0, 0, 0));
					setContentView(R.layout.loading);
					Intent myIntent = new Intent(HovedAktivitet.this, Nyheter.class);
					HovedAktivitet.this.startActivity(myIntent);
					HovedAktivitet.this.finish();
				}

				return false;
			}

		}));
		btn_om.setOnTouchListener((new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					btn_om.setBackgroundColor(Color.argb(120, 46, 136, 158));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					btn_om.setBackgroundColor(Color.argb(0, 0, 0, 0));
					setContentView(R.layout.loading);
					Intent myIntent = new Intent(HovedAktivitet.this, Om.class);
					HovedAktivitet.this.startActivity(myIntent);
					HovedAktivitet.this.finish();
				}

				return false;
			}

		}));
		btn_instillinger.setOnTouchListener((new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					btn_instillinger.setBackgroundColor(Color.argb(120, 46, 136, 158));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					btn_om.setBackgroundColor(Color.argb(0, 0, 0, 0));
					setContentView(R.layout.loading);
					Intent myIntent = new Intent(HovedAktivitet.this, Instillinger.class);
					HovedAktivitet.this.startActivity(myIntent);
					HovedAktivitet.this.finish();
				}

				return false;
			}

		}));
		btn_ukeplan.setOnTouchListener((new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					btn_ukeplan.setBackgroundColor(Color.argb(120, 46, 136, 158));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					btn_ukeplan.setBackgroundColor(Color.argb(0, 0, 0, 0));
					setContentView(R.layout.loading);
					Intent myIntent = new Intent(HovedAktivitet.this, Ukeplan.class);
					HovedAktivitet.this.startActivity(myIntent);
					HovedAktivitet.this.finish();
				}

				return false;
			}

		}));

		if (eIO.fileExist(EasyIO.SETTINGS_UKEPLAN)) {
			new Task_MOTD().execute(Integer.parseInt(SETTINGS_UKEPLAN[2].toString()) == 1, isOnline());

			if (SETTINGS_UKEPLAN[7].equalsIgnoreCase("1")) {
				new Task_Autouke().execute();
			}

		}

	}

	public String getSetning() {

		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);
		SETTINGS_STUDENT = eIO.getTable(EasyIO.SETTINGS_STUDENTER);

		String[] setninger = { "Uke: " + SETTINGS_UKEPLAN[0] + " | " + SETTINGS_STUDENT[0], "Du vet det er helg, " + SETTINGS_STUDENT[0] + "?", "Uke: " + SETTINGS_UKEPLAN[0] + " | " + SETTINGS_STUDENT[1] };
		// "Trives du p� " + SETTINGS_UKEPLAN[3].replaceAll("loren_tabell_", "") + " VGS?", "Vi er i uke: " + SETTINGS_UKEPLAN[0], SETTINGS_UKEPLAN[3].replaceAll("loren_tabell_", "") + " VGS er topp!", SETTINGS_STUDENT[0] + " er best!",
		// SETTINGS_UKEPLAN[2], "F�lg med i timen " + SETTINGS_STUDENT[0], SETTINGS_STUDENT[2], SETTINGS_STUDENT[1] + " er noen glupinger!"

		if (SETTINGS_UKEPLAN[8].equalsIgnoreCase("7") || SETTINGS_UKEPLAN[8].equalsIgnoreCase("1")) {

			return setninger[1]; // [new Random().nextInt(setninger.length)];

		} else {

			if (SETTINGS_STUDENT[0].equalsIgnoreCase("Navnfri")) {
				return setninger[2]; // [new Random().nextInt(setninger.length)];

			} else {
				return setninger[0]; // [new Random().nextInt(setninger.length)];

			}

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

	private class Task_MOTD extends AsyncTask<Boolean, Integer, String> {

		protected String doInBackground(Boolean... params) {

			String mld = null;

			Log.w("Task_MOTD", Boolean.toString(params[0]));

			if (params[0]) {
				if (params[1]) {

					String motd[][] = PReq.request("loren_motd", "printfull", "", "");
					if (motd != null) {
						mld = motd[0][0].toString();
					} else {
						mld = "Shiet database nede!";
					}

				} else {
					mld = "Intet nettverk!";
				}
			} else {

				mld = "Vil du ikke ha spennende meldinger?";
			}

			if (mld == null) {
				mld = "Shieet, database er nede!";
			}
			return mld;
		}

		protected void onPostExecute(String result) {

			txt_nyhet.setText("");
			txt_nyhet.append(result);

		}

	}

	private class Task_Autouke extends AsyncTask<Boolean, Boolean, Integer> {

		protected Integer doInBackground(Boolean... params) {

			int currentWeek = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
			int currentDay = new GregorianCalendar().get(Calendar.DAY_OF_WEEK);

			SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);
			SETTINGS_STUDENT = eIO.getTable(EasyIO.SETTINGS_STUDENTER);

			SETTINGS_UKEPLAN[8] = Integer.toString(currentDay);
			SETTINGS_UKEPLAN[0] = Integer.toString(currentWeek);

			eIO.fileWrite(EasyIO.SETTINGS_UKEPLAN, eIO.fromTable(SETTINGS_UKEPLAN));

			return currentDay;

		}

		protected void onPostExecute(Integer result) {

			// Toast.makeText(getApplicationContext(), Integer.toString(result), Toast.LENGTH_SHORT).show();

		}

	}

}