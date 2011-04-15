package org.game.Battleship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
    	db.insertScore("SIf", 100);
       	db.insertScore("ABg", 12);
    	db.insertScore("XYh", 24);
       	db.insertScore("DCk", 89);
       	db.insertScore("DCkr", 89);
       	
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