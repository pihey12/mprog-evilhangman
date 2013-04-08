package nl.mprog.apps.evilhangman.hangman;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EvilHangman implements Hangman {
	
	private int wordLength;
	private String holdingWord;
	private List<String> words;
	private int guesses;
	private int maxGuesses;
	private List<String> currentWord;
	private List<Character> wrongGuessedChars;
	private List<Character> correctGuessedChars;
	
	public void addLetter(char letter) {
		int wordsWithTheLetter = countWordsWithTheLetter(letter);
		
		if (wordsWithTheLetter == words.size()) {
			// HAVE to give up the letter
			updateWords(letter);
			correctGuessedChars.add(letter);
			updateCurrentWord();
		} else {
			// Words left without the letter
			removeWords(letter);
			wrongGuessedChars.add(letter);
			guesses--;
		}
	}
	
	public void setWords(List<String> words){
		this.words = new ArrayList<String>(words);
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

	public int getWordLength() {
		return wordLength;
	}

	public void setWordLength(int length) {
		this.wordLength = length;
	}

	public int getGuesses() {
		return guesses;
	}
	
	public int getGuessesUsed() {
		return wrongGuessedChars.size();
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
	
	public void setUp() {
		currentWord = new ArrayList<String>();
		guesses = maxGuesses;
		for (int i = 0; i < wordLength; i++) {
			currentWord.add(DEFAULT_LETTER);
		}
		wrongGuessedChars = new ArrayList<Character>();
		correctGuessedChars = new ArrayList<Character>();
	}
	
	public void restart() {
		setUp();
	}
	
	private int countWordsWithTheLetter(char letter) {
		int count = 0;
		int letterCount = wordLength;
		for (String word : words) {
			if (word.indexOf(letter) != -1) {
				int occurences = findOccurrences(word, letter);
				if (occurences < letterCount) {
					letterCount = occurences;
					holdingWord = word;
				}
				count++;
			}
		}
		return count;
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
	
	private void updateWords(char letter) {
		List<Integer> indexes = new ArrayList<Integer>();
		for (int i = 0; i < holdingWord.length(); i++) {
			if (holdingWord.charAt(i) == letter) {
				indexes.add(i);
			}
		}

		Iterator<String> iterator = words.iterator();
		while (iterator.hasNext()) {
			String word = iterator.next();
			
			for (Integer i : indexes) {
				if (word.charAt(i) != letter) {
					iterator.remove();
					break;
				}
			}
		}
	}
	
	private void removeWords(char letter) {
		Iterator<String> iterator = words.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().indexOf(letter) != -1) {
				iterator.remove();
			}
		}
	}
	
	private int findOccurrences(String word, char letter) {
		int count = 0;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == letter) {
				count++;
			}
		}
		return count;
	}

}
