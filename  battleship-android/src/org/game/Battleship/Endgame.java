package org.game.Battleship;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class Endgame extends Activity 
{	
	int sc;
	String res = new String();
	TextView textEnd, textScore;
	ComputeScore cs;
	SaveScore sScore;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {    	
    	Log.d("Endg", "create");
    	res = "You Lose";
    	sScore = new SaveScore();
    	cs = new ComputeScore();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame);
        textEnd = (TextView)findViewById(R.id.res);
        textScore = (TextView)findViewById(R.id.sc);
        textEnd.setText(res);
        sc = cs.compute(10, 3, 5);
        textScore.setText("Your Score = " + Integer.toString(sc));           
        sScore.saveScore(sc);
    }	
}
