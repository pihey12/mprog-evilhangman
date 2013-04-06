package nl.mprog.apps.evilhangman.hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nl.mprog.apps.evilhangman.Words;

public class NormalHangman implements Hangman {

	private int wordLength;
	private String holdingWord;
	private List<String> words = Words.AVAILABLE_WORDS;
	private int guesses;
	private int maxGuesses;
	private List<String> currentWord;
	private List<Character> wrongGuessedChars;
	private List<Character> correctGuessedChars;
	
	@Override
	public void addLetter(char letter) {
		
		if(holdingWord.indexOf(letter) != -1 ){
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


	public int getWordLength() {
		return wordLength;
	}

	public void setWordLength(int length) {
		this.wordLength = length;
	}
	
	public int getGuesses() {
		return guesses;
	}

	public void setMaxGuesses(int maxGuesses) {
		this.maxGuesses = maxGuesses;
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

	@Override
	public void setUp() {
		currentWord = new ArrayList<String>();
		guesses = maxGuesses;
		
		for (int i = 0; i < wordLength; i++) {
			currentWord.add(DEFAULT_LETTER);
		}
		
		wrongGuessedChars = new ArrayList<Character>();
		correctGuessedChars = new ArrayList<Character>();
		
		Random random = new Random();
		this.holdingWord = this.words.get(random.nextInt(this.words.size()));
	}

	@Override
	public void restart() {
		setUp();
	}
	
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
