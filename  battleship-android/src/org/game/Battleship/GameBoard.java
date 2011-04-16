package org.game.Battleship;
 
import android.app.Activity;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard);
        buttonEnd = (Button)findViewById(R.id.Turn);
        buttonDeploy = (Button)findViewById(R.id.Deploy);
        buttonEnd.setOnClickListener(this);
        buttonEnd.setEnabled(false);
        buttonDeploy.setEnabled(false);
        buttonDeploy.setOnClickListener(this);
        frame =	(FrameLayout)findViewById(R.id.main_view1);
        grid = new Grid(this);
        frame.addView(grid);        
//        setContentView(grid);
        grid.requestFocus();        
    }
    
	public void onClick(View src) {
		switch(src.getId()){
		case R.id.Deploy:
	       buttonEnd.setEnabled(true);
	       break;
		}		
	}

}
