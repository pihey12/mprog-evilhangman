package nl.mprog.apps.evilhangman.tests;

import nl.mprog.apps.evilhangman.persistence.Highscore;
import junit.framework.TestCase;

public class HighscoreTest extends TestCase {
	
	public void testGettersAndSetters() {
		String word = "abc";
		int guesses = 12;
		boolean evil = false;
		
		Highscore highscore = new Highscore(word, guesses, evil);
		assertEquals(highscore.getWord(), "abc");
		assertEquals(highscore.getGuesses(), 12);
		assertEquals(highscore.isEvil(), false);
		
		word = "def";
		guesses = 10;
		evil = true;
		highscore.setWord(word);
		highscore.setGuesses(guesses);
		highscore.setEvil(evil);
		assertEquals(highscore.getWord(), "def");
		assertEquals(highscore.getGuesses(), 10);
		assertEquals(highscore.isEvil(), true);
	}

}
