package nl.mprog.apps.evilhangman.hangman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.mprog.apps.evilhangman.Words;

public class EvilHangman implements Hangman {
	
	private int wordLength;
	private String holdingWord;
	private List<String> words = Words.AVAILABLE_WORDS;
	private int guesses;
	private int maxGuesses;
	private List<String> currentWord;
	private List<Character> wrongGuessedChars;
	private List<Character> correctGuessedChars;
	
	public void addLetter(char letter) {
		List<String> availableWords = getAvailableWords();
		Map<String, Integer> wordsWithTheLetter = findWordsWithTheLetter(letter, availableWords);
		
		if (wordsWithTheLetter.size() == availableWords.size()) {
			holdingWord = getWordWithLowestCount(wordsWithTheLetter);
			correctGuessedChars.add(letter);
			updateCurrentWord();
		} else {
			guesses--;
			wrongGuessedChars.add(letter);
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

	private List<String> getAvailableWords() {
		List<String> list = new ArrayList<String>(words);
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String word = iterator.next();
			for (char c : wrongGuessedChars) {
				if (word.indexOf(c) != -1) {
					iterator.remove();
					break;
				}
			}
		}
		
		if (wordHasLetters()) {
			for (int i = 0; i < currentWord.size(); i++) {
				String s = currentWord.get(i);
				char c = s.toLowerCase().charAt(0);
				if (c != '_') {
					iterator = list.iterator();
					while (iterator.hasNext()) {
						String word = iterator.next();
						if (word.charAt(i) != c) {
							iterator.remove();
							break;
						}
					}
				}
			}
		}
		
		
		return list;
	}
	
	private Map<String, Integer> findWordsWithTheLetter(char letter, List<String> words) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String word : words) {
			if (word.indexOf(letter) != -1) {
				map.put(word, findOccurrences(word, letter));
			}
		}
		return map;
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
	
	private int findOccurrences(String word, char letter) {
		int count = 0;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == letter) {
				count++;
			}
		}
		return count;
	}
	
	private boolean wordHasLetters() {
		for (String s : currentWord) {
			if (!s.equals("_")) {
				return true;
			}
		}
		return false;
	}
	
	private String getWordWithLowestCount(Map<String, Integer> map) {
		String res = "";
		int count = wordLength;
		for (Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() < count) {
				res = entry.getKey();
			}
		}
		return res;
	}

}
