package nl.mprog.apps.evilhangman.tests;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import nl.mprog.apps.evilhangman.hangman.EvilHangman;
import nl.mprog.apps.evilhangman.hangman.Hangman;
import nl.mprog.apps.evilhangman.hangman.NormalHangman;
import nl.mprog.apps.evilhangman.persistence.WordsAssetsHelper;

public class HangmanTest extends TestCase {
	
	public void testEvilHangman() {
		List<String> words = Arrays.asList(new String[] {"abc", "def"});
		Hangman hangman = new EvilHangman();
		hangman.initializeWith(3, 2, words);
		
		hangman.addLetter('a');
		assertFalse(hangman.gameOver());
		assertFalse(hangman.gameWon());
		assertEquals(hangman.getGuessesUsed(), 1);
		
		hangman.addLetter('d');
		assertFalse(hangman.gameOver());
		assertFalse(hangman.gameWon());
		assertEquals(hangman.getGuessesUsed(), 1);
		
		hangman.addLetter('e');
		assertFalse(hangman.gameOver());
		assertFalse(hangman.gameWon());
		assertEquals(hangman.getGuessesUsed(), 1);
		
		hangman.addLetter('b');
		assertFalse(hangman.gameWon());
		assertTrue(hangman.gameOver());
		assertEquals(hangman.getGuessesUsed(), 2);
	}
	
	public void testNormalHangman() {
		List<String> words = Arrays.asList(new String[] {"abc"});
		Hangman hangman = new NormalHangman();
		hangman.initializeWith(3, 5, words);
		
		hangman.addLetter('a');
		assertFalse(hangman.gameOver());
		assertFalse(hangman.gameWon());
		assertEquals(hangman.getGuessesUsed(), 0);
		
		hangman.addLetter('d');
		assertFalse(hangman.gameOver());
		assertFalse(hangman.gameWon());
		assertEquals(hangman.getGuessesUsed(), 1);
		
		hangman.addLetter('b');
		assertFalse(hangman.gameOver());
		assertFalse(hangman.gameWon());
		assertEquals(hangman.getGuessesUsed(), 1);
		
		hangman.addLetter('c');
		assertFalse(hangman.gameOver());
		assertTrue(hangman.gameWon());
		assertEquals(hangman.getGuessesUsed(), 1);
	}
}
