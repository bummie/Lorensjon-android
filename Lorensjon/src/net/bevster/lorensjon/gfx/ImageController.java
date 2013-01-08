//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------


package net.bevster.lorensjon.gfx;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageController {

	public ImageController() {
		// TODO Auto-generated constructor stub
	}

	public Bitmap[] delDager(Bitmap bm, int w, int h, boolean resize) {

		int width = w;

		Bitmap Hovedplan = bm;

		Bitmap[] Timedager = new Bitmap[5];

		for (int i = 0; i < Timedager.length; i++) {

			int ww = i * width + 1;

			if (i == 0)
				ww = i * width;

			Log.i("BEVSTER_BILDE_KUTT_INFO", "X: " + ww + " Breddeplan " + (width * 5) + "Bredde: " + width + " Height: " + h);

			Timedager[i] = Bitmap.createBitmap(Hovedplan, ww, 0, width, h);
			if (resize)
				Timedager[i] = Bitmap.createScaledBitmap(Timedager[i], w, h, false);
		}

		Log.w("BEVSTER_LOREN_BILDE", Timedager.toString());

		return Timedager;

	}

	public Bitmap getPlan(String _url) {

		InputStream in = null;
		try {
			in = new java.net.URL(_url).openStream();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bitmap bmp = BitmapFactory.decodeStream(new PatchInputStream(in));

		Log.w("BLAA35", bmp.toString());

		return bmp;
	}

}
