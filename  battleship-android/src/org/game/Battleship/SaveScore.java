package org.game.Battleship;


import android.app.Activity;
import android.database.Cursor;

public class SaveScore extends Activity{
    DBAdapter db = new DBAdapter(this);
	ComputeScore sc = new ComputeScore();
    
	public void saveScore(int turns, int enemydestroyed, int owndestroyed)
	{
		Cursor cursor;
		int playerscore;
		playerscore = sc.compute(turns, enemydestroyed, owndestroyed);
		// read all elements from db
		cursor = db.getallScores();
		// compare min to playerscore
		// if playerscore greater than min
		// prompt for name
		// save player score to db,
		// how to check if we already have 10 values in db
		
	}	
}
