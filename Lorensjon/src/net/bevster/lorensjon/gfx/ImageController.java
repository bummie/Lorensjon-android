//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon.gfx;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import net.bevster.lorensjon.io.EasyIO;
import net.bevster.lorensjon.io.IO;
import net.bevster.lorensjon.url.UniformResourceLocator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageController {

	String[] SETTINGS_UKEPLAN, SETTINGS_STUDENTER;

	EasyIO eIO;
	IO inOut;
	UniformResourceLocator URL;

	public ImageController() {

		eIO = new EasyIO();
		inOut = new IO();
		URL = new UniformResourceLocator();

	}

	public void lagreBitmapCache(String navn, Bitmap bmp) {

		if (bmp != null) {
			Log.i("ImageController", "timeplan_cache blir lagret!");
			inOut.fileWriteBitmap(navn, "cache/", bmp);
		} else {
			Log.w("ImageController", "timeplan_cache er lik null!!");
		}
	}

	public void lagreTimeplan() {

		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);
		SETTINGS_STUDENTER = eIO.getTable(EasyIO.SETTINGS_STUDENTER);

		String FILNAVN = SETTINGS_UKEPLAN[0];

		if (inOut.fileExistBitmap("timeplan_cache", "cache/")) {
			Log.i("ImageController", "timeplan_cache eksisterer!");
			inOut.fileWriteBitmap(FILNAVN, SETTINGS_STUDENTER[2] + "/", inOut.fileReadBitmap("timeplan_cache", "cache/"));

		} else {
			Log.w("ImageController", "timeplan_cache eksisterer ikke!");

		}

	}

	public Bitmap getLagretPlan() {

		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);
		SETTINGS_STUDENTER = eIO.getTable(EasyIO.SETTINGS_STUDENTER);

		Bitmap lagretPlan = null;

		try {
			lagretPlan = inOut.fileReadBitmap(SETTINGS_UKEPLAN[0], SETTINGS_STUDENTER[2] + "/");
			Log.i("ImageController", "Planen for uke: " + SETTINGS_UKEPLAN[0] + " eksisterer!");

		} catch (Exception e) {
			Log.w("ImageController", "timeplan_cache eksisterer ikke!");
		}

		return lagretPlan;
	}

	public Bitmap getCachedPlan() {

		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);
		SETTINGS_STUDENTER = eIO.getTable(EasyIO.SETTINGS_STUDENTER);

		Bitmap lagretPlan = null;

		try {
			lagretPlan = inOut.fileReadBitmap("timeplan_cache", "cache/");
			Log.i("ImageController", "Lastet CachedPlan!");

		} catch (Exception e) {
			Log.w("ImageController", "timeplan_cache eksisterer ikke!");
		}

		return lagretPlan;
	}

	public Bitmap getUkeplan() {
		SETTINGS_STUDENTER = eIO.getTable(EasyIO.SETTINGS_STUDENTER);
		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);

		Bitmap ukeplan = null;

		double width = Integer.parseInt(SETTINGS_STUDENTER[6]);
		double height = Integer.parseInt(SETTINGS_STUDENTER[7]);

		try {

			switch (Integer.parseInt(SETTINGS_UKEPLAN[1].toString().trim())) {

			case 1:
				Log.i("ImageController", "Lav opplosning valgt!");
				width = (width * 5) / 2;
				height = height / 2; // Math.sqrt(height) * 10;
				ukeplan = getBitmap(URL.getUkeplan((int) width, (int) height));
				break;
			case 0:
				Log.i("ImageController", "Hoy opplosning valgt!!");
				ukeplan = getBitmap(URL.getUkeplan(((int) width * 5), (int) height));
				break;
			default:
				break;

			}

		} catch (Exception e) {
			Log.w("ImageController", e);
		}

		return ukeplan;
	}

	public Bitmap[] delDager(Bitmap bm) {

		Bitmap Hovedplan = bm;
		Bitmap[] Timedager = new Bitmap[5];
		if (Hovedplan != null) {
			for (int i = 0; i < Timedager.length; i++) {

				int bilde_lengdedel = (i * Hovedplan.getWidth() / 5) - i;

				Log.i("BEVSTER_BILDE_KUTT_INFO", "X: " + bilde_lengdedel + " Breddeplan " + (i * Hovedplan.getWidth()) + "Bredde: " + Hovedplan.getWidth() / 5 + " Height: " + Hovedplan.getHeight());

				Timedager[i] = Bitmap.createBitmap(Hovedplan, bilde_lengdedel, 0, Hovedplan.getWidth() / 5, Hovedplan.getHeight());

			}

			Log.w("BEVSTER_LOREN_BILDE", Timedager.toString());

			return Timedager;
		} else {
			return null;
		}
	}

	public Bitmap getBitmap(String _url) {
		Bitmap bmp = null;
		InputStream in = null;
		try {
			in = new java.net.URL(_url).openStream();
			bmp = BitmapFactory.decodeStream(new PatchInputStream(in));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bmp;
	}
}
