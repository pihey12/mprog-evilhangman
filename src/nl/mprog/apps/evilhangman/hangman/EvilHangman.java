package nl.mprog.apps.evilhangman.hangman;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Evil Hangman game where we try to make the user lose the game by changing the words everytime a letter is being guessed.
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class EvilHangman implements Hangman {
	
	private String holdingWord;
	private List<String> words;
	private int guesses;
	private List<String> currentWord;
	private List<Character> wrongGuessedChars;
	private List<Character> correctGuessedChars;
	
	public EvilHangman() {
		wrongGuessedChars = new ArrayList<Character>();
		correctGuessedChars = new ArrayList<Character>();
		currentWord = new ArrayList<String>();
	}
	
	public void addLetter(char letter) {
		if (wordWithoutLetterExists(letter)) {
			removeWords(letter);
			wrongGuessedChars.add(letter);
			guesses--;
		} else {
			holdingWord = words.get(0);
			updateWords(letter);
			correctGuessedChars.add(letter);
			updateCurrentWord();
		}
	}
	
	public String getWord() {
		return holdingWord;
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
	
	public int getGuessesUsed() {
		return wrongGuessedChars.size();
	}

	public boolean gameOver() {
		return guesses == 0;
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

	public void initializeWith(int wordLength, int maxGuesses,
			List<String> words) {
		this.words = new LinkedList<String>(words);
		
		guesses = maxGuesses;
		for (int i = 0; i < wordLength; i++) {
			currentWord.add(DEFAULT_LETTER);
		}
	}
	
	/**
	 * We loop through all the words and see if there is still one left that doesn't contain the letter
	 * 
	 * @param letter The letter that the user has guessed
	 * @return	Whether there is still a word left without the letter
	 */
	private boolean wordWithoutLetterExists(char letter) {
		for (String word : words) {
			if (word.indexOf(letter) == -1) {
				holdingWord = word;
				return true;
			}
		}
		
		return false;
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
	
	/**
	 * First we find all the indexes of where the letter is located from the word we have in mind.
	 * Then we remove all the words that have the same letter at a different index.
	 * Then we remove all the words that don't have the same letter located at the index.
	 * 
	 * Which will leave us with all the words left that match the pattern from the word we have in mind.
	 * 
	 * @param letter The letter that the user has guessed
	 */
	private void updateWords(char letter) {
		List<Integer> indexes = new ArrayList<Integer>();
		for (int i = 0; i < holdingWord.length(); i++) {
			if (holdingWord.charAt(i) == letter) {
				indexes.add(i);
			}
		}

		Iterator<String> iterator = words.iterator();
		while (iterator.hasNext()) {
			boolean removed = false;
			
			String word = iterator.next();
			
			// Remove the word if it contains the letter, but not at the right index
			// e.g. "eager" will be removed when the word is _ _ _ e _
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (c == letter && !indexes.contains(i)) {
					iterator.remove();
					removed = true;
					break;
				}
			}
			
			if (removed) {
				continue;
			}
			
			// Remove the word if the letter is not located at the right index
			// e.g. "test" will be removed when the word is e _ _ _
			for (Integer i : indexes) {
				if (word.charAt(i) != letter) {
					iterator.remove();
					break;
				}
			}
		}
	}
	
	/**
	 * Removes all the words that contain the letter
	 * 
	 * @param letter The letter that the user has guessed
	 */
	private void removeWords(char letter) {
		Iterator<String> iterator = words.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().indexOf(letter) != -1) {
				iterator.remove();
			}
		}
	}

}
