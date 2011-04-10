package org.game.Battleship;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class HighScore extends Activity{

    private static final String TABL_NAME = "HighScores";
	DBHelper dbhelp;
	
	public ArrayList<List> getscores()
	{
//	    String SELECT_SCORES = "SELECT * FROM " + TABL_NAME;		
        ArrayList<List> scores = new ArrayList<List>();
//        SQLiteDatabase sqliteDB = dbhelp.getReadableDatabase();
//        Cursor crsr = sqliteDB.rawQuery(SELECT_SCORES, null);
/*        crsr.moveToFirst();
        for (int i = 0; i < crsr.getCount(); i++)
        {
            //scores.add(crsr.getString(0), crsr.getString(1)));
            crsr.moveToNext();
        }
*/
        return scores;
    }		
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<List> scores = new ArrayList<List>();
        scores = getscores();
        setContentView(R.layout.highscores);
    }	


}
