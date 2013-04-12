package nl.mprog.apps.evilhangman.persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Mapper between the database and the Highscore model
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class HighscoresHandler extends SQLiteOpenHelper {
	
	private static final int VERSION = 1;
	private static final String NAME = "highscores";

	public HighscoresHandler(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String create = "CREATE TABLE "+ NAME +
				" (id INTEGER PRIMARY KEY," +
				" word TEXT," +
				" guesses INTEGER)";
		db.execSQL(create);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+ NAME);
		onCreate(db);
	}
	
	public void addHighscore(Highscore highscore) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("word", highscore.getWord());
		values.put("guesses", highscore.getGuesses());
		
		db.insert(NAME, null, values);
		db.close();
	}
	
	public List<Highscore> getHighscores() {
		List<Highscore> list = new ArrayList<Highscore>();
		
		String query = "SELECT * FROM "+ NAME +" ORDER BY guesses ASC LIMIT 10";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		if (cursor.moveToFirst()) {
			do {
				Highscore highscore = new Highscore(cursor.getString(1), Integer.parseInt(cursor.getString(2)));
				list.add(highscore);
			} while (cursor.moveToNext());
		}
		
		return list;
	}
	
	public int getHighscoreCount() {
		String query = "SELECT * FROM "+ NAME;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		cursor.close();
		
		return cursor.getCount();
	}

}
