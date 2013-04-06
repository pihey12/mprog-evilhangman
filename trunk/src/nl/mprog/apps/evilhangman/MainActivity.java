package nl.mprog.apps.evilhangman;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.apps.evilhangman.clickhandlers.LettersClickHandler;
import nl.mprog.apps.evilhangman.hangman.EvilHangman;
import nl.mprog.apps.evilhangman.hangman.Hangman;
import nl.mprog.apps.evilhangman.hangman.NormalHangman;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView currentWord;
	private TextView currentPogingen;
	
	private Hangman hangman;
	
	private List<Button> buttons = new ArrayList<Button>();
	private LettersClickHandler lettersClickHandler;

	private static final int BUTTON_WIDTH = 59;
	private static final int BUTTON_HEIGHT = 59;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		int guesses = sharedPref.getInt(SettingsActivity.PREF_GUESSES, 5);
		boolean evil = sharedPref.getBoolean(SettingsActivity.PREF_EVIL, true);
		
		hangman = evil ? new EvilHangman() : new NormalHangman();
		if(evil){
			hangman = new EvilHangman();
			hangman.setMaxGuesses(guesses);
			hangman.setWordLength(10);
			hangman.setUp();
		} else {
			hangman = new NormalHangman();
			hangman.setMaxGuesses(guesses);
			hangman.setWordLength(10);
			hangman.setUp();
		}
		
		setUp();
				
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
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.restart:
	            this.restart();
	            return true;
	        case R.id.settings:
	        	Intent intent = new Intent(this, SettingsActivity.class);
	        	startActivity(intent);
	        	return true;
	    }
	    
	    return false;
	}
	
	private Button createButton(char c) {
		String text = c +"";
		Button button = new Button(this);
		button.setLayoutParams(new LayoutParams(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setText(text.toUpperCase());
		button.setOnClickListener(lettersClickHandler);
		
		buttons.add(button);
		
		return button;
	}
	
	public Hangman getHangman() {
		return hangman;
	}
	
	public void setWord(String word) {
		currentWord.setText(word);
	}
	
	public void gameWon(String word, int guesses) {
//		currentPogingen.setText("YOU WIN! The word was: "+ word);
//		
//		for (Button button : buttons) {
//			button.setEnabled(false);
//		}
		Intent intent = new Intent(this, WinActivity.class);
		intent.putExtra("word", word);
		startActivity(intent);
		restart();
	}
	
	public void gameOver(String word) {
//		currentPogingen.setText("YOU LOST! The word was: "+ word);
		
//		for (Button button : buttons) {
//			button.setEnabled(false);
//		}
		Intent intent = new Intent(this, LostActivity.class);
		intent.putExtra("word", word);
		startActivity(intent);
		restart();
	}
	
	public void updateGuesses(int guesses) {
		currentPogingen.setText("Je hebt nog "+ guesses +" pogingen over");
	}
	
	private void setUp() {
		currentWord = (TextView) findViewById(R.id.current_word);
		currentPogingen = (TextView) findViewById(R.id.current_pogingen);
		currentPogingen.setText("Je hebt nog "+ hangman.getGuesses() +" pogingen over");
		
		currentWord.setText(hangman.getCurrentWord());
		lettersClickHandler = new LettersClickHandler(this);
	}
	
	private void restart() {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		int guesses = sharedPref.getInt(SettingsActivity.PREF_GUESSES, 5);
		hangman.setMaxGuesses(guesses);
		
		hangman.restart();
		for (Button button : buttons) {
			button.setEnabled(true);
		}
		
		setUp();
	}

}
