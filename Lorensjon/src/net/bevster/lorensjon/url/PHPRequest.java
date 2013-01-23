//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon.url;
import net.bevster.lorensjon.io.EasyIO;

import android.util.Log;
import java.io.IOException;

// jSoup
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PHPRequest {

	EasyIO eIO;

	public static final int SEARCH_CAP = 100;

	static Document loren_document;

	String[] SETTINGS_DOMAIN;
	public final String dir = "/loren/ut/loren_ut.php?";

	public PHPRequest() {

		eIO = new EasyIO();
		SETTINGS_DOMAIN = eIO.getTable(EasyIO.SETTINGS_DOMAIN);

	}

	/* Funksjoner
	 * 
	 * $LOR_FUNC_PRINTFULL = "printfull"; $LOR_FUNC_SPEC = "spec"; $LOR_FUNC_SEARCH = "search"; */

	// Sender forespørsel etter ønsket tabell
	public String[][] request(String table, String function, String search, String type) {
		String dom = null;
		String loren_adresse = null;
		String loren_req = "table=" + table + "&function=" + function + "&search=" + search + "&type=" + type;
		if (SETTINGS_DOMAIN[0] != null) {
			dom = SETTINGS_DOMAIN[0];
			Log.w("Domain_Read", "Domain is: " + dom);
		} else {
			dom = "bevster.net";
			Log.e("Domain_default", "Domain is: " + dom);
		}

		loren_adresse = "http://www." + dom + dir + loren_req;

		Log.e("Adresse_Request", loren_adresse);

		return reqToArray(loren_adresse);
	}

	// Gjør om HTML-tabellen om til en 2D java tabell
	private String[][] reqToArray(String adresse) {

		int[] mengde = elementAmount(adresse);

		String[][] loren_array = new String[mengde[1]][mengde[0]];

		try {
			loren_document = Jsoup.connect(adresse).get();

			//Log.w("DOKUMENT", loren_document.toString());

			for (Element table : loren_document.select("table").select("table")) {
				int i = 0;
				for (Element row : table.select("tr")) {
					Elements tds = row.select("td");

					for (int x = 0; x < mengde[0]; x++) {

						loren_array[i][x] = utfFra(removetd(tds.get(x).toString()));
						// Log.e("reqToArray_Yup", "i: " + i + " x: " + x + " Info: " + loren_array[i][x].toString());
					}
					i++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return loren_array;
	}

	public int[] elementAmount(String adresse) {

		int[] mengde = new int[2];

		try {
			loren_document = Jsoup.connect(adresse).get();
			for (Element table : loren_document.select("table").select("table")) {
				int i = 0;
				for (Element row : table.select("tr")) {
					Elements tds = row.select("td");
					mengde[0] = tds.size();
					i++;
				}
				mengde[1] = i;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("Amount_elAmount", "Inner_rows: " + Integer.toString(mengde[0]) + " Rows: " + Integer.toString(mengde[1]));
		return mengde;
	}

	public String removetd(String td) {

		String fiks = td.replaceAll("<td>", "");
		fiks = fiks.replaceAll("</td>", "");
		return fiks;
	}

	// -----------------------------------------------------------------------------
	// Purpose: Spesielle funksjoner!
	// -----------------------------------------------------------------------------

	// Returnerer navn over skolene som er tilgjengelige
	public String[] returnSkoler(String tabell) {

		String[][] resultat = request(tabell, "printfull", "", "");
		String[] skoleNavn = new String[resultat.length];

		for (int i = 0; i < resultat.length; i++) {
			skoleNavn[i] = resultat[i][1].replace("loren_tabell_", "");
			Log.e("skoleNavn", skoleNavn[i].toString());
		}
		return skoleNavn;
	}

	// -----------------------------------------------------------------------------
	// Purpose: Fiks for Ae, /O og aa
	// -----------------------------------------------------------------------------

	public String utfTil(String in) {
		String fiks = in.substring(0, 1).toUpperCase() + in.substring(1).toLowerCase();
		String[] chars = { "Æ", "Ø", "Å", "æ", "ø", "å" };
		String[] charsUTF = { "AElig", "Oslash", "Aring", "aelig", "oslash", "aring" };

		for (int i = 0; i < charsUTF.length; i++) {

			fiks = fiks.replaceAll(chars[i], charsUTF[i]);
		}

		return fiks;
	}

	public String utfFra(String in) {
		String fiks = in;
		String[] chars = { "Æ", "Ø", "Å", "æ", "ø", "å" };
		String[] charsUTF = { "&AElig;", "&Oslash;", "&Aring;", "&aelig;", "&oslash;", "&aring;" };

		for (int i = 0; i < charsUTF.length; i++) {
			fiks = fiks.replaceAll(charsUTF[i], chars[i]);
		}

		return fiks;
	}

}
