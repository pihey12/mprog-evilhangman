package nl.mprog.apps.evilhangman;

import nl.mprog.apps.evilhangman.persistence.Highscore;
import nl.mprog.apps.evilhangman.persistence.HighscoresHandler;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Activity that shows the top 10 highscores from the user.
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class HighscoresActivity extends Activity {
	
	public static final String HIGHSCORE = "highscore";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highscores);
		// Show the Up button in the action bar.
		setupActionBar();
		
		makeHighscoreList(0);
	}
	
	public void makeHighscoreList(int evil){
		LinearLayout layout = (LinearLayout) findViewById(R.id.highscores);
		layout.removeAllViewsInLayout();

		HighscoresHandler handler = new HighscoresHandler(this);
		int i = 1; // rank in highscores
	
		for (Highscore highscore : handler.getHighscores(evil)) {
			String text = String.valueOf(i) +" "+ highscore.getWord() +" "+ highscore.getGuesses();
			TextView textView = new TextView(this);
			textView.setText(text);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
			textView.setGravity(Gravity.CENTER);
			layout.addView(textView);
			i++;
		}
	}
	
	public void normal(View view){
		Button buttonnormal = (Button) findViewById(R.id.normalhighscores);
		buttonnormal.setEnabled(false);
		
		Button buttonevil = (Button) findViewById(R.id.evilhighscores);
		buttonevil.setEnabled(true);
		
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("Normal Highscores");
		
		makeHighscoreList(0);
	}
	
	public void evil(View view){
		Button buttonnormal = (Button) findViewById(R.id.normalhighscores);
		buttonnormal.setEnabled(true);
		
		Button buttonevil = (Button) findViewById(R.id.evilhighscores);
		buttonevil.setEnabled(false);
		
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("Evil Highscores");
		
		makeHighscoreList(1);
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
	
	public void playAgain(View view) {
		setResult(RESULT_OK);
		finish();
	}

}
