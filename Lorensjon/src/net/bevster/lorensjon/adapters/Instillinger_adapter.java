//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------


package net.bevster.lorensjon.adapters;

import net.bevster.lorensjon.R;

import org.taptwo.android.widget.TitleProvider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

//import org.taptwo.android.widget.viewflow.example.R;

public class Instillinger_adapter extends BaseAdapter implements TitleProvider {

	private LayoutInflater mInflater;

	boolean Online;

	private static final String[] names = { "Status", "Profil", "Ukeplan" };

	public Instillinger_adapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

		switch (position) {
		case 0:
			convertView = mInflater.inflate(R.layout.content_instillinger_status, null);
			break;
		case 1:
			convertView = mInflater.inflate(R.layout.content_instillinger_ukeplan, null);
			break;
		case 2:
			convertView = mInflater.inflate(R.layout.content_instillinger_profil, null);
			break;
		default:
			break;

		}

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
