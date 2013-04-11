package nl.mprog.apps.evilhangman.clickhandlers;

import nl.mprog.apps.evilhangman.MainActivity;
import nl.mprog.apps.evilhangman.hangman.Hangman;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ButtonClickHandler implements OnClickListener {
	
	private MainActivity activity;
	private Hangman hangman;
	
	public ButtonClickHandler(MainActivity activity) {
		this.activity = activity;
		this.hangman = activity.getHangman();
	}

	public void setHangman(Hangman hangman) {
		this.hangman = hangman;
	}

	public void onClick(View v) {
		Button button = (Button) v;
		button.getText();
		
		button.setEnabled(false);
		char letter = button.getText().toString().toLowerCase().charAt(0);
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
