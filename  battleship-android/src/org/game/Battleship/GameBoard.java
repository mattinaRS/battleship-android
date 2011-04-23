package org.game.Battleship;
 
import java.util.Arrays;

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
        aigrid = ai.aiGrid();
        frame.addView(grid);        
        grid.requestFocus();        
    }
    
	public void onClick(View src) {
		switch(src.getId())
		{
		case R.id.Deploy:
	        Log.d(TAG, "Deployment ends");
			playergrid = grid.getPlayerGrid();
	        buttonEnd.setEnabled(true);
	        playergrid = grid.getPgrid();
	        grid.setAigrid(aigrid);	        
	        Log.d("aigrid", gridtoString(aigrid, 10, 10));
			grid.setDeploy_phase(false);
			grid.invalidate();
	        grid.requestFocus();        
	        break;
		case R.id.Turn:			
	        Log.d(TAG, "Turn ends");
			plCell = grid.updateaigrid(aigrid);
			updateAigrid(plCell);
			Log.d(TAG, "before calling AI attack");
			aiCell = ai.aiAttack();
			Log.d("aicell", Integer.toString(aiCell.x));
			Log.d("aicell", Integer.toString(aiCell.y));
			Log.d(TAG, "after calling AI attack");
			if(playergrid[aiCell.x][aiCell.y] ==0)
			{
				ai.isHit(false);				
			}
			else
			{
				ai.isHit(true);
			}
			grid.updateUIonattk(aiCell);			
	        Log.d(TAG, "Update UI called");
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

	private String gridtoString(int[][] arr, int r, int c)
	{
		String str = new String();
		for(int i=0; i<r; i++)
		{
			for(int j =0; j<c; j++)
			{
				str += Integer.toString(arr[i][j]);
				str += " ";
			}
			str += "\n";				
		}
		return str;
	}
	
}
