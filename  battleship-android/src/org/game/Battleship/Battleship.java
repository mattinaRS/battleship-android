package org.game.Battleship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import android.content.DialogInterface;
import android.app.AlertDialog;

public class Battleship extends Activity implements OnClickListener {
	
	Button buttonPlay, buttonHiscore, buttonQuit, buttonEnd;
	DBAdapter db;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	final DBAdapter db = new DBAdapter(this);
    	db.open();
    	db.deleteallScores();
    	db.insertScore("SID", 100);
       	db.insertScore("ABC", 12);
    	db.insertScore("XYZ", 24);
       	db.insertScore("DCG", 89);
       	
        Log.d("Hiscore", "insert");
        db.close();
        buttonPlay = (Button)findViewById(R.id.play);
        buttonHiscore = (Button)findViewById(R.id.hiscore);
        buttonQuit = (Button)findViewById(R.id.quit);
        buttonEnd = (Button)findViewById(R.id.end);
        
        buttonPlay.setOnClickListener(this);
        buttonHiscore.setOnClickListener(this);
        buttonQuit.setOnClickListener(this);
        buttonEnd.setOnClickListener(this);
    }
    
    public void onDestroy(Bundle savedInstanceState) {
        super.onDestroy();
    }	

	@Override
	public void onClick(View src) {
		switch(src.getId()){
		case R.id.play:
			Intent myIntent = new Intent(Battleship.this, GameBoard.class);
			Battleship.this.startActivity(myIntent);
	    	final DBAdapter db = new DBAdapter(this);			
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("You got a HIGH SCORE!");
			alert.setMessage("Enter your Name fool!");

			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("ENTER", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = (input.getText()).toString();
				Log.d("menu", value);
		    	db.open();
				db.insertScore(value, 50);
				db.close();
			  }
			});

			alert.show();
			
			break;
		case R.id.hiscore:
			Intent hiscore = new Intent(Battleship.this, HighScore.class);
			Battleship.this.startActivity(hiscore);
			break;
		case R.id.end:
			Intent endg = new Intent(Battleship.this, Endgame.class);
			Battleship.this.startActivity(endg);
			break;
		case R.id.quit:
			this.finish();
			break;
		}		
	}
}