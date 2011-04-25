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
	int turns;
	int aig, aic, aid, ais, aib;
	int pg, pc, pd, ps, pb;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard);
        turns = 0;
        buttonEnd = (Button)findViewById(R.id.Turn);
        buttonDeploy = (Button)findViewById(R.id.Deploy);
        buttonEnd.setOnClickListener(this);
        buttonEnd.setEnabled(false);
        buttonDeploy.setOnClickListener(this);
        frame =	(FrameLayout)findViewById(R.id.main_view1);
        aig =  pg =2;
        aic = pc = 5;
        aid = pd = 3;
        ais = ps = 3;
        aib = pb = 4;
        grid = new Grid(this);
        aigrid = new int[10][10];
        playergrid = new int[10][10];
        aiCell = new Point(4,4);
        plCell = new Point();
        ai = new aiPlayer();
        aigrid = ai.aiGrid();
        frame.addView(grid);        
//        grid.requestFocus();        
    }
    
	public void onClick(View src) {
		switch(src.getId())
		{
		case R.id.Deploy:
	        Log.d(TAG, "Deployment ends");
	        buttonEnd.setEnabled(true);
	        playergrid = grid.getPgrid();
	        grid.setAigrid(aigrid);	        
	        Log.d("aigrid", gridtoString(aigrid, 10, 10));
			grid.setDeploy_phase(false);
			grid.invalidate();
//	        grid.requestFocus();        
	        break;
		case R.id.Turn:			
	        Log.d(TAG, "Turn ends");
	        turns++;
			plCell = grid.updateaigrid(aigrid);
//			Log.d("Plgrid", gridtoString(playergrid, 10, 10));
			updateAigrid(plCell);
			Log.d("Pl point", plCell.toString());
			aiCell = ai.aiAttack();
			Log.d("ai x", Integer.toString(aiCell.x));
			Log.d("ai y", Integer.toString(aiCell.y));
			updatePlayergrid(aiCell);
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
//	        grid.requestFocus();        
	        break;
		}		
	}

	public void updatePlayergrid(Point p) 
	{
		switch (playergrid[p.x][p.y])
		{
			case 2:
				pg--;
				break;
			case 3:
				pd--;
				break;
			case 32:
				ps--;
				break;
			case 4:
				pb--;
				break;
			case 5:
				pc--;
				break;
		}
		if(pg == 0 && pb == 0 && pd == 0 && ps == 0 && pc == 0)
		{
			endgame = true;
		}
		if(endgame == true)
		{
			grid.requestFocus();
	        Log.d(TAG, "Game ends");
			Intent endg = new Intent(GameBoard.this, Endgame.class);
			GameBoard.this.startActivity(endg);			
		}
	}

	private void updateAigrid(Point p) 
	{
		switch (aigrid[p.x][p.y])
		{
			case 2:
				aig--;
				break;
			case 3:
				aid--;
				break;
			case 6:
				ais--;
				break;
			case 4:
				aib--;
				break;
			case 5:
				aic--;
				break;
		}
		Log.d("sub", Integer.toString(ais));
		Log.d("gb", Integer.toString(aig));
		Log.d("carr", Integer.toString(aic));
		Log.d("des", Integer.toString(aid));
		Log.d("bs", Integer.toString(aib));
		if(aig == 0 && aib == 0 && aid == 0 && ais == 0 && aic == 0)
		{
			endgame = true;
		}
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
