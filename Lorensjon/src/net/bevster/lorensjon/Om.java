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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class Om extends Activity {

	ActionBar actionBar;
	ViewFlow viewFlow;

	Intent intent_menu;

	AdView adView;

	TextView txt_kred, txt_om, txt_don;

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

		// Vi elsker hardkoding!

		String Om_donasjon = "\nSjekk ut Bevster.net/lorenplan for mer informasjon!\n\n Smask ;*";
		; // "\nLithium Hosting - Hosting av database \n\n jSoup - Jonathan Hedley \n\n ViewFlow -  Patrik 'pakerfeldt' Åkerfeldt \n\n Actionbar - Johan 'johannilsson' Nilsson  \n\n Webviewer -  Nova Software \n\n Ikoner - 'brsev' \n\n 3rik -  Link til Rælingen VGS \n\n MCane -  Link til Strømmen VGS\n\n Sindre -  Link til Lillestrøm VGS \n\n Google ;) \n\n\n Skål! - Sebsen";
		String Om_om = "\nSjekk ut Bevster.net/lorenplan for mer informasjon!";
		; // "\n\nBRUKSANVISNING: www.bevster.net/lorenplan\n\nLorenplan startet som et lite prosjekt i slutten av 2012. Timeplanstøtten for mobile enheter var relativt dårlig, så som et læringsprosjekt tenkte jeg at jeg kunne skape en selv. Dette ble dermed gjort og etter en stund har det nå blitt til noe brukbart. Appklikasjonen er stadig under arbeid og alt som sees kan blir forandret.\n\nSliter du med å komme i gang?\n\nOm du sliter med å komme i gang og ikke er av den eksperimentelle personen, eller at du rett og slett ikke har all verden av tid kan du sjekke ut siden \"Komme i gang\" for informasjon om nettop dette!\nEksisterer ikke skolen din i systemet?\nFinner du ikke skolen din i systemet? Frykt ikke kjære deg, sjekk ut \"Mangler skolen din?\" siden for informasjon om timeplanen din er støttet!\nKontaktgrunnlag\n\nOm du av en eller annen grunn ikke ønsker å være i databasen må du for all del ta kontakt og dette vil bli fikset på. Føl også friheten til å foreslå endringer som kan bli gjort. Om du skulle komme over noen feil vill det også settes stor pris på om det ble meldt i fra om!\n\nKontaktopplysninger\n\nMail: seebzei@gmail.com \n\nITL: sebaber\n";
		String om_kreditt = "\nSjekk ut Bevster.net/lorenplan for mer informasjon!"; // "\nOm du av en eller annen grunn skulle ha et ønske om å gi litt støtte, så kan dette bli gjort!\nDonasjoner tas gjerne i mot via Paypal!\n\nPaypal: \"seebzei@gmail.com\"\n\nOpplysninger vil trolig være tilgjengelig på: \n\nwww.bevster.net/lorenplan.\n\nHa en fin dag!";

		txt_kred = (TextView) findViewById(R.id.om_tekst_kreditt);
		txt_om = (TextView) findViewById(R.id.om_tekst_om);
		txt_don = (TextView) findViewById(R.id.om_tekst_donasjon);

		txt_kred.setText(om_kreditt);
		txt_om.setText(Om_om);
		txt_don.setText(Om_donasjon);

		// Create the adView
		adView = new AdView(this, AdSize.BANNER, "a150fc509e75c70");

		LinearLayout layout = (LinearLayout) findViewById(R.id.header_layout);

		// Add the adView to it
		layout.addView(adView);

		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest());

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

	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

}
