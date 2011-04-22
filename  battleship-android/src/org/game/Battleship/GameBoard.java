package org.game.Battleship;
 
import android.app.Activity;
import android.graphics.Point;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
 
public class GameBoard extends Activity implements OnClickListener{
	Button buttonEnd, buttonDeploy;
	Grid grid;
	FrameLayout frame; 
	Aiplayer ai;
	int[][] aigrid, playergrid;
	private Point p;
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        p = new Point();
        ai = new Testai();
        aigrid = ai.aiGrid();
        frame.addView(grid);        
        grid.requestFocus();        
    }
    
	public void onClick(View src) {
		switch(src.getId())
		{
		case R.id.Deploy:
			playergrid = grid.getPlayerGrid();
	        buttonEnd.setEnabled(true);
			grid.setDeploy_phase(false);
			grid.invalidate();
	        grid.requestFocus();        
	        break;
		case R.id.Turn:			
			grid.updateaigrid(aigrid);
			updateaigrid();
			p = ai.attackCell();
			grid.updateplayergrid(p);			
			grid.invalidate();
	        grid.requestFocus();        
	        break;
		}		
	}

	private void updateaigrid() {
		// TODO Auto-generated method stub
		
	}

}
