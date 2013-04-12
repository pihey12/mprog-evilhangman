package nl.mprog.apps.evilhangman.clickhandlers;

import nl.mprog.apps.evilhangman.MainActivity;
import nl.mprog.apps.evilhangman.hangman.Hangman;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ButtonClickHandler implements OnClickListener {
	
	private MainActivity activity;
	private Hangman hangman;
	
	public ButtonClickHandler(MainActivity activity, Hangman hangman) {
		this.activity = activity;
		this.hangman = hangman;
	}

	public void onClick(View v) {
		Button button = (Button) v;
		button.setEnabled(false);
		
		char letter = button.getText().toString().charAt(0);
		hangman.addLetter(letter);
		
		activity.setWord(hangman.getCurrentWord());
		activity.updateGuesses(hangman.getGuesses());
		
		if (hangman.gameOver()) {
			activity.gameOver(hangman.getWord());
		} else if (hangman.gameWon()) {
			activity.gameWon(hangman.getWord(), hangman.getGuessesUsed());
		}
	}
}
