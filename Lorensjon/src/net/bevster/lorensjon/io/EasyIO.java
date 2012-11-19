//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class EasyIO extends IO {

	public static final String SETTINGS_STUDENTER = "settings_studenter";
	public static final String SETTINGS_UKEPLAN = "settings_ukeplan";

	private static final int SETTINGS_PLASSER = 10;

	private static final String DEFAULT_TABLETEXT = "Fabeldyr";

	String[] default_table_studenter = { DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, DEFAULT_TABLETEXT, "0", "1", "0", "0", "0", "0", "null", "null" };
	String[] default_table_ukeplan = { "0", "1", "0", "null", "null", "null", "null", "null", "null", "null" };

	public EasyIO() {

		if (!fileExist(SETTINGS_STUDENTER)) {

			fileWrite(SETTINGS_STUDENTER, fromTable(default_table_studenter));
		}
		if (!fileExist(SETTINGS_UKEPLAN)) {

			fileWrite(SETTINGS_UKEPLAN, fromTable(default_table_ukeplan));
		}

	}

	public EasyIO(int id) {
		if (id == 0) {

			if (!fileExist(SETTINGS_STUDENTER)) {

				fileWrite(SETTINGS_STUDENTER, fromTable(default_table_studenter));
			}
		}

		if (id == 1) {

			if (!fileExist(SETTINGS_UKEPLAN)) {

				fileWrite(SETTINGS_UKEPLAN, fromTable(default_table_ukeplan));

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
