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

import android.os.Environment;
import android.app.Activity;

public class IO extends Activity {

	public static final String FILE_LOCATION = Environment.getExternalStorageDirectory() + "/lorensjon/";
	public static final String FILE_TYPE = ".dat";

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
	
	

	public boolean fileExist(String name) {

		File file = null;

		try {
			file = new File(FILE_LOCATION + name + FILE_TYPE);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return file.exists();
	}
}
