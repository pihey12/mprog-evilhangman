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
	private boolean evil;
	
	public Highscore(String word, int guesses, boolean evil) {
		this.word = word;
		this.guesses = guesses;
		this.evil = evil;
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
	
	public boolean isEvil() {
		return evil;
	}
	
	public void setEvil(boolean evil) {
		this.evil = evil;
	}

}
