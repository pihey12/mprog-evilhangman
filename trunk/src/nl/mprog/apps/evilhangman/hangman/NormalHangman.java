package nl.mprog.apps.evilhangman.hangman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Standard hangman game with a single word to guess.
 * The word to guess will be decided in the initializeWith method.
 * http://en.wikipedia.org/wiki/Hangman_%28game%29
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class NormalHangman implements Hangman {

	private String holdingWord;
	private int guesses;
	private List<String> currentWord;
	private List<Character> wrongGuessedChars;
	private List<Character> correctGuessedChars;
	
	public NormalHangman() {
		wrongGuessedChars = new ArrayList<Character>();
		correctGuessedChars = new ArrayList<Character>();
		currentWord = new ArrayList<String>();
	}
	
	public void addLetter(char letter) {
		if (holdingWord.indexOf(letter) != -1){
			correctGuessedChars.add(letter);
			updateCurrentWord();
		} else {
			guesses--;
			wrongGuessedChars.add(letter);
		}		
	}

	public String getWord() {
		if (!holdingWord.isEmpty()) {
			return holdingWord;
		} else {
			return getCurrentWord();
		}
	}
	
	public String getCurrentWord() {
		String res = "";
		for (String s : currentWord) {
			res += s +" ";
		}
		return res;
	}
	
	public int getGuesses() {
		return guesses;
	}

	public boolean gameOver() {
		return guesses == 0;
	}

	public int getGuessesUsed() {
		return wrongGuessedChars.size();
	}

	public void initializeWith(int wordLength, int maxGuesses,
			List<String> words) {
		guesses = maxGuesses;
		for (int i = 0; i < wordLength; i++) {
			currentWord.add(DEFAULT_LETTER);
		}
		
		Random random = new Random();
		holdingWord = words.get(random.nextInt(words.size()));
	}

	public boolean gameWon() {
		boolean finished = true;
		for (String s : currentWord) {
			if (s.equals(DEFAULT_LETTER)) {
				finished = false;
			}
		}
		
		return finished;
	}
	
	/**
	 * Checks which letters have been guessed already, and replaces the ones that aren't guessed yet
	 *  with the {@link #DEFAULT_LETTER}.
	 */
	private void updateCurrentWord() {
		List<String> list = new ArrayList<String>();
		
		for (int i = 0; i < holdingWord.length(); i++) {
			char c = holdingWord.charAt(i);
			if (correctGuessedChars.contains(c)) {
				list.add(""+ c);
			} else {
				list.add(DEFAULT_LETTER);
			}
		}
		
		currentWord = list;
	}

}
