package org.game.Battleship;
 
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
 
public class GameBoard extends Activity implements OnClickListener
{
	private static final String TAG = new String("GameBoard");
	AlertDialog.Builder alert;	
	Button buttonEnd, buttonDeploy, buttonMenu, diff;
	RadioGroup rg;
	RadioButton rb1, rb2;
	Grid grid;
	FrameLayout frame; 
	AbstractAI ai;
	int numshipsai, numshipspl;
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
    	alert = new AlertDialog.Builder(this);	
    	turns = 0;
        numshipsai = numshipspl = 5;
        buttonEnd = (Button)findViewById(R.id.Turn);
        buttonDeploy = (Button)findViewById(R.id.Deploy);
        buttonMenu = (Button)findViewById(R.id.MM);
        diff = (Button)findViewById(R.id.Diff);
        rb1 = (RadioButton)findViewById(R.id.RB1);
        rb2 = (RadioButton)findViewById(R.id.RB1);
        rg = (RadioGroup)findViewById(R.id.RG);
        buttonEnd.setOnClickListener(this);
        buttonEnd.setEnabled(false);
        buttonDeploy.setOnClickListener(this);
        buttonMenu.setOnClickListener(this);
        diff.setOnClickListener(this);
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
        frame.addView(grid);        
//        grid.requestFocus();        
    }
    
    
    
    public Button getButtonEnd() {
		return buttonEnd;
	}


	public void onClick(View src) {
		switch(src.getId())
		{
		case R.id.Diff:
			switch (rg.getCheckedRadioButtonId())
			{
				case R.id.RB1:
			        ai = new aiPlayerEasy();
			        aigrid = ai.aiGrid();
					break;
				case R.id.RB2:
			        ai = new aiPlayer();
			        aigrid = ai.aiGrid();
					break;				
			}
			rb1.setEnabled(false);
			diff.setEnabled(false);
			buttonDeploy.setEnabled(true);
			break;
		case R.id.MM:
			Intent myIntent = new Intent(GameBoard.this, Battleship.class);
			GameBoard.this.startActivity(myIntent);
			this.finish();
			break;
		case R.id.Deploy:
	        Log.d(TAG, "Deployment ends");
	        playergrid = grid.getPgrid();
	        if(validateDeployment())
	        {
		        buttonDeploy.setEnabled(false);
		        grid.setAigrid(aigrid);	        
		        Log.d("aigrid", gridtoString(aigrid, 10, 10));
				grid.setDeploy_phase(false);
				grid.invalidate();
	        }
	        else
	        {
				alert.setTitle("Invalid Deployment !");
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
				alert.show();						        	
	        }
//	        grid.requestFocus();        
	        break;
		case R.id.Turn:		
	        Log.d(TAG, "Turn ends");
	        buttonEnd.setEnabled(false);
	        turns++;
			plCell = grid.updateaigrid(aigrid);
			updateAigrid(plCell);
			aiCell = ai.aiAttack();
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
			grid.invalidate();
//	        grid.requestFocus();        
	        break;
		}		
	}

	private boolean validateDeployment() 
	{
		int cg, cb, cc, cs, cd;
		cg = cb = cc = cs = cd = 0;
		for(int i =0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				if(playergrid[i][j] == 1)
				{
					cc++;
				}
				else if(playergrid[i][j] == 2)
				{
					cg++;
				}
				else if(playergrid[i][j] == 3)
				{
					cd++;
				}
				else if(playergrid[i][j] == 4)
				{
					cs++;
				}
				else if(playergrid[i][j] == 5)
				{
					cb++;
				}
			}
		}
		if(cc != pc || cg != pg || cd != pd || cs != ps || cb != pb )
		{
			return false;
		}
		else
		{
			return true;			
		}
	}



	public void updatePlayergrid(Point p) 
	{
		switch (playergrid[p.x][p.y])
		{
			case 1:
				pc--;
				if(pc == 0)
				{
					alert.setTitle("YOUR CARRIER WAS DESTROYED!");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						}
					});
					alert.show();
				}
				break;
			case 2:
				pg--;
				if(pg == 0)
				{
					alert.setTitle("YOUR GUNBOAT WAS DESTROYED!");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						}
					});
					alert.show();
				}
				break;
			case 3:
				ps--;
				if(ps == 0)
				{
					alert.setTitle("YOUR SUBMARINE WAS DESTROYED!");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						}
					});
					alert.show();
				}
				break;
			case 4:
				pd--;
				if(pd == 0)
				{
					alert.setTitle("YOUR DESTROYER WAS DESTROYED!");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						}
					});
					alert.show();
				}
				break;
			case 5:
				pb--;
				if(pb == 0)
				{
					alert.setTitle("YOUR BATTLESHIP WAS DESTROYED!");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						}
					});
					alert.show();
				}
				break;
		}
//		Log.d("sub", Integer.toString(ps));
//		Log.d("gb", Integer.toString(pg));
//		Log.d("carr", Integer.toString(pc));
//		Log.d("des", Integer.toString(pd));
//		Log.d("bs", Integer.toString(pb));
		if(pg == 0 && pb == 0 && pd == 0 && ps == 0 && pc == 0)
		{
			endgame = true;
		}
		if(endgame == true)
		{
	        Log.d(TAG, "Game ends");
			Intent endg = new Intent(GameBoard.this, Endgame.class);
			endg.putExtra("Win", "You Win");
			endg.putExtra("turns", turns);
			endg.putExtra("aiships", numshipsai);
			endg.putExtra("aiships", numshipspl);			
			GameBoard.this.startActivity(endg);			
			GameBoard.this.finish();
		}
	}

	private void updateAigrid(Point p) 
	{
		switch (aigrid[p.x][p.y])
		{
			case 2:
				aig--;
				if(aig == 0)
				{
					alert.setTitle("YOU DESTROYED A GUNBOAT !");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						}
					});
					alert.show();					
				}
				break;
			case 3:
				aid--;
				if(aid == 0)
				{
					alert.setTitle("YOU DESTROYED A DESTROYER!");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						}
					});
					alert.show();
				}
				break;
			case 6:
				ais--;
				if(ais == 0)
				{
					alert.setTitle("YOU DESTROYED A SUBMARINE!");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						}
					});
					alert.show();
				}
				break;
			case 4:
				aib--;
				if(aib == 0)
				{
					alert.setTitle("YOU DESTROYED A BATTLESHIP!");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						}
					});
					alert.show();
				}
				break;
			case 5:
				aic--;
				if(aic == 0)
				{
					alert.setTitle("YOU DESTROYED A CARRIER!");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						}
					});
					alert.show();
				}
				break;
			}
		
//		Log.d("sub", Integer.toString(ais));
//		Log.d("gb", Integer.toString(aig));
//		Log.d("carr", Integer.toString(aic));
//		Log.d("des", Integer.toString(aid));
//		Log.d("bs", Integer.toString(aib));
		if(aig == 0 && aib == 0 && aid == 0 && ais == 0 && aic == 0)
		{
			endgame = true;
		}
		if(endgame == true)
		{
	        Log.d(TAG, "Game ends");
			Intent endg = new Intent(GameBoard.this, Endgame.class);
			endg.putExtra("Win", "You Lose");
			endg.putExtra("turns", turns);
			endg.putExtra("aiships", numshipsai);
			endg.putExtra("aiships", numshipspl);			
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
