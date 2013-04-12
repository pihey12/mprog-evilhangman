package nl.mprog.apps.evilhangman.hangman;

import java.util.List;

/**
 * Interface around the Hangman game.
 * The computer will think of a word, and the user will try to guess the word by suggesting letters.
 * 
 * Only accepts words, not phrases or sentences.
 * 
 * Be sure to call initializeWith() to initialize the game
 *  or else the game will be unplayable.
 * 
 * @author Marten
 * @author Sebastiaan
 */
public interface Hangman {
	
	/**
	 * The character that will be shown for an unguessed letter
	 */
	static String DEFAULT_LETTER = "_";
	
	/**
	 * Initialises the hangman game with a wordlength, amount of guesses and a list of words
	 * After calling this method, the game will be playable
	 * 
	 * @param wordLength	The length of the word to guess
	 * @param maxGuesses	The maximum amount of guesses the user has
	 * @param words			The list of words to guess
	 */
	public void initializeWith(int wordLength, int maxGuesses, List<String> words);
	
	/**
	 * Adds a letter to the game. The game will see if the word to be guessed contains the letter.
	 * 
	 * @param letter	The letter that the user has guessed
	 */
	public void addLetter(char letter);
	
	/**
	 * Returns the word that the hangman game is holding.
	 * 
	 * @return 	The word that's being guessed
	 */
	public String getWord();
	
	/**
	 * Returns the word that's currently being guessed, 
	 * and replaces the not yet guessed characters with the {@link #DEFAULT_LETTER}
	 * 
	 * @return 	The word that's currently being guessed, without the letters that aren't guessed yet
	 */
	public String getCurrentWord();
	
	/**
	 * Returns how many guesses the user has left before he loses the game
	 * 
	 * @return	Guesses left for the user
	 */
	public int getGuesses();
	
	/**
	 * Returns the amount of guesses that the user has used
	 * 
	 * @return	Amount of guesses the user has used
	 */
	public int getGuessesUsed();
	
	/**
	 * Looks if all the characters from the word have been guessed
	 * 
	 * @return Whether the game has been won
	 */
	public boolean gameOver();
	
	/**
	 * Looks if the user has used all of his guesses without having guessed the entire word
	 * 
	 * @return Whether the game has been lost
	 */
	public boolean gameWon();

}
