package nl.mprog.apps.evilhangman;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private static final int BUTTON_WIDTH = 59;
	private static final int BUTTON_HEIGHT = 59;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LinearLayout buttonLayout_1 = (LinearLayout) findViewById(R.id.button_layout_1);
		for (char c = 'a'; c <= 'g'; c++) {
			String text = c +"";
			Button button = new Button(this);
			button.setLayoutParams(new LayoutParams(BUTTON_WIDTH, BUTTON_HEIGHT));
			button.setText(text.toUpperCase());
			buttonLayout_1.addView(button);
		}
		
		LinearLayout buttonLayout_2 = (LinearLayout) findViewById(R.id.button_layout_2);
		for (char c = 'h'; c <= 'n'; c++) {
			String text = c +"";
			Button button = new Button(this);
			button.setLayoutParams(new LayoutParams(BUTTON_WIDTH, BUTTON_HEIGHT));
			button.setText(text.toUpperCase());
			buttonLayout_2.addView(button);
		}
		
		LinearLayout buttonLayout_3 = (LinearLayout) findViewById(R.id.button_layout_3);
		for (char c = 'o'; c <= 'u'; c++) {
			String text = c +"";
			Button button = new Button(this);
			button.setLayoutParams(new LayoutParams(BUTTON_WIDTH, BUTTON_HEIGHT));
			button.setText(text.toUpperCase());
			buttonLayout_3.addView(button);
		}
		
		LinearLayout buttonLayout_4 = (LinearLayout) findViewById(R.id.button_layout_4);
		for (char c = 'v'; c <= 'z'; c++) {
			String text = c +"";
			Button button = new Button(this);
			button.setLayoutParams(new LayoutParams(BUTTON_WIDTH, BUTTON_HEIGHT));
			button.setText(text.toUpperCase());
			buttonLayout_4.addView(button);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
