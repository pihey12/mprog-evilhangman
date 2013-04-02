package nl.mprog.apps.evilhangman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	static final int WORD_LENGTH = 10;
	private List<String> words = Words.AVAILABLE_WORDS;
	static int POGINGEN = 10;
	private List<String> CURRENT_WORD = new ArrayList<String>();
	private TextView currentWord;
	private TextView currentPogingen;
	private List<Character> WRONG_GUESSED_CHARS = new ArrayList<Character>();
	private List<Character> CORRECT_GUESSED_CHARS = new ArrayList<Character>();
	
	private List<Button> buttons = new ArrayList<Button>();

	private static final int BUTTON_WIDTH = 59;
	private static final int BUTTON_HEIGHT = 59;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setup();
		
		((Button) findViewById(R.id.restart)).setOnClickListener(this);
		
		LinearLayout buttonLayout_1 = (LinearLayout) findViewById(R.id.button_layout_1);
		for (char c = 'a'; c <= 'g'; c++) {
			buttonLayout_1.addView(createButton(c));
		}
		
		LinearLayout buttonLayout_2 = (LinearLayout) findViewById(R.id.button_layout_2);
		for (char c = 'h'; c <= 'n'; c++) {
			buttonLayout_2.addView(createButton(c));
		}
		
		LinearLayout buttonLayout_3 = (LinearLayout) findViewById(R.id.button_layout_3);
		for (char c = 'o'; c <= 'u'; c++) {
			buttonLayout_3.addView(createButton(c));
		}
		
		LinearLayout buttonLayout_4 = (LinearLayout) findViewById(R.id.button_layout_4);
		for (char c = 'v'; c <= 'z'; c++) {
			buttonLayout_4.addView(createButton(c));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private Button createButton(char c) {
		String text = c +"";
		Button button = new Button(this);
		button.setLayoutParams(new LayoutParams(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setText(text.toUpperCase());
		button.setOnClickListener(this);
		
		buttons.add(button);
		
		return button;
	}

	@Override
	public void onClick(View v) {
		Button button = (Button) v;
		button.getText();
		
		if (button.getText().equals("Restart game")) {
			restart();
		} else {
			button.setEnabled(false);
			char letter = button.getText().toString().toLowerCase().charAt(0);
			
			List<String> availableWords = getAvailableWords();
			Map<String, Integer> wordsWithTheLetter = findWordsWithTheLetter(letter, availableWords);
			
			if (wordsWithTheLetter.size() == availableWords.size()) {
				String word = getWordWithLowestCount(wordsWithTheLetter);
				CORRECT_GUESSED_CHARS.add(letter);
				updateCurrentWord(word);
				checkIfFinished();
			} else {
				POGINGEN--;
				currentPogingen.setText("Je hebt nog "+ POGINGEN +" pogingen over");
				WRONG_GUESSED_CHARS.add(letter);
			}
			
			if (POGINGEN == 0) {
				gameOver();
			}
		}
	}
	
	private List<String> getAvailableWords() {
		List<String> list = new ArrayList<String>(words);
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String word = iterator.next();
			for (char c : WRONG_GUESSED_CHARS) {
				if (word.indexOf(c) != -1) {
					iterator.remove();
					break;
				}
			}
		}
		
		if (wordHasLetters()) {
			for (int i = 0; i < CURRENT_WORD.size(); i++) {
				String s = CURRENT_WORD.get(i);
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
		for (String s : CURRENT_WORD) {
			if (!s.equals("_")) {
				return true;
			}
		}
		return false;
	}
	
	private String getWordWithLowestCount(Map<String, Integer> map) {
		String res = "";
		int count = WORD_LENGTH;
		for (Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() < count) {
				res = entry.getKey();
			}
		}
		return res;
	}
	
	private void updateCurrentWord(String word) {
		List<String> list = new ArrayList<String>();
		String text = "";
		
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (CORRECT_GUESSED_CHARS.contains(c)) {
				list.add(""+ c);
				text += c +" ";
			} else {
				list.add("_");
				text += "_ ";
			}
		}
		
		currentWord.setText(text);
		CURRENT_WORD = list;
	}
	
	private void checkIfFinished() {
		boolean finished = true;
		String word = "";
		for (String s : CURRENT_WORD) {
			word += s;
			if (s.equals("_")) {
				finished = false;
			}
		}
		
		if (finished) {
			currentPogingen.setText("YOU WIN! The word was: "+ word);
		}
	}
	
	private void gameOver() {
		currentPogingen.setText("YOU LOST! ... newbie");
		
		for (Button button : buttons) {
			button.setEnabled(false);
		}
//		Intent intent = new Intent(this, LostActivity.class);
//		startActivity(intent);
	}
	
	private void setup() {
		currentWord = (TextView) findViewById(R.id.current_word);
		currentPogingen = (TextView) findViewById(R.id.current_pogingen);
		currentPogingen.setText("Je hebt nog "+ POGINGEN +" pogingen over");
		
		String text = "";
		for (int i = 0; i < WORD_LENGTH; i++) {
			CURRENT_WORD.add("_");
			text +="_ ";
		}
		currentWord.setText(text);
	}
	
	private void restart() {
		CURRENT_WORD = new ArrayList<String>();
		WRONG_GUESSED_CHARS = new ArrayList<Character>();
		CORRECT_GUESSED_CHARS = new ArrayList<Character>();
		
		POGINGEN = 10;
		
		for (Button button : buttons) {
			button.setEnabled(true);
		}
		
		setup();
	}

}
