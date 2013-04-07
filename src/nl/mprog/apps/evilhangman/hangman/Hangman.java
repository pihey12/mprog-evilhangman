package nl.mprog.apps.evilhangman.hangman;

import java.util.List;

public interface Hangman {
	
	static String DEFAULT_LETTER = "_";
	
	public void addLetter(char letter);
	public String getWord();
	public String getCurrentWord();
	public int getWordLength();
	public void setWordLength(int length);
	public int getGuesses();
	public int getGuessesUsed();
	public void setMaxGuesses(int maxGuesses);
	public boolean gameOver();
	public boolean gameWon();
	public void setUp();
	public void restart();
	public void setWords(List<String> words);

}
