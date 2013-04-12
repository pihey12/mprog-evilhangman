package nl.mprog.apps.evilhangman.persistence;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class WordsAssetsHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "words";
    private static final int DATABASE_VERSION = 1;

    public WordsAssetsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
    
    public List<String> wordsByLength(int length){
		List<String> list = new LinkedList<String>();
    	SQLiteDatabase db = getReadableDatabase();
    	
    	String sql = "SELECT * FROM " + DATABASE_NAME + " WHERE length = " + length + ";";
    	
		Cursor cursor = db.rawQuery(sql, null);
				
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(1).toLowerCase());
			} while (cursor.moveToNext());
		}
				
		return list;
    }
}
