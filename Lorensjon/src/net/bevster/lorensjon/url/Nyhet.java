//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------


package net.bevster.lorensjon.url;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Nyhet {

	Document melding_main;

	public static final String ADRESSE_NETTSIDE = "http://bevster.net/bev_nyheter.php";

	public Nyhet(String adresse) {
		melding_main = null;

		try {

			melding_main = Jsoup.connect(adresse).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public String returnMelding() {

		String name = null;

		for (Element table : melding_main.select("table").select("table")) {
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				if (tds.size() > 0) {

					name = tds.get(0).text().trim();

				}
			}
		}

		return name;
	}
}
