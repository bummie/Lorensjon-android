//-----------------------------------------------------------------------------
// Bevster 2012 - Kos deg unge and!
//-----------------------------------------------------------------------------

package net.bevster.lorensjon.actions;

import net.bevster.lorensjon.gfx.ImageController;
import net.bevster.lorensjon.io.IO;
import android.R;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar.Action;

public class Lagreplan implements Action {

	Intent myIntent;
	Action action;

	ImageController imgCon;
	IO inOut;

	@Override
	public int getDrawable() {
		return R.drawable.ic_menu_save;
	}

	@Override
	public void performAction(View view) {

		imgCon = new ImageController();
		inOut = new IO();

		Toast.makeText(view.getContext(), "Lagrer plan!", Toast.LENGTH_SHORT).show();

		if (inOut.fileReadBitmap("timeplan_cache", "cache/") != null) {
			imgCon.lagreTimeplan();
			Toast.makeText(view.getContext(), "Lagret plan!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(view.getContext(), "Kunne ikke lagre plan!", Toast.LENGTH_SHORT).show();
		}
	}
}
