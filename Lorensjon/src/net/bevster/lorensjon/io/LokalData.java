//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//
// Denne klassen er ikke i bruk.
//-----------------------------------------------------------------------------

package net.bevster.lorensjon.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.util.Log;

import net.bevster.lorensjon.url.Parser;

public class LokalData extends EasyIO {

	// Hente ut lokal informasjon
	String[] SETTINGS_STUDENT, SETTINGS_UKEPLAN;

	// Offline liste
	public static final String SETTINGS_DATA_STUDENTER = "settings_data_studenter";
	public static final String SETTINGS_DATA_KLASSE = "settings_data_klasse";

	public static final int SETTINGS_PLASSER = 150;

	String[] default_table = { "Stump", "Stumpe", "Stumpe", "Stump", "Stumpe", "Stumpe" };

	Parser prs;
	EasyIO eIO;

	public LokalData() {

		if (!fileExist(SETTINGS_DATA_STUDENTER)) {

			fileWrite(SETTINGS_DATA_STUDENTER, fromTable(default_table));

		}
		if (!fileExist(SETTINGS_DATA_KLASSE)) {

			fileWrite(SETTINGS_DATA_KLASSE, fromTable(default_table));

		}

		eIO = new EasyIO();

		// Last inn lokal data til tabell
		loadValues();
	}

	public String[] returnKlasseArray(boolean nett) {
		loadValues();

		if (nett && SETTINGS_STUDENT[6].equalsIgnoreCase("1")) {
			initParserKlasse();

			String[] x;

			if (Integer.parseInt(SETTINGS_STUDENT[8].toString()) == 2) { // Strommen sine spesielle needs
				x = prs.returnKlasseArrayStrommen();

			} else {
				x = prs.returnKlasseArray();

			}

			fileWrite(SETTINGS_DATA_KLASSE, fromTable(x));
			return x;

		} else if (!nett || SETTINGS_STUDENT[6].equalsIgnoreCase("0")) {

			return getTable(SETTINGS_DATA_KLASSE);
		}
		return null;

	}

	public String[] returnStudentArray(boolean nett, String klasse) {
		loadValues();

		if (nett && SETTINGS_STUDENT[6].equalsIgnoreCase("1") && klasse != null) {
			initParser();

			String[] x = prs.returnStudentArrayFromKlasse(klasse);

			fileWrite(SETTINGS_DATA_STUDENTER, fromTable(x));
			return x;

		} else if (!nett || SETTINGS_STUDENT[6].equalsIgnoreCase("0")) {

			return getTable(SETTINGS_DATA_STUDENTER);
		}
		return null;
	}

	public String getKlasse(boolean nett, String id) {
		loadValues();

		initParserKlasse();

		if (nett && prs.getKlasse(id) != null) {
			return prs.getKlasse(id); // Klasse
		} else {
			return SETTINGS_STUDENT[1];
		}

	}

	public String returnIdFromName(boolean nett, String id) {
		loadValues();

		initParser();

		if (nett) {

			return prs.returnIdFromName(id);

		} else {
			return SETTINGS_STUDENT[2];
		}

	}

	public String returnIdFromKlasse(boolean nett, String klasse) {
		loadValues();
		initParser();

		if (nett) {

			return prs.returnIdFromKlasse(klasse);

		} else {
			return SETTINGS_STUDENT[2];
		}

	}

	@Override
	public String[] getTable(String filnavn) {
		int antall_linjer = 0;

		String[] dataTable = new String[SETTINGS_PLASSER];

		try {
			File myFile = new File(FILE_LOCATION + filnavn + FILE_TYPE);
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
			String aDataRow = "";
			while ((aDataRow = myReader.readLine()) != null) {

				dataTable[antall_linjer] = aDataRow;

				antall_linjer++;
			}
			myReader.close();
		} catch (Exception e) {
		}

		return dataTable;
	}

	void initParser() {
		loadValues();

		switch (Integer.parseInt(SETTINGS_STUDENT[8])) {

		case 0:
			prs = new Parser(Parser.TABELL_ADRESSE_LORENSKOG);

			break;
		case 1:
			prs = new Parser(Parser.TABELL_ADRESSE_RAELINGEN);

			break;

		case 2:
			prs = new Parser(Parser.TABELL_ADRESSE_STROMMEN);
			break;
		}
	}

	void initParserKlasse() {
		loadValues();

		Log.e("Badds", SETTINGS_STUDENT[4]);

		switch (Integer.parseInt(SETTINGS_STUDENT[8])) {

		case 0:
			prs = new Parser(Parser.TABELL_ADRESSE_LORENSKOG, Parser.TABELL_ADRESSE_LORENSKOG_KLASSER);
			break;
		case 1:
			prs = new Parser(Parser.TABELL_ADRESSE_RAELINGEN, Parser.TABELL_ADRESSE_RAELINGEN_KLASSER);
			break;
		case 2:
			prs = new Parser(Parser.TABELL_ADRESSE_STROMMEN);
			break;

		}

	}

	void loadValues() {
		SETTINGS_STUDENT = eIO.getTable(EasyIO.SETTINGS_STUDENTER); // 0 = Navn, 1 = Klasse, 2 = UkeplanID, 3 = Plassering i spinner
		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);// 0 = Uke, 1 = Modus(1 Egen, 2 High, 3 low), 2 = Bredde, 3 = H�yde
	}

}
