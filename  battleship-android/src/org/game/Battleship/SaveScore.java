package org.game.Battleship;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SaveScore extends Activity{
	ComputeScore sc = new ComputeScore();
    private static final String TABL_NAME = "HighScores";
    private static final String COL1 = "name";
    private static final String COL2 = "score";
    DBHelper dbhelp;
    
    SaveScore()
    {
    	DBHelper dbhelp = new DBHelper(SaveScore.this, TABL_NAME, COL1 + COL2);
    }
    
	public void saveScore(int turns, int enemydestroyed, int owndestroyed)
	{
		int playerscore;
		playerscore = sc.compute(turns, enemydestroyed, owndestroyed);
		// read all elements from db
		// compare min to playerscore
		// if playerscore greater than min
		// prompt for name
		// save player score to db,
		// how to check if we already have 10 values in db
		
	}
	
}
