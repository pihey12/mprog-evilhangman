package nl.mprog.apps.evilhangman;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.apps.evilhangman.clickhandlers.ButtonClickHandler;
import nl.mprog.apps.evilhangman.hangman.EvilHangman;
import nl.mprog.apps.evilhangman.hangman.Hangman;
import nl.mprog.apps.evilhangman.hangman.NormalHangman;
import nl.mprog.apps.evilhangman.persistence.Highscore;
import nl.mprog.apps.evilhangman.persistence.HighscoresHandler;
import nl.mprog.apps.evilhangman.persistence.WordsAssetsHelper;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView currentWord;
	private TextView currentPogingen;
	private GridView gridview;
	
	private Hangman hangman;
	
	private List<Button> buttons = new ArrayList<Button>();
	private ButtonAdapter buttonAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		gridview = (GridView) findViewById(R.id.gridview);
		
		buttonAdapter = new ButtonAdapter(this);
		gridview.setAdapter(buttonAdapter);
		
		setUp();
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
	
	public Hangman getHangman() {
		return hangman;
	}
	
	public void setWord(String word) {
		currentWord.setText(word);
	}
	
	public void gameWon(String word, int guesses) {

		HighscoresHandler handler = new HighscoresHandler(this);
		handler.addHighscore(new Highscore(word, guesses));
		
		Intent intent = new Intent(this, WinActivity.class);
		intent.putExtra("word", word);
		startActivity(intent);
		restart();
	}
	
	public void gameOver(String word) {

		Intent intent = new Intent(this, LostActivity.class);
		intent.putExtra("word", word);
		startActivity(intent);
		restart();
	}
	
	public void updateGuesses(int guesses) {
		currentPogingen.setText("Je hebt nog "+ guesses +" pogingen over");
	}
	
	private void setUp() {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		int guesses = sharedPref.getInt(SettingsActivity.PREF_GUESSES, 10);
		int length = sharedPref.getInt(SettingsActivity.PREF_LENGTH, 10);
		boolean evil = sharedPref.getBoolean(SettingsActivity.PREF_EVIL, true);
		
		WordsAssetsHelper wordsAssetsHelper = new WordsAssetsHelper(this);
		List<String> words = wordsAssetsHelper.wordsByLength(length);
		
		currentWord = (TextView) findViewById(R.id.current_word);
		updateCurrentWordTextSize(length);

		hangman = evil ? new EvilHangman() : new NormalHangman();
		hangman.setMaxGuesses(guesses);
		hangman.setWordLength(length);
		hangman.setWords(words);
		hangman.setUp();
		
		currentPogingen = (TextView) findViewById(R.id.current_pogingen);
		currentPogingen.setText("Je hebt nog "+ hangman.getGuesses() +" pogingen over");
		
		currentWord.setText(hangman.getCurrentWord());
		
		for(int i = 0; i < gridview.getChildCount(); i++) {
			  Button btn = (Button) gridview.getChildAt(i);
		      btn.setOnClickListener(new ButtonClickHandler(this));
			  btn.setEnabled(true);
		}				
	}
	
	private void restart() {
		setUp();
	}
	
	private void updateCurrentWordTextSize(int wordLength) {
		if (wordLength < 10) {
			currentWord.setTextSize(36);
		} else if (wordLength < 15) {
			currentWord.setTextSize(24);
		} else if (wordLength < 20) {
			currentWord.setTextSize(20);
		} else {
			currentWord.setTextSize(16);
		}
	}

}
