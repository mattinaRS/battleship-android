package org.game.Battleship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Battleship extends Activity implements OnClickListener {
	
	Button buttonPlay, buttonHiscore, buttonQuit;
	TextView testText;
	SaveScore score = new SaveScore();
	SaveScore sc = new SaveScore();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        buttonPlay = (Button)findViewById(R.id.play);
        buttonHiscore = (Button)findViewById(R.id.hiscore);
        buttonQuit = (Button)findViewById(R.id.quit);
        testText = (TextView)findViewById(R.id.testText);
        
        buttonPlay.setOnClickListener(this);
        buttonHiscore.setOnClickListener(this);
        buttonQuit.setOnClickListener(this);
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
//			testText.setText("You Pressed Play");
			break;
		case R.id.hiscore:
			Intent hiscore = new Intent(Battleship.this, HighScore.class);
			Battleship.this.startActivity(hiscore);
			break;
		case R.id.quit:
			testText.setText("You Pressed Quit");
			this.finish();
			break;
		}		
	}
}