//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon.url;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Parser {

	static Document doc_onlyone, doc_lorenskog_klasser;

	public static final String ADRESSE_NETTSIDE = "http://bevster.net/";

	public static final String TABELL_ADRESSE_LORENSKOG = "tabell_lorenskog.php";
	public static final String TABELL_ADRESSE_RAELINGEN = "tabell_raelingen.php";
	public static final String TABELL_ADRESSE_STROMMEN = "tabell_strommen.php";

	public static final String TABELL_ADRESSE_LORENSKOG_KLASSER = "klasse_lorenskog.php";
	public static final String TABELL_ADRESSE_RAELINGEN_KLASSER = "klasse_raelingen.php";

	public Parser(String adresse) {

		doc_onlyone = null;

		try {

			doc_onlyone = Jsoup.connect(ADRESSE_NETTSIDE + adresse).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public Parser(String adresse, String adress) {

		doc_onlyone = null;
		doc_lorenskog_klasser = null;

		try {

			doc_onlyone = Jsoup.connect(ADRESSE_NETTSIDE + adresse).get();
			doc_lorenskog_klasser = Jsoup.connect(ADRESSE_NETTSIDE + adress).get();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public String returnIdFromName(String name) {

		String ID = null;

		for (Element table : doc_onlyone.select("table").select("table")) {
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				if (tds.size() > 0) {

					if (name.trim().equalsIgnoreCase(tds.get(2).text().trim())) {
						ID = tds.get(0).text();
						break;
					}

				}
			}
		}

		return ID;

	}

	public String returnIdFromKlasse(String klasse) {

		String ID = null;

		for (Element table : doc_onlyone.select("table").select("table")) {
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				if (tds.size() > 0) {

					if (klasse.trim().equalsIgnoreCase(tds.get(1).text().trim())) {
						ID = tds.get(0).text();
						break;
					}

				}
			}
		}

		return ID;

	}

	public String returnNameFromId(String id) {

		String name = null;

		for (Element table : doc_onlyone.select("table").select("table")) {
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				if (tds.size() > 0) {

					if (id.trim().equalsIgnoreCase(tds.get(0).text().trim())) {
						name = tds.get(2).text();
						break;
					}

				}
			}
		}

		return name;
	}

	public String getKlasse(String id) {

		// 0 = ID, 1 = NAVN

		String Klasse = "Rompe";

		for (Element table : doc_onlyone.select("table").select("table")) {
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				if (tds.size() > 0) {

					if (id.trim().equalsIgnoreCase(tds.get(0).text().trim()) || id.trim().equalsIgnoreCase(tds.get(2).text().trim())) {
						return tds.get(1).text();
					}

				}
			}
		}
		return Klasse;

	}

	public String[] returnStudentArray() {

		String[] liste = new String[2100];
		String[] freshListe;

		int i = 0;
		boolean x = false; // Skip first element

		for (Element table : doc_onlyone.select("table").select("table")) {
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				if (tds.size() > 0) {

					if (x)
						liste[i] = tds.get(2).text();
					i++;
					if (x != true)
						x = true;
				}
			}
		}

		freshListe = new String[i];

		for (int y = 0; y < freshListe.length; y++) {

			freshListe[y] = liste[y];
		}

		return freshListe;

	}

	public String[] returnKlasseArray() {

		String[] Klasse = new String[150];
		String[] freshListe;

		int i = 0;

		for (Element table : doc_lorenskog_klasser.select("table").select("table")) {
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				if (tds.size() > 0) {
					Klasse[i] = tds.get(0).text();

				}
				i++;
			}
		}

		freshListe = new String[i];

		for (int y = 0; y < freshListe.length; y++) {

			freshListe[y] = Klasse[y];
		}

		return freshListe;
	}
	
	public String[] returnKlasseArrayStrommen() {

		String[] Klasse = new String[150];
		String[] freshListe;

		int i = 0;

		for (Element table : doc_onlyone.select("table").select("table")) {
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				if (tds.size() > 0) {
					Klasse[i] = tds.get(1).text();

				}
				i++;
			}
		}

		freshListe = new String[i];

		for (int y = 0; y < freshListe.length; y++) {

			freshListe[y] = Klasse[y];
		}

		return freshListe;
	}

	public String[] returnStudentArrayFromKlasse(String klasse) {

		String[] Studenter = new String[100];
		String[] freshListe;

		boolean bola = false;

		int i = 0;

		for (Element table : doc_onlyone.select("table").select("table")) {
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				if (tds.size() > 0) {

					if (klasse.trim().equalsIgnoreCase(tds.get(1).text().trim())) {
						Studenter[i] = tds.get(2).text().toString();
						i++;
						if (!bola)
							bola = true;
					} else {
						if (bola)
							break;
					}

				}
			}
		}

		freshListe = new String[i];

		for (int y = 0; y < freshListe.length; y++) {

			freshListe[y] = Studenter[y];
		}

		return freshListe;
	}

}