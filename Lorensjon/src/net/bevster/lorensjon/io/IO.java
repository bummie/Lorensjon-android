//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class IO extends Activity {

	public static final String FILE_LOCATION = Environment.getExternalStorageDirectory() + "/loren/data/";
	public static final String FILE_LOCATION_BITMAP = Environment.getExternalStorageDirectory() + "/loren/planer/";

	public static final String FILE_TYPE = ".dat";
	public static final String FILE_TYPE_BITMAP = ".png";

	public IO() {
		// TODO Auto-generated constructor stub
	}

	public void fileWrite(String name, String data) {

		try {
			File myFile = new File(FILE_LOCATION);
			myFile.mkdirs();
			myFile = new File(FILE_LOCATION + name + FILE_TYPE);
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(data);
			myOutWriter.close();
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
		}

	}

	public String fileRead(String name) {
		String data = null;

		try {
			File myFile = new File(FILE_LOCATION + name + FILE_TYPE);
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
			String aDataRow = "";
			String aBuffer = "";
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}
			data = aBuffer;
			myReader.close();
		} catch (Exception e) {
		}

		return data;
	}

	public void fileWriteBitmap(String name, String src, Bitmap data) {

		try {
			Log.i("Bitmap IO", "Lager bitmap!");
			File bitmap_dir = new File(FILE_LOCATION_BITMAP + src);
			if (!bitmap_dir.exists())
				bitmap_dir.mkdirs();

			bitmap_dir = new File(FILE_LOCATION_BITMAP + src, name + FILE_TYPE_BITMAP);
			bitmap_dir.createNewFile();
			FileOutputStream fOut = new FileOutputStream(bitmap_dir);
			data.compress(Bitmap.CompressFormat.PNG, 85, fOut);
			fOut.flush();
			fOut.close();
			Log.i("Bitmap IO", "Bitmap lagret! " + name);
		} catch (Exception e) {
		}

	}

	public Bitmap fileReadBitmap(String name, String src) {

		Bitmap data = null;

		try {
			Log.i("Bitmap IO", "Aapner png!");
			File bitmap_file = new File(FILE_LOCATION_BITMAP + src + name + FILE_TYPE_BITMAP);
			FileInputStream fIn = new FileInputStream(bitmap_file);
			data = BitmapFactory.decodeStream(fIn);
			fIn.close();
		} catch (Exception e) {

		}
		Log.i("Bitmap IO", "Returnerer bitmap! " + data);
		return data;
	}

	public boolean fileExist(String name) {

		File file = null;

		try {
			file = new File(FILE_LOCATION + name + FILE_TYPE);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return file.exists();
	}

	public boolean fileExistBitmap(String name, String src) {

		File file = null;

		try {
			file = new File(FILE_LOCATION_BITMAP + src + name + FILE_TYPE_BITMAP);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return file.exists();
	}
}
