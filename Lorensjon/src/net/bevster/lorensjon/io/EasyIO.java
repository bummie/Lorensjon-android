//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.util.Log;

public class EasyIO extends IO {

	public static final String SETTINGS_STUDENTER = "loren_settings_studenter";
	public static final String SETTINGS_UKEPLAN = "loren_settings_ukeplan";
	public static final String SETTINGS_DOMAIN = "loren_domain";

	private static final int SETTINGS_PLASSER = 10;

	private static final String DEFAULT_DOMAIN = "bevster.net";
	private static final String DEFAULT_TABLETEXT = "0";

	String[] default_table_studenter = { DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT };
	String[] default_table_ukeplan = { DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT };
	String[] default_table_domain = { DEFAULT_DOMAIN };

	public EasyIO() {

		if (!fileExist(SETTINGS_STUDENTER)) {
			Log.w("EasyIO", SETTINGS_STUDENTER + " eksisterer ikke, skaper han naa!");
			fileWrite(SETTINGS_STUDENTER, fromTable(default_table_studenter));
		}
		if (!fileExist(SETTINGS_UKEPLAN)) {
			Log.w("EasyIO", SETTINGS_UKEPLAN + " eksisterer ikke, skaper han naa!");
			fileWrite(SETTINGS_UKEPLAN, fromTable(default_table_ukeplan));
		}
		if (!fileExist(SETTINGS_DOMAIN)) {
			Log.w("EasyIO", SETTINGS_DOMAIN + " eksisterer ikke, skaper han naa!");
			fileWrite(SETTINGS_DOMAIN, fromTable(default_table_domain));
		}

	}

	public EasyIO(int id) {
		if (id == 0) {
			if (!fileExist(SETTINGS_STUDENTER)) {
				Log.w("EasyIO", SETTINGS_STUDENTER + " eksisterer ikke, skaper han naa!");
				fileWrite(SETTINGS_STUDENTER, fromTable(default_table_studenter));
			}
		}

		if (id == 1) {
			if (!fileExist(SETTINGS_UKEPLAN)) {
				Log.w("EasyIO", SETTINGS_UKEPLAN + " eksisterer ikke, skaper han naa!");
				fileWrite(SETTINGS_UKEPLAN, fromTable(default_table_ukeplan));
			}
		}
		if (id == 2) {
			if (!fileExist(SETTINGS_DOMAIN)) {
				Log.w("EasyIO", SETTINGS_DOMAIN + " eksisterer ikke, skaper han naa!");
				fileWrite(SETTINGS_DOMAIN, fromTable(default_table_domain));
			}
		}
	}

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

	public String fromTable(String[] table) {

		StringBuilder sB = new StringBuilder();

		for (int i = 0; i < table.length; i++) {

			if (i == 0)
				sB.append(table[i]);
			if (i > 0)
				sB.append("\n" + table[i]);

		}

		return sB.toString();
	}

	public static String getSettingsStudenter() {
		return SETTINGS_STUDENTER;
	}

	public static String getSettingsUkeplan() {
		return SETTINGS_UKEPLAN;
	}

}
