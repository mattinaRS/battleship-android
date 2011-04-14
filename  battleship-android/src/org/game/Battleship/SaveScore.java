package org.game.Battleship;


import android.app.Activity;
import android.database.Cursor;

public class SaveScore extends Activity{
    DBAdapter db = new DBAdapter(this);
    
	public void saveScore(int score)
	{
		Cursor cursor;
//		cursor = db.getallScores();
		// compare min to playerscore
		// if playerscore greater than min
		// prompt for name
		// save player score to db,
		// how to check if we already have 10 values in db
		
	}	
}
