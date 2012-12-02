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

		int skoleid = Integer.parseInt(SETTINGS_STUDENT[4]);

		String id = SETTINGS_STUDENT[2];
		int week = Integer.parseInt(SETTINGS_UKEPLAN[0].toString().trim());

		switch (skoleid) {
		case 0:
			adresse = "http://www.novasoftware.se/ImgGen/schedulegenerator.aspx?format=png&schoolid=72150/nb-no&type=3&id={" + id + "}&period=&week=" + week + "&mode=0&printer=0&colors=32&head=0&clock=0&foot=0&day=0&width=" + width + "&height=" + height + "&maxwidth=" + width + "&maxheight=" + height;
			break;
		case 1:
			// Merkelige raelingen som ikke har uke som en variabel?
			adresse = "http://www.novasoftware.se/ImgGen/schedulegenerator.aspx?format=png&schoolid=72950/nb-no&type=3&id={" + id + "}&period=&week=&mode=0&printer=0&colors=32&head=0&clock=0&foot=0&day=0&width=" + width + "&height=" + height + "&maxwidth=" + width + "&maxheight=" + height;
			break;
		case 2:
			adresse = "http://www.novasoftware.se/ImgGen/schedulegenerator.aspx?format=png&schoolid=72290/nb-no&type=1&id={" + id + "}&period=&week=" + week + "&mode=0&printer=0&colors=32&head=0&clock=0&foot=0&day=0&width=" + width + "&height=" + height + "&maxwidth=" + width + "&maxheight=" + height;
			break;

		}

		Log.w("BLAA", adresse);

		return adresse;
	}
}
