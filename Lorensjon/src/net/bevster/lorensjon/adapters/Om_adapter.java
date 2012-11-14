package net.bevster.lorensjon.adapters;

import net.bevster.lorensjon.R;

import org.taptwo.android.widget.TitleProvider;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class Om_adapter extends BaseAdapter implements TitleProvider {

	private LayoutInflater mInflater;

	private static final String[] names = { "Om", "Kreditt", "Donasjoner" };

	public Om_adapter(Context context) {
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

			switch (position) {

			case 0:
				convertView = mInflater.inflate(R.layout.content_om_om, null);
				break;
			case 1:
				convertView = mInflater.inflate(R.layout.content_om_kreditt, null);

				break;
			case 2:
				convertView = mInflater.inflate(R.layout.content_om_donasjoner, null);
				break;
			default:
				convertView = mInflater.inflate(R.layout.content_om_donasjoner, null);
				break;

			}

		}

		return convertView;
	}

	@Override
	public String getTitle(int position) {
		return names[position];
	}

}
