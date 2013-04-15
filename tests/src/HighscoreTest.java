package src;

import junit.framework.TestCase;
import nl.mprog.apps.evilhangman.persistence.Highscore;

public class HighscoreTest extends TestCase {
	
	public void testGettersAndSetters() {
		String word = "abc";
		int length = 12;
		boolean evil = false;
		
		Highscore highscore = new Highscore(word, length, evil);
		assertEquals(highscore.getWord(), "abc");
		assertEquals(highscore.getGuesses(), 12);
		assertEquals(highscore.isEvil(), false);
	}

}
