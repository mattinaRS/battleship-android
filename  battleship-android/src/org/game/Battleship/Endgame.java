package org.game.Battleship;

import java.util.Timer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Endgame extends Activity implements OnClickListener
{	
	int sc;
	static final String tag = new String("Endgame");
	DBAdapter db;
	int playerScore;
	int aiships, plships, turns;
	String res = new String();
	TextView textEnd, textScore;
	Button menu;
	ComputeScore cs;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame);
    	Bundle extras = getIntent().getExtras();
    	if(extras != null)
    	{
    		res = extras.getString("Win");
    		aiships = extras.getInt("aiships",0);
    		plships = extras.getInt("plships",0);
    		turns = extras.getInt("turns", 100);
    	}
    	Log.d("Endg", "create");
    	db= new DBAdapter(this);
    	cs = new ComputeScore();
        textEnd = (TextView)findViewById(R.id.res);
        textScore = (TextView)findViewById(R.id.sc);
        menu = (Button)findViewById(R.id.mainmenu);
        menu.setOnClickListener(this);

        textEnd.setText(res);
        sc = cs.compute(turns, aiships, plships);
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

	@Override
	public void onClick(View src) 
	{
		switch(src.getId())
		{
		case R.id.mainmenu:
			Intent myIntent = new Intent(Endgame.this, Battleship.class);
			Endgame.this.startActivity(myIntent);
			Endgame.this.finish();
			break;		
		}
	}
}