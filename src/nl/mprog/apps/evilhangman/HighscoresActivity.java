package nl.mprog.apps.evilhangman;

import nl.mprog.apps.evilhangman.persistence.Highscore;
import nl.mprog.apps.evilhangman.persistence.HighscoresHandler;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HighscoresActivity extends Activity {
	
	public static final String HIGHSCORE = "highscore";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highscores);
		// Show the Up button in the action bar.
		setupActionBar();
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.highscores);

		HighscoresHandler handler = new HighscoresHandler(this);
		for (Highscore highscore : handler.getAllHighscores()) {
			TextView textView = new TextView(this);
			textView.setText(highscore.getWord() +" "+ highscore.getGuesses());
			layout.addView(textView);
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.highscores, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
