package nl.mprog.apps.evilhangman;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private static final int BUTTON_WIDTH = 59;
	private static final int BUTTON_HEIGHT = 59;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LinearLayout buttonLayout_1 = (LinearLayout) findViewById(R.id.button_layout_1);
		for (char c = 'a'; c <= 'g'; c++) {
			buttonLayout_1.addView(createButton(c));
		}
		
		LinearLayout buttonLayout_2 = (LinearLayout) findViewById(R.id.button_layout_2);
		for (char c = 'h'; c <= 'n'; c++) {
			buttonLayout_2.addView(createButton(c));
		}
		
		LinearLayout buttonLayout_3 = (LinearLayout) findViewById(R.id.button_layout_3);
		for (char c = 'o'; c <= 'u'; c++) {
			buttonLayout_3.addView(createButton(c));
		}
		
		LinearLayout buttonLayout_4 = (LinearLayout) findViewById(R.id.button_layout_4);
		for (char c = 'v'; c <= 'z'; c++) {
			buttonLayout_4.addView(createButton(c));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private Button createButton(char c) {
		String text = c +"";
		Button button = new Button(this);
		button.setLayoutParams(new LayoutParams(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setText(text.toUpperCase());
		button.setOnClickListener(this);
		
		return button;
	}

	@Override
	public void onClick(View v) {
		Button button = (Button) v;
		button.getText();
		TextView textView = (TextView) findViewById(R.id.current_pogingen);
		textView.setText("BUTTON "+ button.getText() +" CLICKED YO!");
	}

}
