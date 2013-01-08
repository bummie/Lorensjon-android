//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon;

import net.bevster.lorensjon.adapters.Instillinger_adapter;
import net.bevster.lorensjon.io.EasyIO;
import net.bevster.lorensjon.url.PHPRequest;

import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class Instillinger extends Activity {

	ActionBar actionBar;

	PHPRequest PReq;

	EasyIO eIO;

	Intent intent_menu;

	String[] SETTINGS_STUDENT, SETTINGS_UKEPLAN, SETTINGS_DOMAIN;
	String[][] Loren_data_student;
	String[][] Loren_data_skole;

	ViewFlow viewFlow;

	Spinner spinner_skole, spinner_navn, spinner_ukemodus;
	SeekBar uke_bar;
	Button btn_lagre, btn_sok;
	RadioGroup radio_sok;
	CheckBox chk_motd, chk_ukefiks;
	TextView txt_info_ukeplan, txt_info_profil, txt_ukeplan;
	EditText loren_sok_tekst;
	ArrayAdapter<String> spinner_adapter_navn, spinner_adapter_skole, spinner_adapter_ukemodus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		setContentView(R.layout.instillinger);

		intent_menu = new Intent(Instillinger.this, HovedAktivitet.class);
		intent_menu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent_menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		Instillinger_adapter adapter = new Instillinger_adapter(this);
		viewFlow.setAdapter(adapter, 0);
		TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
		indicator.setTitleProvider(adapter);
		viewFlow.setFlowIndicator(indicator);

		// -----------------------------------------------------------------------------
		// Purpose: Laste inn view elementer
		// -----------------------------------------------------------------------------

		actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setTitle(R.string.app_name);
		actionBar.setHomeAction(new IntentAction(Instillinger.this.getBaseContext(), intent_menu, R.drawable.akershus_logo_96));

		// Laster inn tekst
		txt_info_ukeplan = (TextView) findViewById(R.id.info_skole);
		txt_info_profil = (TextView) findViewById(R.id.info_student);
		txt_ukeplan = (TextView) findViewById(R.id.info_ukeplan);

		// Laster inn tekstfelt for sok
		loren_sok_tekst = (EditText) findViewById(R.id.loren_sok_tekst);

		// Laster inn radiogruppe
		radio_sok = (RadioGroup) findViewById(R.id.loren_radio_sok);

		// Laster inn spinnere
		spinner_skole = (Spinner) findViewById(R.id.loren_spinner_skole);
		spinner_navn = (Spinner) findViewById(R.id.loren_spinner_navn);
		spinner_ukemodus = (Spinner) findViewById(R.id.spinner_modus);

		// Laster inn for valg av uke
		uke_bar = (SeekBar) findViewById(R.id.Uke_bar);

		// Sjekkboks for nett eller lokal henting av data-
		// chk_nett = (CheckBox) findViewById(R.id.checkBox_profil_nett);
		chk_motd = (CheckBox) findViewById(R.id.chk_motd);
		chk_ukefiks = (CheckBox) findViewById(R.id.loren_check_ukefiks);

	}

	@Override
	protected void onStart() {
		super.onStart();

		// Last inn EasyIO;
		eIO = new EasyIO();
		PReq = new PHPRequest();

		// Knapp for aa lagre informasjon
		btn_lagre = (Button) findViewById(R.id.btn_lagre);
		btn_lagre.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				new Task_Lagre().execute(true);

			}
		});

		// Sokeknapp
		btn_sok = (Button) findViewById(R.id.loren_knapp_sok);
		btn_sok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (isOnline()) {
					if (!loren_sok_tekst.getText().toString().equalsIgnoreCase("")) {
						new Task_Sok().execute(loren_sok_tekst.getText().toString());
					} else {
						Toast.makeText(getApplicationContext(), "Kan ikke søke blankt!", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "Intet internett!", Toast.LENGTH_SHORT).show();
				}
			}

		});

		spinner_navn.post(new Runnable() {
			public void run() {
				spinner_navn.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

						int student_plassering = spinner_navn.getSelectedItemPosition();

						SETTINGS_STUDENT[2] = Loren_data_student[student_plassering][1]; // ID
						SETTINGS_STUDENT[1] = Loren_data_student[student_plassering][2]; // Klasse
						SETTINGS_STUDENT[0] = Loren_data_student[student_plassering][3]; // Navn

						Toast.makeText(getApplicationContext(), SETTINGS_STUDENT[0].toString() + " har blitt valgt!", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {
					}

				});
			}
		});

		// Liveoppdatering fra ukebaren
		uke_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub

				txt_ukeplan.setText("");
				txt_ukeplan.append(Integer.toString(progress));

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

		});

		new Task_Oppstart().execute(true);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		setContentView(R.layout.loading);
		Intent myIntent = new Intent(Instillinger.this, HovedAktivitet.class);
		Instillinger.this.startActivity(myIntent);
		Instillinger.this.finish();

	}

	// -----------------------------------------------------------------------------
	// Purpose: Laste inn fra alle lagrede innstillinger til statusside
	// -----------------------------------------------------------------------------

	void setStatus() {

		loadValuesArrays();

		String kulStart = "( \"";
		String kulSlutt = "\" )";

		StringBuilder sbP = new StringBuilder();

		for (int j = 0; j < SETTINGS_STUDENT.length; j++) {

			if (!SETTINGS_STUDENT[j].toString().equals("null"))
				sbP.append(j + " " + kulStart + SETTINGS_STUDENT[j] + kulSlutt + "\n");
		}

		txt_info_profil.setText("");
		txt_info_profil.append(sbP);

		StringBuilder sbU = new StringBuilder();

		for (int j = 0; j < SETTINGS_UKEPLAN.length; j++) {
			if (!SETTINGS_UKEPLAN[j].toString().equals("null"))
				sbU.append(j + " " + kulStart + SETTINGS_UKEPLAN[j] + kulSlutt + "\n");
		}
		sbU.append("Domene: " + kulStart + SETTINGS_DOMAIN[0] + kulSlutt + "\n");
		txt_info_ukeplan.setText("");
		txt_info_ukeplan.append(sbU);
	}

	// -----------------------------------------------------------------------------
	// Purpose: Last inn lagrede verdier til view elementer
	// -----------------------------------------------------------------------------

	void loadStoredValues() {

		if (eIO.fileExist(EasyIO.SETTINGS_STUDENTER)) {

			if (isOnline()) {
				spinner_skole.setSelection(Integer.parseInt(SETTINGS_STUDENT[3].toString()));
			} else {
				spinner_skole.setSelection(0);
			}

			spinner_navn.setSelection(0);
			spinner_ukemodus.setSelection(Integer.parseInt(SETTINGS_UKEPLAN[1].toString()));

		}

		if (eIO.fileExist(EasyIO.SETTINGS_UKEPLAN)) {

			uke_bar.setProgress(Integer.parseInt(SETTINGS_UKEPLAN[0].toString()));
			spinner_ukemodus.setSelection((Integer.parseInt(SETTINGS_UKEPLAN[1].toString())));

			switch (Integer.parseInt(SETTINGS_UKEPLAN[2].toString())) {
			case 0:
				chk_motd.setChecked(false);
				break;
			case 1:
				chk_motd.setChecked(true);
				break;
			default:
				chk_motd.setChecked(false);
				break;
			}

			switch (Integer.parseInt(SETTINGS_UKEPLAN[6].toString())) {
			case 0:
				chk_ukefiks.setChecked(false);
				break;
			case 1:
				chk_ukefiks.setChecked(true);
				break;
			default:
				chk_ukefiks.setChecked(false);
				break;
			}

		}
	}

	// -----------------------------------------------------------------------------
	// Purpose: Last inn verdier fra fil til tabell
	// -----------------------------------------------------------------------------

	void loadValuesArrays() {

		SETTINGS_STUDENT = eIO.getTable(EasyIO.SETTINGS_STUDENTER);
		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);
		SETTINGS_DOMAIN = eIO.getTable(EasyIO.SETTINGS_DOMAIN);

	}

	void loadArrayAdapters() {

		loadValuesArrays();

		spinner_adapter_ukemodus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[] { "Høy", "Lav" });

		if (isOnline()) {
			spinner_adapter_skole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PReq.returnSkoler("loren_skoleliste"));
		} else {
			spinner_adapter_skole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[] { "Kunne ikke laste skoler" });
		}

		spinner_adapter_navn = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[] { "Søk for navn" });

	}

	void loadArrayAdapters(String[] loren_navn) {

		loadValuesArrays();

		spinner_adapter_ukemodus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[] { "Høy", "Lav" });

		if (isOnline()) {
			spinner_adapter_skole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PReq.returnSkoler("loren_skoleliste"));
		} else {
			spinner_adapter_skole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[] { "Kunne ikke laste skoler" });
		}

		spinner_adapter_navn = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, loren_navn);

	}

	// -----------------------------------------------------------------------------
	// Purpose: Store the tabled values to file
	// -----------------------------------------------------------------------------

	void saveValues() {

		// -----------------------------------------------------------------------------
		// Studenter
		// -----------------------------------------------------------------------------
		if (isOnline()) {

			// SETTINGS_STUDENT[0] = spinner_student.getSelectedItem().toString(); // Navn
			// SETTINGS_STUDENT[1] = spinner_klasse.getSelectedItem().toString(); // Klasse
			// SETTINGS_STUDENT[2] = spinner_klasse.getSelectedItem().toString(); // ID
			SETTINGS_STUDENT[3] = Integer.toString(spinner_skole.getSelectedItemPosition()); // Plass i spinner elev
			// SETTINGS_STUDENT[4] = Integer.toString(spinner_skole.getSelectedItemPosition()); // Plass i spinner skole
			// SETTINGS_STUDENT[5] = Integer.toString(spinner_klasse.getSelectedItemPosition()); // Plass i spinner klasse
			// SETTINGS_STUDENT[7] = spinner_skole.getSelectedItem().toString(); // Skole
			// SETTINGS_STUDENT[8] = Integer.toString(spinner_skole.getSelectedItemPosition()); // Spinner skole

		}

		// -----------------------------------------------------------------------------
		// Ukeplan
		// -----------------------------------------------------------------------------

		SETTINGS_UKEPLAN[0] = Integer.toString(uke_bar.getProgress()); // Uke
		SETTINGS_UKEPLAN[1] = Integer.toString(spinner_ukemodus.getSelectedItemPosition()); // Modus
		SETTINGS_UKEPLAN[3] = Loren_data_skole[spinner_skole.getSelectedItemPosition()][1];
		SETTINGS_UKEPLAN[4] = Loren_data_skole[spinner_skole.getSelectedItemPosition()][2];
		SETTINGS_UKEPLAN[5] = Loren_data_skole[spinner_skole.getSelectedItemPosition()][3];

		// MOTD
		if (chk_motd.isChecked())
			SETTINGS_UKEPLAN[2] = "1";
		if (!chk_motd.isChecked())
			SETTINGS_UKEPLAN[2] = "0";

		// Ukefiks
		if (chk_ukefiks.isChecked())
			SETTINGS_UKEPLAN[6] = "1";
		if (!chk_ukefiks.isChecked())
			SETTINGS_UKEPLAN[6] = "0";
		// -----------------------------------------------------------------------------
		// Purpose: Write table to file
		// -----------------------------------------------------------------------------

		eIO.fileWrite(EasyIO.SETTINGS_UKEPLAN, eIO.fromTable(SETTINGS_UKEPLAN));
		eIO.fileWrite(EasyIO.SETTINGS_STUDENTER, eIO.fromTable(SETTINGS_STUDENT));

	}

	// -----------------------------------------------------------------------------
	// Purpose: Er du tilkoblet nettverket?
	// -----------------------------------------------------------------------------

	public boolean isOnline() {

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	String returnRadioText() {

		int selectedId = radio_sok.getCheckedRadioButtonId();
		RadioButton rB = (RadioButton) findViewById(selectedId);

		String resultat = rB.getText().toString();

		if (resultat.equalsIgnoreCase("id"))
			resultat = "StudentID";

		return resultat;
	}

	// -----------------------------------------------------------------------------
	// Purpose: Forskjellige oppgaver som kjører på egne tråder
	// -----------------------------------------------------------------------------

	private class Task_Lagre extends AsyncTask<Boolean, Integer, String> {

		protected String doInBackground(Boolean... params) {

			saveValues();

			return "Padde";
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			// Toast.makeText(getApplicationContext(), "Kan ta litt tid!", Toast.LENGTH_SHORT).show();

		}

		protected void onPostExecute(String result) {
			setStatus();
			Toast.makeText(getApplicationContext(), "Lagret data", Toast.LENGTH_SHORT).show();
		}
	}

	private class Task_Sok extends AsyncTask<String, Integer, Integer> {

		protected Integer doInBackground(String... params) {

			String tabell_pluss = "loren_tabell_";

			String[] navnListe;
			String tabell = tabell_pluss + spinner_skole.getSelectedItem().toString().trim();
			String type = returnRadioText();
			String search = PReq.utfTil(params[0].toString());

			Loren_data_student = PReq.request(tabell, "search", search, type);

			navnListe = new String[Loren_data_student.length];
			for (int i = 0; i < navnListe.length; i++) {

				if (Loren_data_student[i][3].equalsIgnoreCase("Navnfri")) {
					navnListe[i] = Loren_data_student[i][2];
				} else {
					navnListe[i] = Loren_data_student[i][3];
				}

				Log.e("Sokenavnting!", navnListe[i]);
			}

			loadArrayAdapters(navnListe);
			return navnListe.length;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			Toast.makeText(getApplicationContext(), "Søker!", Toast.LENGTH_SHORT).show();

		}

		protected void onPostExecute(Integer result) {

			if (result > 1) {
				Toast.makeText(getApplicationContext(), "Søket gav " + result + " resultater!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "Søket gav " + result + " resultat!", Toast.LENGTH_SHORT).show();
			}

			spinner_navn.setAdapter(spinner_adapter_navn);

		}

	}

	private class Task_Oppstart extends AsyncTask<Boolean, Integer, String> {

		protected String doInBackground(Boolean... params) {

			loadValuesArrays(); // Fyll tabellene med lagret data
			loadArrayAdapters(); // Last inn arrayadaptere

			Loren_data_skole = PReq.request("loren_skoleliste", "printfull", "", "");

			return "Padde";
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Toast.makeText(getApplicationContext(), "Laster inn lagrede verdier!", Toast.LENGTH_SHORT).show();

		}

		protected void onPostExecute(String result) {

			spinner_skole.setAdapter(spinner_adapter_skole);
			// spinner_navn.setAdapter(spinner_adapter_navn);
			spinner_ukemodus.setAdapter(spinner_adapter_ukemodus);
			loadStoredValues(); // Last inn lagrede data til view elementer
			setStatus(); // Oppdater status fra lokaldata

			Toast.makeText(getApplicationContext(), "Lastet verdier!", Toast.LENGTH_SHORT).show();
		}

	}

}
