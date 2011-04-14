package org.game.Battleship;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.widget.EditText;

public class SaveScore extends Activity {
	DBAdapter db = new DBAdapter(this);
	/*
	final DBAdapter db = new DBAdapter(this);
	*/
	public void saveScore(int score)
	{
		@SuppressWarnings("unused")
		Cursor cursor;
		final int playerScore;
		int minScore;
		playerScore = score;
		
		// read all elements from db
		cursor = db.getallScores();
		
		// check if we already have 10 values in db
		// if not then insert into db
		if (cursor.getCount() < 10)	{
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
			minScore = cursor.getInt(2);
			if (playerScore > minScore)
			{
				final long minScoreID = Long.parseLong(cursor.getString(0));
		
				//////////////////
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
			
				alert.setTitle("You got a HIGH SCORE!");
				alert.setMessage("Enter yourNam e fool!");
			
				// Set an EditText view to get user input
				final EditText input = new EditText(this);
				alert.setView(input);


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