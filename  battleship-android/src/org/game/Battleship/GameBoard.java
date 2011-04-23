package org.game.Battleship;
 
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
 
public class GameBoard extends Activity implements OnClickListener
{
	private static final String TAG = new String("GameBoard");
	Button buttonEnd, buttonDeploy;
	Grid grid;
	FrameLayout frame; 
	AbstractAI ai;
	int[][] aigrid, playergrid;
	private Point aiCell, plCell;
	boolean endgame = false;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard);
        buttonEnd = (Button)findViewById(R.id.Turn);
        buttonDeploy = (Button)findViewById(R.id.Deploy);
        buttonEnd.setOnClickListener(this);
        buttonEnd.setEnabled(false);
        buttonDeploy.setOnClickListener(this);
        frame =	(FrameLayout)findViewById(R.id.main_view1);
        grid = new Grid(this);
        aigrid = new int[10][10];
        playergrid = new int[10][10];
        aiCell = new Point(4,4);
        plCell = new Point();
        ai = new aiPlayer();
        Log.d(TAG, "before grid creation");
//        aigrid = ai.aiGrid();
        Log.d(TAG, "after grid creation");
        frame.addView(grid);        
        grid.requestFocus();        
        Log.d(TAG, "gameboard launched");
    }
    
	public void onClick(View src) {
		switch(src.getId())
		{
		case R.id.Deploy:
//	        Log.d(TAG, "Deployment ends");
			playergrid = grid.getPlayerGrid();
	        buttonEnd.setEnabled(true);
	        playergrid = grid.getPgrid();
//	        grid.setAigrid(aigrid);
//	        Log.d(TAG, playergrid.toString());
			grid.setDeploy_phase(false);
			grid.invalidate();
	        grid.requestFocus();        
	        break;
		case R.id.Turn:			
//	        Log.d(TAG, "Turn ends");
			plCell = grid.updateaigrid(aigrid);
			updateAigrid(plCell);
			aiCell = ai.aiAttack();
			grid.updateUIonattk(aiCell);			
			updatePlayergrid(aiCell);
			grid.invalidate();
	        grid.requestFocus();        
	        break;
		}		
	}

	public void updatePlayergrid(Point p) 
	{
		// TODO Auto-generated method stub
		//endgame = true;
		if(endgame == true)
		{
	        Log.d(TAG, "Game ends");
			Intent endg = new Intent(GameBoard.this, Endgame.class);
			GameBoard.this.startActivity(endg);			
		}
	}

	private void updateAigrid(Point p) 
	{
		// TODO Auto-generated method stub
		if(endgame == true)
		{
	        Log.d(TAG, "Game ends");
			Intent endg = new Intent(GameBoard.this, Endgame.class);
			GameBoard.this.startActivity(endg);			
		}		
	}

	
}
