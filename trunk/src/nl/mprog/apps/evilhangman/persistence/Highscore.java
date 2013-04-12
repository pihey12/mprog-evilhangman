package nl.mprog.apps.evilhangman.persistence;

/**
 * The model for the highscores
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class Highscore {
	
	private String word;
	private int guesses;
	
	public Highscore(String word, int guesses) {
		this.word = word;
		this.guesses = guesses;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public int getGuesses() {
		return guesses;
	}
	
	public void setGuesses(int guesses) {
		this.guesses = guesses;
	}

}
