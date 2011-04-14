package org.game.Battleship;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class Endgame extends Activity 
{	
	int sc;
	static final String tag = new String("Endgame");
	DBAdapter db;
	int playerScore;
	String res = new String();
	TextView textEnd, textScore;
	ComputeScore cs;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {    	
    	Log.d("Endg", "create");
    	db= new DBAdapter(this);
    	res = "You Lose";
    	cs = new ComputeScore();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame);
        textEnd = (TextView)findViewById(R.id.res);
        textScore = (TextView)findViewById(R.id.sc);
        textEnd.setText(res);
        sc = cs.compute(10, 3, 5);
        textScore.setText("Your Score = " + Integer.toString(sc));     
        Log.d(tag, "sc called");
        saveScore(sc);
    }	
    
	public void saveScore(int score)
	{
		Log.d(tag, "in save score");
		Cursor cursor;
		int minScore;
		playerScore = score;
		
		// read all elements from db
		db.open();
		Log.d(tag, "DB OPENED");
		cursor = db.getallScores();
		Log.d("savescore", "gotscores");
		// check if we already have 10 values in db
		// if not then insert into db
		cursor.moveToFirst();
		Log.d(tag, Integer.toString(cursor.getCount()));
		int count = 0;
		db.close();
		while(cursor.isAfterLast() == false)
		{	
			count++;
			cursor.moveToNext();
		}
		Log.d("count", Integer.toString(count));
		if (cursor.getCount() < 10)	{
			Log.d(tag, "less than 10");			
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
			alert.setTitle("You got a HIGH SCORE!");
			alert.setMessage("Enter your Name fool!");
		
			// Set an EditText view to get user input
			final EditText input = new EditText(this);
			alert.setView(input);
		
			alert.setPositiveButton("ENTER", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String playerName =(input.getText()).toString();
					Log.d("menu", playerName);
					db.open();
					db.insertScore(playerName, playerScore);
					db.close();
				}
			});
		
			alert.show();
			//db.insertScore(playerName, playerScore);
			//db.close();
		}

		// compare minScore to playerScore
		// if playerScore greater than minScore
		// prompt for name and save player score to db
		else {	
			db.open();
			Log.d(tag, "in else");
			cursor = db.getallScores();
			cursor.moveToFirst();
			minScore = cursor.getInt(2);
			if (playerScore > minScore)
			{
				Log.d(tag, "entering score");
				final long minScoreID = Long.parseLong(cursor.getString(0));
		
				//////////////////
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
			
				alert.setTitle("You got a HIGH SCORE!");
				alert.setMessage("Enter yourNam e fool!");
			
				// Set an EditText view to get user input
				final EditText input = new EditText(this);
				alert.setView(input);

				db.close();
				alert.setPositiveButton("ENTER", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String playerName = (input.getText()).toString();
						Log.d("menu", playerName);
						db.open();
						db.deleteScore(minScoreID);
						db.insertScore(playerName, playerScore);
						db.close();
					}
				});
				
				alert.show();
				//////////////////
			}
		}
	}
}