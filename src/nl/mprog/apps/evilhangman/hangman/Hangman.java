package nl.mprog.apps.evilhangman.hangman;

import java.util.List;

public interface Hangman {
	
	static String DEFAULT_LETTER = "_";
	
	public void initializeWith(int wordLength, int maxGuesses, List<String> words);
	public void addLetter(char letter);
	public String getWord();
	public String getCurrentWord();
	public int getWordLength();
	public int getGuesses();
	public int getGuessesUsed();
	public boolean gameOver();
	public boolean gameWon();
	public void setUp();
	public void restart();

}
