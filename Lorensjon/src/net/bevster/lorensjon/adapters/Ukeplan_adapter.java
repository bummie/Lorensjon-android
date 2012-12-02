//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon.adapters;

import net.bevster.lorensjon.R;
import net.bevster.lorensjon.image.ImageController;
import net.bevster.lorensjon.io.EasyIO;
import net.bevster.lorensjon.io.IO;
import net.bevster.lorensjon.url.UniformResourceLocator;

import org.taptwo.android.widget.TitleProvider;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

//import org.taptwo.android.widget.viewflow.example.R;

public class Ukeplan_adapter extends BaseAdapter implements TitleProvider {

	private LayoutInflater mInflater;

	EasyIO eIO;
	UniformResourceLocator URLA;
	ImageController imgC;
	Bitmap[] ukeBilder;

	String[] SETTINGS_UKEPLAN;

	int height, width;

	boolean Online;

	private static final String[] names = { "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag" };

	public Ukeplan_adapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		URLA = new UniformResourceLocator();
		eIO = new EasyIO(1);
		imgC = new ImageController();
		Online = true;

		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.getSettingsUkeplan());

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int droid_width = metrics.widthPixels;
		int droid_height = metrics.heightPixels;

		height = 1;
		width = 1;

		try {

			switch (Integer.parseInt(SETTINGS_UKEPLAN[1].toString().trim())) {

			case 0:
				height = droid_height;
				width = droid_width * 5;
				ukeBilder = imgC.delDager(imgC.getPlan(URLA.getUkeplan(width + 5, height)), width / 5, height, false);
				break;
			case 1:
				height = droid_height / 2;
				width = droid_width / 2;
				ukeBilder = imgC.delDager(imgC.getPlan(URLA.getUkeplan((width * 5) + 5, height)), width, height, false);
				break;
			case 2:
				height = Integer.parseInt(SETTINGS_UKEPLAN[3].toString().trim());
				width = Integer.parseInt(SETTINGS_UKEPLAN[2].toString().trim());
				ukeBilder = imgC.delDager(imgC.getPlan(URLA.getUkeplan(width * 5, height)), width / 5, height, false);
				break;
			default:
				break;

			}

			Online = true;
		} catch (Exception e) {
			Online = false;
		}
	}

	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.content_ukeplan, null);
		}

		ImageView bilde = ((ImageView) convertView.findViewById(R.id.ukeplanbilde));

		if (Online) {
			switch (position) {
			case 0:
				bilde.setImageBitmap(ukeBilder[0]);
				break;
			case 1:
				bilde.setImageBitmap(ukeBilder[1]);
				break;
			case 2:
				bilde.setImageBitmap(ukeBilder[2]);
				break;
			case 3:
				bilde.setImageBitmap(ukeBilder[3]);
				break;
			case 4:
				bilde.setImageBitmap(ukeBilder[4]);
				break;
			default:

				bilde.setImageResource(R.drawable.aks_blu);

				break;
			}
		} else {
			bilde.setImageResource(R.drawable.akershus_logo_96);
		}

		// bilde.setImageResource(R.drawable.akershus_logo_96);
		return convertView;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.taptwo.android.widget.TitleProvider#getTitle(int) */
	@Override
	public String getTitle(int position) {
		return names[position];
	}

}
