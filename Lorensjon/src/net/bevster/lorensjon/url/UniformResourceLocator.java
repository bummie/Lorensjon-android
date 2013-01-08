//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon.url;

import net.bevster.lorensjon.io.EasyIO;
import android.util.Log;

public class UniformResourceLocator {

	EasyIO eIO;

	String[] SETTINGS_STUDENT, SETTINGS_UKEPLAN;

	String adresse;

	public UniformResourceLocator() {
		// TODO Auto-generated constructor stub
		eIO = new EasyIO();

		SETTINGS_STUDENT = eIO.getTable(EasyIO.SETTINGS_STUDENTER);
		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);
	}

	public String getUkeplan(int w, int h) {

		int width = w;
		int height = h;

		SETTINGS_STUDENT = eIO.getTable(EasyIO.SETTINGS_STUDENTER); // 0 = Navn, 1 = Klasse, 2 = UkeplanID, 3 = Plassering i spinner
		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN); // 0 = Uke

		return formUkeplan(SETTINGS_STUDENT[2], SETTINGS_UKEPLAN[4], "", SETTINGS_UKEPLAN[0], height, width);
	}

	String formUkeplan(String studentid, String skoleid, String skolekode, String uke, int height, int width) {

		if (SETTINGS_UKEPLAN[6].equalsIgnoreCase("1")) {
			uke = "";
			Log.e("URL_E", "FIKS ER AKTIVERT!");
		} else {
			Log.e("URL_E", "FIKS ER ikke AKTIVERT! Uke: " + uke);
		}
		String adresse = "http://www.novasoftware.se/ImgGen/schedulegenerator.aspx?format=png&schoolid=" + skoleid + "&id={" + studentid + "}&period=&week=" + uke + "&width=" + width + "&height=" + height;

		Log.w("URL_IMAGE", adresse);
		return adresse;
	}

}
