package net.bevster.lorensjon;

import net.bevster.lorensjon.adapters.Ukeplan_adapter;
import net.bevster.lorensjon.io.EasyIO;

import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class Ukeplan extends Activity {

	ActionBar actionBar;
	ViewFlow viewFlow;
	LinearLayout LinLay;
	String[] SETTINGS_UKEPLAN;
	Intent intent_menu;

	EasyIO eIO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		setContentView(R.layout.main);

		eIO = new EasyIO();
		SETTINGS_UKEPLAN = eIO.getTable(EasyIO.SETTINGS_UKEPLAN);

		intent_menu = new Intent(Ukeplan.this, HovedAktivitet.class);
		intent_menu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent_menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		LinLay = (LinearLayout) findViewById(R.id.header_layout);

		actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setTitle("Uke: " + SETTINGS_UKEPLAN[0].toString());
		actionBar.setHomeAction(new IntentAction(Ukeplan.this.getBaseContext(), intent_menu, R.drawable.akershus_logo_96));

		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		Ukeplan_adapter adapter = new Ukeplan_adapter(this);
		viewFlow.setAdapter(adapter, 0);
		TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
		indicator.setTitleProvider(adapter);
		viewFlow.setFlowIndicator(indicator);

	}
}
