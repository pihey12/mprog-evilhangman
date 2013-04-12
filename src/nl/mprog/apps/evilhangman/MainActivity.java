package nl.mprog.apps.evilhangman;

import java.util.List;

import nl.mprog.apps.evilhangman.adapters.ButtonAdapter;
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
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

/**
 * The main activity which starts the hangman game and the user interface.
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class MainActivity extends Activity {
	
	/**
	 * The code that's being used to know when the activity is finished
	 */
	static final int RESTART_GAME = 1337;
	
	private TextView currentWord;
	private TextView currentGuesses;
	private GridView gridview;
	
	private Hangman hangman;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		gridview = (GridView) findViewById(R.id.gridview);
		currentGuesses = (TextView) findViewById(R.id.current_guesses);
		currentWord = (TextView) findViewById(R.id.current_word);
		
		startNewHangmanGame();
		initializeGrid();
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
	        	startNewHangmanGame();
	            return true;
	        case R.id.settings:
	        	Intent intent = new Intent(this, SettingsActivity.class);
	        	startActivity(intent);
	        	return true;
	    }
	    
	    return false;
	}
	
	/**
	 * Informs the user about the word he has been trying to guess
	 * 
	 * @param word The word to show
	 */
	public void setWord(String word) {
		currentWord.setText(word);
	}
	
	/**
	 * Starts the win activity since the user has won the game
	 * 
	 * @param word The word the user guessed
	 * @param guesses The amount of guesses that the user has used
	 */
	public void gameWon(String word, int guesses) {
		HighscoresHandler handler = new HighscoresHandler(this);
		handler.addHighscore(new Highscore(word, guesses));
		
		Intent intent = new Intent(this, WinActivity.class);
		intent.putExtra("word", word);
		startActivityForResult(intent, RESTART_GAME);
	}
	
	/**
	 * Starts the lost activity since the user has lost the game
	 * 
	 * @param word The word the user failed to guess
	 */
	public void gameOver(String word) {
		Intent intent = new Intent(this, LostActivity.class);
		intent.putExtra("word", word);
		startActivityForResult(intent, RESTART_GAME);
	}
	
	/**
	 * Informs the user about the amount of guesses he has left.
	 * 
	 * @param guesses The guesses that the user has left
	 */
	public void updateGuesses(int guesses) {
		String s = getResources().getString(R.string.current_guesses);
		currentGuesses.setText(s.replace("###", ""+ guesses));
	}
	
	/**
	 * Is used to see when an activity is finished.
	 * This is done because the game should restart after the win/loss activity is finished
	 *  so that there is no huge delay/lagg when entering those activities.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESTART_GAME) {
			if (resultCode == RESULT_OK) {
				startNewHangmanGame();
			}
		}
	}
	
	/**
	 * Creates all the buttons that represents the alfabet and places them on the grid.
	 */
	private void initializeGrid() {
		ButtonAdapter buttonAdapter;
		if (isTablet()){
			buttonAdapter = new ButtonAdapter(this, 100, 48);
			gridview.setColumnWidth(100);
		} else {
			buttonAdapter = new ButtonAdapter(this, 60, 16);
		}
		
		buttonAdapter.setHangman(hangman);
		gridview.setAdapter(buttonAdapter);
	}
	
	/**
	 * Initializes a new hangman game according to the given settings.
	 */
	private void startNewHangmanGame() {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		int guesses = sharedPref.getInt(SettingsActivity.PREF_GUESSES, 10);
		int length = sharedPref.getInt(SettingsActivity.PREF_LENGTH, 10);
		boolean evil = sharedPref.getBoolean(SettingsActivity.PREF_EVIL, true);
		
		WordsAssetsHelper wordsAssetsHelper = new WordsAssetsHelper(this);
		List<String> words = wordsAssetsHelper.wordsByLength(length);
		
		updateCurrentWordTextSize(length);

		hangman = evil ? new EvilHangman() : new NormalHangman();
		hangman.initializeWith(length, guesses, words);
		
		updateGuesses(guesses);
		setWord(hangman.getCurrentWord());
		
		for(int i = 0; i < gridview.getChildCount(); i++) {
			  Button btn = (Button) gridview.getChildAt(i);
		      btn.setOnClickListener(new ButtonClickHandler(this, hangman));
			  btn.setEnabled(true);
		}
	}
	
	/**
	 * Checks if the user is using a tablet
	 * 
	 * @return Whether the user uses a tablet or not
	 */
	private boolean isTablet() {
	    boolean xlarge = ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
	    boolean large = ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
	    return (xlarge || large);
	}
	
	/**
	 * Updates the text size of the word to be guessed based on the length of the word
	 *  and whether the user uses a tablet or not.
	 *  
	 *  For user interface purposes
	 * 
	 * @param wordLength The length of the word to be guessed
	 */
	private void updateCurrentWordTextSize(int wordLength) {
		if (isTablet()){
			if (wordLength < 10) {
				currentWord.setTextSize(60);
			} else if (wordLength < 15) {
				currentWord.setTextSize(52);
			} else if (wordLength < 20) {
				currentWord.setTextSize(40);
			} else {
				currentWord.setTextSize(40);
			}
		} else {
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
	

}
